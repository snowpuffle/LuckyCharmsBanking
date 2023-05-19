package controllers.admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.main.User;
import javafx.scene.control.PasswordField;

public class AddUserController implements Initializable {
	private User admin;

	// User Attributes
	public TextField FirstNameField;
	public TextField LastNameField;
	public ComboBox<String> UserTypeField;
	public PasswordField PasswordField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public AddUserController(User admin) {
		this.admin = admin;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame
		initializeFrame();

		// Initialize OnClick Action of Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleAddUser());
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		UserTypeField.getItems().addAll("Admin", "Client");
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showAdminDashboardFrame(admin);
	}

	// Event: "Submit" Button is Clicked
	private void handleAddUser() {
		// Retrieve Account Attributes
		String firstName = FirstNameField.getText().trim();
		String lastName = LastNameField.getText().trim();
		String type = UserTypeField.getValue();
		String password = PasswordField.getText().trim();

		// Continue ONLY if Fields are NOT Empty
		if (validateFields(firstName, lastName, type, password)) {
			// Generate Attributes & Add New User
			int ID = generateID();
			String username = generateUsername(firstName, lastName);
			User user = new User(ID, firstName, lastName, username, password, type);

			// Attempt to Add New User to Database
			boolean success = Model.getInstance().addNewUser(user);
			if (success) {
				handleMessageLabel("User Successfully Added!", true);
			} else {
				handleMessageLabel("Cannot Add User!", false);
			}
		}
	}

	// Validate Fields
	private boolean validateFields(String firstName, String lastName, String type, String password) {
		// Initialize Flag
		boolean success = true;

		// Validate All Fields are NOT Empty
		if (firstName == null || firstName.isBlank()) {
			success = false;
			handleMessageLabel("Please Enter First Name!", false);
		} else if (lastName == null || lastName.isBlank()) {
			success = false;
			handleMessageLabel("Please Enter Last Name!", false);
		} else if (type == null || type.isBlank()) {
			success = false;
			handleMessageLabel("Please Select a User Type!", false);
		} else if (password == null || password.isBlank()) {
			success = false;
			handleMessageLabel("Please Enter a Password!", false);
		}

		// Return Flag
		return success;
	}

	// Generate Random ID
	private int generateID() {
		Random random = new Random();
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

	// Generate Random Username
	private String generateUsername(String firstName, String lastName) {
		return firstName.toLowerCase() + "_" + lastName.toLowerCase();
	}

	// Handle Error
	private void handleMessageLabel(String message, boolean success) {
		if (success) {
			MessageLabel.setText("SUCCESS: " + message);
			MessageLabel.setTextFill(Color.GREEN); // Set Text Color to Green for Success
		} else {
			MessageLabel.setText("ERROR: " + message);
			MessageLabel.setTextFill(Color.RED); // Set Text Color to Red for Error
		}
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) SubmitButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}

}