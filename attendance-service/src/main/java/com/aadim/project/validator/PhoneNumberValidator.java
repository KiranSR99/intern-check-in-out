package com.aadim.project.validator;


public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEX = "^\\+[0-9]{1,3} [0-9]{4,14}(?:x.+)?$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_REGEX);
    }
}

