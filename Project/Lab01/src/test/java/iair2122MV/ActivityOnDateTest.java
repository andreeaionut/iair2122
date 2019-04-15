package iair2122MV;

import iair2122MV.model.Activity;
import iair2122MV.repositories.RepositoryActivityMock;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ActivityOnDateTest {

    private RepositoryActivityMock repositoryActivityMock;

    @Before
    public void setUp() throws Exception {
        this.repositoryActivityMock = new RepositoryActivityMock();
    }

    public void addData(){
        this.repositoryActivityMock.removeAll();
        LocalDate startDate = LocalDate.of(2019,10,10);
        this.repositoryActivityMock.add(new Activity("username1", startDate, LocalTime.NOON, "Lunch"));
        this.repositoryActivityMock.add(new Activity("username1", startDate, LocalTime.MIDNIGHT, "Midnight"));
        LocalDate startDate2 = LocalDate.of(2019,10,11);
        this.repositoryActivityMock.add(new Activity("username1", startDate2, LocalTime.NOON, "Lunch"));
    }

    @Test
    public void testBBT_valid(){
        this.addData();
        LocalDate startDate = LocalDate.of(2019,10,10);
        List<Activity> result = this.repositoryActivityMock.activitiesOnDate("username1", startDate);
        assert (result.size() == 2);
        for(Activity activity : result){
            assert (activity.getStart().equals(startDate));
        }
        assert (result.get(0).getStartTime().equals(LocalTime.MIDNIGHT));
        assert (result.get(1).getStartTime().equals(LocalTime.NOON));
    }

    @Test
    public void testBBT_nonvalid(){
        this.addData();
        LocalDate startDate = LocalDate.of(2019,10,12);
        List<Activity> result = this.repositoryActivityMock.activitiesOnDate("username1", startDate);
        assert (result.size() == 0);
    }

}
