package iair2122MV.repositories;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.model.Contact;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class RepositoryContactFile implements IRepository<Contact> {

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
	public List<Contact> getAll() {
		return new LinkedList<Contact>(contacts);
	}

	@Override
	public boolean add(Contact contact) {
		contacts.add(contact);
		return true;
	}

	public void addContact(String name, String address, String telefon) throws InvalidFormatException {
		Contact c = new Contact(name, address, telefon);
		add(c);
	}

	@Override
	public boolean remove(Contact contact) {
		int index = contacts.indexOf(contact);
		if (index < 0)
			return false;
		else
			contacts.remove(index);
		return true;
	}

	@Override
	public boolean save() {
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
	public List<Contact> getByName(String name) {
		return null;
	}

	public Contact getContactByName(String contactName) {
		for (Contact c : contacts)
			if (c.getName().equals(contactName))
				return c;
		return null;
	}

}
