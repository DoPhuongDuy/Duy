package vn.codezx.judging.entity.AuditEventListener;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Giả sử có một phương thức để lấy tên người dùng hiện tại

        return Optional.ofNullable("current_user");
    }
}

