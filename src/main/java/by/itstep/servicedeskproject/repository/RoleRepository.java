package by.itstep.servicedeskproject.repository;

import by.itstep.servicedeskproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {


}