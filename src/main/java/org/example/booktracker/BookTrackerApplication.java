package org.example.booktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BookTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookTrackerApplication.class, args);
    }

}
