package iair2122MV;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.repositories.RepositoryContactFile;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {

    private RepositoryContactFile repositoryContactFile;

    @Before
    public void setUp() throws Exception {
        this.repositoryContactFile = new RepositoryContactFile();
    }

    @Test(expected = InvalidFormatException.class)
    public void testBVA_1() throws InvalidFormatException {
        this.repositoryContactFile.addContact("P", "A", "+4078");
    }

    @Test(expected = InvalidFormatException.class)
    public void testBVA_2() throws InvalidFormatException {
        this.repositoryContactFile.addContact("PP", "AA", "+4078");
    }

    @Test
    public void testBVA_3() throws InvalidFormatException {
        this.repositoryContactFile.addContact("PPP", "AAA", "+4078");
        assert this.repositoryContactFile.getByName("PPP") != null;
    }

    @Test(expected = InvalidFormatException.class)
    public void testBVA_4() throws InvalidFormatException {
        this.repositoryContactFile.addContact("P", "AAA", "+4078");
    }

    @Test
    public void testECP_1() throws InvalidFormatException {
        this.repositoryContactFile.addContact("PPPP", "AAA", "+4078");
        assert this.repositoryContactFile.getByName("PPPP") != null;
    }

    @Test(expected = InvalidFormatException.class)
    public void testECP_2() throws InvalidFormatException {
        this.repositoryContactFile.addContact("PPP", "AAA", "4");
    }

    @Test
    public void testECP_5() throws InvalidFormatException {
        this.repositoryContactFile.addContact("PPP", "AAA", "0730");
        assert this.repositoryContactFile.getByName("PPP") != null;
    }
}
