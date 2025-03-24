package com.example.project3.Model.package1;

import com.example.project3.Model.util.Date;
import com.example.project3.Model.util.List;
import com.example.project3.Model.util.Sort;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */

public class AccountDatabase extends List<Account> {
    public static final int NOT_FOUND = -1;
    public static final int GROW = 4;
    private Archive archive; //a linked list of closed account

    /**
     * Initializes an AccountDataBase object with an array of Accounts, size(number of accounts) = 0, and an Archive
     */
    public AccountDatabase() {
        /*this.accounts = new Account[4];
        this.size = 0;
         */
        this.archive = new Archive();
    }

    /**
     * This helper method is needed for the O command
     * It takes a string passed from user input and returns the AccountType
     * or null if the string entered is not an account type
     *
     * @param string the string to be converted to AccountType
     * @return return the converted type or null
     */

    private AccountType toType(String string) {
        String formattedString = string.trim().toLowerCase();
        switch (formattedString) {
            case "checking":
                return AccountType.Checking;
            case "savings":
            case "regularsavings":
                return AccountType.RegularSavings;
            case "moneymarket":
            case "moneymarketsavings":
                return AccountType.MoneyMarketSavings;
            case "college":
            case "collegechecking":
                return AccountType.CollegeChecking;
            case "certificate":
            case "certificateofdeposit":
            case "cd":
                return AccountType.CD;
            default:
                return null;
        }
    }
    /**
     * This helper method is needed for the O command
     * It takes a string passed from user input and returns the branch
     * or null if the string entered is not an branch
     *
     * @param string the string to be converted to Branch
     * @return return the converted branch or null
     */
    private Branch toBranch(String string) {
        Branch branch = null;
        switch (string.toLowerCase()) {
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
                branch = null;
        }
        return branch;
    }
    /**
     * A method that returns if two accounts have the same account number
     *
     * @param string the object to be compared.
     * @return Campus
     */
    private Campus toCampus(String string) {
        Campus campus = null;
        switch (string) {
            case "1":
                campus = Campus.NewBrunswick;
                break;
            case "2":
                campus = Campus.Newark;
                break;
            case "3":
                campus = Campus.Camden;
                break;
            default:
                campus = null;
        }
        return campus;
    }


    /**
     * A method for subtracting an ammount ot the balance of an Account
     *
     * @param number AccountNumber of the account that is being withdrawn from
     * @param amount The Amount of money client wants to withdraw
     * @return returns a boolean on whether the amount could be with drawn
     */
    public boolean withdraw(AccountNumber number, double amount) {
        if (this.findAccount(number) == NOT_FOUND) {
            return false;
        }
        int index = this.findAccount(number);
        double newBalance = this.get(index).getBalance() - amount;
        if (newBalance >= 2000.00) {
            this.get(index).setBalance(newBalance);
            return true;
        } else if (newBalance > 0.00) {
            this.get(index).setBalance(newBalance);
            return true;
        } else {
            return false;
        }


    }

    /**
     * A method for adding an ammount to the balance of an Account
     *
     * @param number AccountNumber of the account that is being deposited to
     * @param amount The amount of money client wants to deposit
     */

    public void deposit(AccountNumber number, double amount) {
        int index = findAccount(number);
        if (index != NOT_FOUND) {
            double newBalance = this.get(index).getBalance() + amount;
            this.get(index).setBalance(newBalance);
        }
    }


    /**
     * A method for getting the current archived accounts
     */
    public Archive getArchive() {
        return archive;
    }

    public String returnByBranch() {
        Sort.AccountSort(this, 'B');
        StringBuilder sb = new StringBuilder();
        sb.append("*List of accounts ordered by branch location (county, city).\n");
        for (Account account : this) {
            sb.append(account).append("\n");
        }
        sb.append("*end of list.");
        return sb.toString();
    }

    public String returnByHolder() {
        Sort.AccountSort(this, 'H');
        StringBuilder sb = new StringBuilder();
        sb.append("*List of accounts ordered by account holder and number.\n");
        for (Account account : this) {
            sb.append(account).append("\n");
        }
        sb.append("*end of list.");
        return sb.toString();
    }

    public String returnByType() {
        Sort.AccountSort(this, 'T');
        StringBuilder sb = new StringBuilder();
        sb.append("*List of accounts ordered by account type and number.\n");
        for (Account account : this) {
            sb.append(account).append("\n");
        }
        sb.append("*end of list.");
        return sb.toString();
    }


    /**
     * A search method that traverses accounts in search for specfic account by reference of accountNumber String
     *
     * @param acctNumber String that is being searched for in array accounts
     * @return returns an index int value of where in the array the parameter account is, return the index or -1 not found.
     */

