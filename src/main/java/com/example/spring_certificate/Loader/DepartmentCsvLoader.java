package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Loader.DataLoader.DataCsvLoader;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
@Order(2)
@RequiredArgsConstructor
public class DepartmentCsvLoader implements DataCsvLoader {
    private final DepartmentRepository deptRepo;
    private final FacultyRepository facultyRepo;
    private static final Charset ENCODING = Charset.forName("UTF-8");

    public void clear() {
        deptRepo.deleteAll();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/department.csv").getInputStream(), ENCODING))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 3) {
                    Department dept = new Department();
                    dept.setId(Long.parseLong(tokens[0].trim()));
                    dept.setName(tokens[1].trim());

                    String facultyToken = tokens[2].trim();
                    if(!facultyToken.isEmpty()){
                        try {
                            Long facultyId = Long.parseLong(facultyToken);
                            facultyRepo.findById(facultyId).ifPresent(dept::setFaculty);
                            //facultyRepository에서 Id를 조회해서 만약 있으면 department 엔티티에서 Faculty 엔티티를
                            //연결하라는 뜻이다. 즉 해당 학과가 해당 학부에 연결되게 만들라는 것이다.
                        } catch (NumberFormatException e) {
                            System.err.println("Department 내 facultyId 파싱 오류: " + Arrays.toString(tokens));
                        }
                    }
                    deptRepo.save(dept);
                }
            }
        } catch (Exception e) {
            System.err.println("Department CSV 파싱 오류: " + e.getMessage());
        }
    }
}
