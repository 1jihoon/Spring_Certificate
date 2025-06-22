package com.example.spring_certificate.Controller.LoginController;

import com.example.spring_certificate.Repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DuplicateCheckController {

    private final LoginRepository loginRepository;

    @GetMapping("/api/check-duplicate")
    public Map<String, Boolean> checkDuplicate(@RequestParam String type, @RequestParam String value){
        boolean exists = switch(type){
            case "id" -> loginRepository.existsById(value);
            //case "password" -> loginRepository.existsByPassword(value);
            case "email" -> loginRepository.existsByEmail(value);
            default -> false;
        };
        return Map.of("exists", exists);
    }
}
