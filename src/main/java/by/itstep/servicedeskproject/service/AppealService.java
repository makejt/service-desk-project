package by.itstep.servicedeskproject.service;

import by.itstep.servicedeskproject.model.Appeal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppealService {
    List<Appeal> getAllAppeals();
    List<Appeal> getUserAppeals(int user_id);
    List<Appeal> getSectionAppeals(int section);
    Appeal getById(long id);
    void saveAppeal(Appeal appeal);
    void deleteAppealById(long id);
    Appeal update (long id, Appeal appeal);
    Page<Appeal> pagination(int page, int size, String sortByField, String sortDir);
}