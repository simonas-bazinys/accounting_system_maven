package com.accouting;


import com.accouting.utilities.DataValidity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CodeValue_tests {
        @Test
        public void numberWithLessThan6symbolShouldBeIncorrect()
        {
            String authenticationCode ="vfb12";
            assertFalse(DataValidity.isAuthenticationCodeValid(authenticationCode));
        }
        @Test
        public void numberWithMoreThan6symbolShouldBeIncorrect()
        {
            String authenticationCode = "vbg125g";
            assertFalse(DataValidity.isAuthenticationCodeValid(authenticationCode));
        }
        @Test public void numberWithEqual6ShouldBeCorrect()
        {
            String authenticationCode="sda454";
            assertTrue(DataValidity.isAuthenticationCodeValid(authenticationCode));
        }
}
