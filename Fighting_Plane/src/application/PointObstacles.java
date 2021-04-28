package application;

import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;

public class PointObstacles {
	private TranslateTransition o; 
	
	public PointObstacles(TranslateTransition o) {
		super();
		this.o = o;
	}

	public void setPointObstacles(boolean first,ImageView obstacle) {
		o.setNode(obstacle);
		o.setToX(-1300);
		if(first== false) {
			Random ran = new Random();
			o.setFromX(120);
			o.setFromY(ran.nextInt(500)-200);
		}
	}
}
