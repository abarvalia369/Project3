package com.example.project3;

import com.example.project3.Model.package1.*;
import com.example.project3.Model.package1.Account;
import com.example.project3.Model.util.Date;
import com.example.project3.Model.util.List;


import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.net.URL;

public class Controller implements Initializable {
    private AccountDatabase database = new AccountDatabase();
    @FXML
    private Label welcomeText;

    @FXML
    protected void open() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private Button test;

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField dob;

    //AccountTypes
    @FXML
    private RadioButton collegeChecking;
    @FXML
    private RadioButton checking;
    @FXML
    private RadioButton savings;
    @FXML
    private RadioButton moneyMarket;
    @FXML
    private RadioButton cd;

    //campus buttons
    @FXML
    private HBox collegeCheckingHBox;
    @FXML
    private RadioButton newBrunswick;
    @FXML
    private RadioButton newark;
    @FXML
    private RadioButton camden;
    @FXML
    private CheckBox loyalCustomer;

    //cd
    @FXML
    private HBox cdHBox;
    @FXML
    private ComboBox<String> termBox;
    @FXML
    private TextField cdDate;

    //initialDeposit
    @FXML
    private TextField initialDeposit;

    @FXML
    private ComboBox<String> branchBox;
    @FXML
    private Button open;
    @FXML
    private Button clearAllFields;

    //toggleGroups
    @FXML
    private ToggleGroup accountType;
    @FXML
    private ToggleGroup campuses;

    //tab 2 (close/deposit/withdraw)
    @FXML
    private TextField acctnum;
    @FXML
    private TextField dorwAmount;
    @FXML
    private Button deposit;
    @FXML
    private Button withdraw;
    @FXML
    private TextField closingDate;
    @FXML
    private Button close;
    @FXML
    private Button closeAll;
    @FXML
    private TextField fname2;
    @FXML
    private TextField lname2;
    @FXML
    private TextField dob2;

    //tab 3 (account database)
    @FXML
    private Button loadAccountsFromFile;
    @FXML
    private Button printByHolder;
    @FXML
    private Button printByType;
    @FXML
    private Button printArchive;
    @FXML
    private Button loadActivitiesFromFile;
    @FXML
    private Button printByBranch;
    @FXML
    private Button printAccountStatements;

    //textArea
    @FXML
    private TextArea textArea;

    /**
     * initialize
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        termBox.setItems(FXCollections.observableArrayList("3","6","9","12"));
        branchBox.setItems(FXCollections.observableArrayList("Edison","Bridgewater","Princeton","Piscataway", "Warren"));
        //campuses
        collegeCheckingHBox.disableProperty().bind(collegeChecking.selectedProperty().not());

        //newBrunswick.disableProperty().bind(collegeChecking.selectedProperty().not());
        //newark.disableProperty().bind(collegeChecking.selectedProperty().not());
        //camden.disableProperty().bind(collegeChecking.selectedProperty().not());

        loyalCustomer.disableProperty().bind(savings.selectedProperty().not().and(moneyMarket.selectedProperty().not()));

        cdHBox.disableProperty().bind(cd.selectedProperty().not());

        //when clicking off college checking campus selection will go back to false etc.
        accountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            boolean isCollegeChecking = (newValue == collegeChecking);
            if( !isCollegeChecking ){
                campuses.selectToggle(null);
            }

            boolean isSavings = (newValue == savings);
            if( !isSavings ){
                loyalCustomer.setSelected(false);
            }

            boolean isCd = (newValue == cd);
            if( !isCd ){
                termBox.getSelectionModel().clearSelection();
            }

        });

    }

    /**
     * Functionality for the clear all button
     */
    @FXML
    private void clearAll(){
        fname.clear();
        lname.clear();
        dob.clear();
        collegeChecking.setSelected(false);
        checking.setSelected(false);
        savings.setSelected(false);
        moneyMarket.setSelected(false);
        cd.setSelected(false);
        termBox.getSelectionModel().clearSelection();
        cdDate.clear();
        branchBox.getSelectionModel().clearSelection();
        initialDeposit.clear();
    }

