package com.company.szf.repository;

import com.company.szf.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByTaxId(String taxId);

    Optional<Company> findByEmail(String email);

    List<Company> findByStatus(Company.CompanyStatus status);

    @Query("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Company> findByNameContainingIgnoreCase(@Param("name") String name);

    boolean existsByTaxId(String taxId);

    boolean existsByEmail(String email);
}