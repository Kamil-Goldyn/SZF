package com.company.szf.controller;

import com.company.szf.entity.Department;
import com.company.szf.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "APIs for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @Operation(summary = "Get all departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(department -> ResponseEntity.ok(department))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get departments by company")
    public ResponseEntity<List<Department>> getDepartmentsByCompany(@PathVariable Long companyId) {
        List<Department> departments = departmentService.getDepartmentsByCompany(companyId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/search")
    @Operation(summary = "Search departments by name")
    public ResponseEntity<List<Department>> searchDepartmentsByName(@RequestParam String name) {
        List<Department> departments = departmentService.searchDepartmentsByName(name);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/company/{companyId}/search")
    @Operation(summary = "Search departments by company and name")
    public ResponseEntity<List<Department>> searchDepartmentsByCompanyAndName(
            @PathVariable Long companyId,
            @RequestParam String name) {
        List<Department> departments = departmentService.searchDepartmentsByCompanyAndName(companyId, name);
        return ResponseEntity.ok(departments);
    }

    @PostMapping
    @Operation(summary = "Create a new department")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update department")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            return ResponseEntity.ok(updatedDepartment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete department")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}