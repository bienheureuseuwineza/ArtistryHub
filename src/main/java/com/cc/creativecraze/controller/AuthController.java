package com.cc.creativecraze.controller;

import com.cc.creativecraze.dto.PortfolioDto;
import com.cc.creativecraze.dto.UserDto;
import com.cc.creativecraze.model.Portfolio;
import com.cc.creativecraze.model.User;
import com.cc.creativecraze.service.PortfolioService;
import com.cc.creativecraze.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    public AuthController(UserService userService, PortfolioService portfolioService) {
        this.userService = userService;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }
    @GetMapping("/login")
    public  String showLoginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "signup";
    }

    @GetMapping("/admin")
    public String getDashboard(Model model, @Param("keyword") String keyword){
        List<PortfolioDto> allPortfolios = portfolioService.getAllPortfolios(keyword);
        model.addAttribute("portfolios" , allPortfolios);
        model.addAttribute("keyword", keyword);

        return "dashboard";
    }

    //    @PreAuthorize("hasAuthority('MODEL')")
    @GetMapping("/artist")
    public String getArtistDash( Model model){
        System.out.println("========================I'm getting called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();;
        String email = user.getUsername();
        User user1 = userService.findUserByEmail(email);
        model.addAttribute("loggedInUserName", user1.getName());
        List<Portfolio> portfolios = portfolioService.getPortfolioByEmail(email);
        if (!portfolios.isEmpty()) {
            model.addAttribute("portfolios", portfolios);
            return "artist_dashboard";
        } else {
            return "redirect:/add_artist";
        }
    }


    @PostMapping("/register")
    public String registration(@ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty()
        ){
            result.reject("The email already exists");
        }
        if(result.hasErrors()){
            model.addAttribute("user",userDto);
            return"/signup";
        }
        userService.saveUser(userDto);
        return "redirect:/add_artist";
    }
    @GetMapping("/users")
    public String users (Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "portfolios";
    }
}

