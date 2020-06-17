package detNodulos;


import detNodulos.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewAjustaConstrasteController {
	
    @FXML private Slider slConstraste;

    @FXML private Slider slBrilho;
    
    @FXML private ImageView imgViewTonalidade;
    
    public void initialize() {
    	Image imgTonalidade = detNoduloController.instance.img1;
    	imgViewTonalidade.setImage(imgTonalidade);
    	
    	slConstraste.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setContrasteImagem();
           });
    	
    	slBrilho.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setBrilhoImagem();
           });
    	
    	
    }

	private void setBrilhoImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}

	private void setContrasteImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}
	
}
