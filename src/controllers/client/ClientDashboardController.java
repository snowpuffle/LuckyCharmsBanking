package controllers.client;

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
import models.main.*;

public class ClientDashboardController implements Initializable {
	// Main Attributes
	private User client;

	// User Card Attributes
	public ImageView ImageField;
	public Label FirstNameField;
	public Label LastNameField;
	public Label UsernameField;

	// User Action Buttons
	public Button ChangePasswordButton;
	public Button EditClientButton;
	public Button ManageAccountsButton;

	// Utility Buttons
	public Button LogoutButton;

	// Default Class Constructor
	public ClientDashboardController(User client) {
		this.client = client;
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
		ManageAccountsButton.setOnAction(event -> handleManageAccounts());
		EditClientButton.setOnAction(event -> handleEditUser());
	}

	// Get Associated Accounts with Client
	private void initializeFrame() {
		// Continue ONLY if Client Exists
		if (client != null) {
			String imageURL = fixImage(client.getImageURL(), client.getGender());
			ImageField.setImage(new Image(imageURL));
			FirstNameField.setText(client.getFirstName());
			LastNameField.setText(client.getLastName());
			UsernameField.setText(client.getUsername());
		}
	}

	// Event: "Manage Accounts" Button is Clicked
	private void handleManageAccounts() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showManageAccountsFrame(client);
	}

	// Event: "Edit User Profile" Button is Clicked
	private void handleEditUser() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showEditClientAccountFrame(client);
	}

	// Event: "Change Password" Button is Clicked
	private void handleChangePassword() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showChangePasswordFrame(client);
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

	// Event: "Logout" Button is Clicked
	private void handleLogout() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showLoginFrame();
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) LogoutButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}