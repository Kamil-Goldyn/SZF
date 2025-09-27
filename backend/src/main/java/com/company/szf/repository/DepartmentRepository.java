package com.company.szf.repository;

import com.company.szf.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByCompanyId(Long companyId);

    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Department> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT d FROM Department d WHERE d.company.id = :companyId AND LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Department> findByCompanyIdAndNameContaining(@Param("companyId") Long companyId, @Param("name") String name);

    boolean existsByNameAndCompanyId(String name, Long companyId);
}