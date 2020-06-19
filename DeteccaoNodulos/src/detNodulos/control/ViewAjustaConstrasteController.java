package detNodulos.control;

import detNodulos.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ViewAjustaConstrasteController {
	
    @FXML private Slider slConstraste;
    @FXML private Slider slBrilho;
    @FXML private ImageView imgViewTonalidade;
    
    public static ViewAjustaConstrasteController instance; 
    
    public void initialize() {
    	Image imgTonalidade = detNoduloController.instance.imgNegativo;
    	imgViewTonalidade.setImage(imgTonalidade);
    	
    	
    	slConstraste.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setContrasteImagem();
           });
    	
    	slBrilho.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setBrilhoImagem();
           });
    }

    @FXML
    public void salvaImgBrilhoContraste() {
    	detNoduloController.instance.imgBrilhoContrasteAjustado = imgViewTonalidade.snapshot(null, null);
    	closeWindow();
    }
    		
	private void closeWindow() {
	    Stage stage = (Stage) slBrilho.getScene().getWindow(); //pego a instância atual da window à partir de um componente da tela para converter para Stage e poder fechar a janela
	    stage.close();
	}

	private void setBrilhoImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}

	private void setContrasteImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}

}