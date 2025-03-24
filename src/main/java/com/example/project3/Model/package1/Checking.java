package com.example.project3.Model.package1;

/**
 * Checking class extending Account.
 */
public class Checking extends Account {
    public Checking(AccountNumber number, Profile holder, double balance) {
        super(number, holder, balance);
    }
    @Override
    public double interest() {
        double balance = getBalance();
        return balance >= 1000 ? (balance * 0.015 / 12) : 0;
    }

    @Override
    public double fee() {
        double balance = getBalance();
        return balance >= 1000 ? 0 : 15;
    }
}
