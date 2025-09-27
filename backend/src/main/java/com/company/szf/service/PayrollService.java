package com.company.szf.service;

import com.company.szf.entity.Payroll;
import com.company.szf.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollService {

    private final PayrollRepository payrollRepository;

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    public Optional<Payroll> getPayrollById(Long id) {
        return payrollRepository.findById(id);
    }

    public List<Payroll> getPayrollsByEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    public List<Payroll> getPayrollsByStatus(Payroll.PayrollStatus status) {
        return payrollRepository.findByStatus(status);
    }

    public List<Payroll> getPayrollsByEmployeeAndStatus(Long employeeId, Payroll.PayrollStatus status) {
        return payrollRepository.findByEmployeeIdAndStatus(employeeId, status);
    }

    public List<Payroll> getPayrollsByPeriod(LocalDate startDate, LocalDate endDate) {
        return payrollRepository.findByPayPeriodRange(startDate, endDate);
    }

    public List<Payroll> getPayrollsByEmployeeAndPeriod(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return payrollRepository.findByEmployeeAndPayPeriodRange(employeeId, startDate, endDate);
    }

    public List<Payroll> getPayrollsByCompany(Long companyId) {
        return payrollRepository.findByCompanyId(companyId);
    }

    public List<Payroll> getPayrollsByDepartment(Long departmentId) {
        return payrollRepository.findByDepartmentId(departmentId);
    }

    public Payroll createPayroll(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    public Payroll updatePayroll(Long id, Payroll payrollDetails) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with id: " + id));

        payroll.setPayPeriodStart(payrollDetails.getPayPeriodStart());
        payroll.setPayPeriodEnd(payrollDetails.getPayPeriodEnd());
        payroll.setBaseSalary(payrollDetails.getBaseSalary());
        payroll.setOvertimeHours(payrollDetails.getOvertimeHours());
        payroll.setOvertimeRate(payrollDetails.getOvertimeRate());
        payroll.setBonus(payrollDetails.getBonus());
        payroll.setDeductions(payrollDetails.getDeductions());
        payroll.setStatus(payrollDetails.getStatus());

        if (payrollDetails.getStatus() == Payroll.PayrollStatus.PAID && payroll.getPaidDate() == null) {
            payroll.setPaidDate(LocalDate.now());
        }

        return payrollRepository.save(payroll);
    }

    public void deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with id: " + id));
        payrollRepository.delete(payroll);
    }

    public Payroll markAsPaid(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with id: " + id));

        payroll.setStatus(Payroll.PayrollStatus.PAID);
        payroll.setPaidDate(LocalDate.now());

        return payrollRepository.save(payroll);
    }
}