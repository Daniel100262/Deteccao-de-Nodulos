package detNodulos.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class VBoxBottomController {
	
	public static VBoxBottomController instance;
	
	@FXML private Slider slider;
	@FXML private Label labelSlider;
	
	public Slider getSlider() {
		return slider;
	}

	public void setSlider(Slider slider) {
		this.slider = slider;
	}

	public Label getLabelSlider() {
		return labelSlider;
	}

	public void setLabelSlider(Label labelSlider) {
		this.labelSlider = labelSlider;
	}

	public void initialize() {
		instance = this;
	}
	
}