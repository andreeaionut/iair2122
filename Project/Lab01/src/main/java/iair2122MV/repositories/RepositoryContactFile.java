package iair2122MV.repositories;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.model.Contact;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class RepositoryContactFile implements RepositoryContact {

	private static final String filename = "src\\main\\files\\contacts.txt";
	private List<Contact> contacts;

	public RepositoryContactFile() throws Exception {
		contacts = new LinkedList<Contact>();
		BufferedReader br = null;
//		String currentDir = new File(".").getAbsolutePath();
//		System.out.println(currentDir);
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				Contact c = null;
				try {
					c = Contact.fromString(line, "#");
				} catch (InvalidFormatException e) {
					throw new InvalidFormatException("Error in file at line " + i);
				}
				contacts.add(c);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			if (br != null) br.close();
		}
	}

	@Override
	public List<Contact> getContacts() {
		return new LinkedList<Contact>(contacts);
	}

	@Override
	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	public void addContact(String name, String address, String telefon) throws InvalidFormatException {
		Contact c = new Contact(name, address, telefon);
		addContact(c);
	}

	@Override
	public boolean removeContact(Contact contact) {
		int index = contacts.indexOf(contact);
		if (index < 0)
			return false;
		else
			contacts.remove(index);
		return true;
	}

	@Override
	public boolean saveContracts() {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(Contact c : contacts)
				pw.println(c.toString());
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
		return contacts.size();
	}

	@Override
	public Contact getByName(String string) {
		for (Contact c : contacts)
			if (c.getName().equals(string))
				return c;
		return null;
	}

}
