package com.liuwangpan.urlconvert;

import com.liuwangpan.urlconvert.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TinyUrlApplicationTest {

    private URL base;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 测试地址
     */
    private final static String test_long_url = "https://baijiahao.baidu.com/s?id=1722459247606477869&wfr=spider&for=pc";


    @BeforeEach
    void setUp() throws MalformedURLException {
        String url = "http://localhost:8080/urlConvertController";
        this.base = new URL(url);
    }


    /**
     * 创建短链case
     *
     * @throws Exception
     */
    @Test
    public void createTest() throws Exception {
        Assert.assertTrue("生成短链失败", StringUtils.isNotBlank(createShortUrl(test_long_url)));
    }

    /**
     * 测试url为空case
     *
     * @throws Exception
     */
    @Test
    public void createRequestUrlNullTest() throws Exception {
        try {
            createShortUrl("");
        } catch (Exception e) {

        }

    }

    /**
     * 创建短链
     *
     * @return
     */
    private String createShortUrl(String longUrl) {
        String requestUrl = this.base.toString() + "/v1/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> param = new HashMap<>();
        param.put("longUrl", longUrl);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(param, headers);
        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(requestUrl, requestEntity, BaseResponse.class);
        String shortUrl = (String) response.getBody().getData();
        return shortUrl;
    }


    /**
     * 获取短链case
     *
     * @throws Exception
     */
    @Test
    public void getOriginalUrlTest() throws Exception {
        String requestUrl = this.base.toString() + "/v1/getOriginalUrl";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> param = new HashMap<>();
        param.put("shortUrl", createShortUrl(test_long_url));
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(param, headers);
        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(requestUrl, requestEntity, BaseResponse.class);
        String longUrl = (String) response.getBody().getData();
        Assert.assertTrue("获取长链失败", StringUtils.isNotBlank(longUrl));
    }

    /**
     * 获取短链为空case
     *
     * @throws Exception
     */
    @Test
    public void getOriginalUrlByUrlIsNullTest() throws Exception {
        try {
            String requestUrl = this.base.toString() + "/v1/getOriginalUrl";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> param = new HashMap<>();
            param.put("shortUrl", "");
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(param, headers);
            ResponseEntity<BaseResponse> response = restTemplate.postForEntity(requestUrl, requestEntity, BaseResponse.class);
             String longUrl = (String) response.getBody().getData();
        } catch (Exception e) {

        }

    }

    @AfterEach
    void tearDown() {
    }
}