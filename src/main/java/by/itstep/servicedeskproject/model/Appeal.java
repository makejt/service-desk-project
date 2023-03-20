package by.itstep.servicedeskproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appeals")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String theme;
    @Column
    private String description;
    @Column
    private String status;
    @Column
    private Timestamp created;
    @Column
    private Timestamp last_updated;

    @Column
    private String user;

    @Column
    private String manager;

    // исполнитель заявки
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User manager;
    @Column
    private Integer section;


    public Appeal(String theme, String description) {
        this.theme = theme;
        this.description = description;

    }

    public enum StatusType {
        NEW ("new"),
        IN_WORK ("in work"),
        RESOLVED ("resolved");
        private String name;
        StatusType(String name) {
            this.name = name;
        }
    }
}
