package com.example.project3.Model.package1;

/**
 * Savings class extending Account.
 */
public class Savings extends Account {

    protected boolean isLoyal;

    public Savings(AccountNumber number, Profile holder, double balance, boolean isLoyal) {
        super(number, holder, balance);
        this.isLoyal = isLoyal;//does this need to be false on default?
    }

    @Override
    public double interest() {
        double balance = getBalance();
        double rate = isLoyal ? 0.0275 : 0.025;
        return (balance * rate / 12);
    }

    @Override
    public double fee() {
        double balance = getBalance();
        return balance >= 500 ? 0 : 25;
    }

    @Override
    public String toString() {
        if(this.isLoyal){
            return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name().toUpperCase() + "] [LOYAL]";
        }else{
            return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name().toUpperCase() + "]";
        }
    }

    public boolean loyalty(){
        return isLoyal;
    }
}
