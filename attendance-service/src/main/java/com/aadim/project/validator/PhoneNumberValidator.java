package com.aadim.project.validator;

public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEX = "(?:\\(?\\+977\\)?)?9[6-9]\\d{8}|01-?[\\\\d]{7}";


    private PhoneNumberValidator() {
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_REGEX);
    }
}
