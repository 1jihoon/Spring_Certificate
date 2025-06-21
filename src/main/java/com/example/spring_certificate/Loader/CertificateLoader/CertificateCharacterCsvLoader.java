package com.example.spring_certificate.Loader.CertificateLoader;


import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import com.example.spring_certificate.Entity.CertificateEntity.CertificateCharacter;
import com.example.spring_certificate.Loader.DataLoader.DataCsvLoader;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateCharacterRepository;
import com.example.spring_certificate.Repository.CertificateRepository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Order(7)
@RequiredArgsConstructor
public class CertificateCharacterCsvLoader implements DataCsvLoader {
    private final CertificateRepository certificateRepository;
    private final CertificateCharacterRepository characterRepository;

    public void clear(){characterRepository.deleteAll();}

    public void load(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("csv/certificate_character.csv").getInputStream(), StandardCharsets.UTF_8))){

            String line;
            reader.readLine();

            while((line = reader.readLine()) != null){
                line = line.trim();

                if(line.isBlank()) continue;

                String []tokens = line.split(",", -1);
                String certificateName = tokens[0].trim(); // certificateId → name

                if(tokens.length < 3) continue;

                Optional<Certificate> certs = certificateRepository.findByName(certificateName);
                if (certs.isEmpty()) {
                    throw new RuntimeException("자격증 이름 '" + certificateName + "'을 찾을 수 없습니다.");
                }
                Certificate cert = certs.get(); // 또는 조건에 따라 적절한 것을 선택


                CertificateCharacter character = new CertificateCharacter();
                character.setCertificate(cert);
                character.setUrl(tokens[1].trim());
                character.setAltText(tokens[2].trim());

                characterRepository.save(character);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
