package com.company.szf.controller;

import com.company.szf.entity.Employee;
import com.company.szf.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Get all employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok(employee))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get employee by email")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email)
                .map(employee -> ResponseEntity.ok(employee))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get employees by company")
    public ResponseEntity<List<Employee>> getEmployeesByCompany(@PathVariable Long companyId) {
        List<Employee> employees = employeeService.getEmployeesByCompany(companyId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get employees by department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get employees by status")
    public ResponseEntity<List<Employee>> getEmployeesByStatus(@PathVariable Employee.EmployeeStatus status) {
        List<Employee> employees = employeeService.getEmployeesByStatus(status);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/company/{companyId}/active")
    @Operation(summary = "Get active employees by company")
    public ResponseEntity<List<Employee>> getActiveEmployeesByCompany(@PathVariable Long companyId) {
        List<Employee> employees = employeeService.getActiveEmployeesByCompany(companyId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}/active")
    @Operation(summary = "Get active employees by department")
    public ResponseEntity<List<Employee>> getActiveEmployeesByDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.getActiveEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    @Operation(summary = "Search employees by name")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        List<Employee> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary-range")
    @Operation(summary = "Get employees by salary range")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam BigDecimal minSalary,
            @RequestParam BigDecimal maxSalary) {
        List<Employee> employees = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/company/{companyId}/count")
    @Operation(summary = "Count active employees by company")
    public ResponseEntity<Long> countActiveEmployeesByCompany(@PathVariable Long companyId) {
        long count = employeeService.countActiveEmployeesByCompany(companyId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/department/{departmentId}/count")
    @Operation(summary = "Count active employees by department")
    public ResponseEntity<Long> countActiveEmployeesByDepartment(@PathVariable Long departmentId) {
        long count = employeeService.countActiveEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}