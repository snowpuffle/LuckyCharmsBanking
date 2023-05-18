package views;

import controllers.*;
import controllers.admin.*;
import controllers.client.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.main.*;

public class ViewFactory {

	// Default Class Constructor
	public ViewFactory() {
	}

	// Show User Login Window
	public void showLoginFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/Login.fxml"));
		LoginController LoginController = new LoginController();
		fxmlLoader.setController(LoginController);
		createStage(fxmlLoader, "User Login");
	}

	// Show Admin Dashboard Window
	public void showAdminDashboardFrame(User admin) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/admin/AdminDashboard.fxml"));
		AdminDashboardController AdminDashboardController = new AdminDashboardController(admin);
		fxmlLoader.setController(AdminDashboardController);
		createStage(fxmlLoader, "Admin Dashboard");
	}

	// Show Client Dashboard Window
	public void showClientDashboardFrame(User client) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/client/ClientDashboard.fxml"));
		ClientDashboardController ClientDashboardController = new ClientDashboardController(client);
		fxmlLoader.setController(ClientDashboardController);
		createStage(fxmlLoader, "Client Dashboard");
	}

	// Show Add Client Account Window
	public void showAddClientAccountFrame(User user) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/client/AddAccount.fxml"));
		AddAccountController AddAccountController = new AddAccountController(user);
		fxmlLoader.setController(AddAccountController);
		createStage(fxmlLoader, "Add New Client Account");
	}

	// Show User Change Password Window
	public void showTransactionFrame(User user) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/client/Transaction.fxml"));
		TransactionController TransactionController = new TransactionController(user);
		fxmlLoader.setController(TransactionController);
		createStage(fxmlLoader, "Transaction Frame");
	}

	// Show User Change Password Window
	public void showChangePasswordFrame(User user) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/ChangePassword.fxml"));
		PasswordController PasswordController = new PasswordController(user);
		fxmlLoader.setController(PasswordController);
		createStage(fxmlLoader, "Change Password");
	}

	// Generic: Create Stage
	private void createStage(FXMLLoader fxmlLoader, String stageTitle) {
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Lucky Charm Banking");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Issues with Creating Stage " + stageTitle + "!");
		}
	}

	// Generic: Close Stage
	public void closeStage(Stage stage) {
		stage.close();
	}
}