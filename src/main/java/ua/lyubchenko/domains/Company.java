package ua.lyubchenko.domains;

import lombok.*;
import ua.lyubchenko.repositories.Identity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "companies")
public class Company implements Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "developer_companie",
            joinColumns = @JoinColumn(name = "companie_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    @ToString.Exclude

    private List<Developer> developers;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "companie_project",
            joinColumns = @JoinColumn(name = "companie_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @ToString.Exclude

    private List<Project> projects;

}
