package ee.mrtnh.inbank_test_assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoanRequest {

    private long personalCode;
    private int requestedLoanAmount;
    private int loanPeriodInMonths;
}
