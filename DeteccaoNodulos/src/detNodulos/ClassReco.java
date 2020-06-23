package detNodulos;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import detNodulos.util.Util;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ClassReco {

	public static Image detectaNodulo(Image img) {
		try {
			
			Mat imgNormal = Util.image2Mat(img);
			
			Mat imgTomCinza = new Mat();
	       // Imgproc.cvtColor(imgNormal, gray, Imgproc.COLOR_BGR2GRAY);
	        Imgproc.medianBlur(imgNormal, imgTomCinza, 5);
	        
	        Mat circles = new Mat();
	        Imgproc.HoughCircles(imgTomCinza, circles, Imgproc.HOUGH_GRADIENT, 1.0,
	                (double)imgTomCinza.rows()/16, // change this value to detect circles with different distances to each other
	                100.0, 30.0, 30, 50); // change the last two parameters
	                // (min_radius & max_radius) to detect larger circles
	        
	        
	        for (int x = 0; x < circles.cols(); x++) {
	            double[] c = circles.get(0, x);
	            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
	            // circle center
	            Imgproc.circle(imgTomCinza, center, 1, new Scalar(0,100,100), 3, 8, 0 );
	            // circle outline
	            int radius = (int) Math.round(c[2]);
	            Imgproc.circle(imgTomCinza, center, radius, new Scalar(255,0,255), 3, 8, 0 );
	            
	            
	        }

			img = Util.mat2Image(imgTomCinza);
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
