package com.company.szf.controller;

import com.company.szf.entity.Payroll;
import com.company.szf.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
@Tag(name = "Payroll Management", description = "APIs for managing payrolls")
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    @Operation(summary = "Get all payrolls")
    public ResponseEntity<List<Payroll>> getAllPayrolls() {
        List<Payroll> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payroll by ID")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id) {
        return payrollService.getPayrollById(id)
                .map(payroll -> ResponseEntity.ok(payroll))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get payrolls by employee")
    public ResponseEntity<List<Payroll>> getPayrollsByEmployee(@PathVariable Long employeeId) {
        List<Payroll> payrolls = payrollService.getPayrollsByEmployee(employeeId);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get payrolls by status")
    public ResponseEntity<List<Payroll>> getPayrollsByStatus(@PathVariable Payroll.PayrollStatus status) {
        List<Payroll> payrolls = payrollService.getPayrollsByStatus(status);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    @Operation(summary = "Get payrolls by employee and status")
    public ResponseEntity<List<Payroll>> getPayrollsByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @PathVariable Payroll.PayrollStatus status) {
        List<Payroll> payrolls = payrollService.getPayrollsByEmployeeAndStatus(employeeId, status);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/period")
    @Operation(summary = "Get payrolls by period")
    public ResponseEntity<List<Payroll>> getPayrollsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Payroll> payrolls = payrollService.getPayrollsByPeriod(startDate, endDate);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/employee/{employeeId}/period")
    @Operation(summary = "Get payrolls by employee and period")
    public ResponseEntity<List<Payroll>> getPayrollsByEmployeeAndPeriod(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Payroll> payrolls = payrollService.getPayrollsByEmployeeAndPeriod(employeeId, startDate, endDate);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get payrolls by company")
    public ResponseEntity<List<Payroll>> getPayrollsByCompany(@PathVariable Long companyId) {
        List<Payroll> payrolls = payrollService.getPayrollsByCompany(companyId);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get payrolls by department")
    public ResponseEntity<List<Payroll>> getPayrollsByDepartment(@PathVariable Long departmentId) {
        List<Payroll> payrolls = payrollService.getPayrollsByDepartment(departmentId);
        return ResponseEntity.ok(payrolls);
    }

    @PostMapping
    @Operation(summary = "Create a new payroll")
    public ResponseEntity<Payroll> createPayroll(@Valid @RequestBody Payroll payroll) {
        try {
            Payroll createdPayroll = payrollService.createPayroll(payroll);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayroll);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update payroll")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable Long id, @Valid @RequestBody Payroll payrollDetails) {
        try {
            Payroll updatedPayroll = payrollService.updatePayroll(id, payrollDetails);
            return ResponseEntity.ok(updatedPayroll);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/mark-paid")
    @Operation(summary = "Mark payroll as paid")
    public ResponseEntity<Payroll> markAsPaid(@PathVariable Long id) {
        try {
            Payroll paidPayroll = payrollService.markAsPaid(id);
            return ResponseEntity.ok(paidPayroll);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payroll")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        try {
            payrollService.deletePayroll(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}