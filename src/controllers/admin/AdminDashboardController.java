package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Model;
import models.main.User;

public class AdminDashboardController implements Initializable {
	private User admin;

	// Buttons for Client
	public Button ViewClientsButton;
	public Button AddClientButton;
	public Button SearchClientButton;

	// Buttons for Account
	public Button ViewAccountsButton;
	public Button AddAccountButton;

	// Utility Buttons
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
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		LogoutButton.setOnAction(event -> handleLogout());
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