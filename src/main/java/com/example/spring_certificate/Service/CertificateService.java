package com.example.spring_certificate.Service;

import com.example.spring_certificate.Dto.CertificateDto;
import com.example.spring_certificate.Dto.DepartDto;
import com.example.spring_certificate.Dto.FacultyDto;
import com.example.spring_certificate.Dto.MajorDto;
import com.example.spring_certificate.Entity.Certificate;
import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Repository.CertificateRepository;
import com.example.spring_certificate.Repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final FacultyRepository facultyRepository;

    public CertificateService(CertificateRepository certificateRepository,
                              FacultyRepository facultyRepository) {
        this.certificateRepository = certificateRepository;
        this.facultyRepository = facultyRepository;
    }


    public List<FacultyDto> getFacultyTreeWithCertificates() {
        List<Faculty> faculties = facultyRepository.findAll();

        return faculties.stream().map(faculty -> {
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setFacultyId(faculty.getId());
            facultyDto.setFacultyName(faculty.getName());

            // 학과 리스트 구성
            List<DepartDto> departmentDtos = faculty.getDepartments().stream().map(dept -> {
                DepartDto departDto = new DepartDto();
                departDto.setDepartmentId(dept.getId());
                departDto.setDepartmentName(dept.getName());

                // 전공 리스트 구성
                List<MajorDto> majorDtos = dept.getMajors().stream().map(major -> {
                    MajorDto majorDto = new MajorDto();
                    majorDto.setMajorId(major.getId());
                    majorDto.setMajorName(major.getName());

                    // 자격증 리스트 구성 (중복 방지)
                    List<CertificateDto> certDtos = major.getCertificates().stream()
                            .distinct()  // Optional: equals/hashCode 필요
                            .map(cert -> {
                                CertificateDto dto = new CertificateDto();
                                dto.setCertificateId(cert.getId());
                                dto.setCertificateName(cert.getName());
                                return dto;
                            }).toList();

                    majorDto.setCertificates(certDtos);
                    return majorDto;
                }).toList();

                // 학과 직속 자격증도 추가 (전공 없이 학과에만 연결된 자격증)
                List<CertificateDto> deptCerts = dept.getCertificates().stream()
                        .distinct()
                        .map(cert -> {
                            CertificateDto dto = new CertificateDto();
                            dto.setCertificateId(cert.getId());
                            dto.setCertificateName(cert.getName());
                            return dto;
                        }).toList();

                departDto.setMajors(majorDtos);
                departDto.setCertificates(deptCerts);  // 필요 시 DepartDto에 필드 추가
                return departDto;
            }).toList();

            facultyDto.setDepartments(departmentDtos);
            return facultyDto;
        }).toList();
    }

    public List<Certificate> getCertificatesByDepartmentId(Long departmentId) {
        return certificateRepository.findByDepartmentId(departmentId);
    }

    public List<Certificate> getCertificatesByMajorId(Long majorId) {
        return certificateRepository.findByMajorId(majorId);
    }

}
