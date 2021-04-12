import com.accouting.utilities.DataValidity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameTest {
    @Test
    public void nameWithLessThan30symbolShouldBeCorrect()
    {
        String name = "qawscderfvbgtrfdewsxcvfdrtgfv";
        assertTrue(DataValidity.isValidNameLength(name));
    }
    @Test
    public void nameWithMoreThan30symbolShouldBeInorrect()
    {
        String name = "qawscderfvbgtrfdewsxcvfdrtgfvp";
        assertTrue(DataValidity.isValidNameLength(name));
    }
    @Test
    public void nameWithLessThan1symbolShouldBeIncorrect()
    {
        String name ="";
        assertFalse(DataValidity.isValidNameLength(name));
    }
    @Test
    public void nameIsOnlyAlphabetShouldBeCorrect()
    {
        String name ="Benasdfdsdfsd";
        assertTrue(DataValidity.isValidNameAlphabeticForm(name));
    }
    @Test
    public void nameIsNotOnlyAlphabetShouldBeIncorrect()
    {
        String name ="Benas545425sfsdf";
        assertFalse(DataValidity.isValidNameAlphabeticForm(name));
    }
    @Test
    public void nameIsOnlyNumberAndSymbolShouldBeIncorrect()
    {
        String name ="86125/*-+()_&^%$#";
        assertFalse(DataValidity.isValidNameAlphabeticForm(name));
    }
}
