package test;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.model.Activity;
import iair2122MV.model.Contact;
import iair2122MV.repositories.RepositoryActivityMock;
import iair2122MV.repositories.RepositoryContactFile;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TopDownIntegrationTesting {

    private RepositoryContactFile repositoryContactFile;
    private RepositoryActivityMock repositoryActivityMock;
    private ContactTest contactTest;
    private ActivityOnDateTest activityOnDateTest;

    @Before
    public void setUp() throws Exception {
        this.repositoryContactFile = new RepositoryContactFile();
        this.repositoryActivityMock = new RepositoryActivityMock();
        this.activityOnDateTest = new ActivityOnDateTest();
        this.contactTest = new ContactTest();
    }

    @Test
    public void testBVA_valid() throws Exception {
        this.contactTest.setUp();
        this.contactTest.testBVA_3();
    }

    @Test
    public void testBBT_valid() throws Exception {
        this.activityOnDateTest.setUp();
        this.activityOnDateTest.testBBT_valid();
    }

    @Test
    public void test_1() {
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryActivityMock.getByDescription("descr1") != null);
    }

    @Test
    public void test_PA() throws InvalidFormatException {
        this.repositoryContactFile.addContact("ContactTest", "AAA", "+4078");
        assert (this.repositoryContactFile.getContactByName("ContactTest") != null);
    }

    @Test
    public void test_PAB_1() throws InvalidFormatException {
        //A - valid, B - valid
        this.repositoryContactFile.addContact("ContactTest", "AAA", "+4078");
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryContactFile.getContactByName("ContactTest") != null);
        assert (this.repositoryActivityMock.getByDescription("descr1") != null);
    }

    @Test(expected = InvalidFormatException.class)
    public void test_PAB_2() throws InvalidFormatException {
        //A - invalid B - valid
        this.repositoryContactFile.addContact("P", "AAA", "+4078");
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryContactFile.getContactByName("P") == null);
        assert (this.repositoryActivityMock.getByDescription("descr1") != null);
    }

    @Test
    public void test_PAB_3() throws InvalidFormatException {
        //A - valid B - invalid
        this.repositoryContactFile.addContact("Cont", "AAA", "+4078");
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.add(new Activity("username1", localDate, localDate, new ArrayList<Contact>(), "descr1"));
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryContactFile.getContactByName("Cont") != null);
        assert (this.repositoryActivityMock.count() == 1);
    }

    @Test(expected = InvalidFormatException.class)
    public void test_PAB_4() throws InvalidFormatException {
        //A - invalid B - invalid
        this.repositoryContactFile.addContact("P", "AAA", "+4078");
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);

        this.repositoryActivityMock.add(new Activity("username1", localDate, localDate, new ArrayList<Contact>(), "desc1"));
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryContactFile.getContactByName("P") == null);
        assert (this.repositoryActivityMock.count() == 1);
    }

    @Test
    public void test_PABC() throws Exception {
        // A B C valide
        this.test_PAB_1();
        this.testBBT_valid();
    }
}
