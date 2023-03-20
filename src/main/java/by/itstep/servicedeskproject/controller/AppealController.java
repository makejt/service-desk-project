package by.itstep.servicedeskproject.controller;

import by.itstep.servicedeskproject.model.Appeal;

import by.itstep.servicedeskproject.service.AppealService;
import by.itstep.servicedeskproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Controller

public class AppealController {
    @Autowired
    AppealService service;

    @Autowired
    UserService userService;

    // главная страница, разное отображение для:
    // USER (видит только свои заявки, может создать новую, отредактировать старую),
    // MANAGER (USER + менеджер может работать с заявками, распределнными на его подразделение),
    // ADMIN (USER + MANAGER + админ создает/редактирует/удаляет пользователей)
    @GetMapping("/main")
    public String showMenu(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.findByEmail(user.getUsername()).getId();
        model.addAttribute("user_id", user_id);
        if (user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "main_admin";
        }
        if (user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"))) {
            return "main_manager";
        }
        return "main_user";
    }
    // отображение всех заявок
    @GetMapping("/appeals")
    public String showAllAppeals(Model model){
        return pagination(1, "status", "ASC", model);
    }
    @GetMapping("/appeals/page/{pageNo}")
    public String pagination(@PathVariable(value = "pageNo") int pageNo,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             Model model){
        int pageSize = 15;
        Page<Appeal> page = service.pagination(pageNo, pageSize, sortField, sortDir);
        List<Appeal> listAppeals = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listAppeals", listAppeals);
        return "appeals";
    }

    // добавить новую заявку, выбор секции
    @GetMapping("/add/{section}")
    public String addNewTask(Model model, @PathVariable(value = "section") int section) {
        Appeal appeal = new Appeal();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.findByEmail(user.getUsername()).getId();
        model.addAttribute("user_id", user_id);
        model.addAttribute("appeal", appeal);
        return "form" + section;
    }

    // сохранить новую заявку
    @PostMapping("/save/{section}")
    public String createNewTask(@ModelAttribute("appeal") Appeal appeal, Model model) {
        appeal.setCreated(new Timestamp(System.currentTimeMillis()));
        appeal.setStatus("new");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findByEmail(user.getUsername()).getId();
        appeal.setUser(userService.findByEmail(user.getUsername()).getName() + " " +
               userService.findByEmail(user.getUsername()).getLastname());
        service.saveAppeal(appeal);
        model.addAttribute("id", id);
        model.addAttribute("userAppeals", service.getUserAppeals(id));
        return "user_appeals";
    }

    // просмотр менеджером заявок для работы его подразделения
    @GetMapping("/appeals/department")
    public String showDepatrmentAppeal(Model model) {
        User manager = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appeal> departmentAppeals = new ArrayList<>();
        String managerDepart = userService.findByEmail(manager.getUsername()).getDepartment();
        switch (managerDepart) {
                case "Управление цифрового развития": departmentAppeals = service.getSectionAppeals(5); break;
                case "Отдел поддержки внутренних пользователей": departmentAppeals = service.getSectionAppeals(3); break; // 3.4
                case "Организационный отдел": departmentAppeals = service.getSectionAppeals(1); break; // 1.2
        }
        model.addAttribute("departmentAppeals", departmentAppeals);
        return "department_appeals";
    }

    // менеджер берет заявку в работу, статус "in work"
    @GetMapping("/appeals/work/{id}")
    public String inWork(@PathVariable(value = "id") int id, Model model) {
        Appeal updAppeal = service.getById(id);
        updAppeal.setLast_updated(new Timestamp(System.currentTimeMillis()));
        updAppeal.setStatus("in work");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updAppeal.setManager(userService.findByEmail(user.getUsername()).getName() + " " +
                userService.findByEmail(user.getUsername()).getLastname());
        service.saveAppeal(updAppeal);
        List<Appeal> listAppeals = service.getAllAppeals();
        model.addAttribute("listAppeals", listAppeals);
        return "redirect:/appeals/department";
    }

    // менеджер закрывает заявку, которую разрешил, статус "resolved"
    @GetMapping("/appeals/close/{id}")
    public String closeTask(@PathVariable(value = "id") int id, Model model) {
        Appeal updAppeal = service.getById(id);
        updAppeal.setLast_updated(new Timestamp(System.currentTimeMillis()));
        updAppeal.setStatus("resolved");
        service.saveAppeal(updAppeal);
        List<Appeal> listAppeals = service.getAllAppeals();
        model.addAttribute("listAppeals", listAppeals);
        return "redirect:/appeals/department";
    }

    // просмотр пользователем своих заявок
    @GetMapping("/appeals/{id}")
    public String showUserAppeal(@PathVariable(value = "id") int user_id, Model model) {
        List<Appeal> userAppeals = service.getUserAppeals(user_id);
        model.addAttribute("userAppeals", userAppeals);
        return "user_appeals";
    }

//        пользователь отменяет заявку
    @GetMapping("/appeals/cancele/{id}")
    public String deleteUserTask(@PathVariable(value = "id") int id, Model model) {
        Appeal updAppeal = service.getById(id);
        updAppeal.setLast_updated(new Timestamp(System.currentTimeMillis()));
        updAppeal.setStatus("canceled");
        service.saveAppeal(updAppeal);
        List<Appeal> userAppeals = service.getUserAppeals(id);
        model.addAttribute("userAppeals", userAppeals);
        return "user_appeals";
    }

//        пользователь редактирует заявку, открывается форма для редактирования
    @GetMapping("/appeals/update/{id}")
    public String updateUserTask(@PathVariable(value = "id") int id, Model model) {
        Appeal updAppeal = service.getById(id);
        model.addAttribute("updAppeal", updAppeal);
        return "update_appeal";
    }

//        пользователь сохраняет отредактированную заявку
    @PostMapping("/appeals/save/{id}")
    public String saveUser(@PathVariable("id") Integer id, @ModelAttribute("updAppeal") Appeal updAppeal, Model model) {
        Appeal appeal = service.getById(id);
        appeal.setLast_updated(new Timestamp(System.currentTimeMillis()));
        appeal.setDescription(updAppeal.getDescription());
        appeal.setTheme(updAppeal.getTheme());
        service.saveAppeal(appeal);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.findByEmail(user.getUsername()).getId();
        List<Appeal> userAppeals = service.getUserAppeals(user_id);
        model.addAttribute("userAppeals", userAppeals);
        return "user_appeals";
    }
}