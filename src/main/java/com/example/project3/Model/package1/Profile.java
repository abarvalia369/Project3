package com.example.project3.Model.package1;
import com.example.project3.Model.util.Date;
/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Initializes an Profile Object with the following 5 parameters.
     *
     * @param fname String first name
     * @param lname String last name
     * @param year int year
     * @param month int month
     * @param day int day
     */
    public Profile(String fname, String lname, int year, int month, int day) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(year, month, day);
    }

    /**
     * Initializes an Profile Object with the following 3 parameters.
     *
     * @param fname String first name
     * @param lname String last name
     * @param dob Date object
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }


    public String getFname() {
        return fname;
    }

    public Date getDob() {
        return dob;
    }

    public String getLname() {
        return lname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public static boolean isAdult(Date dob){
        if (!dob.isValid()) {
            return false;
        }
        Date today = new Date();
        Date adult = new Date(today.getYear() - 18, today.getMonth(), today.getDay());

        return dob.compareTo(adult) <= 0;
    }
    public int getAge() {
        // 1) Obtain today's date (however your Date class handles it).
        //    If you have a default constructor that sets to today's date, you can do:
        Date today = new Date();
        // OR if you have something like Date.today(), use that:
        // Date today = Date.today();

        // 2) Compute the difference in years.
        int age = today.getYear() - dob.getYear();

        // 3) If we haven't yet reached the birthday this year, subtract 1.
        if (today.getMonth() < dob.getMonth() ||
                (today.getMonth() == dob.getMonth() && today.getDay() < dob.getDay())) {
            age--;
        }

        return age;
    }

    /**
     * Return a textual representation of the Profile object.
     *
     * @return first and last name along with date of birth.
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }



    /**
     * @param obj check if obj equals Profile
     * @return return true if two profile objects match, if not, return false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return (this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname) && this.dob.equals(profile.dob));
        }
        return false;
    }

    /**
     * Translates 2 Profiles to String variables, and compares them lexographically.
     *
     * @param profile the profile object to be compared.
     * @return return 1 if this profile object is greater than "profile", return -1 if smaller;
     * return 0 if they are equal.
     */
    @Override
    public int compareTo(Profile profile) {
        int lastNameCompare = this.lname.toLowerCase().compareTo(profile.lname.toLowerCase());
        if (lastNameCompare != 0) return lastNameCompare;

        int firstNameCompare = this.fname.toLowerCase().compareTo(profile.fname.toLowerCase());
        if (firstNameCompare != 0) return firstNameCompare;

        return this.dob.compareTo(profile.dob);
    }


}