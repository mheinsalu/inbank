package ee.mrtnh.inbank_test_assignment.service;

import ee.mrtnh.inbank_test_assignment.model.Client;
import ee.mrtnh.inbank_test_assignment.model.LoanRequest;
import ee.mrtnh.inbank_test_assignment.model.LoanRequestResult;
import ee.mrtnh.inbank_test_assignment.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InbankService {

    @Autowired
    ClientRepository clientRepository;

    /*
    If read from properties file and value not hardcoded
    @Value("maxLoanAmount")
    String MAX_LOAN_AMOUNT;
    */
    int MAX_LOAN_AMOUNT = 10000;
    int MIN_LOAN_AMOUNT = 2000;
    int MAX_LOAN_PERIOD = 60;
    int MIN_LOAN_PERIOD = 12;

    public LoanRequestResult processLoanRequest(LoanRequest loanRequest) {
        log.info("Processing loan request");
        log.info(loanRequest.toString());

        if (loanRequestViolatesMinimumConstraints(loanRequest)) {
            log.info("Loan request violates minimum constraints");
            return new LoanRequestResult("Loan request violates minimum constraints");
        }

        Client client = clientRepository.findByPersonalCode(loanRequest.getPersonalCode());
        if (client == null) { // no loan if person not client of bank
            log.warn("Client not found in database");
            return new LoanRequestResult("Client not found in database. No loan allowed.");
        }

        if (client.getSegmentCode() == 0) { // no loan if client is in debt
            log.info("Client in debt. No loan allowed.");
            return new LoanRequestResult("Client in debt. No loan allowed.");
        }

        log.info("Client's credit score based on this loan request is " + Math.round(calculateCreditScore(loanRequest, client) * 100.0) / 100.0);

        double maxLoanAmount = calculateMaxLoanAmountForPeriod(loanRequest, client);
        if (maxLoanAmount >= loanRequest.getRequestedLoanAmount()) {
            LoanRequestResult loanRequestResult = new LoanRequestResult(true, maxLoanAmount, loanRequest.getLoanPeriodInMonths());
            log.info("Loan approved: " + loanRequestResult);
            return loanRequestResult;
        }

        if (maxLoanAmount >= MIN_LOAN_AMOUNT) {
            LoanRequestResult loanRequestResult = new LoanRequestResult(true, maxLoanAmount, loanRequest.getLoanPeriodInMonths());
            log.info("Loan approved: " + loanRequestResult);
            return loanRequestResult;
        }

        // max loan for this period is less than MIN_LOAN_AMOUNT
        double periodForMinLoanAmount = calculatePeriodForMinimumLoanAmount(client);
        if (periodForMinLoanAmount > MAX_LOAN_PERIOD) {
            log.info("No loan approved. Period for minimum amount exceeds maximum loan period.");
            return new LoanRequestResult("No loan approved. Period for minimum amount exceeds maximum loan period.");
        }

        LoanRequestResult loanRequestResult = new LoanRequestResult(true, MIN_LOAN_AMOUNT, periodForMinLoanAmount);
        log.info("Loan approved: " + loanRequestResult);
        return loanRequestResult;
    }

    private boolean loanRequestViolatesMinimumConstraints(LoanRequest loanRequest) {
        if (loanRequest.getRequestedLoanAmount() < MIN_LOAN_AMOUNT ||
                loanRequest.getLoanPeriodInMonths() < MIN_LOAN_PERIOD) {
            return true;
        }
        return false;
    }

    private double calculateCreditScore(LoanRequest loanRequest, Client client) {
        return (client.getCreditModifier() / loanRequest.getRequestedLoanAmount())
                * loanRequest.getLoanPeriodInMonths();
    }

    private double calculateMaxLoanAmountForPeriod(LoanRequest loanRequest, Client client) {
        int score = 1;
        int period = loanRequest.getLoanPeriodInMonths();
        double modifier = client.getCreditModifier();
        double maxAmount = (modifier / score) * period;
        return Math.min(maxAmount, MAX_LOAN_AMOUNT);
    }

    private double calculatePeriodForMinimumLoanAmount(Client client) {
        int score = 1;
        double modifier = client.getCreditModifier();
        int amount = MIN_LOAN_AMOUNT;
        double periodInMonths = (score * amount) / modifier;
        return periodInMonths;
    }
}
