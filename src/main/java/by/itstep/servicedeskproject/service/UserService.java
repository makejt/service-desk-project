package by.itstep.servicedeskproject.service;

import by.itstep.servicedeskproject.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getById(int id);
    void saveUser(User user);
    void deleteUserById(int id);
    User update (int id, User user);

    User findByEmail(String email);

    User findByLogin(String login);

    Page<User> pagination(int page, int size, String sortByField, String sortDir);
}