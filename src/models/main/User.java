package models.main;

public class User {
	private int ID;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String type;
	private String gender;
	private String imageURL;

	// Default Class Constructor
	public User(int ID, String firstName, String lastName, String username, String password, String gender,
			String imageURL, String type) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.imageURL = imageURL;
		this.type = type;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\tFirstName: " + firstName + "\tLastName: " + lastName + "\tUsername: " + username
				+ "\tPassword: " + password + "\tType: " + type + "\tImageURL: " + imageURL;
	}

	// Getter and Setter Methods
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}