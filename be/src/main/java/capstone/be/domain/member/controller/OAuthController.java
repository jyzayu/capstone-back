package capstone.be.domain.member.controller;


import capstone.be.domain.member.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class OAuthController {


    private final OAuthService oAuthService;

    @GetMapping("/oauth2/code/kakao")
    public String getAuthCode(@RequestParam String code) throws Exception {
        System.out.println("author code = "+ code);
        String access_Token = oAuthService.getToken(code);

        return null;

    }

}
