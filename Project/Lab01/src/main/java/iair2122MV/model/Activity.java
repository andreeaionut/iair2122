package iair2122MV.model;

import iair2122MV.repositories.RepositoryContactFile;
import sun.rmi.server.LoaderHandler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Activity {
	private String name;
	private LocalDate start;
	private long duration;
	private List<Contact> contacts;
	private String description;
	
	public Activity(String name, LocalDate start, LocalDate end, List<Contact> contacts,
					String description) {
		this.name = name;
		this.description = description;
		this.start = start;
		this.duration = DAYS.between(start, end);
		if (contacts == null)
			this.contacts = new LinkedList<Contact>();
		else
			this.contacts = new LinkedList<Contact>(contacts);
	}

    public Activity(String name, LocalDate start, long duration, List<Contact> contacts, String description) {
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.contacts = contacts;
        this.description = description;
    }

    public String getName() {
		return name;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Activity))
			return false;
		Activity act = (Activity) obj;
		if (act.description.equals(description) && start.equals(act.start)
				&& duration == act.duration && name.equals(act.name))
			return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("#");
		sb.append(start.toString());
		sb.append("#");
		sb.append(duration);
		sb.append("#");
		sb.append(description);
		sb.append("#");
		for (Contact c : contacts) {
			sb.append("#");
			sb.append(c.getName());
		}
		return sb.toString();
	}

	public static Activity fromString(String line, RepositoryContactFile repcon) {
		try {
			String[] str = line.split("#");
			String name = str[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            LocalDate start = LocalDate.parse("2005-10-12", formatter);
			long duration = Integer.parseInt(str[2]);
			String description = str[3];
			List<Contact> conts = new LinkedList<Contact>();
			for (int i = 5; i < str.length; i++) {
				conts.add(repcon.getContactByName(str[i]));
			}
			return new Activity(name, start, duration, conts, description);
		} catch (Exception e) {
			return null;
		}
	}
}
