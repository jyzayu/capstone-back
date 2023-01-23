package capstone.be.domain.member.controller;


import capstone.be.domain.member.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class OAuthController {


    private final OAuthService oAuthService;

    @GetMapping("/oauth2/code/kakao")
    public String getAuthCode(@RequestParam String code) throws Exception {
        System.out.println("author code = "+ code);
        String access_Token = oAuthService.getToken(code);
        Map<String, Object> result = oAuthService.getUserInfo(access_Token);
        String snsId = (String) result.get("snsId");


        // 일치하는 snsId가 없을 시 회원가입(해당 snsId로 kakaologin을 했을때 null반환하면 회원가입)
        // 프론트가 가입여부 정보를 요청하면 사용자 정보 불러오기를 ? ㄴ
        // 동의항목 끝나고 인가코드전달하고난 후 =>  바로 정보 불러오기 =>
        // 없다면 null을 전달하고 있으면 정보 전달하여 정보와 입력한 것으로 회원가입 요청보내달라하기
        // 일치하는 snsId가 있으면 멤버객체에 담음
        return null;
    }
}
