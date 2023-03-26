package by.itstep.servicedeskproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="roles")
public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer role_id;
        @Column(nullable=false, unique=true)
        private String name;
        
        @ManyToMany(mappedBy="roles")
        private Set<User> users;

        public Role(Integer role_id, String name) {
                this.role_id = role_id;
                this.name = name;
        }
}