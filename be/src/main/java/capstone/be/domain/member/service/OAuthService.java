package capstone.be.domain.member.service;

import capstone.be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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

            System.out.println("2");

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body" + result);

            JSONObject jsonObject = new JSONObject(result);
            access_Token = jsonObject.getString("access_token");
            refresh_Token = jsonObject.getString("refresh_token");

            System.out.println("access_Token = " + access_Token);
            System.out.println("refresh_Token = " + refresh_Token);

            System.out.println("3");
            bw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

}