package hello.springmvc.basic;

import lombok.Data;


/**
 * lombok의 @Data 는
 * @Getter, @Setter, @ToString, @EqualsAndHashCode , @RequiredArgsConstructor 를 자동 적용해준다!
 */
@Data //lombok
public class HelloData {
    private String username;
    private int age;
}
