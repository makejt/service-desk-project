package by.itstep.servicedeskproject.util;
import by.itstep.servicedeskproject.model.AppealRevision;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

@Controller
public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        AppealRevision entity = (AppealRevision) revisionEntity;
        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        entity.setUserName(userName);
    }
}