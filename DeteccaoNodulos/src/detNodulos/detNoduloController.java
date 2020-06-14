package detNodulos;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class detNoduloController {
	@FXML ImageView imgOriginal;
	@FXML ImageView imgProcessada;
	private Image img1;
	private Image img2;
	
	@FXML
	public void abrirImagem() {
		imgOriginal.maxHeight(640);
		imgOriginal.maxWidth(480);
		imgOriginal.setPreserveRatio(true);
		img1 = abreImagem(imgOriginal, img1);
	}
	
	@FXML
	public void rodarAlgoritmos() {
		
	}
	
	
	private void mostraImagemProcessada() {
		imgProcessada.setImage(img2);
		imgProcessada.setFitWidth(img2.getWidth());
		imgProcessada.setFitHeight(img2.getHeight());
	}
	
	
	private Image abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if(f != null) {
			image = new Image(f.toURI().toString());
			imageView.setImage(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
			return image;
		}
		return null;
	}
	
	private File selecionaImagem() {
		   FileChooser fileChooser = new FileChooser();
		   fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		   fileChooser.setInitialDirectory(new File(
				   "G:\\Arquivos_Usuario\\DeskWin10\\imgs"));
		   File imgSelec = fileChooser.showOpenDialog(null);
		   try {
			   if (imgSelec != null) {
			    return imgSelec;
			   }
		   } catch (Exception e) {
			e.printStackTrace();
		   }
		   return null;
	}
	
	@FXML
	public void salvarImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				FileChooser.ExtensionFilter("PNG",".png"));
		fileChooser.setTitle("Salvar Imagem");
		File file = fileChooser.showSaveDialog(null); 
			
		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(img2, null), "png", file);
			} catch (IOException ex) {
				System.out.println("aconteceu um erro inesperado");
			}
		}
		
	}
}
