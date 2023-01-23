package capstone.be.domain.member.service;

import capstone.be.domain.member.repository.MemberRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private MemberRepository memberRepository;

    public String getToken(String code) throws Exception {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=440189d0beaaef7222dfde922bfc23e9");
            sb.append("&redirect_uri=http://localhost:8080/login/oauth2/code/kakao");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();


            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));



            JsonElement jsonElement = JsonParser.parseReader(br);
            access_Token = jsonElement.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = jsonElement.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_Token = " + access_Token);
            System.out.println("refresh_Token = " + refresh_Token);

            bw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }


    public Map<String, Object> getUserInfo(String access_token) {
        Map<String, Object> map = new HashMap<>();
        String read_line = "";
        String result = "";
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization","Bearer " + access_token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JsonElement element = JsonParser.parseReader(br);
            System.out.println("response = " + element);


            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String snsId = element.getAsJsonObject().get("id").getAsString();
            // Todo : 연령, 성별 동의 항목
//            int age = properties.get("age").getAsInt();
            String email = kakao_account.get("email").getAsString();


//            map.put("age", age);
            map.put("email", email);
            // 회원가입 여부를 확인할 snsId
            map.put("snsId", snsId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}