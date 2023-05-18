module LuckyCharmsBanking {
	requires javafx.fxml;
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;

	opens bankingsystem to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
	opens controllers.admin to javafx.graphics, javafx.fxml;
	opens controllers.client to javafx.graphics, javafx.fxml;
}
