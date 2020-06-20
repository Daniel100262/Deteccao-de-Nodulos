package detNodulos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Util {
	
	public static int[] histogramaAC(int hist[]) {
		int histAC[] = new int[hist.length];
		int histSoma = 0;
		
		for(int i=0; i<hist.length; i++) {
			histSoma += hist[i];
			histAC[i] = histSoma;
		}
		return histAC;
	}
	
	public static int qtTons(int hist[]) {
		
		int qt = 0;
		
		for(int i=0; i<hist.length; i++) {
			if (hist[i] > 0) {
				qt++;
			}
		}
		return qt;	
	}
	
	public static int pontoMin(int[] hist) {
		for (int i=0; i<hist.length; i++) {
			if(hist[i] > 0) {
				return i;
			}
		}
		return 0;
	}
	
	public static void ajustaCor(ImageView imgView, double contrast, double brightness) {
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(contrast);
		colorAdjust.setBrightness(brightness);
		imgView.setEffect(colorAdjust);
	}
	
	
	public static Image adicao(Image img1, Image img2, double ti1, double ti2) {
		int w1 = (int)img1.getWidth();
		int h1 = (int)img1.getHeight();
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);
		
		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		WritableImage wi = new WritableImage(w,h);
		PixelWriter pw = wi.getPixelWriter();
		
		for (int i = 1; i < w-1; i++) {
			for (int j = 1;j < h-1; j++) {
				Color colorImg1 = pr1.getColor(i, j);
				Color colorImg2 = pr2.getColor(i, j);
				double r = (colorImg1.getRed()*ti1) + (colorImg2.getRed()*ti2);
				double g = (colorImg1.getGreen()*ti1) + (colorImg2.getGreen()*ti2);
				double b = (colorImg1.getBlue()*ti1) + (colorImg2.getBlue()*ti2);
				r = r > 1 ? 1 : r;
				g = g > 1 ? 1 : g;
				b = b > 1 ? 1 : b;
				Color newColor = new Color(r, g, b, 1);
				pw.setColor(i, j, newColor);
			}
		}
		return wi;
	}
	
	
	
	public static void exibeErro(String titulo, String cabecalho, String msg, AlertType tipoAlerta) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(msg);
		alerta.showAndWait();
	}
	
}
