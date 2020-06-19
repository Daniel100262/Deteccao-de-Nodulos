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
    	imgViewLimiar.setImage(imgLimiarizar);
    	
    	slLimiar.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setLimiarImagem(imgLimiarizar);
           });
    	
    }

	private void setLimiarImagem(Image img) {
		detNodulos.PreProcessamento.limiarizacao(img, slLimiar.getValue());
	}

}
