package controllers.admin;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Model;
import models.main.User;

public class AdminDashboardController implements Initializable {
	// Main Attributes
	private User admin;

	// User Card Attributes
	public ImageView ImageField;
	public Label FirstNameField;
	public Label LastNameField;
	public Label UsernameField;

	// Button Attributes
	public Button ViewUsersButton;
	public Button AddUserButton;
	public Button ChangePasswordButton;
	public Button LogoutButton;

	// Default Class Constructor
	public AdminDashboardController(User admin) {
		this.admin = admin;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		addListeners();
		initializeFrame();
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		LogoutButton.setOnAction(event -> handleLogout());
		ChangePasswordButton.setOnAction(event -> handleChangePassword());
		ViewUsersButton.setOnAction(event -> handleViewUsers());
		AddUserButton.setOnAction(event -> handleAddUser());
	}

	// Get Associated Accounts with Admin
	private void initializeFrame() {
		// Continue ONLY if Admin Exists
		if (admin != null) {
			String imageURL = fixImage(admin.getImageURL(), admin.getGender());
			ImageField.setImage(new Image(imageURL));
			FirstNameField.setText(admin.getFirstName());
			LastNameField.setText(admin.getLastName());
			UsernameField.setText(admin.getUsername());
		}
	}

	// Event: "Change Password" Button is Clicked
	private void handleChangePassword() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showChangePasswordFrame(admin);
	}

	// Event: "View All Users" Button is Clicked
	private void handleViewUsers() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showViewUsersFrame(admin);
	}

	// Event "Add New User" Button is Clicked
	private void handleAddUser() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showAddNewUserFrame(admin);
	}

	// Event: "Logout" Button is Clicked
	private void handleLogout() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showLoginFrame();
	}

	// Fix ImageURL Based on Gender
	private String fixImage(String image, String gender) {
		// Initialize Empty Location
		String mainLocation = System.getProperty("user.dir") + "\\resources\\images";
		String imageLocation = "";

		// Set Image Folder Location based on Type
		if ("MALE".equalsIgnoreCase(gender)) {
			imageLocation = mainLocation + "\\males\\" + image;
		} else if ("FEMALE".equalsIgnoreCase(gender)) {
			imageLocation = mainLocation + "\\females\\" + image;
		}

		// Check if File Exists
		File file = new File(imageLocation);
		if (!file.exists()) {
			imageLocation = mainLocation + "\\icons\\warning.png";
		}

		// Return Image Location
		return imageLocation;
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) LogoutButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}