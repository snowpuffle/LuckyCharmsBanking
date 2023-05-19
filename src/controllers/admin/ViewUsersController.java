package controllers.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Model;
import models.main.*;

public class ViewUsersController implements Initializable {
	private User admin;
	private List<User> listOfUsers;

	// Attributes for Table of Users
	public TableView<User> TableOfUsers;
	public TableColumn<User, Integer> ColumnID;
	public TableColumn<User, String> ColumnFirstName;
	public TableColumn<User, String> ColumnLastName;
	public TableColumn<User, String> ColumnType;
	public TableColumn<User, String> ColumnUsername;
	public Button GoBackButton;

	// Default Class Constructor
	public ViewUsersController(User admin) {
		this.admin = admin;
		this.listOfUsers = new ArrayList<User>();
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTableColumns();
		displayUserData();
		GoBackButton.setOnAction(event -> handleGoBack());
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (admin != null) {
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showAdminDashboardFrame(admin);
		}
	}

	// Initialize Frame
	private void initializeTableColumns() {
		ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		ColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		ColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
		ColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
	}

	// Display User Data
	private void displayUserData() {
		try {
			listOfUsers = Model.getInstance().getAllUsers();
			ObservableList<User> data = FXCollections.observableList(listOfUsers);
			TableOfUsers.setItems(data);
		} catch (Exception e) {

		}
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}