package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
// @Controller
// @ResponseBody 를 클래스 레벨에 붙이면, 메서드에 모두 적용된다.
// @RestController ==  @Controller + @ResponseBody
@RestController // Rest API 를 만들 때 사용하는 컨트롤러. 
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok"); //
    }

    // ResponseEntity 는 HttpEntity를 상속받았는데, http 메시지의 헤더, 바디 정보를 가지고 있다.
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK); // 상태 코드 지정 가능
    }

    @ResponseBody // 메시지 컨버터 바로 타서 http 메시지 입력 가능
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    // JSON 처리해보자
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("useraA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK); // 메시지 컨버터를 통해 json형식으로 변환되어서 반환됨.
    }

    @ResponseStatus(HttpStatus.OK) // http 응답 코드 설정 가능
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("useraA");
        helloData.setAge(20);
        return helloData;
    }
}
