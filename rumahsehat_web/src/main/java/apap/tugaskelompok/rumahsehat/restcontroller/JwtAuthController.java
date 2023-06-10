package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.config.jwt.JwtRequest;
import apap.tugaskelompok.rumahsehat.config.jwt.JwtResponse;
import apap.tugaskelompok.rumahsehat.config.jwt.JwtTokenUtil;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.PasienDTO;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.repository.PasienDb;
import apap.tugaskelompok.rumahsehat.security.UserDetailsServiceImpl;
import apap.tugaskelompok.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:51962/")
public class JwtAuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  @Autowired
  private UserService userService;

  @Autowired
  private PasienDb pasienDb;

  @PostMapping(value = "/auth/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    System.out.println("masuk springboot222");
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

    UserModel user = userService.getUserByUsername(authenticationRequest.getUsername());
    PasienModel pasien = pasienDb.findByUuid(user.getUuid());
    PasienDTO userInfo = new PasienDTO(user.getNama(), user.getRole(), user.getUsername(), user.getEmail(),
            pasien.getSaldo(), pasien.getUmur());

    final String token = jwtTokenUtil.generateToken(userDetails);
    System.out.println("masuk springboot");

    return ResponseEntity.ok(new JwtResponse(userInfo, token));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}

