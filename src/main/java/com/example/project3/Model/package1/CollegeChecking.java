package com.example.project3.Model.package1;

/**
 * CollegeChecking class extending Checking.
 */
public class CollegeChecking extends Checking {
    private Campus campus;
    public CollegeChecking(AccountNumber number, Profile holder, double balance, Campus campus) {
        super(number, holder, balance);
        int age = holder.getAge();
        if(age > 24){
            System.out.println("Not eligible to open: " + holder.getDob() + " is over 24.");
        }
        this.campus = campus;
    }

    @Override
    public String toString() {
        return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name() + "] Campus[" + campus.toString() + "]";
    }

    @Override
    public double fee() {
        return 0; // No fee for college checking
    }
}