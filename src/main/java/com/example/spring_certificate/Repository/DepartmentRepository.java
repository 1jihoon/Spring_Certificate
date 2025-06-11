package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    /*// DepartmentRepository
    @Query("SELECT d FROM Department d WHERE d.faculty.id IN :facultyIds")
    List<Department> findByFacultyIds(@Param("facultyIds") List<Long> facultyIds);
    //순수 쿼리문 select * from department where faculty_id IN (1,2,3)을 써야 돌아간다.
    //쿼리문에서 :facultyIds 부분에서 공백을 넣어버리면 그것도 문법오류로 처리하기에 반드시 이 부분은 공백을 넣으면 안된다.
    //facultyIds란 이름의 파라미터를 못 찾기 때문이다.

    @Query("SELECT d FROM Department d WHERE d.faculty IS NULL")
    List<Department> findByFacultyIsNull();*/

    @Query("SELECT d.name FROM Department d WHERE d.id = :id")
    Optional<String> findNameById(@Param("id") Long id);
}
