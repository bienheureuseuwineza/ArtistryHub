package com.cc.creativecraze.service;

import com.cc.creativecraze.dto.PortfolioDto;
import com.cc.creativecraze.model.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    List<PortfolioDto> getAllPortfolios(String keyword);
    Optional<Portfolio> getPortfolioById(int id);
    void savePortfolio(PortfolioDto portfolioDto);
    void deletePortfolioById(int id);
    void updatePortfolio( PortfolioDto portfolioDto);

    List<Portfolio> getPortfolioByEmail(String email);

//    List<Portfolio> searchAndFilterPortfolios(String searchTerm, String filterCriteria);

}
