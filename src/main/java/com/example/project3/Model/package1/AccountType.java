package com.example.project3.Model.package1;


/**
 * An Enum class that defines the name of the account types with an additional property listed below.
 * Account types: 01 checking, 02 regular savings, 03 money market savings. 04 for college checkings and 05 for
 * CDS
 *
 * @author arpeet barvalia, jonathan john
 */
public enum AccountType {

    Checking("01"),
    RegularSavings("02"),
    MoneyMarketSavings("03"),
    CollegeChecking("04"),
    CD("05");


    private String code;

    AccountType( String code ){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}