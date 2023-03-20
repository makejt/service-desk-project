package by.itstep.servicedeskproject.service;

import by.itstep.servicedeskproject.model.Role;
import by.itstep.servicedeskproject.model.User;
import by.itstep.servicedeskproject.repository.RoleRepository;
import by.itstep.servicedeskproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getById(int id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new RuntimeException("User don't exist by ID = " + id);
        }
        return user;
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User findByLogin(String login) {
        List<User> allUsers = userRepository.findAll();
        for (User user: allUsers) {
            if(user.getLogin()==login){
                return user;
            }
        }
        return null;
    }


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(int id, User user) {
        User updUser = getById(id);
        updUser.setName(user.getName());
        updUser.setLastname(user.getLastname());
        updUser.setDepartment(user.getDepartment());
        updUser.setLogin(user.getLogin());
        updUser.setEmail(user.getEmail());
        updUser.setPassword(user.getPassword());
        updUser.setPhone(user.getPhone());
        updUser.setPosition(user.getPosition());
        return updUser;
    }

    @Override
    public Page<User> pagination(int page, int size, String sortByField, String sortDir) {
        return null;
    }


}