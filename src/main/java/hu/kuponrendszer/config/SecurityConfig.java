package hu.kuponrendszer.config;
import hu.kuponrendszer.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(UserRepository repository){return email->repository.findByEmail(email.toLowerCase().trim())
  .map(user->User.withUsername(user.getEmail()).password(user.getPasswordHash()).roles(user.getRole()).build())
  .orElseThrow(()->new UsernameNotFoundException("Ismeretlen felhasználó"));}
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
  http.authorizeHttpRequests(auth->auth
   .requestMatchers("/","/login","/register","/css/**","/js/**","/favicon.ico","/error").permitAll()
   .requestMatchers("/h2-console/**").permitAll() // csak helyi fejlesztéshez
   .requestMatchers("/admin/**").hasRole("ADMIN")
   .requestMatchers(HttpMethod.GET,"/coupons","/coupons/activated").hasRole("USER")
   .requestMatchers(HttpMethod.POST,"/coupons/*/activate").hasRole("USER")
   .requestMatchers("/coupons/**").denyAll()
   .anyRequest().authenticated())
   .formLogin(form->form.loginPage("/login").successHandler((req,resp,authentication)->{
    boolean admin=authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));
    resp.sendRedirect(req.getContextPath()+(admin?"/admin/coupons":"/coupons"));
   }).permitAll())
   .logout(logout->logout.logoutSuccessUrl("/login?logout").permitAll())
   .csrf(csrf->csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
   .headers(headers->headers.frameOptions(frame->frame.sameOrigin()));
  return http.build();
 }
}
