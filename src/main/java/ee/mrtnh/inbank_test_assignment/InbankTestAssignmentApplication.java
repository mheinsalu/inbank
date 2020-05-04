package ee.mrtnh.inbank_test_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class InbankTestAssignmentApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(InbankTestAssignmentApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InbankTestAssignmentApplication.class);
    }
}
