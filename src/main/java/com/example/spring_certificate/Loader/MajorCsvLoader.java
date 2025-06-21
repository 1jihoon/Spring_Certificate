package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Major;
import com.example.spring_certificate.Loader.DataLoader.DataCsvLoader;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
@Order(3)
@RequiredArgsConstructor
public class MajorCsvLoader implements DataCsvLoader {
    private final MajorRepository majorRepo;
    private final DepartmentRepository deptRepo;
    private static final Charset ENCODING = Charset.forName("UTF-8");

    public void clear() {
        majorRepo.deleteAll();
    }

    public void load() {
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
}
