package by.itstep.servicedeskproject.service;

import by.itstep.servicedeskproject.model.AppealRevision;
import by.itstep.servicedeskproject.repository.AppealRevisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class AppealRevisionServiceImpl implements AppealRevisionService{

    private AppealRevisionRepository repository;

    public AppealRevisionServiceImpl(AppealRevisionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AppealRevision> getAllAppealRevision() {
        return repository.findAll();
    }
}