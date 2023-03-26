package by.itstep.servicedeskproject.model;

import by.itstep.servicedeskproject.util.CustomRevisionListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@AuditTable("appeals_audit")
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
    @NotAudited
    @Column
    private Timestamp created;
    @NotAudited
    @Column
    private Timestamp last_updated;

    @Column
    private String user;
    @Column
    private String manager;

    @NotAudited
    @Column
    private Integer section;

    public Appeal(String theme, String description) {
        this.theme = theme;
        this.description = description;

    }
}
