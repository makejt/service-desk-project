package by.itstep.servicedeskproject.repository;

import by.itstep.servicedeskproject.model.AppealRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRevisionRepository extends JpaRepository<AppealRevision, Long> {
}
