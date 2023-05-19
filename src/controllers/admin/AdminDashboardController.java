package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Model;
import models.main.User;

public class AdminDashboardController implements Initializable {
	// Main Attributes
	private User admin;

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
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		LogoutButton.setOnAction(event -> handleLogout());
		ChangePasswordButton.setOnAction(event -> handleChangePassword());
		ViewUsersButton.setOnAction(event -> handleViewUsers());
		AddUserButton.setOnAction(event -> handleAddUser());
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

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) LogoutButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}