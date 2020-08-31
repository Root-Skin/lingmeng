package com.lingmeng.test;

import com.lingmeng.entity.UserInfo;
import com.lingmeng.utils.JwtUtils;
import com.lingmeng.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
    private static final String pubKeyPath = "D:\\tem_key\\rsa.pub";

    private static final String priKeyPath = "D:\\tem_key\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token(过期时间5分钟)
        String token = JwtUtils.generateToken(new UserInfo("20", "lingmeng"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1OTg2OTA4NTd9.OtrkMeQnJlUdxZsTjI5KEFMsEjDUg7lmMsAcRrLRuylcPwlWxKCrDllV2rHLE-2HpAMV5r6pi_uhRnHA30Apjd4-653azriF-eG5RbCK2mxKBdQrg3p5-q4bf1svVOZGuqoiWAyI1-Cj2Fo1m5xjE_cg7Etw8Pa8L_hjziil27o";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUserName());
    }
}
