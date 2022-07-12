package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // hello라는 url로 오는 get 리퀘스트에 매핑,
    @GetMapping("hello")
    public String hello(Model model) {
        // 모델에 데이터를 실어서 뷰에 넘길 수 있다
        model.addAttribute("data", "hello!!!");
        return "hello";
        // 뷰이름을 리턴한다,
        // 타임리프가 매핑을 알아서 해준다,
    }
}
