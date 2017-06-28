package br.com.spotippos.auth.controller;

import br.com.spotippos.auth.model.UserAccount;
import br.com.spotippos.auth.service.UserAccountService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by vinic on 25/06/2017.
 */
@Controller
public class LoginController {

    @Autowired
    private UserAccountService userService;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null && !StringUtils.isEmpty(principal.getName())){
            modelAndView.setViewName("redirect:/myaccount");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }


    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserAccount user = new UserAccount();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid UserAccount user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserAccount userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserAccount());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping("/myaccount")
    public ModelAndView myaccount(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = userService.findByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUsername() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("myaccount");
        return modelAndView;
    }


}