    public int findAccount(String acctNumber) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null) {
                if (this.get(i).getNumber().toString().equals(acctNumber)) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.


    public int findAccount(AccountNumber acctNumber) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null) {
                if (this.get(i).getNumber().toString().equals(acctNumber.toString())) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    } //return the index or -1 not found.


    /**
     * A method for printing the current archived accounts
     */
    public String printStatements() {
        Sort.AccountSort(this, 'H');
        StringBuilder sb = new StringBuilder();
        sb.append("*Account statements by account holder*\n");

        List<Profile> seenProfiles = new List<>(); // your custom util.List

        for (int i = 0; i < this.size(); i++) {
            Account currentAccount = this.get(i);
            Profile currentHolder = currentAccount.getHolder();

            boolean alreadyPrinted = false;
            for (int j = 0; j < seenProfiles.size(); j++) {
                if (seenProfiles.get(j).equals(currentHolder)) {
                    alreadyPrinted = true;
                    break;
                }
            }
            if (alreadyPrinted) continue;

            seenProfiles.add(currentHolder);
            sb.append(seenProfiles.size()).append(". ").append(currentHolder).append("\n");

            for (int k = 0; k < this.size(); k++) {
                Account account = this.get(k);
                if (account.getHolder().equals(currentHolder)) {
                    sb.append("[Account #] ").append(account.getNumber()).append("\n");
                    sb.append(account.statement()).append("\n\n"); // assuming .statement() returns a String
                }
            }
        }

        sb.append("*end of statements*");
        return sb.toString();
    } //print account statements


    /**
     * A method for printing the current archived accounts
     */
    public void loadAccounts(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String account = scanner.nextLine().trim();
            if (account.isEmpty())
                continue;
            String[] token = account.split(",");
            AccountType type = toType(token[0]);
            Branch branch = toBranch(token[1]);
            Profile holder = new Profile(token[2], token[3], new Date(token[4]));
            double balance = Double.parseDouble(token[5]);
            int term = 0;
            Campus c = null;
            Date start = null;
            if (token.length > 6) {
                if (token[0].equals("certificate")) {
                    term = Integer.parseInt(token[6]);
                } else {
                    c = toCampus(token[6]);
                }
            }
            if (token.length > 7) {
                start = new Date(token[7]);
            }

            Account acct = null;
            if (token.length > 7) {
                acct = createAccount(type, branch, holder, balance, term, start);
            } else if (token.length > 6) {
                acct = createAccount(type, branch, holder, balance, c);
            } else {
                acct = createAccount(type, branch, holder, balance);
            }
            if(!this.contains((acct))){
                this.add(acct);
            }

        }

    }

    public Account createAccount(AccountType accountType, Branch branch, Profile holder, Double balance) {
        Account acct = null;
        AccountNumber acctnum = null;

        switch (accountType) {
            case Checking:
                acctnum = new AccountNumber(branch, AccountType.Checking);
                acct = new Checking(acctnum, holder, balance);
                break;
            case RegularSavings:
                acctnum = new AccountNumber(branch, AccountType.RegularSavings);
                if (SavingsLoyal(holder)) {
                    acct = new Savings(acctnum, holder, balance, true);
                } else {
                    acct = new Savings(acctnum, holder, balance, false);
                }
                break;
            case MoneyMarketSavings:
                acctnum = new AccountNumber(branch, AccountType.MoneyMarketSavings);
                if (balance >= 5000) {
                    acct = new MoneyMarket(acctnum, holder, balance, true);
                } else {
                    acct = new MoneyMarket(acctnum, holder, balance, false);
                }
                break;
            default:
                break;
        }
        return acct;
    }

    public Account createAccount(AccountType accountType, Branch branch, Profile holder, Double balance, Campus campus) {
        AccountNumber acctnum = new AccountNumber(branch, AccountType.CollegeChecking);
        Account acct = new CollegeChecking(acctnum, holder, balance, campus);
        return acct;
    }

    public Account createAccount(AccountType accountType, Branch branch, Profile holder, Double balance, int term, Date start) {
        AccountNumber acctnum = new AccountNumber(branch, AccountType.CD);
        Account acct = new CertificateDeposit(acctnum, holder, balance, term, start);
        return acct;
    }

    public boolean SavingsLoyal(Profile holder) {
        for (int i = 0; i < size(); i++) {
            Account account = this.get(i);
            if (account != null && account.getHolder().equals(holder)) {
                if (account.getNumber().getAccountType().equals(AccountType.Checking)) {
                    return true; // Immediately return true once a checking account is found
                }
            }
        }
        return false;
    }


    /**
     * A method for printing the current archived accounts
     */
    public String processActivities(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        List<Activity> process = new List<Activity>();
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String account = scanner.nextLine().trim();
            if (account.isEmpty())
                continue;
            String[] token = account.split(",");
            String type = (token[0]);
            String accountNumberStr = token[1].trim();
            int index = findAccount(accountNumberStr);
            Account acct = get(index);
            AccountNumber accountNumber = acct.getNumber();
            Date date = new Date(token[2]);
            Branch location = toBranch(token[3]);
            double amount = Double.parseDouble(token[4]);
            //boolean atm  = Boolean.parseBoolean(token[5]);
            Activity act = new Activity(date,location,type.charAt(0),amount, true );
            acct.addActivity(act);
            String WoD = "";
            if(type.charAt(0) == 'W'){
                WoD = "W";
                withdraw(accountNumber, amount);
            }
            else if(type.charAt(0) == 'D'){
                WoD = "D";
                deposit(accountNumber, amount);
            }
            sb.append(accountNumber).append("::");
            sb.append(date).append("::");
            sb.append(location.getBranchName().toUpperCase()).append("[ATM]::");
            sb.append(WoD).append("::");
            sb.append(String.format("$%,.2f", amount)).append("\n");
        }
        return sb.toString();


    }

}
