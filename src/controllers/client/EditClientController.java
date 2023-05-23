package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.main.User;

public class EditClientController implements Initializable {
	// Main Attributes
	private User client;
	public TextField UsernameField;
	public TextField ImageField;
	public TextField FirstNameField;
	public TextField LastNameField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public EditClientController(User client) {
		this.client = client;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame
		initializeFrame();

		// Initialize OnClick Action of Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleEditClient());
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		if (client != null) {
			// Initialize Client Data
			UsernameField.setText(client.getUsername());
			ImageField.setText(client.getImageURL());
			FirstNameField.setText(client.getFirstName());
			LastNameField.setText(client.getLastName());

			// Set Non-Editable Fields
			FirstNameField.setEditable(false);
			LastNameField.setEditable(false);
		}
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (client != null) {
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showClientDashboardFrame(client);
		}
	}

	// Event: "Submit" Button is Clicked
	private void handleEditClient() {

		String newUsername = UsernameField.getText().trim();
		String newImageURL = ImageField.getText().trim();

		// Continue ONLY if Fields are Valid
		if (validateFields(newUsername, newImageURL)) {
			client.setUsername(newUsername);
			client.setImageURL(newImageURL);

			boolean success = Model.getInstance().updateUser(client);
			if (success) {
				handleMessageLabel("Client Information Successfully Updated!", true);
			} else {
				handleMessageLabel("Cannot Update Client!", false);
			}
		}
	}

	// Validate Fields
	private boolean validateFields(String username, String imageURL) {
		if (!validateUsername(username)) {
			return false;
		} else if (!validateImageURL(imageURL)) {
			return false;
		}
		return true;
	}

	// Validate Username
	private boolean validateUsername(String username) {
		// Check if Username Field is Empty
		if (username.isBlank() || username.isEmpty()) {
			handleMessageLabel("Please Enter a Username!", false);
			return false;
		}
		return true;
	}

	// Validate Username
	private boolean validateImageURL(String imageURL) {
		// Check if Username Field is Empty
		if (imageURL.isBlank() || imageURL.isEmpty()) {
			handleMessageLabel("Please Enter an ImageURL!", false);
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