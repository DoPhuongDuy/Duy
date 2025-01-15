package vn.codezx.judging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "role")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name", nullable = false, length = 50, unique = true)
    private String roleName;

    @Column(name = "description", length = 256)
    private String description;

    // Thiết lập mối quan hệ ManyToMany với User
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public static String ADMIN = "ADMIN";
    public static String ORGANIZER = "ORGANIZER";
    public static String USER = "USER";
}

