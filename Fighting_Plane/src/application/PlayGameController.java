package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayGameController{
	@FXML
	private ImageView planeId;
	@FXML
	private ImageView ocId2;
	@FXML
	private ImageView ocId3;
	@FXML
	private ImageView ocId1;
	@FXML
	private ImageView ocId5;
	@FXML
	private ImageView ocId4;
	@FXML
	private ImageView gameoverId;
	@FXML
	private Label messId;
	@FXML
	private Label scoreId;
	@FXML
	private Label HigestId;
	@FXML
	private Label nameId;
	@FXML
	private Pane rootId;
	@FXML
	private Button restartId;
	
	private TranslateTransition o1 = new TranslateTransition();
	private TranslateTransition o2 = new TranslateTransition();
	private TranslateTransition o3 = new TranslateTransition(); 
	private TranslateTransition o4 = new TranslateTransition(Duration.seconds(8), ocId4);
	private TranslateTransition o5 = new TranslateTransition(Duration.seconds(8), ocId5);
	private TranslateTransition plane  = new TranslateTransition();
	private TranslateTransition[] obstacles = {o1,o2,o3,o4,o5};
	
	private PointObstacles p1 = new PointObstacles(o1);
	private PointObstacles p2 = new PointObstacles(o2);
	private PointObstacles p3 = new PointObstacles(o3);
	private PointObstacles p4 = new PointObstacles(o4);
	private PointObstacles p5 = new PointObstacles(o5);
	
	DoubleProperty planeY = new SimpleDoubleProperty();
	Thread threado1;
	Thread threado2;
	Thread threado3;
	Thread threado4;
	Thread threado5;
	private int score = 0;
	private Timeline CheckAnimation;
	private Timeline CheckAnimationBullet;
	private Timeline explosionAnimation;
	private Boolean firstPlayObstacles = true;
	private int highestScore;
	private String name;  
	private Rectangle rec;
	private Rectangle recPlane1 = new Rectangle(14, 303, 95, 8); 
	private Rectangle recPlane2 = new Rectangle(26, 296, 37, 20);
	private Rectangle recPlane3 = new Rectangle(27, 297, 69, 8);  
	private Rectangle recPlane4 = new Rectangle(24, 282, 31, 20);
	private Rectangle recPlane5 = new Rectangle(74, 291, 14, 20);
	private TranslateTransition runRecPlane1 = new TranslateTransition();
	private TranslateTransition runRecPlane2 = new TranslateTransition();
	private TranslateTransition runRecPlane3 = new TranslateTransition();
	private TranslateTransition runRecPlane4 = new TranslateTransition();
	private TranslateTransition runRecPlane5 = new TranslateTransition();
	final String DB_URL = "jdbc:derby:UserDB";

	@FXML
	public void PlayGameAction(KeyEvent event) {
		recPlane1.setVisible(false);
		recPlane2.setVisible(false);
		recPlane3.setVisible(false);
		recPlane4.setVisible(false);
		recPlane5.setVisible(false);
		rootId.getChildren().addAll(recPlane1,recPlane2,recPlane3,recPlane4,recPlane5);
		nameId = (Label) rootId.getChildren().get(12);
		HigestId = (Label) rootId.getChildren().get(11);
		RunObstacles();
		plane.setNode(planeId);
		runRecPlane1.setNode(recPlane1);
		runRecPlane2.setNode(recPlane2);
		runRecPlane3.setNode(recPlane3);
		runRecPlane4.setNode(recPlane4);
		runRecPlane5.setNode(recPlane5);
		rootId.setOnKeyPressed(k->{
			KeyCode key = k.getCode();
			
			switch (key) {
			case SPACE:
				RunBullet();
				break;
			case UP:
				if(planeId.getTranslateY()>= -300) {
					planeTransition(-10);
					RunPlane();
				}
				break;
			case DOWN:
				if(planeId.getTranslateY() <= 400) {
					planeTransition(10);
					RunPlane();
				}
				break;
			default:
				break;
			}
		});
		planeId.translateYProperty().bind(planeY);
		CheckAnimation = new Timeline(
                new KeyFrame(new Duration(60.0), t -> {
                    try {
						checkCollisionPlane(ocId1);
						checkCollisionPlane(ocId2);
	                    checkCollisionPlane(ocId3);
	                    checkCollisionPlane(ocId4);
	                    checkCollisionPlane(ocId5);
					} catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
                    
                })
        );
		CheckAnimation.setCycleCount(Timeline.INDEFINITE);
		CheckAnimation.playFromStart();
	}
	
	@FXML
	public void RestartAction(ActionEvent event) throws IOException {
		restart(event);
	}
	
	private void RunObstacles() { 
		
		Platform.runLater(() -> {
			Runnable task1 = () ->{
			try {
				while(true) {
					p1.setPointObstacles(firstPlayObstacles,ocId1);
					if(firstPlayObstacles) {
						o1.setDuration(Duration.seconds(7));
					}else {
						if(ocId1.isVisible()== false){
							o1.playFromStart();
						}
						ocId1.setVisible(true);
						Thread.sleep(50);
						o1.setDuration(Duration.seconds(8));
					}
					o1.play();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		};
		threado1= new Thread(task1);
		threado1.start();
		});
		Platform.runLater(() -> {
			Runnable task2 = () ->{
				try {
					while(true) {
						p2.setPointObstacles(firstPlayObstacles,ocId2);
						if(firstPlayObstacles) {
							Thread.sleep(5);
							o2.setDuration(Duration.seconds(7));
						}else {
							if(ocId2.isVisible()== false){
								o2.playFromStart();
								
							}
							ocId2.setVisible(true);
							Thread.sleep(80);
							o2.setDuration(Duration.seconds(8));
						}
						o2.play();
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};
			threado2 = new Thread(task2);
			threado2.start();
		});
		Platform.runLater(() -> {
			Runnable task3 = () ->{
				try {
					while(true) {
						p3.setPointObstacles(firstPlayObstacles,ocId3);
						if(firstPlayObstacles) {
							Thread.sleep(10);
							o3.setDuration(Duration.seconds(7));
						}else {
							if(ocId3.isVisible()== false){
								o3.playFromStart();
							}
							ocId3.setVisible(true);
							Thread.sleep(150);
							o3.setDuration(Duration.seconds(8));
						}
						o3.play();
						
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};
			threado3 = new Thread(task3);
			threado3.start();
		});
		Platform.runLater(() -> {
			Runnable task4 = () ->{
				try {
					while(true) {
						p4.setPointObstacles(firstPlayObstacles,ocId4);
						if(!firstPlayObstacles) {
							Thread.sleep(200);	
							if(ocId4.isVisible()== false){
								o4.playFromStart();
							}
							ocId4.setVisible(true);
							o4.play();
					}
				}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};
			threado4 = new Thread(task4);
			threado4.start();
		});
		Platform.runLater(() -> {
			Runnable task5 = () ->{
				firstPlayObstacles = false;
				try {
					while(true) {
						if(!firstPlayObstacles) {
							Thread.sleep(300);
							p5.setPointObstacles(firstPlayObstacles,ocId5);
							if(ocId5.isVisible()== false){
								o5.playFromStart();
							}
							ocId5.setVisible(true);
							o5.play();
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};
			threado5 = new Thread(task5);
			threado5.start();
		});
	}
	private void RunPlane() {
		Platform.runLater(() -> {
			Runnable task = () ->{
			try {
				plane.play();
				runRecPlane1.play();
				runRecPlane2.play();
				runRecPlane3.play();
				runRecPlane4.play();
				runRecPlane5.play();
			} catch (Exception e) {
				System.out.println("Plane"+e.getMessage());
			}
			};
			new Thread(task).start();
		});
		
	}
	
	private void RunBullet() {
		rec = new Rectangle(70,310,12,3);
		rootId.getChildren().add(rec);
		rec.setFill(Color.DARKRED);
		TranslateTransition bullet = new TranslateTransition(Duration.seconds(1), rec);
		Platform.runLater(() -> {
			Runnable task = () ->{
				try {
					bullet.setFromY(planeId.getTranslateY());
					bullet.setFromX(planeId.getTranslateX());
					bullet.setToX(1300);
					bullet.setNode(rec);
					bullet.playFromStart();
					CheckAnimationBullet = new Timeline(
			                new KeyFrame(new Duration(0.1), t -> {
			                    try {
									checkCollisionBullet(ocId1,rec);
									checkCollisionBullet(ocId2,rec);
				           		    checkCollisionBullet(ocId3,rec);
				            		checkCollisionBullet(ocId4,rec);
				            		checkCollisionBullet(ocId5,rec);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									System.out.println(e.getMessage());
								}
			            		
			                })
			        );
					CheckAnimationBullet.setCycleCount(Timeline.INDEFINITE);
					CheckAnimationBullet.playFromStart();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};
			new Thread(task).start();
		});
	}

	private void checkCollisionPlane(ImageView obstacle) throws FileNotFoundException {
		if (recPlane1.getBoundsInParent().intersects(obstacle.getBoundsInParent()) || 
			recPlane2.getBoundsInParent().intersects(obstacle.getBoundsInParent()) ||
			recPlane3.getBoundsInParent().intersects(obstacle.getBoundsInParent()) || 
			recPlane4.getBoundsInParent().intersects(obstacle.getBoundsInParent()) ||
			recPlane5.getBoundsInParent().intersects(obstacle.getBoundsInParent()) ){
			if(obstacle.isVisible() == true) {
				FileInputStream stream = new FileInputStream("D://SPRING 2021//Advantage_JAVA//Final_Project//images//explosion2.png");
				Image im = new Image(stream);
				ImageView image = new ImageView(im);
				image.setFitWidth(200);
				image.setPreserveRatio(true);
				image.setSmooth(true);
				image.setCache(true);
				image.setX(recPlane1.getBoundsInParent().getMinX()-50);
				image.setY(recPlane1.getBoundsInParent().getMinY()-50);
				image.setVisible(true);
				
				FileInputStream stream2 = new FileInputStream("D://SPRING 2021//Advantage_JAVA//Final_Project//images//explosion4.png");
				Image im2 = new Image(stream2);
				ImageView image2 = new ImageView(im2);
				image2.setFitWidth(300);
				image2.setPreserveRatio(true);
				image2.setSmooth(true);
				image2.setCache(true);
				image2.setX(recPlane1.getBoundsInParent().getMinX()-50);
				image2.setY(recPlane1.getBoundsInParent().getMinY()-50);
				image2.setVisible(false);
				rootId.getChildren().addAll(image,image2);
				explosionAnimation = new Timeline(
		                new KeyFrame(new Duration(300.0), t -> {
		                	if(image.isVisible() && !image2.isVisible( )) {
		                		image.setVisible(false);
		                		image2.setVisible(true);
		                	}
		                	else {
		                		image2.setVisible(false);
		                	}
		                })
		                
		        );
				explosionAnimation.setCycleCount(2);
				explosionAnimation.playFromStart();
				
				planeId.setVisible(false);
				obstacle.setVisible(false);
				CheckAnimation.stop();
				gameoverId.setVisible(true);
				messId.setText("Your score is: "+score);
				restartId.setVisible(true);
				threado1.stop();
				threado2.stop();
				threado3.stop();
				threado4.stop();
				threado5.stop();for(TranslateTransition ob:obstacles) {
					if(ob.getNode()==obstacle)
						ob.pause();
				}
			}
			name = nameId.getText().substring(8);
			highestScore = Integer.parseInt( HigestId.getText().substring(15));
			if(score> highestScore) {
				try {
					highestScore = score;
					Connection conn = DriverManager.getConnection(DB_URL);
					Statement stmt = conn.createStatement();
					String stringStatement = "UPDATE Users SET Score= "+score+"WHERE UserName = '"+ name  +"'";
					int result = stmt.executeUpdate(stringStatement);
					if(result==1) {
						System.out.println("Update successfull");
					}
					conn.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	

	private void checkCollisionBullet(ImageView a,Rectangle b) throws FileNotFoundException{
		if (a.getBoundsInParent().intersects(b.getBoundsInParent())){
			if(a.isVisible() == true && b.isVisible() == true) {
				score++;
				scoreId.setText(""+score);
				FileInputStream stream = new FileInputStream("D://SPRING 2021//Advantage_JAVA//Final_Project//images//explosion2.png");
				Image im = new Image(stream);
				ImageView image = new ImageView(im);
				image.setFitWidth(100);
				image.setPreserveRatio(true);
				image.setSmooth(true);
				image.setCache(true);
				image.setX(b.getBoundsInParent().getMinX()-50);
				image.setY(b.getBoundsInParent().getMinY()-50);
				image.setVisible(true);
				
				FileInputStream stream2 = new FileInputStream("D://SPRING 2021//Advantage_JAVA//Final_Project//images//explosion3.png");
				Image im2 = new Image(stream2);
				ImageView image2 = new ImageView(im);
				image2.setFitWidth(100);
				image2.setPreserveRatio(true);
				image2.setSmooth(true);
				image2.setCache(true);
				image2.setX(b.getBoundsInParent().getMinX()-50);
				image2.setY(b.getBoundsInParent().getMinY()-50);
				image2.setVisible(false);
				rootId.getChildren().addAll(image,image2);
				a.setVisible(false);
				b.setVisible(false);
				for(TranslateTransition ob:obstacles) {
					if(ob.getNode()==a)
						ob.stop();	
				}
				explosionAnimation = new Timeline(
		                new KeyFrame(new Duration(100.0), t -> {
		                	if(image.isVisible() && !image2.isVisible()) {
		                		image.setVisible(false);
		                		//image2.setVisible(true);
		                	}
		                	//else 
		                		//image2.setVisible(false);
		                })
		        );
				explosionAnimation.setCycleCount(2);
				explosionAnimation.playFromStart();
			}
		}
	}
	
	private void restart(ActionEvent event) throws IOException{
		nameId.setText("Player: " + name);
		HigestId.setText("Highest Score: "+ highestScore);
		FXMLLoader fxmlLoader = new FXMLLoader(
				Main.class.getResource("PlayGame.fxml"));
		PlayGameController viewController = new PlayGameController();
		fxmlLoader.setController(viewController);
		Pane root = fxmlLoader.load();
		root.getChildren().set(12, nameId);
		root.getChildren().set(11, HigestId);
		Scene sc = new Scene(root);
		sc.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Fighting Plane");
		primaryStage.setScene(sc);
		sc.getRoot().requestFocus();
	}
	private void planeTransition(int num) {
		recPlane1.setTranslateY(recPlane1.getTranslateY() + num);
		recPlane2.setTranslateY(recPlane2.getTranslateY() + num);
		recPlane3.setTranslateY(recPlane3.getTranslateY() + num);
		recPlane4.setTranslateY(recPlane4.getTranslateY() + num);
		recPlane5.setTranslateY(recPlane5.getTranslateY() + num);
		planeY.setValue(planeId.getTranslateY() + num);
	}

}