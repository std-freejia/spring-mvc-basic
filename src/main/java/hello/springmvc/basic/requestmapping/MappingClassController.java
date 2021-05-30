package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// postman 에서 매핑 테스트하기
@RestController
public class MappingClassController {

    @GetMapping("/mapping/users")
    public String user(){
        return "get users";
    }

    @PostMapping("/mapping/users")
    public String addUser(){
        return "post user";
    }

    // 회원 하나 조회
    @GetMapping("/mapping/users/{userId}")
    public String findUser(@PathVariable String userId){
        return "get userId=" + userId;
    }

    @PatchMapping("/mapping/users/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userId=" + userId;
    }

    @DeleteMapping("/mapping/users/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete userId=" + userId;
    }
}
