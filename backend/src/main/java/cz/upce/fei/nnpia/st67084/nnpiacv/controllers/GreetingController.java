package cz.upce.fei.nnpia.st67084.nnpiacv.controllers;

import cz.upce.fei.nnpia.st67084.nnpiacv.services.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    public String greeting() {
         return greetingService.sayGreeting();
    }



}
