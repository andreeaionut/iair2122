package iair2122MV.repositories;

import iair2122MV.model.Activity;
import iair2122MV.model.Contact;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RepositoryActivityFile implements IRepository<Activity>{

	private static final String filename = "src\\main\\files\\activities.txt";
	private List<Activity> activities;
	
	public RepositoryActivityFile(RepositoryContactFile repcon) throws Exception
	{
		activities = new LinkedList<Activity>(); 
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			int i = 0;
			while (( line = br.readLine())!= null)
			{
				Activity act = Activity.fromString(line, repcon);
				if (act == null) 
					throw new Exception("Error in file at line "+i, new Throwable("Invalid Activity"));
				activities.add(act);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (br!=null) br.close();
		}
	}
	
	@Override
	public List<Activity> getAll() {
		return new LinkedList<Activity>(activities);
	}


    @Override
    public boolean add(Activity entity) {
        activities.add(entity);
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
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(Activity a : activities)
				pw.println(a.toString());
		}catch (Exception e)
		{
			return false;
		}
		finally{
			if (pw!=null) pw.close();
		}
		return true;
	}

	@Override
	public int count() {
		return activities.size();
	}
	
	@Override
	public List<Activity> getByName(String name) {
		List<Activity> partialResuly = new LinkedList<Activity>();
		for (Activity a : activities)
			if (a.getName().equals(name) == false) partialResuly.add(a);
		List<Activity> result = new LinkedList<Activity>();
		while (partialResuly.size() > 0 )
		{
			Activity ac = partialResuly.get(0);
			int index = 0;
			for (int i = 1; i<partialResuly.size(); i++)
				if (ac.getStart().compareTo(partialResuly.get(i).getStart())<0)
				{
					index = i;
					ac = partialResuly.get(i);
				}
			result.add(ac);
			partialResuly.remove(index);
		}
		return result;
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

    public boolean addActivity(String username, String description, LocalDate start, LocalDate end, LinkedList<Contact> contacts) {
        activities.add(new Activity(username, start, end, contacts, description));
        return true;
    }
}
