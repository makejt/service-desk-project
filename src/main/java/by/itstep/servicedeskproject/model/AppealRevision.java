package by.itstep.servicedeskproject.model;

import by.itstep.servicedeskproject.util.CustomRevisionListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "revinfo")
@RevisionEntity(CustomRevisionListener.class)
public class AppealRevision implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue
    @RevisionNumber
    @Column(name = "rev")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    @Column (name = "revtstmp")
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
