package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/** postman으로 테스트
 *
 * json 내용 -> {"username":"hello", "age":20}
 * content-type: application/json  -> 이렇게 되어야 json 처리 가능.
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //스트림 읽기
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        HelloData helloData= objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData={}", helloData);
        response.getWriter().write("ok");
    }

    // 스트림 안쓰고, @RequestBody와 @ResponseBody 애노테이션 적용!
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        // objectMapper : JSON 데이터 -> 자바 객체로 변환
        HelloData helloData= objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData={}", helloData);

        return "ok"; // @ResponseBody 쓰니까, 리턴값은 String 가능.
    }

    /**
     * objectMapper 안쓰고, 객체 파라미터를 넘기자!
     * @RequestBody 는 http header의 content-Type이 JSON이라면, http body의 내용을 문자나 객체로 변환해준다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("helloData={}", helloData);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("helloData={}", data);
        return "ok";
    }

    // HelloData라는 객체가 http message converter에 의해 json으로 변환되어 http body에 박혀서 리턴된다.
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) { // json -> HelloData객체
        log.info("helloData={}", data);
        return data; // HelloData객체 -> json
    }
}
