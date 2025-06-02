package com.example.spring_certificate.Service;

import com.example.spring_certificate.Dto.LoginDto;
import com.example.spring_certificate.Entity.Login;
import com.example.spring_certificate.Repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    //로그인 검증
    public boolean login(LoginDto loginDto){
        return loginRepository.findById(loginDto.getId()).
                map(login -> login.getPassword().equals(loginDto.getPassword()))
                .orElse(false);
    }

    //회원가입
    public void register(LoginDto loginDto){

        if (loginRepository.existsById(loginDto.getId())) {
            throw new IllegalStateException("❗ 이미 사용 중인 ID입니다.");
        }

        if(loginRepository.existsByPassword(loginDto.getPassword())){
            throw new IllegalStateException("❗ 이미 사용 중인 비밀번호입니다.");
        }

        if(loginRepository.existsByEmail(loginDto.getEmail())){
            throw new IllegalStateException("❗ 이미 사용 중인 이메일입니다.");
        }

        Login user = new Login();
        user.setId(loginDto.getId());
        user.setPassword(loginDto.getPassword());
        user.setName(loginDto.getName());
        user.setGender(loginDto.getGender());
        user.setEmail(loginDto.getEmail());
        loginRepository.save(user);//DB에 저장
    }
}
