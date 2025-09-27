package com.company.szf.service;

import com.company.szf.entity.Company;
import com.company.szf.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> getCompanyByTaxId(String taxId) {
        return companyRepository.findByTaxId(taxId);
    }

    public Optional<Company> getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    public List<Company> getCompaniesByStatus(Company.CompanyStatus status) {
        return companyRepository.findByStatus(status);
    }

    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    public Company createCompany(Company company) {
        if (companyRepository.existsByTaxId(company.getTaxId())) {
            throw new RuntimeException("Company with this Tax ID already exists");
        }
        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new RuntimeException("Company with this email already exists");
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        // Check if tax ID is being changed and if it already exists
        if (!company.getTaxId().equals(companyDetails.getTaxId()) &&
                companyRepository.existsByTaxId(companyDetails.getTaxId())) {
            throw new RuntimeException("Company with this Tax ID already exists");
        }

        // Check if email is being changed and if it already exists
        if (!company.getEmail().equals(companyDetails.getEmail()) &&
                companyRepository.existsByEmail(companyDetails.getEmail())) {
            throw new RuntimeException("Company with this email already exists");
        }

        company.setName(companyDetails.getName());
        company.setTaxId(companyDetails.getTaxId());
        company.setEmail(companyDetails.getEmail());
        company.setPhone(companyDetails.getPhone());
        company.setAddress(companyDetails.getAddress());
        company.setDescription(companyDetails.getDescription());
        company.setStatus(companyDetails.getStatus());

        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        companyRepository.delete(company);
    }

    public boolean existsByTaxId(String taxId) {
        return companyRepository.existsByTaxId(taxId);
    }

    public boolean existsByEmail(String email) {
        return companyRepository.existsByEmail(email);
    }
}