package apap.tugaskelompok.rumahsehat.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apap.tugaskelompok.rumahsehat.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {
    if (request.getServletPath().contains("/api/v1/") && (!request.getServletPath().equals("/api/v1/auth/login")) && (!request.getServletPath().equals("/api/v1/pasien/add")) && (!request.getServletPath().equals("/api/v1/manajemen/"))  && (!request.getServletPath().contains("/api/v1/appointment/"))) {

      final String requestTokenHeader = request.getHeader("Authorization");
      final var MESSAGGE = "message";
      final var JSON_TYPE = "application/json";
      String username = null;
      String jwtToken = null;
      Map<String, String> err = new HashMap<>(); // buat nampung error
      // JWT Token is in the form "Bearer token". Remove Bearer word and get
      // only the Token
      if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
        jwtToken = requestTokenHeader.substring(7);
        try {
          username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
          logger.info("Unable to get JWT Token");
          err.put(MESSAGGE, "Unable to get JWT Token");
          response.setContentType(JSON_TYPE);
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          new ObjectMapper().writeValue(response.getOutputStream(), err);

        } catch (ExpiredJwtException e) {
          logger.info("JWT Token has expired");
          err.put(MESSAGGE, "JWT Token has expired");
          response.setContentType(JSON_TYPE);
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
      } else {
        logger.info("JWT Token does not begin with Bearer String");

        err.put(MESSAGGE, "JWT Token does not begin with Bearer String");
        response.setContentType(JSON_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), err);
      }
      // Once we get the token validate it.
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
        // if token is valid configure Spring Security to manually set
        // authentication
        if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
          var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken
                  .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // After setting the Authentication in the context, we specify
          // that the current user is authenticated. So it passes the
          // Spring Security Configurations successfully.
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
