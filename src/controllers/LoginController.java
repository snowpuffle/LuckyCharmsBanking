package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import models.Model;
import models.main.User;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class LoginController implements Initializable {
	// Main Attributes
	public TextField usernameField;
	public PasswordField passwordField;

	// Utility Attributes
	public Button submitButton;
	public Label errorLabel;

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize OnClick Action of Login Button
		submitButton.setOnAction(event -> handleLogin());
	}

	// Event: Login Submit Button is Pressed
	private void handleLogin() {
		// Get UserName and Password Fields
		String username = usernameField.getText().trim();
		String password = passwordField.getText().trim();

		// Login Failed: Username or Password is Empty
		if (username.isEmpty() || password.isEmpty()) {
			handleMessageLabel("ERROR: Username and Password Must Not be Empty.");
		} else {
			// Get User Login Data from Database by Accessing the Model
			User user = Model.getInstance().loginUser(username, password);

			// Login Success: Close Window & Display Dashboard
			if (user != null) {
				// Get & Close the Current Window
				Stage stage = (Stage) errorLabel.getScene().getWindow();
				Model.getInstance().getViewFactory().closeStage(stage);
				// Get & Display Dashboard Frame
				handleUserType(user);
			} else {
				// Login Failed: Invalid Username or Password
				handleMessageLabel("ERROR: Invalid Username or Password!");
			}
		}
	}

	// Display Dashboard Based on User Type
	private void handleUserType(User user) {
		String type = user.getType();
		if ("ADMIN".equalsIgnoreCase(type)) {
			Model.getInstance().getViewFactory().showAdminDashboardFrame(user);
		} else if ("CLIENT".equalsIgnoreCase(type)) {
			Model.getInstance().getViewFactory().showClientDashboardFrame(user);
		}
	}

	// Handle Error
	private void handleMessageLabel(String message) {
		errorLabel.setText(message);
	}
}
