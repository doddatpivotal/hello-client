package io.pivotal.helloclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Log
@RestController
public class ClientController {

    private RestTemplate restTemplate;

    public ClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "gibberish")
    @GetMapping("/")
    String get() {
        String language = restTemplate.getForObject("//hello/language", String.class);
        String response = "Your language is " + language;
        log.info("Successfully retrieved language.  " + response);
        return response;
    }

    String gibberish() {
        log.warning("Failure retrieving language, returning gibberish.");
        return "Your language is gibberish";
    }
}
