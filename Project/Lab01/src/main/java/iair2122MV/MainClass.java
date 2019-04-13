package iair2122MV;

import iair2122MV.exceptions.InvalidFormatException;
import iair2122MV.model.Activity;
import iair2122MV.model.Contact;
import iair2122MV.model.User;
import iair2122MV.repositories.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//functionalitati
//F01.	 adaugarea de contacte (nume, adresa, numar de telefon, adresa email);
//F02.	 programarea unor activitati (denumire, descriere, data, locul, ora inceput, durata, contacte).
//F03.	 generarea unui raport cu activitatile pe care le are utilizatorul (nume, user, parola) la o anumita data, ordonate dupa ora de inceput.

public class MainClass {

	public static void main(String[] args) {
		BufferedReader in = null;
		try {
			RepositoryContactFile contactRep = new RepositoryContactFile();
			RepositoryUserFile userRep = new RepositoryUserFile();
			RepositoryActivityFile activityRep = new RepositoryActivityFile(
					contactRep);

			User user = null;
			in = new BufferedReader(new InputStreamReader(System.in));
			while (user == null) {
				System.out.printf("Enter username: ");
				String u = in.readLine();
				System.out.printf("Enter password: ");
				String p = in.readLine();

				user = ((RepositoryUserFile) userRep).getByUsername(u);
				if (user != null && user.isPassword(p))
					break;
			}

			int chosen = 0;
			while (chosen != 4) {
				printMenu();
				chosen = Integer.parseInt(in.readLine());
				try {
					switch (chosen) {
					case 1:
						adaugContact(contactRep, in);
						break;
					case 2:
						adaugActivitate(activityRep, contactRep, in, user);
						break;
					case 3:
						afisActivitate(activityRep, in, user);
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			// List<Activity> act =
			// activityRep.activitiesByName(user.getName());
			// for(Activity a : act)
			// System.out.println(a.toString());

		} catch (Exception e) {

		}
		System.out.println("Program over and out\n");
	}

	private static void afisActivitate(RepositoryActivityFile activityRep,
			BufferedReader in, User user) {
		try {
			System.out.printf("Afisare Activitate: \n");
			System.out.printf("Data(format: yyyy-MMM-dd): ");
			String dateS = in.readLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
			LocalDate d = LocalDate.parse(dateS, formatter);

			System.out.println("Activitatile din ziua " + d.toString() + ": ");

			List<Activity> act = activityRep
					.activitiesOnDate(user.getName(), d);
			for (Activity a : act) {
				System.out.printf("%s - %s: %s - %s with: ", a.getStart()
						.toString(), a.getDuration(), a
						.getDescription());
				for (Contact con : a.getContacts())
					System.out.printf("%s, ", con.getName());
				System.out.println();
			}
		} catch (IOException e) {
			System.out.printf("Eroare de citire: %s\n" + e.getMessage());
		}
	}

	private static void adaugActivitate(RepositoryActivityFile activityRep,
			RepositoryContactFile contactRep, BufferedReader in, User user) {
		try {
			System.out.printf("Adauga Activitate: \n");
			System.out.printf("Descriere: ");
			String description = in.readLine();
			System.out.printf("Start Date(format: yyyy-MMM-dd): ");
			String dateS = in.readLine();
			System.out.printf("End Date(format: yyyy-MMM-dd): ");
			String dateE = in.readLine();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
			LocalDate start = LocalDate.parse(dateS, formatter);
			LocalDate end = LocalDate.parse(dateE, formatter);

			Activity act = new Activity(user.getName(), start, end,
					new LinkedList<Contact>(), description);

			activityRep.addActivity(user.getUsername(), description, start, end, new LinkedList<Contact>());
			//activityRep.add(act);

			System.out.printf("S-a adugat cu succes\n");
		} catch (IOException e) {
			System.out.printf("Eroare de citire: %s\n" + e.getMessage());
		}
	}

	private static void adaugContact(RepositoryContactFile contactRep,
			BufferedReader in) {

		try {
			System.out.printf("Adauga Contact: \n");
			System.out.printf("Nume: ");
			String name = in.readLine();
			System.out.printf("Adresa: ");
			String adress = in.readLine();
			System.out.printf("Numar de telefon: ");
			String telefon = in.readLine();
			
			Contact c = new Contact(name, adress, telefon);

			contactRep.add(c);

			System.out.printf("S-a adugat cu succes\n");
		} catch (IOException e) {
			System.out.printf("Eroare de citire: %s\n" + e.getMessage());
		} catch (InvalidFormatException e) {
			if (e.getCause() != null)
				System.out.printf("Eroare: %s - %s\n" + e.getMessage(), e
						.getCause().getMessage());
			else
				System.out.printf("Eroare: %s\n" + e.getMessage());
		}
	}

	private static void printMenu() {
		System.out.printf("Please choose option:\n");
		System.out.printf("1. Adauga contact\n");
		System.out.printf("2. Adauga activitate\n");
		System.out.printf("3. Afisare activitati din data de...\n");
		System.out.printf("4. Exit\n");
		System.out.printf("Alege: ");
	}
}
