package detNodulos;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class detNoduloController {
	
	@FXML ImageView imgOriginal;
	@FXML ImageView imgProcessada;
	public Image img1;
	public Image img2;
	public static detNoduloController instance;
	
	@FXML
	public void abrirImagem() {
		imgOriginal.maxHeight(640);
		imgOriginal.maxWidth(480);
		imgOriginal.setPreserveRatio(true);
		img1 = abreImagem(imgOriginal, img1);
	}
	
	@FXML
	public void rodarAlgoritmos() {
		abreModalAjusteTonalidade(img2);
	}
	
	@FXML
	public void cinzaAritmetica(){
		img2 = PreProcessamento.cinzaMediaAritmetica(img1, 0, 0, 0);
		mostraImagemProcessada();
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
	
	public Image abreModalAjusteTonalidade(Image imgAntesAjuste) {

		Image imgDepoisAjuste = imgAntesAjuste;

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewAjustaContraste.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Ajuste de tonalidade");
			stage.setScene(new Scene(root1));  
			stage.show();
		} 
		catch (Exception e) {

		}

		return imgDepoisAjuste;
	}
	
	 public void initialize() {
		 instance = this;
	 }
	 
}
