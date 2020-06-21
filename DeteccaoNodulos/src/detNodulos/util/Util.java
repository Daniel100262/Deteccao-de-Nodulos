package detNodulos.util;

import java.util.Arrays;
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
	
	
	
	public static Image ruidos(Image imagem, int tipoVizinhos) {
		try {
			
			 int w = (int)imagem.getWidth();
			 int h = (int)imagem.getHeight();
			 
			 PixelReader pr = imagem.getPixelReader();
			 WritableImage wi = new WritableImage(w,h);
			 PixelWriter pw = wi.getPixelWriter();
			 
			 
			 for(int i=1; i<w-1; i++) {
				 for(int j=1; j<h-1; j++) {
					 Color corA = pr.getColor(i,j);
					 Pixel p = new Pixel(corA.getRed(), corA.getGreen(), corA.getBlue(), i, j);
					 buscaVizinhos(imagem, p);
					 Pixel vetor[] = null;
					 if(tipoVizinhos == Constantes.VIZINHOS3x3) {
						 vetor = p.viz3;
					 }
					 if(tipoVizinhos == Constantes.VIZINHOSCRUZ) {
						 vetor = p.vizC;
					 }
					 if(tipoVizinhos == Constantes.VIZINHOSX) {
						 vetor = p.vizX;
					 }
					 double red = mediana(vetor, Constantes.CANALR);
					 double green = mediana(vetor, Constantes.CANALG);
					 double blue = mediana(vetor, Constantes.CANALB);
					 Color corN = new Color(red, green, blue, corA.getOpacity());
					 pw.setColor(i, j, corN);
				 }
			 }
			
			 
			 return wi;
			 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static void buscaVizinhos(Image imagem, Pixel p) {
		p.vizX = buscaVizinhosX(imagem, p);
		p.vizC = buscaVizinhosC(imagem, p);
		p.viz3 = buscaVizinhos3(imagem, p);
	}
	
	
	private static Pixel[] buscaVizinhosX(Image imagem, Pixel p) {
		Pixel[] vizX = new Pixel[5];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x-1,p.y+1);
		vizX[0] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y+1);
		
		cor = pr.getColor(p.x+1, p.y-1);
		vizX[1] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y-1);
		
		cor = pr.getColor(p.x-1, p.y-1);
		vizX[2] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y-1);
		
		cor = pr.getColor(p.x+1, p.y+1);
		vizX[3] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y+1);
		
		vizX[4] = p;
		return vizX;
	}
	
	
	
	private static Pixel[] buscaVizinhosC(Image imagem, Pixel p) {
		Pixel[] vizC = new Pixel[5];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x,p.y+1);
		vizC[0] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y+1);
		
		cor = pr.getColor(p.x, p.y-1);
		vizC[1] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y-1);
		
		cor = pr.getColor(p.x-1, p.y);
		vizC[2] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y);
		
		cor = pr.getColor(p.x+1, p.y);
		vizC[3] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y);
		
		vizC[4] = p;
		return vizC;
	}
	
	private static Pixel[] buscaVizinhos3(Image imagem, Pixel p) {
		Pixel[] viz3 = new Pixel[9];
		PixelReader pr = imagem.getPixelReader();
		Color cor;
		
		cor = pr.getColor(p.x-1,p.y+1);
		viz3[0] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y+1);
		
		cor = pr.getColor(p.x+1, p.y-1);
		viz3[1] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y-1);
		
		cor = pr.getColor(p.x-1, p.y-1);
		viz3[2] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y-1);
		
		cor = pr.getColor(p.x+1, p.y+1);
		viz3[3] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y+1);
		
		cor = pr.getColor(p.x,p.y+1);
		viz3[4] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y+1);
		
		cor = pr.getColor(p.x, p.y-1);
		viz3[5] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y-1);
		
		cor = pr.getColor(p.x-1, p.y);
		viz3[6] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y);
		
		cor = pr.getColor(p.x+1, p.y);
		viz3[7] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y);
		
		viz3[8] = p;
		
		return viz3;
	}
	
	
	
	private static double mediana(Pixel[] pixels, int canal) {
		double v[] = new double[pixels.length];
		
		for(int i=0; i<pixels.length; i++) {
			if(canal == Constantes.CANALR) {
				v[i] = pixels[i].r;
			}
			if(canal == Constantes.CANALG) {
				v[i] = pixels[i].g;
			}
			if(canal == Constantes.CANALB) {
				v[i] = pixels[i].b;
			}
		}
		Arrays.sort(v);
		return v[v.length/2];
	}
	
	
	
	
	
	public static void exibeErro(String titulo, String cabecalho, String msg, AlertType tipoAlerta) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(msg);
		alerta.showAndWait();
	}
	
}
