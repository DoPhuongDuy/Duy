package vn.codezx.judging.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.codezx.judging.components.JwtTokenFilter;
import vn.codezx.judging.entity.Role;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(request -> {
                     request.requestMatchers(
                                    String.format("%s/users/register",apiPrefix),
                                     String.format("%s/users/login",apiPrefix),
                                     String.format("%s/mapping-package",apiPrefix)
                             )
                             .permitAll()
                             //ADMIN
                             .requestMatchers(POST,
                                     String.format("%s/Admin/package-plans",apiPrefix),
                                     String.format("%s/categories",apiPrefix),
                                     String.format("%s/projects",apiPrefix),
                                     String.format("%s/projects/uploadCSV",apiPrefix),
                                     String.format("%s/events",apiPrefix),
                                     String.format("%s/criteria-packs",apiPrefix),
                                     String.format("%s/criteria",apiPrefix)
                             ).hasRole(Role.ADMIN)

                             .requestMatchers(GET,
                                     String.format("%s/Admin/package-plans",apiPrefix),
                                     String.format("%s/Admin/package-plans/{id}",apiPrefix),
                                     String.format("%s/users/{id}",apiPrefix),
                                     String.format("%s/events**",apiPrefix),
                                     String.format("%s/categories**",apiPrefix),
                                     String.format("%s/projects",apiPrefix),
                                     String.format("%s/projects**",apiPrefix),
                                     String.format("%s/criteria-packs/{id}",apiPrefix),
                                     String.format("%s/criteria-packs/event/{id}",apiPrefix),
                                     String.format("%s/criteria/{id}",apiPrefix),
                                     String.format("%s/criteria",apiPrefix)
                                     ).hasRole(Role.ADMIN)

                             .requestMatchers(PUT,
                                     String.format("%s/Admin/package-plans/{id}",apiPrefix),
                                     String.format("%s/mapping-package/{id}",apiPrefix),
                                     String.format("%s/categories/{id}",apiPrefix),
                                     String.format("%s/projects/{id}",apiPrefix),
                                     String.format("%s/criteria-packs/{id}",apiPrefix),
                                     String.format("%s/criteria/{id}",apiPrefix),
                                     String.format("%s/criteria/mapping/{id}",apiPrefix)
                                     ).hasRole(Role.ADMIN)
                             .requestMatchers(DELETE,
                                     String.format("%s/Admin/package-plans/{id}",apiPrefix),
                                     String.format("%s/categories/{id}",apiPrefix),
                                     String.format("%s/projects/{id}",apiPrefix),
                                     String.format("%s/criteria-packs/{id}",apiPrefix),
                                     String.format("%s/criteria/{id}",apiPrefix)
                                     ).hasRole(Role.ADMIN)
                             //Organizer
                             //User
                             .requestMatchers(GET,
                                     String.format("%s/roles/**",apiPrefix)).hasRole(Role.USER)


                             .anyRequest().authenticated();
                 })
                 .csrf(AbstractHttpConfigurer::disable);
         http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
             @Override
             public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                 //Đồng ý tất ai cũng cho phép
                 CorsConfiguration configuration = new CorsConfiguration();
                 configuration.setAllowedOrigins(List.of("*"));
                 configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                 configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                 configuration.setExposedHeaders(List.of("x-auth-token"));
                 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                 source.registerCorsConfiguration("/**", configuration);
                 httpSecurityCorsConfigurer.configurationSource(source);
             }
         });
         return http.build();
    }
}
