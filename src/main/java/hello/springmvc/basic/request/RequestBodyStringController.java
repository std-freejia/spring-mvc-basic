package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * String 은 바이트코드다!
     * 바이트코드를 문자로 받을 때는 인코딩 형식을 반드시 지정해줘야 한다. (지정 안하면, OS의 지정 형태대로 받게됨)
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok"); // http body에 "ok"뿌려주기
    }

    /**
     * Method Arguments 로
     * InputStream, Reader 를 받을 수 있다.  -> HTTP 요청 메시지 바디의 내용을 조회
     * OutputStream, Writer 를 받을 수 있다. -> HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    // 메시지 컨버터로 간결하게!
    /**
     * HttpEntity<String>
     * String 이라면?
     * http message converter가 동작하여 Http Body에 있는 것을 문자로 바꿔서 body 내용을 조회!
     *
     * 요청 파라미터를 조회하는 기능과 관계 없음!!
     * 즉, 요청 파라미터 조회 기능 2가지 @RequestParam, @ModelAttribute를 쓰는 것 이외에는
     * 모두 HttpEntity 를 써서 http body 내용을 조회/출력 한다.
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        log.info("messageBody={}", body);
        return new HttpEntity<>("ok"); // 리턴도 가능
    }

    /**
     * @ReqeuestBody 와 @ResponseBody 사용으로 더 간결 하게 body 정보를 조회 및 전송 가능.
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String meessageBody) throws IOException {
        log.info("messageBody={}", meessageBody);
        return "ok"; // @ResponseBody
    }

}
