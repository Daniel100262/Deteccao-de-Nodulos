package detNodulos;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PreProcessamento {

	public static Image cinzaMediaAritmetica(Image imagem, int pcr, int pcb, int pcg) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color corA = pr.getColor(i,j);
					double media = (corA.getRed()+corA.getGreen()+corA.getBlue())/3;
					if(pcr != 0 || pcg != 0 || pcb != 0)
						media = (corA.getRed()*pcr + corA.getGreen()*pcg +corA.getBlue()*pcb)/100;
					Color corN = new Color(media, media, media, corA.getOpacity());
					pw.setColor(i, j, corN);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	public static Image negativa (Image imagem) {
		try {
			
			int w = (int)imagem.getWidth();
			 int h = (int)imagem.getHeight();
			 
			 PixelReader pr = imagem.getPixelReader();
			 WritableImage wi = new WritableImage(w,h);
			 PixelWriter pw = wi.getPixelWriter();
			 
			 for (int i=0; i<w; i++) {
				 for (int j=0; j<h; j++) {
					 Color corA = pr.getColor(i, j);
					 Color corN = new Color ( 1-corA.getRed(),  1-corA.getGreen(),  1-corA.getBlue(), corA.getOpacity());
					 pw.setColor(i, j, corN); 
				 }
			 }
			 return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
