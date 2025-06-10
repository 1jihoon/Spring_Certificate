package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Major;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.DepartmentRepository;
import com.example.spring_certificate.Repository.MajorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;


@Component
@RequiredArgsConstructor
public class CertificateCsvLoader {

    private final CertificateRepository certRepo;
    private final DepartmentRepository deptRepo;
    private final MajorRepository majorRepo;
    private static final Charset ENCODING = Charset.forName("UTF-8");

    @Transactional//트랜젝셔널이 있어야지 데이터를 훨씬 안정적으로 저장하고 삭제할 수 있음
    public void clear() {
       majorRepo.findAll().forEach(major -> major.getCertificates().clear());
       majorRepo.flush();//flush()는 현재 버퍼의 내용을 비우고 클라이언트로 전송한다.

       certRepo.deleteAll();
    }

    @Transactional
    public List<Certificate> load() {
        List<Certificate> certificates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/certificate.csv").getInputStream(), ENCODING))) {
            reader.readLine(); // 헤더 스킵
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (tokens.length >= 4) {
                    String certName = tokens[0].trim();
                    String detail = tokens[1].trim();

                    // 자격증 중복 체크 후 저장 또는 재사용
                    Optional<Certificate> existingCert = certRepo.findByName(certName);
                    Certificate cert;
                    if (existingCert.isPresent()) {
                        cert = existingCert.get();
                    } else {
                        cert = new Certificate();
                        cert.setName(certName);
                        cert.setDetail(detail);

                        // 부서 연결
                        if (!tokens[2].isBlank()) {
                            try {
                                Long deptId = Long.parseLong(tokens[2].trim());
                                deptRepo.findById(deptId).ifPresent(cert::setDepartment);
                            } catch (NumberFormatException e) {
                                System.err.println("❌ departmentId 파싱 오류: " + Arrays.toString(tokens));
                            }
                        }

                        cert = certRepo.save(cert); // DB에 저장
                    }

                    // 전공 연결
                    if (!tokens[3].isBlank()) {
                        try {
                            Long majorId = Long.parseLong(tokens[3].trim());
                            Optional<Major> optionalMajor = majorRepo.findById(majorId);
                            if (optionalMajor.isPresent()) {
                                Major major = optionalMajor.get();
                                major.getCertificates().add(cert); // 관계 연결
                                majorRepo.save(major);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("⚠️ majorId 파싱 오류: " + Arrays.toString(tokens));
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.err.println("Certificate CSV 파싱 오류: " + e.getMessage());
        }

        return certificates;  // ✅ 이제 리스트가 채워짐
    }

}
