package com.company.szf.service;

import com.company.szf.entity.Department;
import com.company.szf.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public List<Department> getDepartmentsByCompany(Long companyId) {
        return departmentRepository.findByCompanyId(companyId);
    }

    public List<Department> searchDepartmentsByName(String name) {
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Department> searchDepartmentsByCompanyAndName(Long companyId, String name) {
        return departmentRepository.findByCompanyIdAndNameContaining(companyId, name);
    }

    public Department createDepartment(Department department) {
        if (departmentRepository.existsByNameAndCompanyId(department.getName(), department.getCompany().getId())) {
            throw new RuntimeException("Department with this name already exists in the company");
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        // Check if name is being changed and if it already exists in the company
        if (!department.getName().equals(departmentDetails.getName()) &&
                departmentRepository.existsByNameAndCompanyId(departmentDetails.getName(), department.getCompany().getId())) {
            throw new RuntimeException("Department with this name already exists in the company");
        }

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        department.setBudget(departmentDetails.getBudget());
        department.setManager(departmentDetails.getManager());

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    public boolean existsByNameAndCompanyId(String name, Long companyId) {
        return departmentRepository.existsByNameAndCompanyId(name, companyId);
    }
}