package ssafy.closetoyou.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsUtils;
import ssafy.closetoyou.global.jwt.component.CustomAuthenticationEntryPoint;
import ssafy.closetoyou.global.jwt.filter.JwtAuthenticationProcessingFilter;
import ssafy.closetoyou.global.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import ssafy.closetoyou.global.oauth.handler.OAuth2LoginFailureHandler;
import ssafy.closetoyou.global.oauth.handler.OAuth2LoginSuccessHandler;
import ssafy.closetoyou.global.oauth.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter;
    private final JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable().cors().and().csrf().disable()
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 무상태 세션 관리
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/api/v1/maillog/**").permitAll()
                        .requestMatchers("/api/v1/user/normal/sign-up").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(c -> c.userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                )
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter, LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter, CustomJsonUsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint); // 인증 실패 시 401 에러 반환
        return http.build();
    }
}
