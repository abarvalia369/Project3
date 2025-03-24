package com.example.project3.Model.package1;

import com.example.project3.Model.util.Date;
import com.example.project3.Model.util.List;
/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */

public abstract class Account implements Comparable<Account> {
    protected AccountNumber number;
    protected Profile       holder;
    protected double       balance;
    protected List<Activity> activities; //list of account activities (D or W)

    /**
     * Initializes an Account Object with the following 3 parameters.
     *
     * @param number the account number for the new account object.
     * @param holder the profile holder for the new account object.
     * @param balance the account balance for the new account object.
     */
    public Account(AccountNumber number, Profile holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.activities = new List<Activity>();
    }

    /**
     * Prints out statements with the help of multiple helper methods
     *
     *
     */
    public final void statement() { //Template Method; DO NOT modify
        printActivities(); //private helper method
        double interest = interest(); //polymorphism based on actual type
        double fee = fee(); //polymorphism based on actual type
        printInterestFee(interest, fee); //private helper method
        printBalance(interest, fee); //private helper method
    }

    /**
     * helper method
     *
     *
     */
    public void printActivities(){
        if (activities == null || activities.isEmpty()) {
            return;
        }
        System.out.println("[Activity]");
        for(int i = 0; i < activities.size(); i++){
            System.out.println(String.format(activities.get(i).getDate()  + "::" +  activities.get(i).getLocation().toString().toUpperCase() +
                    "::" + activities.get(i).getType() + "::$%,.2f", activities.get(i).getAmount()));
        }
    }

    public void printInterestFee(double interest,double fee){
        System.out.println("[interest] " + String.format("$%,.2f", interest) + " [Fee] " + String.format("$%.2f", fee));
    }

    public void printBalance(double interest, double fee){
        double newBalance = balance + interest - fee;
        System.out.println("[Balance] " + String.format("$%,.2f", newBalance));
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }//add account activity, D or W

    public abstract double interest(); //monthly interest
    public abstract double fee(); //account fee

    /**
     * A method to withdraw(subtract) money from the current account balance
     *
     * @param amount the amount to be withdrawn
     *
    public void withdraw(double amount) {
    if( amount > balance ){
    System.out.println("Insufficient Funds.");
    }
    this.balance -= amount;
    }

    /**
     * A method to deposit(add) money to the current account balance
     *
     * @param amount the amount to be deposited
     */
    public void deposit(double amount) {
        this.balance += amount;
    }


    public AccountNumber getNumber() {
        return this.number;
    }
    public void setNumber(AccountNumber number){
        this.number = number;
    }
    public Profile getHolder() {
        return this.holder;
    }
    public void setHolder(Profile holder){
        this.holder = holder;
    }
    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * A method that returns if two accounts have the same account number
     *
     * @param obj the object to be compared.
     * @return true if equal, false if not equal
     */
    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Account other ){
            return this.number.toString().equals(other.number.toString());
        }
        return false;
    }

    /**
     * A method returns a textual representation of an account in the following format.
     * Format: Account#[200017410] Holder[John Doe 2/19/2000] Balance[$600.00] Branch[BRIDGEWATER]
     *
     * @return returns all Account information in the above format
     * this may be wrong(called on the parameters wrong)
     */
    @Override
    public String toString() {
        return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name() + "]";
    }

    /**
     * A method that compares two account numbers to see which is greater or if they are equal.
     *
     * @param other the object to be compared.
     * @return 1 if parameter is greater than Account, -1 if parameter is less than, and 0 if parameter is equal.
     */
    @Override
    public int compareTo(Account other) {
        return this.number.toString().compareTo(other.number.toString());
    }
// Need testing main() method still

}








