package com.accouting;

import com.accouting.utilities.DataValidity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidEmailTest {
    @Test
    public void emailWithEtaAndGoodDomainShouldBeCorrect()
    {
        String email = "tutyzas@gmail.com";
        assertTrue(DataValidity.isValidEmailAddress(email));
    }

    @Test
    public void emailWithBadDomainShouldBeIncorrect()
    {
        String email = "tutyzas@gmailm";
        assertFalse(DataValidity.isValidEmailAddress(email));
    }

    @Test
    public void emptyEmailShouldNotBeAllowed()
    {
        String email = "";
        assertFalse(DataValidity.isValidEmailAddress(email));
    }
}
