package org.capitalone.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DashboardApplication {

    public static void main(String[] args) {
        // Pass repository name as an argument or provide a default value
        SpringApplication.run(DashboardApplication.class, args);
    }
}