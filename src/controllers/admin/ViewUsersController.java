package controllers.admin;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Model;
import models.main.*;

public class ViewUsersController implements Initializable {
	// Main Attributes
	private User admin;
	private List<User> listOfUsers;

	// Table Attributes
	public TableView<User> TableOfUsers;
	public TableColumn<User, Integer> ColumnID;
	public TableColumn<User, String> ColumnFirstName;
	public TableColumn<User, String> ColumnLastName;
	public TableColumn<User, String> ColumnType;

	// User Card Attributes
	public Label NameField;
	public Label IDField;
	public ImageView ImageField;
	public Label TypeField;
	public Label UsernameField;
	public Label GenderField;

	// Utility Attributes
	public Button GoBackButton;

	// Default Class Constructor
	public ViewUsersController(User admin) {
		this.admin = admin;
		this.listOfUsers = new ArrayList<User>();
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame and Add Listeners
		initializeFrame();
		addListeners();
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		initializeTableColumns();
		displayUserData();
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		// Set a Listener on Table View to Update User Data When a Row is Selected
		TableOfUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				updateUserCard(newSelection);
			}
		});

		// Initialize OnClick Action of Buttons
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
	}

	// Display User Data
	private void displayUserData() {
		try {
			listOfUsers = Model.getInstance().getAllUsers();
			ObservableList<User> data = FXCollections.observableList(listOfUsers);
			TableOfUsers.setItems(data);
		} catch (Exception e) {
			System.out.println("ERROR: Cannot Display User Data!");
		}
	}

	// Update User Card
	private void updateUserCard(User user) {
		// Fix Image URL
		String gender = user.getGender();
		String imageURL = fixImage(user.getImageURL(), gender);

		// Update User Card UI based on Selected User
		IDField.setText(String.valueOf(user.getID()));
		NameField.setText(user.getFirstName() + " " + user.getLastName());
		ImageField.setImage(new Image(imageURL));
		TypeField.setText(user.getType());
		UsernameField.setText(user.getUsername());
		GenderField.setText(gender);
	}

	// Fix ImageURL Based on Type
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
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}