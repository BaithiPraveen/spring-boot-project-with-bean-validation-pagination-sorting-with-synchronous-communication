package com.javacompany.employeeservice.validation;

public class EmailValidation {
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z][A-Za-z0-9_.]*[^_.][@][A-Za-z0-9]*[.]{1}[a-zA-Z]*$";
        return email.matches(regex);
    }
    public static void main(String[] args) {
        System.out.println(isValidEmail("abcd2@gmail.com"));
        System.out.println(isValidEmail("a123@outlook.com"));
        System.out.println(isValidEmail("123@gmail.com"));
    }
}
