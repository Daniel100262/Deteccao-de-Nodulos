package detNodulos.control;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewLimiarizacaoController {
	
    @FXML private Slider slLimiar;
    @FXML private ImageView imgViewLimiar;
    
    public void initialize() {
    	Image imgLimiarizar = detNoduloController.instance.imgBrilhoContrasteAjustado;
    	
    	slLimiar.setValue(100);
    	
    	imgViewLimiar.setImage(imgLimiarizar);
    	
    	imgViewLimiar.setImage(detNodulos.PreProcessamento.limiarizacao(imgLimiarizar, slLimiar.getValue()/100));
    	
    	slLimiar.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setLimiarImagem(imgLimiarizar);
           });
    	
    }

	private void setLimiarImagem(Image img) {
		imgViewLimiar.setImage(detNodulos.PreProcessamento.limiarizacao(img, slLimiar.getValue()/100));
	}

}
