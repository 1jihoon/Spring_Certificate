package com.example.spring_certificate.Controller;


import com.example.spring_certificate.Dto.LoginDto;
import com.example.spring_certificate.Entity.Login;
import com.example.spring_certificate.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final LoginService loginService;

    /** 회원가입 화면 표시 */
    @GetMapping("/register")
    public String showRegisterForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if(session.getAttribute("loginId") != null){
            redirectAttributes.addFlashAttribute("accessDeniedMessage", "회원가입 또는 로그인 창은 로그아웃후에 접근해주세요.");
            return "redirect:certificates";
        }
        model.addAttribute("member", new Login());
        return "memberjoin"; // → templates/memberjoin.html 렌더링
    }

    /** HTML 기반 회원가입 처리 */
    @PostMapping("/register")
    public String register(@ModelAttribute LoginDto loginDto, Model model) {
        try{
            loginService.register(loginDto); // ← 실제 엔티티를 받음
            return "redirect:/login";
        }
        catch(RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("member", loginDto);
            return "memberjoin";
        }
    }
}
