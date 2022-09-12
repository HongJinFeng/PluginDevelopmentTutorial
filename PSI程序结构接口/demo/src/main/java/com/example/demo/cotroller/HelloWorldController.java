package com.example.demo.cotroller;

import com.example.demo.dto.User;
import com.example.demo.dto.Order;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/demo/test/gen_doc")
public class HelloWorldController {

    /**
     * hello 接口
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/test_get")
    public String helloWorld(@RequestParam User user) {
        return getHelloWorld();
    }

    /**
     * hello 接口
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/test_post")
    public Order testPost(@RequestBody User user) {
        return new Order();
    }

    private String getHelloWorld() {
        return "Hello World";
    }
}