package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // return 할 String을 HTTP body에 넣어준다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2( // @RequestParam 요청 파라미터를 편리하게 쓸 수 있다
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge){  // == request.getParameter("age")

        log.info("username={}, age={}", memberName, memberAge);
        // http://localhost:8080/request-param-v2?username=hello&age=20
        return "ok";
    }

    @ResponseBody // @RequestParam 변수명 생략
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age){

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // @RequestParam 까지 생략 (String, int, Integer등의 단순 타입인 경우. 생략가능)
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){ // 대신, 요청 파라미터의 이름과 같아야 한다.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // 필수 파라미터 지정
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // required = true, 꼭 받아야 하는 인자!
            @RequestParam(required = false) Integer age){
        /**
         * 참고)
         * int(기본형) 기본값 0, Integer(객체형) 기본값 null
         * String의 경우, null과 ""빈문자는 다르게 인식한다.
         * /?username=  인 경우, "ok"로 넘어간다.
         */

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // 파라미터의 디폴트 값 지정
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age){

        log.info("username={}, age={}", username, age);
        /**
         * defaultValue 를 정의하면 파라미터 입력여부와 관계없이 디폴트값을 넣어주므로, 이 경우에 required 지정은 사실상 무의미해진다.
         *
         * ""같은 빈 문자의 경우, defaultValue 가 적용된다!
         * 예를 들어, /username=   로 요청이 들어오면, username=guest 로 정해진다.
         */
        return "ok";
    }

    @ResponseBody  // 모든 파라미터를 전부 맵으로 받고 싶다!
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        // 단, 파라미터의 값이 1개가 확실할 때 map 쓰기. (MultiValueMap 사용은 드물다)
        return "ok";
    }

    /* @ModelAttribute 적용 전,
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@RequestParam String username, @RequestParam int age){
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData); //toString() 자동 적용

        return "ok";
    }
    */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")  // @ModelAttribute 적용 결과 (매직매직)
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        /**
         *  @ModelAttribute 가 HelloData 객체를 자동 생성한다.
         * URL 요청파라미터의 이름(username, age)으로 객체의 프로퍼티(getter, setter)를 찾는다.
         *
         * 바인딩 오류
         * 바인딩 오류 예시) age 가 int로 들어와야 하는데, 문자열로 들어온다면?
         */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    // @ModelAttribute 생략 가능!
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        /**
         * @RequestParam도 생략할 수 있더니, @ModelAttribute도 생략 가능하다. 스프링은 어떻게 구분하지?
         *
         * @RequestParam 적용 규칙 : String, int ,Integer같은 기본 타입인 경우에 적용.
         * 그 외에 나머지는 전부 @ModelAttribute로 적용.
         */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
