package by.itstep.servicedeskproject.repository;

import by.itstep.servicedeskproject.model.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
}