package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ThreadSceneWorking extends Thread {
	File file, file2,file3, file4;
	Socket s;
	Stage stage;
	File controller;
	ResourceLock lock = new ResourceLock();
	public ThreadSceneWorking(Socket s, Stage stage, File controller) {
		this.s = s;
		this.stage = stage;
		this.controller = controller;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10);
			try (Scanner in = new Scanner(s.getInputStream(), "UTF-8")) {
				OutputStream outStream = null;
				OutputStream outStream2 = null;
				OutputStream outStream3 = null;
				OutputStream outStream4 = null;
				OutputStream out = s.getOutputStream();
				PrintWriter outPrinterMess = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
				file = new File("src/application/Login.fxml");
				file2 = new File("src/application/LoginController.java");
				file3 = new File("src/application/PlayGame.fxml");
				file4 = new File("src/application/PlayGameController.java");
				try {
					file.createNewFile();
					outStream = new FileOutputStream(file);
					PrintWriter outPrinter = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
					//////////////////////////////Print Login & LoginController////////////////////////
					while (in.hasNext()) {
						String string = in.nextLine();
						if (!string.contains("DONE")) {
							outPrinter.println(string);
						} else {
							outPrinter.close();
							file2.createNewFile();
							outStream2 = new FileOutputStream(file2);
							PrintWriter outPrinter2 = new PrintWriter(new OutputStreamWriter(outStream2, "UTF-8"),
									true);
							while (in.hasNext()) {
								String string2 = in.nextLine();
								if (!string2.contains("FINISHED")) {
									outPrinter2.println(string2);
								} else {
									outPrinter2.close();
									URL fileUrl = file.toURL();
									new OpenLoginScene(lock, stage, fileUrl,outPrinterMess).loadTheScene();
									
		//////////////////////////////Print PlayGame & PlayGamController////////////////////////
									file3.createNewFile();
									outStream3 = new FileOutputStream(file3);
									PrintWriter outPrinter3 = new PrintWriter(new OutputStreamWriter(outStream3, "UTF-8"), true);		
									while (in.hasNext()) {
										String string3 = in.nextLine();	
										if (!string3.contains("DONE_PLAYGAME")) {
											outPrinter3.println(string3);
										} else {
											outPrinter3.close();
											file4.createNewFile();
											outStream4 = new FileOutputStream(file4);
											PrintWriter outPrinter4 = new PrintWriter(new OutputStreamWriter(outStream4, "UTF-8"),
													true);
											while (in.hasNext()) {
												String string4 = in.nextLine();
												if (!string4.contains("FINISHED_PLAYGAME")) {
													System.out.println(string4);
													outPrinter4.println(string4);
												} else {
													outPrinter4.close();
													URL fileUrl2 = file3.toURL();
													new OpenPlayGameScene(lock, stage, fileUrl2).loadTheScene();
												}
											}
										}
									}
								}
							}
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				in.close();
//				((Button)lock.root.getChildren().get(2)).setOnAction(null);
//				outPrinterMess.write(((TextField)lock.root.getChildren().get(0)).getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Exception 1 :"+e.getMessage());
		}
	}
}
