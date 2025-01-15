package vn.codezx.judging.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "package_plan")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackagePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "plan_name", nullable = false, unique = true, length = 50)
    private String planName;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "max_events", nullable = false)
    private int maxEvents;

    @Column(name = "max_projects", nullable = false)
    private int maxProjects;

    @Column(name = "max_judges", nullable = false)
    private int maxJudges;

    @Column(name = "max_event_duration", nullable = false)
    private int maxEventDuration;
}

