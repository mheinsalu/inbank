package ee.mrtnh.inbank_test_assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class InbankUiController {

    private static final String HOME_VIEW = "home";

    @GetMapping(value = "/inbank")
    public String showHome() {
        log.info("Loading home page");
        return HOME_VIEW;
    }
}
