package com.example.securitycontext.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication authentication){
//        SecurityContext securityContext =
//                SecurityContextHolder.getContext();
//        return "Hello,"+ securityContext.getAuthentication().getName()+ "!";
        return "Hello,"+ authentication.getName() + "!";
    }

    @Async
    @GetMapping("/bye")
    public void goodBye(){
        SecurityContext securityContext =
                SecurityContextHolder.getContext();
        String userName = securityContext
                .getAuthentication()
                .getName();
        System.out.println("Hi," + userName + "!");
    }

    @GetMapping("/welcome")
    public String welcome() throws Exception{
        Callable<String> task = () -> {
            return SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();
        };
        ExecutorService e = Executors.newCachedThreadPool();
        try {
            return "Welcome!" + e.submit(task).get();
        }finally {
            e.shutdown();
        }
    }
}
