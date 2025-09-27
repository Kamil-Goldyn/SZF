package com.company.szf.repository;

import com.company.szf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByCompanyId(Long companyId);

    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByStatus(Employee.EmployeeStatus status);

    List<Employee> findByCompanyIdAndStatus(Long companyId, Employee.EmployeeStatus status);

    List<Employee> findByDepartmentIdAndStatus(Long departmentId, Employee.EmployeeStatus status);

    @Query("SELECT e FROM Employee e WHERE LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByFullNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findBySalaryRange(@Param("minSalary") BigDecimal minSalary, @Param("maxSalary") BigDecimal maxSalary);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.company.id = :companyId AND e.status = 'ACTIVE'")
    long countActiveEmployeesByCompany(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.id = :departmentId AND e.status = 'ACTIVE'")
    long countActiveEmployeesByDepartment(@Param("departmentId") Long departmentId);

    boolean existsByEmail(String email);
}