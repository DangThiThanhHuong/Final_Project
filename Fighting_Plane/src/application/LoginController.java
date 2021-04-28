package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class LoginController {
	@FXML
	private Pane root;
	@FXML
	private TextField userId;
	@FXML
	private PasswordField passId;
	@FXML
	private Button logInId;
	@FXML
	private Label messId;


//	// Event Listener on Button[#logInId].onAction
//	@FXML
//	public void LoginAction(ActionEvent event) throws IOException {
//		String pass="";
//		int highestScore =0;
//		final String DB_URL = "jdbc:derby:UserDB";
//		if(userId.getText().isEmpty())
//			messId.setText("UserName can not be NULL");
//		else 
//		{
//			try {
//				Connection conn = DriverManager.getConnection(DB_URL);
//				Statement stmt = conn.createStatement();
//				String stringStatement = "SELECT * FROM Users WHERE UserName = '"+ userId.getText()+"'";
//				ResultSet result = stmt.executeQuery(stringStatement);
//				if(result.next()) {
//					pass = result.getString(2).trim();
//					highestScore = result.getInt(3);
//				}
//				if(pass == "") {
//					messId.setText("Account is not exit!");
//					messRegisID.setText("Register Now!");
//				}
//				else {
//					if(passId.getText().isEmpty())
//						messId.setText("Password can not be NULL");
//					else {
//						if(passId.getText().equals(pass)){
//							Label labelName = new Label();
//							labelName.setText("Player: " + userId.getText());
//							labelName.setFont(new Font("System Bold Italic", 24));
//							labelName.setLayoutX(31.0);
//							labelName.setLayoutY(14.0);
//							
//							Label labelHigestScore = new Label();
//							labelHigestScore.setText("Highest Score: "+ highestScore );
//							labelHigestScore.setFont(new Font("System Bold Italic", 24));
//							labelHigestScore.setLayoutX(532.0);
//							labelHigestScore.setLayoutY(14.0);
//							
//							FXMLLoader fxmlLoader = new FXMLLoader(
//									Main.class.getResource("PlayGame.fxml"));
//							PlayGameController viewController = new PlayGameController();
//							fxmlLoader.setController(viewController);
//							Pane root = fxmlLoader.load();
//							root.getChildren().set(12, labelName);
//							root.getChildren().set(11, labelHigestScore);
//							Scene sc = new Scene(root);
//							sc.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//							Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//							primaryStage.setTitle("Fighting Plane");
//							primaryStage.setScene(sc);
//							sc.getRoot().requestFocus();
//						}
//						else
//							messId.setText("Wrong Password!");
//					}
//				}
//				conn.close();
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//			}
//		}
//	}
//	
//	@FXML
//	public void RegisterAction(MouseEvent event) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(
//				Main.class.getResource("Register.fxml"));
//		RegisterController sbViewController = new RegisterController();
//		fxmlLoader.setController(sbViewController);
//		Pane root = fxmlLoader.load();
//		Scene sc = new Scene(root);
//		sc.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		primaryStage.setTitle("Register");
//		primaryStage.setScene(sc);
//	}
}
