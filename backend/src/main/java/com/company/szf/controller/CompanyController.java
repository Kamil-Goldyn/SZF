package com.company.szf.controller;

import com.company.szf.entity.Company;
import com.company.szf.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company Management", description = "APIs for managing companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "Get all companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(company -> ResponseEntity.ok(company))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tax-id/{taxId}")
    @Operation(summary = "Get company by Tax ID")
    public ResponseEntity<Company> getCompanyByTaxId(@PathVariable String taxId) {
        return companyService.getCompanyByTaxId(taxId)
                .map(company -> ResponseEntity.ok(company))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get company by email")
    public ResponseEntity<Company> getCompanyByEmail(@PathVariable String email) {
        return companyService.getCompanyByEmail(email)
                .map(company -> ResponseEntity.ok(company))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get companies by status")
    public ResponseEntity<List<Company>> getCompaniesByStatus(@PathVariable Company.CompanyStatus status) {
        List<Company> companies = companyService.getCompaniesByStatus(status);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/search")
    @Operation(summary = "Search companies by name")
    public ResponseEntity<List<Company>> searchCompaniesByName(@RequestParam String name) {
        List<Company> companies = companyService.searchCompaniesByName(name);
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    @Operation(summary = "Create a new company")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        try {
            Company createdCompany = companyService.createCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update company")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyDetails) {
        try {
            Company updatedCompany = companyService.updateCompany(id, companyDetails);
            return ResponseEntity.ok(updatedCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}