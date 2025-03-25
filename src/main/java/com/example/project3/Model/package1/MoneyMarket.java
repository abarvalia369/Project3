package com.example.project3.Model.package1;
import com.example.project3.Model.util.Date;
import com.example.project3.Model.util.List;
/**
 * MoneyMarket class extending Savings.
 */
public class MoneyMarket extends Savings {
    private int withdrawal;

    public MoneyMarket(AccountNumber number, Profile holder, double balance, boolean isLoyal) {
        super(number, holder, balance, isLoyal);
        this.withdrawal = 0;
    }

    @Override
    public String toString() {
        if(this.isLoyal){
            return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name() + "] [LOYAL] withdrawal[" + withdrawal + "]";
        } else {
            return "Account#[" + number.toString() + "] Holder[" + holder.toString() + "] Balance[" + String.format( "$%.2f" , balance) + "] Branch[" + number.getBranch().name() + "] withdrawal[" + withdrawal + "]";
        }

    }


    @Override
    public double fee() {
        double balance = getBalance();
        if (balance < 2000) return 25;
        return withdrawal > 3 ? 10 : 0;
    }
}