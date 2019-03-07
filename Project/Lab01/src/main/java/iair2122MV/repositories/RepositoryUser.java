package iair2122MV.repositories;

import iair2122MV.model.User;

import java.util.List;

public interface RepositoryUser {

	User getByUsername(String username);
	User getByName(String name);

	boolean changePassword(User user, String oldPassword, String newPassword);
	
	boolean save();
	
	List<User> getUsers();
	int getCount();
	
}
