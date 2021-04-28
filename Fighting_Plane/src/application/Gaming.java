package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import application.Main.SocketConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Gaming {
	Stage primaryStage;
	Label labelName;
	Label labelHigestScore;
	ResourceLock lock;
	Socket incoming;
	PrintWriter outPrinter;
	
	

	public Gaming(Stage primaryStage, Label labelName, Label labelHigestScore, ResourceLock lock, Socket incoming,
			PrintWriter outPrinter) {
		super();
		this.primaryStage = primaryStage;
		this.labelName = labelName;
		this.labelHigestScore = labelHigestScore;
		this.lock = lock;
		this.incoming = incoming;
		this.outPrinter = outPrinter;
	}

	public void startGaming() throws IOException {
//		this.primaryStage = primaryStage;
//		FXMLLoader fxmlLoader = new FXMLLoader(
//				Main.class.getResource("PlayGame.fxml"));
//		PlayGameController viewController = new PlayGameController();
//		fxmlLoader.setController(viewController);
//		Pane root = fxmlLoader.load();
//		root.getChildren().set(12, labelName);
//		root.getChildren().set(11, labelHigestScore);
//		Scene sc = new Scene(root);
//		sc.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//		//Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		primaryStage.setTitle("Fighting Plane");
//		primaryStage.setScene(sc);
//		sc.getRoot().requestFocus();
//		System.out.println("hello Gaming");
//		ThreadLoadScene thread1 = new ThreadLoadScene(lock, incoming, outPrinter);
//		thread1.start();
		
		this.primaryStage = primaryStage;
		FXMLLoader fxmlLoader = new FXMLLoader(
				Main.class.getResource("PlayGame.fxml"));
		PlayGameController sbViewController = new PlayGameController();
		fxmlLoader.setController(sbViewController);
		lock.root = fxmlLoader.load();
		Scene sc = new Scene(lock.root);
		System.out.println(lock.root.getId());
		sc.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		sc.getRoot().requestFocus();
		Platform.runLater(()->{
			primaryStage.setScene(sc);
			primaryStage.setTitle("Fighting Plane");
			primaryStage.show();
		});
		ThreadLoadSceneGame thread1 = new ThreadLoadSceneGame(lock, incoming, outPrinter);
		thread1.start();
		
	}

}
