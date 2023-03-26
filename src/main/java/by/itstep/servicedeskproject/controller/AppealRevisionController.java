package by.itstep.servicedeskproject.controller;

import by.itstep.servicedeskproject.model.AppealRevision;
import by.itstep.servicedeskproject.service.AppealRevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppealRevisionController {

    @Autowired
    AppealRevisionService service;

    @GetMapping("/history")
    public String showAllAppealRevisions(Model model){
        List<AppealRevision> revisions = service.getAllAppealRevision();
        model.addAttribute("revisions", revisions);
        return "history";
    }



}
