package com.company.szf.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
@Data
@EqualsAndHashCode(callSuper = true)
public class Payroll extends BaseEntity {

    @NotNull(message = "Employee is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @NotNull(message = "Pay period start is required")
    @Column(name = "pay_period_start")
    private LocalDate payPeriodStart;

    @NotNull(message = "Pay period end is required")
    @Column(name = "pay_period_end")
    private LocalDate payPeriodEnd;

    @NotNull(message = "Base salary is required")
    @Min(value = 0, message = "Base salary must be positive")
    @Column(name = "base_salary", precision = 12, scale = 2)
    private BigDecimal baseSalary;

    @Column(name = "overtime_hours")
    private Integer overtimeHours = 0;

    @Column(name = "overtime_rate", precision = 12, scale = 2)
    private BigDecimal overtimeRate = BigDecimal.ZERO;

    @Column(name = "bonus", precision = 12, scale = 2)
    private BigDecimal bonus = BigDecimal.ZERO;

    @Column(name = "deductions", precision = 12, scale = 2)
    private BigDecimal deductions = BigDecimal.ZERO;

    @Column(name = "gross_pay", precision = 12, scale = 2)
    private BigDecimal grossPay;

    @Column(name = "net_pay", precision = 12, scale = 2)
    private BigDecimal netPay;

    @NotNull(message = "Payroll status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PayrollStatus status = PayrollStatus.PENDING;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    public enum PayrollStatus {
        PENDING, PAID, CANCELLED
    }

    @PrePersist
    @PreUpdate
    private void calculatePay() {
        BigDecimal overtimePay = BigDecimal.valueOf(overtimeHours).multiply(overtimeRate);
        this.grossPay = baseSalary.add(overtimePay).add(bonus);
        this.netPay = grossPay.subtract(deductions);
    }
}