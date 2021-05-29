package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController : 반환값으로 뷰를 주지 않음. 반환 값을 HTTP body에 바로 입력한다.
// @Slf4j : 로그 선언을 자동으로 해준다
@Slf4j
@RestController
public class LogTestController {

    // 로그 선언
    //private final Logger log = LoggerFactory.getLogger(getClass()); // 내 클래스를 지정해준다.

    @GetMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("log"); // 로그 레벨 구분 없이 다 출력되니까, 실무에서는 반드시 로그를 출력해야 한다.

        // 특정 패키지 마다 출력할 로그 레벨을 정할 수 있다. (로컬 pc에서는 trace, 운영 서버에서는 info 이런 식.)
        // application.properties에 특정 레벨을 설정하지 않으면, 디폴트 레벨은 info.

        log.trace("trace log={}", name);
        log.debug("debug log " + name); // 이렇게 쓰지 말기!
        // 왜냐하면, '+'연산이 발생하기 때문이다! (로그레벨 설정 관련 없이 무조건 연산이 발생함. 연산은 메모리와 CPU를 소모한다)

        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name); // 로그 포맷 : {}안에 name이 출력된다.

// 2021-05-29 22:34:39.149  INFO 1076 --- [nio-8080-exec-2] hello.springmvc.basic.LogTestController  : info log=Spring

        return "ok"; // http body 에 문자열을 넣는다.
    }
}
