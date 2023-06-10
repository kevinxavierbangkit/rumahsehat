package apap.tugaskelompok.rumahsehat.security;

import apap.tugaskelompok.rumahsehat.config.jwt.JwtAuthenticationEntryPoint;
import apap.tugaskelompok.rumahsehat.config.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Configuration
  @Order(1)
  public static class RestApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      // configure AuthenticationManager so that it knows from where to load
      // user for matching credentials
      // Use BCryptPasswordEncoder
      auth.userDetailsService(jwtUserDetailsService).passwordEncoder(encoder);
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

//    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      // We don't need CSRF for this example
      httpSecurity.csrf().disable()
              .antMatcher("/api/v1/**").cors().and()
              // dont authenticate this particular request
              .authorizeRequests().antMatchers("/api/v1/auth/login", "/api/v1/register").permitAll().
              // all other requests need to be authenticated
              and().
              // make sure we use stateless session; session won't be used to
              // store user's state.
                      exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      // Add a filter to validate the tokens with every request
      httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }

  @Configuration
  @Order(2)
  public static class UILoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .authorizeRequests()
              .antMatchers("/css/**").permitAll()
              .antMatchers("/js/**").permitAll()
              .antMatchers("/login-sso", "/validate-ticket").permitAll()
              .antMatchers("/manajemen/**", "/tagihan-chart/**").hasAuthority("Admin")
              .antMatchers("/obat/update/**").hasAuthority("Apoteker")
              .antMatchers("/resep/create").hasAnyAuthority("Dokter","Admin")
              .antMatchers("/obat/viewall").hasAnyAuthority("Admin", "Apoteker")
              .antMatchers("/resep/").hasAnyAuthority("Dokter","Admin")
              .antMatchers("/appointment/viewall").hasAnyAuthority("Dokter","Admin")
              .antMatchers("/api/**").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login").permitAll()
              .and()
              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login").permitAll()
              .and()
              .sessionManagement().sessionFixation().newSession().maximumSessions(1);
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
  }
}