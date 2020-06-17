package detNodulos.util;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Util {
	
	private static final String AJUSTE_COR_TIPO_CONTRASTE = "C";
	private static final String AJUSTE_COR_TIPO_BRILHO = "B";
	
	
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
	
	public static void ajustaCor(ImageView imgView, double value, String tipo) {
		 ColorAdjust colorAdjust = new ColorAdjust();
		 if (tipo.equals(AJUSTE_COR_TIPO_CONTRASTE)) {
			 colorAdjust.setContrast(value);
		 } else if (tipo.equals(AJUSTE_COR_TIPO_BRILHO)) {
			 colorAdjust.setBrightness(value);
		 }
		 imgView.setEffect(colorAdjust);
	}
	
}
