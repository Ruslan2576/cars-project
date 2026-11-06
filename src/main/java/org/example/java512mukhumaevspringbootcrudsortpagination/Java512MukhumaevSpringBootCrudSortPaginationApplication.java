package org.example.java512mukhumaevspringbootcrudsortpagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Java512MukhumaevSpringBootCrudSortPaginationApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Java512MukhumaevSpringBootCrudSortPaginationApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Java512MukhumaevSpringBootCrudSortPaginationApplication.class, args);
    }

}
