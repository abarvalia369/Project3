package com.example.project3.Model.package1;

import com.example.project3.Model.util.Date;
import com.example.project3.Model.util.List;

/**
 * CertificateDeposit class extending Savings.
 */
public class CertificateDeposit extends Account {
    private int term;
    private Date open;

    public CertificateDeposit(AccountNumber number, Profile holder, double balance, int term, Date open) {
        super(number, holder, balance);
        this.term = term;
        this.open = open;
    }

    public Date getOpen(){
        return open;
    }

    public int getTerm(){
        return this.term;
    }

    public double getRate(){
        if(this.getTerm() == 3){
            return .03;
        }else if(this.getTerm() == 6){
            return .0325;
        }else if(this.getTerm() == 9){
            return .035;
        }else if(this.getTerm() == 12){
            return .04;
        }
        else{
            return 0.0;
        }
    }

    @Override
    public double interest() {
        double balance = getBalance();
        switch (term) {
            case 3: return (balance * 0.03 / 12);
            case 6: return (balance * 0.0325 / 12);
            case 9: return (balance * 0.035 / 12);
            case 12: return (balance * 0.04 / 12);
            default: return 0;
        }
    }

    @Override
    public double fee() {
        return 0; // No monthly fee for CD accounts
    }
}