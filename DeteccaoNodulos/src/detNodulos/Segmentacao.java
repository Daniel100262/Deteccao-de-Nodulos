package detNodulos;

import detNodulos.util.Util;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Segmentacao {
	
	
	public static Image posteirizacao (Image imagem) {
		try {
			 int w = (int)imagem.getWidth();
			 int h = (int)imagem.getHeight();
			 
			 PixelReader pr = imagem.getPixelReader();
			 WritableImage wi = new WritableImage(w,h);
			 PixelWriter pw = wi.getPixelWriter();
			 
			 for (int i=0; i<w; i++) {
				 for (int j=0; j<h; j++) {
					 Color corA = pr.getColor(i, j);
					 Color corN;

					 if(corA.getRed() + corA.getBlue() + corA.getGreen() >= 2.2) {
						 corN =  Color.rgb(255,0,255,1.0);
					 }else {
						 corN = corA;
					 }
					 pw.setColor(i, j, corN);
				 }
			 }
			 return wi;
			 
		} catch (Exception erro) {
			Util.exibeErro("ERRO", "Ocorreu uma exceção não tratada", erro.getMessage(), AlertType.ERROR);
			return null;
		}
	}
	
	
	
	public static Image limiarizacao (Image imagem, double limiar) {
		try {
			 int w = (int)imagem.getWidth();
			 int h = (int)imagem.getHeight();
			 
			 PixelReader pr = imagem.getPixelReader();
			 WritableImage wi = new WritableImage(w,h);
			 PixelWriter pw = wi.getPixelWriter();
			 
			 for (int i=0; i<w; i++) {
				 for (int j=0; j<h; j++) {
					 Color corA = pr.getColor(i, j);
					 Color corN;
					 
					 if(corA.getRed() >= limiar) {
						 corN = new Color(1, 1, 1, corA.getOpacity());
					 }
					 else {
						 corN = new Color(0, 0, 0, corA.getOpacity());
					 }
					 pw.setColor(i, j, corN);
				 }
			 }
			 return wi;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static int[] histograma(Image img, Color cor) {
		int[] qt = new int [256];
		PixelReader pr = img.getPixelReader();
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		if(Color.RED.equals(cor)) {
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					qt[(int)(pr.getColor(i,j).getRed()*255)]++;
				}
			}
		}
		else if(Color.GREEN.equals(cor)) {
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					qt[(int)(pr.getColor(i,j).getGreen()*255)]++;
				}
			}
		}
		else if(Color.BLUE.equals(cor)) {
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					qt[(int)(pr.getColor(i,j).getBlue()*255)]++;
				}
			}
		}
		return qt;
	}
	
	public static Image equalizacaoHistograma(Image img, boolean todos) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		PixelReader pr = img.getPixelReader();
		WritableImage wi = new WritableImage(w,h); 
		PixelWriter pw = wi.getPixelWriter();
		
		int[] hR = histograma(img, Color.RED);
		int[] hG = histograma(img, Color.GREEN);
		int[] hB = histograma(img, Color.BLUE);
		
		int[] histAcR = Util.histogramaAC(hR);
		int[] histAcG = Util.histogramaAC(hG);
		int[] histAcB = Util.histogramaAC(hB);
		
		int qtTonsRed = Util.qtTons(hR);
		int qtTonsGreen = Util.qtTons(hG);
		int qtTonsBlue = Util.qtTons(hB);
		
		double minR = Util.pontoMin(hR);
		double minG = Util.pontoMin(hG);
		double minB = Util.pontoMin(hB);
		
		if (todos) {
			qtTonsRed = 255;
			qtTonsGreen = 255;
			qtTonsBlue = 255;
			
			minR = 0;
			minG = 0;
			minB = 0;
		}
		
		double n = w*h;
		
		
		for (int i=1; i<w; i++) {
			for (int j=1; j<h; j++) {
				Color oldColor = pr.getColor(i,j);
				double acR = histAcR[(int)(oldColor.getRed()*255)];
				double acG = histAcG[(int)(oldColor.getGreen()*255)];
				double acB = histAcB[(int)(oldColor.getBlue()*255)];
				
				double pxR = ((qtTonsRed-1)/n)*acR;
				double pxG = ((qtTonsGreen-1)/n)*acG;
				double pxB = ((qtTonsBlue-1)/n)*acB;
				
				double corR = (minR+pxR)/255;
				double corG = (minG+pxG)/255;
				double corB = (minB+pxB)/255;
				
				Color newCor = new Color(corR, corG, corB, oldColor.getOpacity());
				pw.setColor(i, j, newCor);
			}
		}
		return wi;
	}	
	
}
