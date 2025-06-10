package com.example.spring_certificate.Repository;

import com.example.spring_certificate.Entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    // CertificateRepository
    @Query("SELECT c FROM Certificate c WHERE c.department.id IN :departmentIds")
    List<Certificate> findByDepartmentIds(@Param("departmentIds") List<Long> departmentIds);
    //H2 - Console에선 순수 Sql만 받기에
    //select * from certificate where department_id IN (1,2,3,4,5) -> 이런 식으로 바꿈
    //학과 - 자격증에 속하는 것들을 불러오기 위해 쿼리 구문을 미리 쓴다.
    Optional<Certificate> findByName(String name);

   /* @Query("SELECT c FROM Certificate c JOIN c.majors m WHERE m.id IN :majorIds")
    List<Certificate> findByMajorIds(@Param("majorIds") List<Long> majorIds);
    //마찬기지로 H2 - Console에선 select * from certificate where major_id IN (1,2,3) -> 이렇게 쓴다.
    //전공 - 자격증에 속하는 것들을 불러오기 위해 쿼리 구문을 미리 쓴다.
    //findAll()로 데이터를 전부 다 불러올 수 있지만 성능적인 문제나 쓸데없이 걸리는 시간때문에 쿼리문을 쓰고 보내는게 효율적이다.*/
}
