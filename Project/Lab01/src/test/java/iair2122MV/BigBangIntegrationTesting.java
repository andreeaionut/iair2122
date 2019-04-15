package iair2122MV;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.model.Activity;
import iair2122MV.model.Contact;
import iair2122MV.repositories.RepositoryActivityMock;
import iair2122MV.repositories.RepositoryContactFile;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BigBangIntegrationTesting {

    private RepositoryContactFile repositoryContactFile;
    private RepositoryActivityMock repositoryActivityMock;
    private ActivityOnDateTest activityOnDateTest;

    @Before
    public void setUp() throws Exception {
        this.repositoryContactFile = new RepositoryContactFile();
        this.repositoryActivityMock = new RepositoryActivityMock();
        this.activityOnDateTest = new ActivityOnDateTest();
    }

    @Test(expected = InvalidFormatException.class)
    public void testBVA_1() throws InvalidFormatException {
        this.repositoryContactFile.addContact("P", "A", "+4078");
    }

    @Test
    public void test_1() {
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryActivityMock.getByDescription("descr1") != null);
    }

    @Test
    public void testBBT_valid() throws Exception {
        this.repositoryActivityMock.removeAll();
        LocalDate startDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.add(new Activity("username1", startDate, LocalTime.NOON, "Lunch"));
        this.repositoryActivityMock.add(new Activity("username1", startDate, LocalTime.MIDNIGHT, "Midnight"));
        LocalDate startDate2 = LocalDate.of(2019,10,11);
        this.repositoryActivityMock.add(new Activity("username1", startDate2, LocalTime.NOON, "Lunch"));

        List<Activity> result = this.repositoryActivityMock.activitiesOnDate("username1", startDate);
        assert (result.size() == 2);
        for(Activity activity : result){
            assert (activity.getStart().equals(startDate));
        }
        assert (result.get(0).getStartTime().equals(LocalTime.MIDNIGHT));
        assert (result.get(1).getStartTime().equals(LocalTime.NOON));
    }

    @Test
    public void testBigBang() throws Exception {
        //P->A->B->C A B C valide
        this.activityOnDateTest.setUp();
        this.repositoryContactFile.addContact("Contact", "Adresa", "+4078");

        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        LocalDate localDate2 = LocalDate.of(2019,10,11);
        this.repositoryActivityMock.addActivity("descrN", localDate, localDate, new ArrayList<Contact>());
        this.repositoryActivityMock.addActivity("descr1", localDate2, localDate2, new ArrayList<>());

        LocalDate startDate = LocalDate.of(2019,10,10);
        List<Activity> result = this.repositoryActivityMock.activitiesOnDate("username1", startDate);

        assert (this.repositoryContactFile.getContactByName("Contact") != null);
        assert (this.repositoryActivityMock.getByName("descrN") != null);
        assert (this.repositoryActivityMock.getByName("descr1") != null);
        assert (result.size() == 1);
        for(Activity activity : result){
            assert (activity.getStart().equals(startDate));
        }
    }

}
