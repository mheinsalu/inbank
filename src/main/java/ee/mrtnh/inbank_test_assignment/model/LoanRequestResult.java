package ee.mrtnh.inbank_test_assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoanRequestResult {

    private boolean decision;
    private double loanAmount;
    private double loanPeriod;
    private String message;

    public LoanRequestResult(boolean decision, double loanAmount, double loanPeriod) {
        this.decision = decision;
        this.loanAmount = loanAmount;
        this.loanPeriod = loanPeriod;
        if (decision) {
            this.message = "Loan approved";
        } else {
            this.message = "Loan not approved";
        }
    }

    public LoanRequestResult(String message) {
        this.decision = false;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoanRequestResult{" +
                "decision=" + decision +
                ", loanAmount=" + loanAmount +
                ", loanPeriod=" + loanPeriod +
                ", message='" + message + '\'' +
                '}';
    }
}
