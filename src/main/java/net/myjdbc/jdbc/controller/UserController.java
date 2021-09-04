package net.myjdbc.jdbc.controller;

import net.myjdbc.jdbc.dto.User;
import net.myjdbc.jdbc.service.CrudOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final CrudOperationService service;

    @Autowired
    public UserController(CrudOperationService service) {
        this.service = service;
    }

    @GetMapping("viewUser")
    public String showAll(Model model) {
        List<User> users = service.getAll();
        model.addAttribute("users",users);
        return "view-users";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        User user = service.getUserById(String.valueOf(id));
        model.addAttribute("userById",user);
        return "view-user-by-id";
    }

    @GetMapping("/addUser")
    public String createUserView(Model model) {
        model.addAttribute("user",new User());
        return "add-user";
    }

    @PostMapping("/addUser")
    public RedirectView createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes){
        final RedirectView redirectView = new RedirectView("/user/addUser", true);
        User savedUser = service.insert(user.getName(), user.getAge(), user.getEmail());
        redirectAttributes.addFlashAttribute("savedUser", savedUser);
        redirectAttributes.addFlashAttribute("addUserSuccessful", true);
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public String updateUserEmailById(@PathVariable("id") int id, @ModelAttribute("user") User user){
        service.updateUserEmailById(String.valueOf(id),user.getEmail());
        return "updated";
    }


}
