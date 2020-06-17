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
    		setContrasteImagem(imgViewTonalidade);
           });
    	
    	slBrilho.valueProperty().addListener((observable, oldValue, newValue) -> {
    		setBrilhoImagem(imgViewTonalidade);
           });
    	
    }

	private void setBrilhoImagem(ImageView imgViewOriginal) {
		Util.ajustaCor(imgViewOriginal, slBrilho.getValue()/100, "B");
	}

	private void setContrasteImagem(ImageView imgViewOriginal) {
		Util.ajustaCor(imgViewOriginal, slConstraste.getValue()/100, "C");
	}

}
