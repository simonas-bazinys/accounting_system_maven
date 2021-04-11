package com.accouting;

import com.accouting.utilities.DataValidity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Phone_number_tests {
    @Test
    public void phoneNumberWithoutCountryCodeShouldBeIncorrect()
    {
        String phoneNumber = "5878963";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "37064578987";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "+3704578987";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));
    }

    @Test
    public void phoneNumberWithCountryCodeShouldBeCorrect()
    {
        String phoneNumber = "865878963";

        assertTrue(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "+37064578987";

        assertTrue(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "+37069457987";

        assertTrue(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));
    }

    @Test
    public void phoneNumberMoreThan15NumbersShouldBeIncorrect()
    {
        String phoneNumber = "86587896378655235";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "+3706457898746568";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));

        phoneNumber = "+370694579871547";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));
    }

    @Test
    public void emptyPhoneNumberShouldBeIncorrect()
    {
        String phoneNumber = "";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));
    }

    @Test
    public void phoneNumberWithNonNumericalCharsShouldBeIncorrect()
    {
        String phoneNumber = "+3706s548796";

        assertFalse(DataValidity.isValidLithuanianPhoneNumber(phoneNumber));
    }
}
