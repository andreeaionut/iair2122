package test;

import iair2122MV.model.Activity;
import iair2122MV.model.Contact;
import iair2122MV.repositories.RepositoryActivityMock;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ActivityTest {
    private RepositoryActivityMock repositoryActivityMock;

    @Before
    public void setUp() throws Exception {
        this.repositoryActivityMock = new RepositoryActivityMock();
    }

    @Test
    public void test_1() {
        this.repositoryActivityMock.removeAll();
        LocalDate localDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.addActivity("descr1", localDate, localDate, new ArrayList<Contact>());
        assert (this.repositoryActivityMock.getByDescription("descr1") != null);
    }

    @Test
    public void test_2(){
        LocalDate startDate = LocalDate.of(2019,10,10);
        LocalDate endDate = LocalDate.of(2019, 10, 11);
        this.repositoryActivityMock.removeAll();
        Activity activity = new Activity("username1", startDate, 1, new ArrayList<>(), "descr1");
        this.repositoryActivityMock.add(activity);
        this.repositoryActivityMock.addActivity("descr2", startDate, endDate, new ArrayList<>());
        assert (this.repositoryActivityMock.getByDescription("descr2") != null);
    }

    @Test
    public void test_3(){
        LocalDate startDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.removeAll();
        Activity activity = new Activity("username1", startDate, 1, new ArrayList<>(), "descr1");
        this.repositoryActivityMock.add(activity);
        Activity activity2 = new Activity("username1", startDate, 1, new ArrayList<>(), "descr2");
        this.repositoryActivityMock.add(activity2);
        assert (!this.repositoryActivityMock.addActivity("descr2", startDate, startDate, new ArrayList<>()));
        assert (this.repositoryActivityMock.count() == 2);
    }

    @Test
    public void test_4(){
        LocalDate startDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.removeAll();
        Activity activity = new Activity("username1", startDate, 1, new ArrayList<>(), "descr1");
        this.repositoryActivityMock.add(activity);
        Activity activity2 = new Activity("username1", startDate, 1, new ArrayList<>(), "descr2");
        this.repositoryActivityMock.add(activity2);
        LocalDate newStartDate = LocalDate.of(2019, 10, 11);
        assert (this.repositoryActivityMock.addActivity("descr2", newStartDate, startDate, new ArrayList<>()));
        assert (this.repositoryActivityMock.count() == 3);
    }
}
