package pl.mbalcer.enrollmentsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.mbalcer.enrollmentsystem.security.jwt.AuthEntryPointJwt;
import pl.mbalcer.enrollmentsystem.security.jwt.AuthTokenFilter;
import pl.mbalcer.enrollmentsystem.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/changePassword").authenticated()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/faculty/blocked/**").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/faculty/**").authenticated()
                .antMatchers("/api/faculty/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/fieldOfStudy/**").authenticated()
                .antMatchers("/api/fieldOfStudy/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/news/**").authenticated()
                .antMatchers("/api/news/**").hasAuthority("ADMIN")
                .antMatchers("/api/pdf/**").hasAuthority("TEACHER")
                .antMatchers("/api/roles/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/student/**").authenticated()
                .antMatchers("/api/student/**").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/subject/**").authenticated()
                .antMatchers("/api/subject/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/subjectGroup/requests").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/subjectGroup/byTeacher/**").hasAuthority("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/subjectGroup/registration/**").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/subjectGroup/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/subjectGroup/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/subjectGroup/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/subjectGroup/add/**").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.PUT, "/api/subjectGroup/remove/**").hasAuthority("STUDENT")
                .antMatchers(HttpMethod.PUT, "/api/subjectGroup/type/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/subjectGroup/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/teacher/**").authenticated()
                .antMatchers("/api/teacher/**").hasAuthority("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/users/role/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/**").authenticated()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
