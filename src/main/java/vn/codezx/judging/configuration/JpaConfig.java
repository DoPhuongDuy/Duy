package vn.codezx.judging.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // Kích hoạt JPA Auditing
public class JpaConfig {
    // Cấu hình JPA Auditing cho toàn bộ ứng dụng
}
