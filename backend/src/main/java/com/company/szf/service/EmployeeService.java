package com.company.szf.service;

import com.company.szf.entity.Employee;
import com.company.szf.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> getEmployeesByCompany(Long companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByStatus(Employee.EmployeeStatus status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Employee> getActiveEmployeesByCompany(Long companyId) {
        return employeeRepository.findByCompanyIdAndStatus(companyId, Employee.EmployeeStatus.ACTIVE);
    }

    public List<Employee> getActiveEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentIdAndStatus(departmentId, Employee.EmployeeStatus.ACTIVE);
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<Employee> getEmployeesBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return employeeRepository.findBySalaryRange(minSalary, maxSalary);
    }

    public long countActiveEmployeesByCompany(Long companyId) {
        return employeeRepository.countActiveEmployeesByCompany(companyId);
    }

    public long countActiveEmployeesByDepartment(Long departmentId) {
        return employeeRepository.countActiveEmployeesByDepartment(departmentId);
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Employee with this email already exists");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Check if email is being changed and if it already exists
        if (!employee.getEmail().equals(employeeDetails.getEmail()) &&
                employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            throw new RuntimeException("Employee with this email already exists");
        }

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setAddress(employeeDetails.getAddress());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setHireDate(employeeDetails.getHireDate());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());
        employee.setStatus(employeeDetails.getStatus());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setManager(employeeDetails.getManager());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
}