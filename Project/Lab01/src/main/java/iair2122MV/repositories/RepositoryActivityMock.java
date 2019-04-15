package iair2122MV.repositories;


import iair2122MV.model.Activity;
import iair2122MV.model.Contact;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RepositoryActivityMock implements IRepository<Activity> {

	private List<Activity> activities;
	
	public RepositoryActivityMock()
	{
		activities = new LinkedList<Activity>();
//		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//		try {
//			Activity act = new Activity(df.parse("03/20/2013 12:00"), 
//					df.parse("03/20/2013 14:00"),
//					null,
//					"Meal break",
//					"Memo");
//			activities.add(act);
//			act = new Activity(df.parse("03/21/2013 12:00"), 
//					df.parse("03/21/2013 14:00"),
//					null,
//					"Meal break",
//					"Memo");
//			activities.add(act);
//			act = new Activity(df.parse("03/22/2013 12:00"), 
//					df.parse("03/22/2013 14:00"),
//					null,
//					"Meal break",
//					"Memo");
//			activities.add(act);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public List<Activity> getAll() {
		return new LinkedList<Activity>(activities);
	}

	public void removeAll(){
        for (Activity activity:this.activities
             ) {
            this.activities.remove(activity);
        }
    }

	public boolean addActivity(String description, LocalDate startDate, LocalDate endDate, List<Contact> contacts) {
		int  i = 0; boolean conflicts = false; boolean result = false;
		while( i < activities.size()) {
			if ( description.compareTo(activities.get(i).getDescription()) == 0){
				if(startDate.compareTo(activities.get(i).getStart()) == 0){
					conflicts = true;
				}
			}
			i++;
		}
		if ( !conflicts ) {
			activities.add(new Activity("username1", startDate, endDate, contacts, description));
			result = true;
		}
		return result;
	}

	@Override
	public boolean add(Activity activity) {
		this.activities.add(activity);
		return true;
	}

	@Override
	public boolean remove(Activity activity) {
		int index = activities.indexOf(activity);
		if (index<0) return false;
		activities.remove(index);
		return true;
	}

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public int count() {
		return activities.size();
	}

	@Override
	public List<Activity> getByName(String name) {
		List<Activity> result = new LinkedList<Activity>();
		for (Activity a : activities)
			if (a.getName().equals(name)) result.add(a);
		return result;
	}

	public Activity getByDescription(String description){
        for (Activity a : activities)
            if (a.getDescription().equals(description)) return a;
        return null;
    }

	public List<Activity> activitiesOnDate(String name, LocalDate d) {
	    List<Activity> result = new LinkedList<Activity>();
	    for (Activity activity : activities)
	        if (activity.getName().equals(name)){
	        if (activity.getStart().compareTo(d) == 0){
	            result.add(activity);
	        }
	    }
        result.sort(Comparator.comparing(Activity::getStartTime));
	    return result;
    }
}
