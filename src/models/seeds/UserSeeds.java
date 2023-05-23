package models.seeds;

import models.main.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserSeeds {
	// Main Attributes
	private List<User> listOfUsers;
	private static Random random = new Random();

	// User Attributes
	private static String[] GENDERS = { "Female", "Male" };
	private static String[] FEMALE_NAMES = { "Emma", "Olivia", "Ava", "Sophia", "Mia", "Isabella", "Charlotte",
			"Amelia", "Harper", "Evelyn" };
	private static String[] MALE_NAMES = { "Liam", "Noah", "William", "James", "Oliver", "Benjamin", "Elijah", "Lucas",
			"Henry", "Alexander" };

	private static String[] LAST_NAMES = { "Walker", "Martin", "Walker", "Wilson", "Miller", "Howard", "Taylor",
			"Watson", "Morgan", "Turner", "Wright", "Morris", "Butler", "Barnes", "Powell", "Gibson", "Holmes" };

	private static String[] FEMALE_IMAGES = { "woman-1.png", "woman-2.png", "woman-3.png", "woman-4.png",
			"woman-5.png" };
	private static String[] MALE_IMAGES = { "man-1.png", "man-2.png", "man-3.png", "man-4.png", "man-5.png" };

	// Default Class Constructor
	public UserSeeds() {
		this.listOfUsers = new ArrayList<User>();

		// Generate Admin and Clients
		generateAdmin();
		generateClients(15);
	}

	// Generate Random Clients
	private void generateClients(int numberOfClients) {
		// Generate X Number of Clients
		for (int i = 0; i < numberOfClients; i++) {
			// Generate User Attributes
			int ID = generateID();
			String gender = GENDERS[random.nextInt(GENDERS.length)];
			String firstName = genderateFirstName(gender);
			String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
			String username = generateUsername(firstName, lastName);
			String password = generatePassword();
			String imageURL = generateImageURL(gender);

			// Create a New User Object
			User client = new User(ID, firstName, lastName, username, password, gender, imageURL, "CLIENT");
			this.listOfUsers.add(client);
		}
	}

	// Generate Admin
	private void generateAdmin() {
		// Generate User Attributes
		int ID = generateID();
		String gender = GENDERS[random.nextInt(GENDERS.length)];
		String firstName = genderateFirstName(gender);
		String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		String username = "admin";
		String password = generatePassword();
		String imageURL = generateImageURL(gender);

		// Create a New User Object
		User admin = new User(ID, firstName, lastName, username, password, gender, imageURL, "ADMIN");
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

	// Generate First Name Based on Gender
	private String genderateFirstName(String gender) {
		if ("MALE".equalsIgnoreCase(gender)) {
			return MALE_NAMES[random.nextInt(MALE_NAMES.length)];
		} else if ("FEMALE".equalsIgnoreCase(gender)) {
			return FEMALE_NAMES[random.nextInt(FEMALE_NAMES.length)];
		}
		return "Error";
	}

	// Generate ImageURL based on Type and Name
	public String generateImageURL(String gender) {
		if ("MALE".equalsIgnoreCase(gender)) {
			return MALE_IMAGES[random.nextInt(MALE_IMAGES.length)];
		} else if ("FEMALE".equalsIgnoreCase(gender)) {
			return FEMALE_IMAGES[random.nextInt(FEMALE_IMAGES.length)];
		}
		return "unknown.png";
	}

	// Get List of Users
	public List<User> getListOfUsers() {
		return this.listOfUsers;
	}
}