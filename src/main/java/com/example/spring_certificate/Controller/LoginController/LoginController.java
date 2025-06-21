package com.example.spring_certificate.Controller.LoginController;

import com.example.spring_certificate.Dto.LoginDto;
import com.example.spring_certificate.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /** 로그인 화면 표시 */
    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if(session.getAttribute("loginId") != null){
            redirectAttributes.addFlashAttribute("accessDeniedMessage", "회원가입 또는 로그인 창은 로그아웃후에 접근해주세요.");
            return "redirect:certificates";
        }
        model.addAttribute("loginDto", new LoginDto());
        return "login/loginlayout"; // → templates/login.html 렌더링
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/certificates";
    }

    /** HTML 기반 로그인 처리 */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpSession session, Model model) {
        boolean success = loginService.login(loginDto);

        if(success){
            session.setAttribute("loginId", loginDto.getId());//서버에 계속 남아있어야 하므로 세션을 쓴다
            return "redirect:/certificates";
        }

        // 로그인 실패 시 에러 메시지 전달
        model.addAttribute("loginDto", loginDto);
        model.addAttribute("errorMessage", "❗ ID 또는 비밀번호가 일치하지 않습니다.");
        return "login/loginlayout";
    }

    /** API 기반 로그인 처리 (JSON POST 요청용) *//*
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<String> loginApi(@RequestBody LoginDto loginDto){
        boolean success = loginService.login(loginDto);
        return success ? ResponseEntity.ok("로그인 성공") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    *//** API 기반 회원가입 처리 *//*
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> registerApi(@RequestBody LoginDto loginDto){
        loginService.register(loginDto);
        return ResponseEntity.ok("회원가입 성공");
    }*/ // -> 회원가입에 성공하면 바로 Service에 가서 DB에 저장*/
    //postMapping으로 api 형태의 주소는 json 형태로 리액트에 전달하거나 모바일과 연동할 때 필요하고 thymeleaf로 할 때는 필요 없음.
}
