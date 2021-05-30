package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;


// @RestController : 반환값으로 뷰를 주지 않음. 반환 값을 HTTP body에 바로 입력한다.
@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
    @RequestMapping에 http 메서드 속성을 지정하지 않으면, 메서드와 무관하게 전부 호출 가능.
    "/hello-basic" URL 호출이 오면 매핑된다. URL 여러개를 배열로 지정 가능.
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET) //
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    /**
    축약 애노테이션
    @GetMapping
    @PostMapping
    @PutMapping 등이 있다.
     */
    @GetMapping("/mapping-get-v2") // "/hello-basic" URL 호출이 오면 매핑된다. URL 여러개를 배열로 지정 가능.
    public String mappingGetV2(){
        log.info("mappingGetV2");
        return "ok";
    }

    /** [중요]  Path Variable (경로변수)형태 매핑
    변수 명이 같으면 생략 가능
    예를 들어, /mapping/userA  처럼 URL자체에 값이 들어오는 경우.
    @PathVariable("userId") String data
    */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /*  경로변수명과 함수 내부의 변수명이 같으면, 생략가능
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId){
        log.info("mappingPath userId={}", userId);
        return "ok";
    }
    */

    /**
    Path Variable (경로변수)형태 다중 매핑
    */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑  (드물게 사용)
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑 (드물게 사용)
     * 헤더에 값을 주는 스타일
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * 컨트롤러 입장에서 이것을 '소비'하는 것. 요청의 Content-Type을 받아들임.
     * Content-Type 헤더 기반 추가 매핑 Media Type * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * 컨트롤러가 '생산해 내는' 것이니까 produces "Accept 헤더 기반"
     * 예시 ) 클라이언트가 produces = "text/html" html을 받아들일 수 있는 경우.
     * Accept 헤더 기반 Media Type * produces = "text/html"
     * produces = "!text/html" * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }

}
