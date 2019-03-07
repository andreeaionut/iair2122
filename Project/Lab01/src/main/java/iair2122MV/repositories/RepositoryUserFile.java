package iair2122MV.repositories;

import iair2122MV.model.User;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class RepositoryUserFile implements RepositoryUser{

	private List<User> users;
	private static final String filename = "src\\main\\files\\users.txt";
	
	public RepositoryUserFile() throws Exception 
	{
		users = new LinkedList<User>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			int i = 0;
			while (( line = br.readLine())!= null)
			{
				User u = User.fromString(line);
				if (u == null) 
					throw new Exception("Error in file at line "+i, new Throwable("Invalid Activity"));
				users.add(u);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (br!=null) br.close();
		}
	}
	
	@Override
	public User getByUsername(String username) {
		for (User u : users)
			if (u.getUsername().equals(username)) return u;
		return null;
	}

	@Override
	public User getByName(String name) {
		for (User u : users)
			if (u.getName().equals(name)) return u;
		return null;
	}

	@Override
	public boolean changePassword(User user, String oldPassword, String newPassword) {
		int index = users.indexOf(user);
		if (index < 0) return false;
		return users.get(index).setPassword(oldPassword, newPassword);
	}

	@Override
	public boolean save() {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(User u : users)
				pw.println(u.toString());
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
	public List<User> getUsers() {
		return new LinkedList<User>(users);
	}

	@Override
	public int getCount() {
		return users.size();
	}

}
