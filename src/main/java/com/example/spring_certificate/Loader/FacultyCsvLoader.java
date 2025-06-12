package com.example.spring_certificate.Loader;

import com.example.spring_certificate.Entity.Faculty;
import com.example.spring_certificate.Repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Component
@Order(1)
@RequiredArgsConstructor
public class FacultyCsvLoader implements DataCsvLoader {
    private final FacultyRepository facultyRepo;
    private static final Charset ENCODING = Charset.forName("UTF-8");

    public void clear() {
        facultyRepo.deleteAll();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("csv/faculty.csv").getInputStream(), ENCODING))) {
            reader.readLine(); // skip header 첫 줄은 Id,Name이기에 무시한다.
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 2) {
                    Faculty faculty = new Faculty();
                    faculty.setId(Long.parseLong(tokens[0].trim()));//공백이 실수로 껴있을 경우를 대비해서 trim()을 써서 없애주고 Long 형태로 바꿔준다.
                    faculty.setName(tokens[1].trim());//이것도 그렇다 -> tokens[0] = 1, tokens[1] = 공학부
                    facultyRepo.save(faculty);//facultyRepository로 Db에 저장
                }
            }
        } catch (Exception e) {
            System.err.println("Faculty CSV 파싱 오류: " + e.getMessage());
        }
    }
}
