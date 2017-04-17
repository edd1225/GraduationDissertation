package com.gangsterhyj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gangsterhyj on 17-1-17.
 */
@SpringBootApplication
@RestController
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
