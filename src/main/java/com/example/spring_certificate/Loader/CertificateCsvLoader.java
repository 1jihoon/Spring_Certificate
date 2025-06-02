package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CertificateCsvLoader {

    private final CertificateRepository certRepo;
    private final DepartmentRepository deptRepo;
    private final MajorRepository majorRepo;
    private static final Charset ENCODING = Charset.forName("UTF-8");

    public void clear() {
        certRepo.deleteAll();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/certificate.csv").getInputStream(), ENCODING))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (tokens.length >= 4) {
                    Certificate cert = new Certificate();
                    cert.setName(tokens[0].trim());
                    cert.setDetail(tokens[1].trim());

                    if (!tokens[2].isBlank()) {
                        try {
                            String rawId = tokens[2].trim();
                            //System.out.println("📦 departmentId 문자열: [" + rawId + "]");

                            Long deptId = Long.parseLong(rawId);
                            deptRepo.findById(deptId).ifPresent(cert::setDepartment);
                        } catch (NumberFormatException e) {
                            System.err.println("❌ departmentId 파싱 오류: " + Arrays.toString(tokens));
                        }
                    }

                    if (!tokens[3].isBlank()) {
                        try {
                            Long majorId = Long.parseLong(tokens[3].trim());
                            majorRepo.findById(majorId).ifPresent(cert::setMajor);
                        } catch (NumberFormatException e) {
                            System.err.println("⚠️ majorId 파싱 오류: " + Arrays.toString(tokens));
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