    // Example handlers you'll implement (all mapped via FXML)
    @FXML
    private void handleOpenAccount() {
        String result = openAccountLogic(); // helper method
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleDeposit() {
        String accountNumberStr = acctnum.getText();
        String amountStr = dorwAmount.getText();
        String result = depositLogic(accountNumberStr, amountStr); // helper method
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleWithdraw() {
        String accountNumberStr = acctnum.getText();
        String amountstr = dorwAmount.getText();
        String result = withdrawLogic(accountNumberStr, amountstr); // helper method
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleCloseByAccount() {
        String result = closeByAcc();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleCloseByProfile(){
        String result = closeByProfile();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleReturnAccountsByHolder() {
        String result = returnByHolderLogic();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleReturnAccountsByType() {
        String result = returnByTypeLogic();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleReturnAccountsByBranch() {
        String result = returnByBranchLogic();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleReturnArchive() {
        String result =  returnArchiveLogic();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleReturnStatements() {
        String result = returnStatementLogic();
        textArea.appendText(result + "\n");
    }

    @FXML
    private void handleLoadAccounts(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select accounts.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            textArea.appendText("No file selected.\n");
            return;
        }

        try {
            database.loadAccounts(file);
            textArea.appendText("Accounts from '" + file.getName() + "' loaded to the database.\n");
        } catch (IOException e) {
            textArea.appendText("Error loading accounts from file: " + file.getName() + "\n");
        }

    }

    @FXML
    private void handleLoadActivities(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select activities.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            textArea.appendText("No file selected.\n");
            return;
        }

        try {
            textArea.appendText("Processing '" + file.getName() + "'...\n");
            textArea.appendText(database.processActivities(file));
            textArea.appendText("Activities from '" + file.getName() + "' processed.\n");
        } catch (IOException e) {
            textArea.appendText("Error loading activities from file: " + file.getName() + "\n");
        }

    }


    private String returnByHolderLogic(){
        return database.returnByHolder();
    }

    private String returnByTypeLogic(){
        return database.returnByType();
    }

    private String returnByBranchLogic(){
        return database.returnByBranch();
    }


    private String returnArchiveLogic(){
        return database.getArchive().print();
    }

    private String returnStatementLogic(){
        return database.printStatements();
    }




    // Add all other handlers...

    // 👇 These are helper methods (NO @FXML), adapted from TM
    private String openAccountLogic() {

        if (fname.getText().isEmpty() || lname.getText().isEmpty() || dob.getText().isEmpty() || initialDeposit.getText().isEmpty() || branchBox.getValue() == null)
            return "Missing data needed to open an account.";

        String accountTypeStr = ((RadioButton) accountType.getSelectedToggle()).getText().toLowerCase();
        accountTypeStr = accountTypeStr.replaceAll("\\s+", "");
        String branchStr = branchBox.getValue().toLowerCase();
        double amount;
        try { amount = Double.parseDouble(initialDeposit.getText()); } catch (Exception e) { return "Invalid initial deposit."; }

        String[] dobParts = dob.getText().split("/");
        int dobMonth = Integer.parseInt(dobParts[0]);
        int dobDay = Integer.parseInt(dobParts[1]);
        int dobYear = Integer.parseInt(dobParts[2]);
        Date dobDate = new Date(dobYear, dobMonth, dobDay);

        Profile holder = new Profile(fname.getText(), lname.getText(), dobDate);

        Branch branch = Branch.valueOf(branchStr.substring(0, 1).toUpperCase() + branchStr.substring(1).toLowerCase());
        Account account = null;

            if (!dobDate.isValid()) { return "DOB invalid: " + dobDate; }
            int age = getAge(dobDate);
            if (age < 18) { return "Applicant must be at least 18 years old."; }

            AccountType type;
            switch (accountTypeStr.trim().toLowerCase()) {
                case "checking":
                    type = AccountType.Checking;
                    break;
                case "regularsavings", "savings":
                    type = AccountType.RegularSavings;
                    break;
                case "moneymarketsavings", "moneymarket":
                    type = AccountType.MoneyMarketSavings;
                    break;
                case "college", "collegechecking":
                    type = AccountType.CollegeChecking;
                    break;
                case "certificate", "certificatedeposit", "cd":
                    type = AccountType.CD;
                    break;
                default:
                    return accountType + " is an invalid account type.";
            }
            switch (branchStr.toLowerCase()) {
                case "edison":
                    branch = Branch.Edison;
                    break;
                case "bridgewater":
                    branch = Branch.Bridgewater;
                    break;
                case "princeton":
                    branch = Branch.Princeton;
                    break;
                case "piscataway":
                    branch = Branch.Piscataway;
                    break;
                case "warren":
                    branch = Branch.Warren;
                    break;
                default:
                    return branchStr + " is an invalid branch.";
            }


            if (amount <= 0 || (type == AccountType.MoneyMarketSavings && amount < 2000) || (type == AccountType.CD && amount < 1000)){
                return "Invalid initial deposit amount.";
            }

            switch (type) {
                case Checking:
                case RegularSavings:
                case MoneyMarketSavings:
                    // Use the 4-arg createAccount
                    if (exists(holder,type)) {
                        return holder + " already has a " + type + " account.";
                    }
                    account = database.createAccount(type, branch, holder, amount);
                    break;
                case CollegeChecking:
                    if (campuses.getSelectedToggle() == null) {
                        return "Missing data tokens for opening an account.";
                    }
                    if (age >= 24) {
                        return "Not eligible to open: " + dob.toString() + " over 24.";
                    }
                    if (exists(holder,type)) {
                        return holder + " already has a " + type + " account.";
                    }

                    RadioButton selectedCampus = (RadioButton) campuses.getSelectedToggle();
                    String campusText = selectedCampus.getText().toLowerCase();

                    Campus campus;
                    switch (campusText) {
                        case "new brunswick":
                            campus = Campus.NewBrunswick;
                            break;
                        case "newark":
                            campus = Campus.Newark;
                            break;
                        case "camden":
                            campus = Campus.Camden;
                            break;
                        default:
                            return "Invalid campus selection.";
                    }

                    account = database.createAccount(type, branch, holder, amount, campus);
                    break;
                case CD:
                    if (termBox.getValue() == null || cdDate.getText().isEmpty()) {
                        return "Missing data tokens for opening an account.";
                    }
                    int term = Integer.parseInt(termBox.getValue());
                    Date startDate = new Date(cdDate.getText());
                    //Date startDate = new Date(startVal.getYear(), startVal.getMonthValue(), startVal.getDayOfMonth());
                    if(term != 3 && term != 6 && term != 9 && term != 12){
                        return term + " is not a valid term.";
                    }
                    account = database.createAccount(type, branch, holder, amount, term, startDate);
                    break;
            }

            database.add(account);
            return type + " account " + account.getNumber() + " has been opened." + dobDate;

    }

    private int getAge(Date birth){
        Date today = new Date();
        int todayYear = today.getYear();
        int todayMonth = today.getMonth();
        int todayDay = today.getDay();

        int birthYear = birth.getYear();
        int birthMonth = birth.getMonth();
        int birthDay = birth.getDay();

        int age = todayYear - birthYear;
        if (todayMonth < birthMonth || (todayMonth == birthMonth && todayDay < birthDay)) {
            age--;
        }
        return age;
    }

    private boolean exists(Profile holder, AccountType type){
            for (int i = 0; i < this.database.size(); i++) {
                Account existing = this.database.get(i);

                if (existing.getHolder().equals(holder) && existing.getNumber().getAccountType() == type) {
                    return true;
                }
            }

            return false;
        }

    private Campus parseCampus(String string) {
            switch (string) {
                case "1": return Campus.NewBrunswick;
                case "2": return Campus.Newark;
                case "3": return Campus.Camden;
                default:  return null;
            }
        }

    private String depositLogic(String acctnumber, String dAmount) {
        int index = database.findAccount(acctnumber);
        if (index == -1) return acctnumber + " does not exist.";

        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();
        double depositAmount;

        try { depositAmount = Double.parseDouble(dAmount); }
        catch (NumberFormatException e) { return "Invalid deposit type: " + dAmount; }

        if (depositAmount <= 0) return depositAmount + " - deposit amount cannot be 0 or negative.";

        Date date = new Date();
        Branch branch = acctNum.getBranch();
        Activity act = new Activity(date, branch, 'D', depositAmount, true);
        acct.addActivity(act);
        //String old = "OLD: " + acct.getBalance();
        database.deposit(acctNum, depositAmount);
        return String.format("$%.2f deposited to %s. New balance: $%.2f", depositAmount, acctnumber, acct.getBalance());
    }

    private String withdrawLogic(String acctnumber, String wAmount) {
        int index = database.findAccount(acctnumber);
        if (index == -1) return acctnumber + " does not exist.";

        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();
        double withdrawAmount;
        try {
            withdrawAmount = Double.parseDouble(wAmount);
        } catch (NumberFormatException e) {
            return "Invalid deposit type: " + wAmount;
        }

        if (withdrawAmount <= 0) return withdrawAmount + " - deposit amount cannot be 0 or negative.";
        String old = "Old: " + acct.getBalance() + " ";
        boolean success = database.withdraw(acctNum, withdrawAmount);
        if (!success && acctNum.getAccountType() == AccountType.MoneyMarketSavings && acct.getBalance() < 2000) {
            return acctnumber + " balance below $2,000 - withdrawing " + withdrawAmount + " - insufficient funds.";
        } else if (!success) {
            return acctnumber + " withdrawing " + withdrawAmount + " - insufficient funds.";
        } else if (acctNum.getAccountType() == AccountType.MoneyMarketSavings && acct.getBalance() < 2000) {
            return acctnumber + " balance below $2,000 - " + withdrawAmount + " withdrawn from " + acctnumber;
        } else {
            Date date = new Date();
            Branch branch = acctNum.getBranch();
            Activity act = new Activity(date, branch, 'W', withdrawAmount, false);
            acct.addActivity(act);
            return old + String.format("$%.2f withdrawn from %s. New balance: $%.2f", withdrawAmount, acctnumber, acct.getBalance());
        }
    }



    private String closeByAcc(){
        String accountNumberStr = acctnum.getText().trim();
        if (closingDate.getText().isEmpty()) {
            return "Missing data: Please select a closing date.";
        }

        String[] closeParts = closingDate.getText().split("/");
        int closeMonth = Integer.parseInt(closeParts[0]);
        int closeDay = Integer.parseInt(closeParts[1]);
        int closeYear = Integer.parseInt(closeParts[2]);
        Date closeDate = new Date(closeYear, closeMonth, closeDay);

        int index = database.findAccount(accountNumberStr);
        if(index == -1){
            return accountNumberStr + " does not exist";
        }
        Account acct = database.get(index);
        AccountNumber acctNum = acct.getNumber();
        String s1, s2, s3 = "";
        double earnedInterest = 0;
        if (acct instanceof CertificateDeposit cd) {
            Date openDate = cd.getOpen(); // assuming getter exists
            int daysOpen = daysBetween(openDate, closeDate) + 1;
            if (daysOpen >= cd.getTerm() * 30) {
                double rate = cd.getRate(); // based on 3, 6, 9, 12 month terms
                earnedInterest = cd.getBalance() * (rate / 365.0) * daysOpen;
                String formatted = String.format("$%.2f", earnedInterest);
                s1 = "Closing account " + acctNum.toString();
                s2 = "--" + acctNum + " interest earned: " + formatted;
            } else {
                double earlyRate;
                if (daysOpen / 30.0 <= 6) earlyRate = 0.03;
                else if (daysOpen / 30.0 <= 9) earlyRate = 0.0325;
                else earlyRate = 0.035;

                earnedInterest = cd.getBalance() * (earlyRate / 365.0) * daysOpen;
                double penalty = earnedInterest * 0.10;
                penalty = roundUpToTwoDecimal(penalty);
                String formatted = String.format("$%.2f", earnedInterest);
                s1 = "Closing account " + acctNum.toString();
                s2 = "--interest earned: " + formatted;
                s3 = "  [penalty] $" + penalty;
            }
        } else {
            int daysInMonth = closeDate.getDay();
            double annualRate = getAnnualRate(acct);
            earnedInterest = acct.getBalance() * (annualRate / 365.0) * daysInMonth;
            s1 = "Closing account " + acctNum.toString();
            String formatted = String.format("$%.2f", earnedInterest);
            s2 = "--interest earned: " +  formatted;
        }
        database.getArchive().add(acct, closeDate);
        database.remove(acct);
        return s1 + "\n" + s2 + "\n" + s3;
    }

    private String closeByProfile(){
        String firstName = fname2.getText().trim();
        String lastName = lname2.getText().trim();
        if (closingDate.getText().isEmpty()) {
            return "Missing data: Please select a closing date.";
        }
        String[] closeParts = closingDate.getText().split("/");
        if (closeParts.length != 3) {
            return "Closing date must be in MM/DD/YYYY format.";
        }
        int closeMonth, closeDay, closeYear;
        try {
            closeMonth = Integer.parseInt(closeParts[0]);
            closeDay = Integer.parseInt(closeParts[1]);
            closeYear = Integer.parseInt(closeParts[2]);
        } catch (NumberFormatException e) {
            return "Closing date contains invalid numbers.";
        }
        Date closeDate = new Date(closeYear, closeMonth, closeDay);

        String[] dobParts = dob2.getText().trim().split("/");
        if (dobParts.length != 3) {
            return "DOB must be in MM/DD/YYYY format.";
        }
        int dobMonth, dobDay, dobYear;
        try {
            dobMonth = Integer.parseInt(dobParts[0]);
            dobDay = Integer.parseInt(dobParts[1]);
            dobYear = Integer.parseInt(dobParts[2]);
        } catch (NumberFormatException e) {
            return "DOB contains invalid numbers. Please enter valid digits (MM/DD/YYYY).";
        }
        Date birth = new Date(dobYear, dobMonth, dobDay);


        Profile profile = new Profile(firstName, lastName, birth);

        boolean found = false;
        StringBuilder sb = new StringBuilder();
        sb.append("Closing accounts for ").append(profile).append("\n");

        // Loop through accounts and collect matches
        for (int i = 0; i < database.size(); i++) {
            Account acct = database.get(i);
            if (acct != null && acct.getHolder().equals(profile)) {
                found = true;
                AccountNumber acctNum = acct.getNumber();
                double earnedInterest = 0;
                if (acct instanceof CertificateDeposit cd) {
                    Date openDate = cd.getOpen(); // assuming getter exists
                    int daysOpen = daysBetween(openDate,closeDate) + 1;
                    if (daysOpen >= cd.getTerm() * 30) {
                        double rate = cd.getRate(); // based on 3, 6, 9, 12 month terms
                        earnedInterest = cd.getBalance() * (rate / 365.0) * daysOpen;
                        sb.append("--").append(acctNum).append(" interest earned: ")
                                .append(String.format("$%.2f", earnedInterest)).append("\n");
                    } else {
                        double earlyRate = 0.;
                        if (daysOpen / 30.0  <= 6) earlyRate = 0.03;
                        else if (daysOpen / 30.0 <= 9) earlyRate = 0.0325;
                        else if (daysOpen / 30.0  < 12) earlyRate = 0.035;

                        earnedInterest = cd.getBalance() * (earlyRate / 365.0) * daysOpen;
                        double penalty = earnedInterest * 0.10;
                        penalty = roundUpToTwoDecimal(penalty);
                        sb.append("--").append(acctNum).append(" interest earned: ")
                                .append(String.format("$%.2f", earnedInterest)).append("\n");
                        sb.append("  [penalty] $").append(penalty).append("\n");
                    }
                } else {
                    int daysInMonth = closeDate.getDay();
                    double annualRate = getAnnualRate(acct);
                    earnedInterest = acct.getBalance() * (annualRate / 365.0) * daysInMonth;
                    sb.append("--").append(acctNum).append(" interest earned: ")
                            .append(String.format("$%.2f", earnedInterest)).append("\n");
                }

                database.getArchive().add(acct, closeDate);
                database.remove(acct);
                i--;
            }
        }
        if (!found) {
            sb.append(profile).append(" does not have any accounts in the database.\n");
        } else {
            sb.append("All accounts for ").append(profile).append(" are closed and moved to archive.");
        }
        return sb.toString();
    }

    double roundUpToTwoDecimal(double amount) {
        return Math.ceil(amount * 100.0) / 100.0;
    }


    private double getAnnualRate(Account acct){
        AccountType type = acct.getNumber().getAccountType();

        if (type == AccountType.RegularSavings && acct instanceof Savings savings) {
            return savings.loyalty() ? 0.0275 : 0.025;
        }
        else if (type == AccountType.MoneyMarketSavings && acct instanceof MoneyMarket moneyMarket) {
            return moneyMarket.loyalty() ? 0.0375 : 0.035;
        }
        else if (type == AccountType.Checking || type == AccountType.CollegeChecking) {
            return 0.015;
        }

        return 0.0; // unknown type
    }


    public int daysBetween(Date d1, Date d2) {
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        int total1 = d1.getYear() * 365 + d1.getDay();
        for (int i = 0; i < d1.getMonth() - 1; i++) {
            total1 += daysInMonth[i];
        }

        int total2 = d2.getYear() * 365 + d2.getDay();
        for (int i = 0; i < d2.getMonth() - 1; i++) {
            total2 += daysInMonth[i];
        }

        return Math.abs(total2 - total1);
    }


    /**
     * Function allows user to select one .txt file
     * @param event
     */
    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if( file != null ){
            textArea.appendText(file.getAbsolutePath());
        }

    }

}