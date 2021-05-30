package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController // 기본적으로 html이 아니고 다른걸로 보냄.
@RequestMapping("/mapping/users")
public class MappingClassController {

    /**
     * Postman으로 URL 매핑 테스트
     * 회원 목록 조회: GET
     * 회원 등록: POST
     * 회원 조회: GET
     * 회원수정: PATCH /mapping/users/id1
     * 회원 삭제: DELETE /mapping/users/id1
     */
    @GetMapping
    public String user(){
        return "get users";
    }

    @PostMapping
    public String addUser(){
        return "post user";
    }

    // 회원 하나 조회
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId){
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete userId=" + userId;
    }
}
