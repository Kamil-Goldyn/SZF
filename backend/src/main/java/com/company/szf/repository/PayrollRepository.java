package com.company.szf.repository;

import com.company.szf.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);

    List<Payroll> findByStatus(Payroll.PayrollStatus status);

    List<Payroll> findByEmployeeIdAndStatus(Long employeeId, Payroll.PayrollStatus status);

    @Query("SELECT p FROM Payroll p WHERE p.payPeriodStart >= :startDate AND p.payPeriodEnd <= :endDate")
    List<Payroll> findByPayPeriodRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Payroll p WHERE p.employee.id = :employeeId AND p.payPeriodStart >= :startDate AND p.payPeriodEnd <= :endDate")
    List<Payroll> findByEmployeeAndPayPeriodRange(@Param("employeeId") Long employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Payroll p WHERE p.employee.company.id = :companyId")
    List<Payroll> findByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT p FROM Payroll p WHERE p.employee.department.id = :departmentId")
    List<Payroll> findByDepartmentId(@Param("departmentId") Long departmentId);
}