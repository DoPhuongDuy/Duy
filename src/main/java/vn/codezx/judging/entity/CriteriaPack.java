package vn.codezx.judging.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "criteria_pack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriteriaPack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pack_name", nullable = false, length = 50)
    @NotEmpty(message = "Pack name cannot be empty")
    @Size(max = 50, message = "Pack name must not exceed 50 characters")
    private String packName;

    @Column(name = "description", length = 256)
    @Size(max = 256, message = "Description must not exceed 256 characters")
    private String description;

    @Column(name = "round_number", nullable = false)
    @Min(value = 1, message = "Round number must be greater than or equal to 1")
    private int roundNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @ManyToMany
    @JoinTable(
            name = "criteria_pack_mapping",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "criteria_id")
    )
    private List<Criteria> criteriaList;
}
