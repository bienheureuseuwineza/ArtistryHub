package com.cc.creativecraze.repository;

import com.cc.creativecraze.dto.PortfolioDto;
import com.cc.creativecraze.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//import static org.hibernate.FetchMode.SELECT;

@Repository
public interface PortfolioRepository extends JpaRepository <Portfolio, Integer> {
    List<Portfolio> findPortfolioByOwnerEmail(String email);
    @Query("SELECT p FROM Portfolio p where CONCAT(p.id,'',p.ownerEmail,'',p.age,'',p.nationality,'',p.message,'',p.name) LIKE %?1%")
    List<PortfolioDto> search(String keyword);
    @Query("SELECT p.pdf FROM Portfolio p WHERE p.id = :id")
    byte[] findPdfById(@Param("id") int id);
}
