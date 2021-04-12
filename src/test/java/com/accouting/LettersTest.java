package com.accouting;


import com.accouting.utilities.DataValidity;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LettersTest {

    @Test
    public void stringWithNumbersShouldBeIncorrect()
    {
        String word = "sdfdsf456";
        assertFalse(DataValidity.isOnlyAlphabeticalChars(word));
    }

    @Test
    public void stringWithoutNumbersShouldBeCorrect()
    {
        String word = "asdjklf";
        assertTrue(DataValidity.isOnlyAlphabeticalChars(word));
    }

    @Test
    public void emptyStringShouldBeIncorrect()
    {
        String word = "";
        assertFalse(DataValidity.isOnlyAlphabeticalChars(word));
    }
}
