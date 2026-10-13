package com.example.project3.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {


    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http

                .csrf( csrf -> csrf.disable()  )

                .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/user/register",
                                "/api/v1/customer/register"
                        ).permitAll()

                        .requestMatchers(
                                "/api/v1/user/admin/register/admin",
                                "/api/v1/user/show/users",
                                "/api/v1/employee/register",
                                "/api/v1/employee/admin/edit/{empId}",
                                "/api/v1/employee/admin/delete/{empId}"
                        ).hasAuthority("ADMIN")

                        .requestMatchers(
                                "/api/v1/employee/show/info",
                                "/api/v1/employee/edit",
                                "/api/v1/account/activate/{accountId}",
                                "/api/v1/account/block/{accountId}"
                        ).hasAuthority("EMPLOYEE")

                        .requestMatchers(
                                "/api/v1/customer/edit",
                                "/api/v1/customer/delete",
                                "/api/v1/customer/show/info",
                                "/api/v1/account/show/accounts",
                                "/api/v1/account/create/account",
                                "/api/v1/account/show/account/{accountId}",
                                "/api/v1/account/deposit/{accountId}",
                                "/api/v1/account/withdraw/{accountId}",
                                "/api/v1/account/transfer/{accountId}"
                        ).hasAuthority("CUSTOMER")

                        .anyRequest().authenticated()
                )

                .logout(logout -> logout .logoutUrl("/api/v1/user/logout").invalidateHttpSession(true))

                .httpBasic( basic -> {})

                .build();
    }

}
