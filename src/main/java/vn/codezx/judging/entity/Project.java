package vn.codezx.judging.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_name", nullable = false, length = 50)
    private String groupName;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "client", nullable = false, length = 20)
    private String client;

    // Quan hệ với Event
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    @Column(name = "description", length = 512)
    private String description;
}
