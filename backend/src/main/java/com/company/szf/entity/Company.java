package com.company.szf.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {

    @NotBlank(message = "Company name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Tax ID is required")
    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Company status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompanyStatus status = CompanyStatus.ACTIVE;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();

    public enum CompanyStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
}