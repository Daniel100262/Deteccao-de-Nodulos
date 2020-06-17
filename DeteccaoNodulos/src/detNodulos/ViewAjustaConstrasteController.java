package detNodulos;

import detNodulos.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewAjustaConstrasteController {
	
    @FXML
    private Slider slConstraste;

    @FXML
    private Slider slBrilho;
    
    public void initialize() {
    	ImageView imgViewOriginal = detNoduloController.instance.imgOriginal;
    	
    	slConstraste.setOnDragDetected(e -> {
    		setContrasteImagem(imgViewOriginal);
		});
    	
    	slBrilho.setOnDragDetected(e -> {
    		setBrilhoImagem(imgViewOriginal);
		});
    	
    }

	private void setBrilhoImagem(ImageView imgViewOriginal) {
		Util.ajustaCor(imgViewOriginal, slBrilho.getValue()/100, "B");
	}

	private void setContrasteImagem(ImageView imgViewOriginal) {
		Util.ajustaCor(imgViewOriginal, slConstraste.getValue()/100, "C");
	}

}
