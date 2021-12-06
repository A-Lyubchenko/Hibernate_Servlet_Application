package ua.lyubchenko.domains;

import lombok.*;
import ua.lyubchenko.repositories.Identity;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "skills")
public class Skill implements Identity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department")
    private String department;

    @Column(name = "level")
    private String level;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "developer_skill",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    @ToString.Exclude

    private List<Developer> developers;

}
