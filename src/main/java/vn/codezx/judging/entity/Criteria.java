package vn.codezx.judging.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "criteria")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String criteriaName;

    private String description;

    private int weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

