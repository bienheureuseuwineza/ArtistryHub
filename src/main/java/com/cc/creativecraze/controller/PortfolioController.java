package com.cc.creativecraze.controller;


import com.cc.creativecraze.dto.PortfolioDto;
import com.cc.creativecraze.model.Portfolio;
import com.cc.creativecraze.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/add_portfolio")
    public  String getPortfolioForm(Model model){
        PortfolioDto portfolioDto = new PortfolioDto();
        model.addAttribute("portfolio", portfolioDto);
        return "add_portfolio";
    }


    @PostMapping("/add_portfolio")
    public String addPortfolio(@ModelAttribute("portfolio")PortfolioDto portfolioDto){
        portfolioService.savePortfolio(portfolioDto);
        return "redirect:/admin";
    }

    @GetMapping("/delete_profile/{id}")
    public String deletePortfolio(@PathVariable("id")int id ){
        portfolioService.deletePortfolioById(id);
        return  "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public  String updatePortfolio(@PathVariable("id")int id, Model model){
        System.out.println("ID from URL: " + id);
        model.addAttribute("portfolio", portfolioService.getPortfolioById(id).orElse(null));
        return "update_portfolio";

    }
    @PostMapping("/update")
    public String updatePortfolioForm(@ModelAttribute PortfolioDto portfolioDto){
        portfolioService.updatePortfolio(portfolioDto);
        return "redirect:/admin";
    }
    @GetMapping("/update-artist/{id}")
    public  String updateArtist(@PathVariable("id")int id, Model model){
        System.out.println("ID from URL: " + id);
        model.addAttribute("portfolio", portfolioService.getPortfolioById(id).orElse(null));
        return "update_artist";

    }
    @PostMapping("/update_artist")
    public String updateArtistForm(@ModelAttribute PortfolioDto portfolioDto){
        portfolioService.updatePortfolio(portfolioDto);
        System.out.println("Update Artist Form Called");
        return "redirect:/artist";
    }
    @GetMapping("/add_artist")
    public  String getArtistForm(Model model){
        PortfolioDto portfolioDto = new PortfolioDto();
        model.addAttribute("portfolio", portfolioDto);
        return "add_artist";
    }
    @PostMapping("/add_artist")
    public String addArtist(@ModelAttribute("portfolio")PortfolioDto portfolioDto){
        portfolioService.savePortfolio(portfolioDto);
        return "redirect:/artist";
    }
    @GetMapping("/delete_artist/{id}")
    public String deleteArtistPortfolio(@PathVariable("id")int id ){
        portfolioService.deletePortfolioById(id);
        return  "redirect:/artist";
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Portfolio>> searchAndFilterPortfolios(
//            @RequestParam(required = false) String searchTerm,
//            @RequestParam(required = false) String filterCriteria) {
//        List<Portfolio> portfolios = portfolioService.searchAndFilterPortfolios(searchTerm, filterCriteria);
//        return ResponseEntity.ok(portfolios);
//    }
}

