package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }
    
    @RequestMapping(value = "/evilform", method = RequestMethod.GET)
    public String loadEvilForm() {
        return "evilForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String address) {
        signupRepository.save(new Signup(firstname, lastname, address));
        return "done";
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String login() {
        return "auth";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "registerList";
    }
    
    @Autowired
    private CustomUserDetailsService userDetails;
    
    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    //@RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String newUser(@RequestParam String newuser, @RequestParam String newuserpass) {
        userDetails.newUser(newuser, newuserpass);
        return "registerList";
    }
    
    @RequestMapping(value = "/newpassword", method = RequestMethod.GET)
    //@RequestMapping(value = "/newpassword", method = RequestMethod.POST)
    public String changePassowrd(Authentication auth, @RequestParam String newpassword) {
        userDetails.changePassword(auth.getName(), newpassword);
        return "registerList";
    }
}
