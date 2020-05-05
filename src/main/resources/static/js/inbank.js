let apiURL = "/";

let MAX_LOAN_AMOUNT = 10000;
let MIN_LOAN_AMOUNT = 2000;
let MAX_LOAN_PERIOD = 60;
let MIN_LOAN_PERIOD = 12;

function applyButtonClick() {
    fetchLoanRequestResult();
}

function getLoanRequestData() {
    let personalCodeInputFieldValue = document.getElementById("personalCodeInputField").value;
    let loanAmountInputFieldValue = document.getElementById("loanAmountInputField").value;
    let loanPeriodInputFieldValue = document.getElementById("loanPeriodInputField").value;

    let dataJson = {
        personalCode: personalCodeInputFieldValue,
        requestedLoanAmount: loanAmountInputFieldValue,
        loanPeriodInMonths: loanPeriodInputFieldValue
    };
    return dataJson;
}

function loanRequestDataViolatesConstraints(loanRequestData) {
    if (isNotNumeric(loanRequestData.personalCode)
        || loanRequestData.personalCode === "") {
        alert("Personal code must be a number");
        return true;
    }
    if (isNotNumeric(loanRequestData.requestedLoanAmount) ||
        loanRequestData.requestedLoanAmount > MAX_LOAN_AMOUNT
        || loanRequestData.requestedLoanAmount < MIN_LOAN_AMOUNT) {
        alert("Loan amount must be between " + MIN_LOAN_AMOUNT + " and " + MAX_LOAN_AMOUNT + " euros.");
        return true;
    }
    if (isNotNumeric(loanRequestData.loanPeriodInMonths)
        || loanRequestData.loanPeriodInMonths > MAX_LOAN_PERIOD
        || loanRequestData.loanPeriodInMonths < MIN_LOAN_PERIOD) {
        alert("Loan period must be between " + MIN_LOAN_PERIOD + " and " + MAX_LOAN_PERIOD + " months.");
        return true;
    }
    return false;
}

function isNotNumeric(num) {
    return isNaN(num)
}

function fetchLoanRequestResult() {
    let url = apiURL + "loanRequest"; // must be right url. url is different on embedded TC and external TC
    let loanRequestData = getLoanRequestData();
    if (loanRequestDataViolatesConstraints(loanRequestData)) {
        return;
    }
    // send JSON request using Fetch API
    fetch(url, {
        body: JSON.stringify(loanRequestData),
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        }
    }).then(
        response => {
            // returns Promise
            return response.json();
        })
        .then((response) => {
            // now it'a JSON not Promise
            document.getElementById("resultsTableDecisionValueCell").innerHTML = response.message;
            document.getElementById("resultsTableLoanAmountValueCell").innerHTML = response.loanAmount;
            document.getElementById("resultsTableLoanPeriodValueCell").innerHTML = response.loanPeriod;
            // alert(JSON.stringify(response));
        });
}
