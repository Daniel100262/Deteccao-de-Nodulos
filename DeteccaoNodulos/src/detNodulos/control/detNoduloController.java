package detNodulos.control;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import detNodulos.PreProcessamento;
import detNodulos.Segmentacao;
import detNodulos.util.Constantes;
import detNodulos.util.Util;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class detNoduloController {
	
	@FXML public ImageView imgOriginal;
	@FXML public ImageView imgProcessada;
	public Image img1;
	public Image img2;
	public Image imgNegativo;
	public Image imgBrilhoContrasteAjustado;
	public static detNoduloController instance;
	Stage stage;
	
	@FXML
	public void abrirImagem() {
		imgOriginal.setPreserveRatio(true);
		img1 = abreImagem(imgOriginal, img1);
		mostraImagemProcessada();
	}
	
	@FXML
	public void rodarAlgoritmos() {

		if (img1 != null) {
			img1 = PreProcessamento.cinzaMediaAritmetica(img1, 0, 0, 0);
			abreModalAjusteTonalidade();
		} else {
			Util.exibeErro("Erro!", "X", "Não é possível abrir o processamento de imagem sem selecionar uma imagem antes.", AlertType.ERROR);
		}
	}
	
	
	
	public void mostraImagemProcessada() {
		imgProcessada.setFitHeight(640);
		imgProcessada.setPreserveRatio(true);
		imgProcessada.setImage(img2);
	}
	
	
	private Image abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if(f != null) {
			image = new Image(f.toURI().toString(), 640, 440, true, false);
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
				Util.exibeErro("ERRO", "Ocorreu um erro de I/O", ex.getMessage(), AlertType.ERROR);
				ex.printStackTrace();
			}
		}
		
	}
	
	public void abreModalAjusteTonalidade() {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/detNodulos/view/ViewAjustaImagem.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Ajuste de Imagem");
			stage.setScene(new Scene(root1));  
			stage.showAndWait();
			
			
			img2 = Segmentacao.posteirizacao(img2);
			img2 = Util.ruidos(img2, Constantes.VIZINHOS3x3);
			img2 = Util.ruidos(img2, Constantes.VIZINHOS3x3);
			img2 = Util.ruidos(img2, Constantes.VIZINHOS3x3);
			img2 = Util.ruidos(img2, Constantes.VIZINHOS3x3);
			img2 = Util.ruidos(img2, Constantes.VIZINHOS3x3);
			img2 = Util.adicao(img1, img2, 0.8, 0.3);
			mostraImagemProcessada();
			
		} 
		catch (Exception erro) {
			Util.exibeErro("ERRO", "Ocorreu uma exceção não tratada", erro.getMessage(), AlertType.ERROR);
			erro.printStackTrace();
		}
	}
	
	 public void initialize() {
		 instance = this;
		 
	 }

	public ImageView getImgOriginal() {
		return imgOriginal;
	}
}
 