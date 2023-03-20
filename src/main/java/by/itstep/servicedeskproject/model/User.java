package by.itstep.servicedeskproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String lastname;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private String position;

    @Column(nullable=false)
    private String department;
    @Column(nullable=false, unique=true)
    private String login;
    @Column(nullable=false)
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID")})
    private Set<Role> roles=new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

}