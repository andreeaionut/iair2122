package iair2122MV.repositories;

import iair2122MV.model.Activity;

import java.io.*;
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
	public boolean add(Activity activity) {
		int  i = 0;
		boolean conflicts = false;

		while( i < activities.size() && !conflicts)
		{
			if ( activities.get(i).getStart().compareTo(activity.getDuration()) < 0 &&
					activity.getStart().compareTo(activities.get(i).getDuration()) < 0 )
				conflicts = true;
			i++;
		}
		if ( !conflicts )
		{
			activities.add(activity);
			return true;
		}
		return false;
//		for (int i = 0; i< activities.size(); i++)
//		{
//			if (activity.intersect(activities.get(i))) return false;
//		}	
//		int index = activities.indexOf(activity);
//		//if (index >= 0 ) return false;
//		activities.add(activity);
//		return true;
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

	@SuppressWarnings("deprecation")
	public List<Activity> activitiesOnDate(String name, Date d) {
		List<Activity> partialResult = new LinkedList<Activity>();
		for (Activity activity : activities)
			if (activity.getName().equals(name))
				if ((activity.getStart().getYear() == d.getYear() &&
					activity.getStart().getMonth() == d.getMonth() &&
					activity.getStart().getDate() == d.getDate())) partialResult.add(activity);
		List<Activity> result = new LinkedList<Activity>();
		while (partialResult.size() > 0 )
		{
			Activity activity = partialResult.get(0);
			int index = 0;
			for (int activityCounter = 1; activityCounter<partialResult.size(); activityCounter++)
				if (activity.getStart().compareTo(partialResult.get(activityCounter).getStart())>0)
				{
					index = activityCounter;
					activity = partialResult.get(activityCounter);
				}
			
			result.add(activity);
			partialResult.remove(index);
		}
		return result;
	}
}
