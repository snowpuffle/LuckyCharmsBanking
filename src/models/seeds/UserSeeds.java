package models.seeds;

import models.main.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserSeeds {
	// Main Attributes
	private List<User> listOfUsers;
	private static Random random = new Random();

	// Sample Attributes
	private static String[] FIRST_NAMES = { "Oliver", "Jayden", "Oliver", "Elijah", "Carter", "Joseph", "Jayden",
			"Samuel", "Daniel", "Carson", "Hannah", "Olivia", "Camila", "Alison", "Sophia", "Amelia", "Evelyn",
			"Carlie", "Jordan" };
	private static String[] LAST_NAMES = { "Walker", "Martin", "Walker", "Wilson", "Miller", "Howard", "Taylor",
			"Watson", "Morgan", "Turner", "Wright", "Morris", "Butler", "Barnes", "Powell", "Gibson", "Holmes" };

	// Default Class Constructor
	public UserSeeds() {
		this.listOfUsers = new ArrayList<User>();

		// Generate Admin and Clients
		generateAdmin();
		generateClients(10);
	}

	// Generate Random Clients
	private void generateClients(int numberOfClients) {
		// Generate X Number of Clients
		for (int i = 0; i < numberOfClients; i++) {
			// Generate User Attributes
			int ID = generateID();
			String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
			String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
			String username = generateUsername(firstName, lastName);
			String password = generatePassword();

			// Create a New User Object
			User client = new User(ID, firstName, lastName, username, password, "CLIENT");
			this.listOfUsers.add(client);
		}
	}

	// Generate Admin
	private void generateAdmin() {
		// Generate User Attributes
		int ID = generateID();
		String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		String username = "admin";
		String password = generatePassword();

		// Create a New User Object
		User admin = new User(ID, firstName, lastName, username, password, "ADMIN");
		this.listOfUsers.add(admin);
	}

	// Generate Random Username
	private String generateUsername(String firstName, String lastName) {
		return firstName.toLowerCase() + "_" + lastName.toLowerCase();
	}

	// Generate Random Password
	private String generatePassword() {
		return "test";
	}

	// Generate Random ID
	private int generateID() {
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

	// Get List of Users
	public List<User> getListOfUsers() {
		return this.listOfUsers;
	}
}