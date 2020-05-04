package ee.mrtnh.inbank_test_assignment.controller;

import ee.mrtnh.inbank_test_assignment.model.LoanRequest;
import ee.mrtnh.inbank_test_assignment.model.LoanRequestResult;
import ee.mrtnh.inbank_test_assignment.service.InbankService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class InbankController {

    @Autowired
    InbankService inbankService;

    @RequestMapping(value = "/loanRequest", method = RequestMethod.POST)
    // on external Tomcat the url is <artifact name> + value
    @ResponseBody
    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public LoanRequestResult loanRequestEndpoint(@RequestBody LoanRequest loanRequest) {
        log.info("Called loanRequestEndpoint");
        // TODO: casting error: not numbers. custom validation error?
        // TODO: unit tests?
        try {
            LoanRequestResult loanRequestResult = inbankService.processLoanRequest(loanRequest);
            log.info("Returning loan request result");
            return loanRequestResult;
        } catch (Exception e) {
            log.error("Unhandled exception", e);
            return new LoanRequestResult("Internal server error");
        }
    }
}
