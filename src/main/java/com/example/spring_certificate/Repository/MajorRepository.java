package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MajorRepository extends JpaRepository<Major, Long> {
    // MajorRepository
    @Query("SELECT m FROM Major m WHERE m.department.id IN :departmentIds")
    //순수 쿼리문으론 select * from major where department_id IN (1,2,3) -> 이것이고, major 테이블엔 외래키로
    //department_id가 있으니 그걸 참조해야 한다.
    List<Major> findByDepartmentIds(@Param("departmentIds") List<Long> departmentIds);
}
