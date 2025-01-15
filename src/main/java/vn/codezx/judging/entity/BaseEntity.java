package vn.codezx.judging.entity;

import jakarta.persistence.Column;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // Để sử dụng auditing
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modified_by;
}



