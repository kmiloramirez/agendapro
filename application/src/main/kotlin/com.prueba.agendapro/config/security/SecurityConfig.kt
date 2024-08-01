package com.prueba.agendapro.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(HttpMethod.GET, "/v1/products/**").permitAll()
                    .requestMatchers("v1/category/**").hasRole("ADMIN")

                requests.anyRequest().authenticated()
            }
            .httpBasic { }
            .csrf { csrf -> csrf.disable() }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
        return http.build()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun userDetailsService(): UserDetailsService {
        val userBuilder = User.builder()
        val user = userBuilder
            .username("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER")
            .build()
        val admin = userBuilder
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }
}
