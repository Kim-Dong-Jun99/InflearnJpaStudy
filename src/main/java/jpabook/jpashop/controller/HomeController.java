package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // 로그 찍게해주는 듯?
public class HomeController {



    @RequestMapping("/") // 첫번째 화면으로 오면 여기로 온다
    public String home() {
        log.info("home controller");
        return "home"; // home.html로 간다
    }
}
