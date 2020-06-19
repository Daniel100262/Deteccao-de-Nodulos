package detNodulos.control;


import detNodulos.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    	detNoduloController.instance.imgBrilhoContrasteAjustado = imgViewTonalidade.getImage();
    	
    }
    		
	private void setBrilhoImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}

	private void setContrasteImagem() {
		Util.ajustaCor(imgViewTonalidade, slConstraste.getValue()/100, slBrilho.getValue()/100);
	}

	public Image getImgViewTonalidade() {
		return imgViewTonalidade.getImage();
	}
	
}
