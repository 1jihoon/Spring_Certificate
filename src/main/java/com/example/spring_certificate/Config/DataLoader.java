package com.example.spring_certificate.Config;

import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Dto.DepartDto;
import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Dto.MajorDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Department;
import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Entity.Major;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.FacultyRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final FacultyRepository facultyRepo;
    private final DepartmentRepository deptRepo;
    private final MajorRepository majorRepo;
    private final CertificateRepository certRepo;

    private static final Charset ENCODING = Charset.forName("UTF-8");

    @Override
    public void run(String... args) throws Exception {
        // 기존 데이터 삭제
        certRepo.deleteAll();
        majorRepo.deleteAll();
        deptRepo.deleteAll();
        facultyRepo.deleteAll();

        loadFaculties();
        loadDepartments();
        loadMajors();
        loadCertificates();
    }

    private void loadFaculties() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/faculty.csv").getInputStream(), ENCODING))) {

            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 2) {
                    Faculty faculty = new Faculty();
                    faculty.setId(Long.parseLong(tokens[0].trim()));
                    faculty.setName(tokens[1].trim());
                    facultyRepo.save(faculty);
                }
            }
        } catch (Exception e) {
            System.err.println("Faculty CSV 파싱 오류: " + e.getMessage());
        }
    }

    private void loadDepartments() {
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

                    try {
                        Long facultyId = Long.parseLong(tokens[2].trim());
                        facultyRepo.findById(facultyId).ifPresent(dept::setFaculty);
                    } catch (NumberFormatException e) {
                        System.err.println("Department 내 facultyId 파싱 오류: " + Arrays.toString(tokens));
                    }

                    deptRepo.save(dept);
                }
            }
        } catch (Exception e) {
            System.err.println("Department CSV 파싱 오류: " + e.getMessage());
        }
    }

    private void loadMajors() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/major.csv").getInputStream(), ENCODING))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 3) {
                    Major major = new Major();
                    major.setId(Long.parseLong(tokens[0].trim()));
                    major.setName(tokens[1].trim());

                    try {
                        Long deptId = Long.parseLong(tokens[2].trim());
                        deptRepo.findById(deptId).ifPresent(major::setDepartment);
                    } catch (NumberFormatException e) {
                        System.err.println("Major 내 departmentId 파싱 오류: " + Arrays.toString(tokens));
                    }

                    majorRepo.save(major);
                }
            }
        } catch (Exception e) {
            System.err.println("Major CSV 파싱 오류: " + e.getMessage());
        }
    }

    private void loadCertificates() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/certificate.csv").getInputStream(), ENCODING))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                // 쉼표가 detail 안에 포함된 경우를 처리하는 정규식
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (tokens.length >= 4) {
                    Certificate cert = new Certificate();
                    cert.setName(tokens[0].trim());
                    cert.setDetail(tokens[1].trim());

                    // Optional: departmentId
                    if (!tokens[2].isBlank()) {
                        try {
                            Long deptId = Long.parseLong(tokens[2].trim());
                            deptRepo.findById(deptId).ifPresent(cert::setDepartment);
                        } catch (NumberFormatException e) {
                            System.err.println("Certificate 내 departmentId 파싱 오류: " + Arrays.toString(tokens));
                        }
                    }

                    // Optional: majorId
                    if (!tokens[3].isBlank()) {
                        try {
                            Long majorId = Long.parseLong(tokens[3].trim());
                            majorRepo.findById(majorId).ifPresent(cert::setMajor);
                        } catch (NumberFormatException e) {
                            System.err.println("Certificate 내 majorId 파싱 오류: " + Arrays.toString(tokens));
                        }
                    }

                    certRepo.save(cert);
                }
            }
        } catch (Exception e) {
            System.err.println("Certificate CSV 파싱 오류: " + e.getMessage());
        }
    }
}
