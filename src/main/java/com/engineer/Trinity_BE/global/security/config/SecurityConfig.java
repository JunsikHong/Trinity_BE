package com.engineer.Trinity_BE.global.security.config;

import com.engineer.Trinity_BE.global.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 공개 엔드포인트
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                        // 회원가입: ADMIN만
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").hasRole("ADMIN")

                        // 목록 조회, 상세 조회: 로그인한 모든 역할
                        .requestMatchers(HttpMethod.GET, "/api/repair").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/repair/**").authenticated()

                        // 생성: EDITOR, ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/repair").hasAnyRole("EDITOR", "ADMIN")

                        // 수정: EDITOR, ADMIN (본인 글 여부는 Service에서 검증)
                        .requestMatchers(HttpMethod.PUT, "/api/repair/**").hasAnyRole("EDITOR", "ADMIN")

                        // 삭제: EDITOR, ADMIN (본인 글 여부는 Service에서 검증)
                        .requestMatchers(HttpMethod.DELETE, "/api/repair/**").hasAnyRole("EDITOR", "ADMIN")

                        // 나머지 요청: 로그인 필요
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
