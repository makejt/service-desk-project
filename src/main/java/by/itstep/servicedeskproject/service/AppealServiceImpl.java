package by.itstep.servicedeskproject.service;

import by.itstep.servicedeskproject.model.Appeal;
import by.itstep.servicedeskproject.repository.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppealServiceImpl implements AppealService{
    @Autowired
    private AppealRepository repository;

    @Autowired
    private UserService userService;
    @Override
    public List<Appeal> getAllAppeals() {
        return repository.findAll();
    }

    @Override
    public List<Appeal> getSectionAppeals(int section) {
        List<Appeal> appeals = repository.findAll();
        List<Appeal> sectionAppeals = new ArrayList<>();
        for (Appeal ap: appeals) {
            if (ap.getSection().equals(section)){
                sectionAppeals.add(ap);
            }
        }
        return sectionAppeals;
    }

    @Override
   public List<Appeal> getUserAppeals(int user_id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appeal> listAppeals = repository.findAll();
        List<Appeal> userAppeals = new ArrayList<>();
        for (Appeal ap: listAppeals) {
            if (ap.getUser().equals(userService.findByEmail(user.getUsername()).getName() +
                    " " + userService.findByEmail(user.getUsername()).getLastname())){
                userAppeals.add(ap);
            }
        }
        return userAppeals;
    }



    @Override
    public Appeal getById(long id) {
        Appeal appeal = null;
        Optional<Appeal> optionalAppeal = repository.findById(id);
        if (optionalAppeal.isPresent()) {
            appeal = optionalAppeal.get();
        } else {
            throw new RuntimeException("Appeal don't exist by ID = " + id);
        }
        return appeal;
    }
    @Override
    public void saveAppeal(Appeal appeal) {
        repository.save(appeal);
    }

    @Override
    public void deleteAppealById(long id) {
        repository.deleteById(id);
    }

    @Override
    public Appeal update(long id, Appeal appeal) {
        Appeal updAppeal = repository.getById(id);
        updAppeal.setLast_updated(new Timestamp(System.currentTimeMillis()));
        updAppeal.setStatus("in work");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updAppeal.setManager(userService.findByEmail(user.getUsername()).getName() + " " +
                userService.findByEmail(user.getUsername()).getLastname());
        return updAppeal;
    }

    @Override
    public Page<Appeal> pagination(int page, int size, String sortByField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortByField).ascending() :
                Sort.by(sortByField).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return repository.findAll(pageable);
    }
}
