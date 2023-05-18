package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.main.User;

public class PasswordController implements Initializable {
	private User user;

	// Login Fields
	public TextField UsernameField;
	public PasswordField OldPasswordField;
	public PasswordField NewPasswordField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public PasswordController(User user) {
		this.user = user;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeFrame();
		// Initialize OnClick Action of Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleChangePassword());
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		if (user != null) {
			String username = user.getUsername();
			UsernameField.setText(username);
		}
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (user != null) {
			String type = user.getType();
			closeCurrentWindow();
			if ("ADMIN".equalsIgnoreCase(type)) {
				Model.getInstance().getViewFactory().showAdminDashboardFrame(user);
			} else if ("CLIENT".equalsIgnoreCase(type)) {
				Model.getInstance().getViewFactory().showClientDashboardFrame(user);
			}
		}
	}

	// Event: "Submit" is Clicked
	private void handleChangePassword() {
		String oldPassword = OldPasswordField.getText().trim();
		String newPassword = NewPasswordField.getText().trim();
		if (validateFields(oldPassword, newPassword)) {
			changePassword(oldPassword, newPassword);
		} else {
			handleMessageLabel("Fields CANNOT be Empty!", false);
		}
	}

	// Change User Password
	private boolean changePassword(String oldPassword, String newPassword) {
		// Initialize Flag
		boolean success = false;

		// Validate Old Password
		if (checkPassword(oldPassword)) {
			// Update Password in Database
			success = Model.getInstance().updateUserPassword(user, newPassword);

			if (success) {
				// Update THIS User Object with New Password
				user.setPassword(newPassword);
				handleMessageLabel("User Password Updated!", true);
			} else {
				handleMessageLabel("Cannot Update Password!", false);
			}
		} else {
			success = false;
			handleMessageLabel("Incorrect Old Password!", false);
		}

		return success;
	}

	// Check Old and New Password
	private boolean checkPassword(String oldPassword) {
		String password = user.getPassword();
		return oldPassword.equals(password);
	}

	// Validate Fields
	private boolean validateFields(String oldPassword, String newPassword) {
		if (oldPassword.isBlank() || newPassword.isBlank()) {
			return false;
		}
		return true;
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