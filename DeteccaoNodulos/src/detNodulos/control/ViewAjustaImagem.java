package detNodulos.control;

import java.io.IOException;

import detNodulos.Segmentacao;
import detNodulos.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewAjustaImagem {

	@FXML private HBox hbTop;
	@FXML private HBox hbBottom;
	@FXML private Button btConfirmar;
	@FXML public ImageView imgViewEstadoInicial;
	@FXML public ImageView imgViewEstadoFinal;
	
	private int nextPage;
	
	private static final int PAGINA_AJUSTE_TONALIDADE = 0;
	private static final int PAGINA_AJUSTE_LIMIARIZACAO = 1;

	private static final String CAMINHO_COMPONENTE_VBOXBOTTOM = "../component/VBoxBottom.fxml";
	
	public void initialize() throws IOException {
		transformViewStepOne();
	}

	private void transformViewStepOne() throws IOException {
		adicionaLabelTop("Ajuste Tonalidade");
		adicionaImages();
		adicionaComponentesBottom(PAGINA_AJUSTE_TONALIDADE);
		setEventBtConfirmarStepOne();
	}
	
	private void transformViewStepTwo() throws IOException {
		adicionaLabelTop("Limiarização");
		trocaImagens();
		adicionaComponentesBottom(PAGINA_AJUSTE_LIMIARIZACAO);
	}
	
	private void setEventBtConfirmarStepOne() {
		btConfirmar.setOnAction(e -> {
			try {
				btConfirmarClick();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			});
	}

	private void btConfirmarClick() throws IOException {
		nextPage++;
		hbTop.getChildren().remove(0);
		hbBottom.getChildren().remove(0);
		hbBottom.getChildren().remove(0);
		if (nextPage == PAGINA_AJUSTE_LIMIARIZACAO) {
			transformViewStepTwo();
		} else {
			//detNoduloController.instance.imgProcessada = imgViewEstadoFinal;
			detNoduloController.instance.img2 = imgViewEstadoFinal.getImage();
			detNoduloController.instance.mostraImagemProcessada();
			detNoduloController.instance.stage.close();
			
		}
	}

	private void trocaImagens() {
		Image currentImage = imgViewEstadoFinal.snapshot(null, null);
		imgViewEstadoInicial.setImage(currentImage);
		imgViewEstadoFinal.setImage(currentImage);
	}

	private void adicionaImages() {
		Image imgNegativa = detNoduloController.instance.imgNegativo;
		imgViewEstadoInicial.setImage(imgNegativa);
		imgViewEstadoFinal.setImage(imgNegativa);
	}

	private void adicionaComponentesBottom(int idPage) throws IOException {
		if (idPage == PAGINA_AJUSTE_TONALIDADE) {
			constructorVboxSlider(hbBottom, idPage);
		} else if (idPage == PAGINA_AJUSTE_LIMIARIZACAO) {
			constructorVboxSlider(hbBottom, idPage);
		} 
	}

	private void adicionaLabelTop(String titulo) throws IOException {
		Label label = ((Label)FXMLLoader.load(getClass().getResource("../component/LabelTop.fxml")));
		label.setText(titulo);
		hbTop.getChildren().add(label);
	}
	
	private void constructorVboxSlider(HBox parent, int idPage) throws IOException {
		if (idPage == PAGINA_AJUSTE_TONALIDADE) {
			transformHboxBottomPageOne(parent);
		} else if (idPage == PAGINA_AJUSTE_LIMIARIZACAO) {
			transformHboxBottomPageTwo(parent);
		}
	}

	private void transformHboxBottomPageTwo(HBox parent) throws IOException {
		VBox vbSliderLimiar = getVBoxBottom("Limiar");
		Slider sliderLimiar = VBoxBottomController.instance.getSlider();
		setEventSliderLimiar(sliderLimiar);
		sliderLimiar.setValue(100);
		parent.getChildren().add(0, vbSliderLimiar);
	}

	private void transformHboxBottomPageOne(HBox parent) throws IOException {
		VBox vbSliderContraste = getVBoxBottom("Contraste");
		Slider slContraste = VBoxBottomController.instance.getSlider();
		VBox vbSliderBrilho = getVBoxBottom("Brilho");
		Slider slBrilho = VBoxBottomController.instance.getSlider();
		setEventsSliderContrasteEBrilho(slContraste, slBrilho);
		parent.getChildren().add(0, vbSliderContraste);
		parent.getChildren().add(1, vbSliderBrilho);
	}
	
	private VBox getVBoxBottom(String label) throws IOException {
		VBox vbSliderBrilho = ((VBox)FXMLLoader.load(getClass().getResource(CAMINHO_COMPONENTE_VBOXBOTTOM)));
		Label labelSlBrilho = VBoxBottomController.instance.getLabelSlider();
		labelSlBrilho.setText(label);
		return vbSliderBrilho;
	}

	
	public void setEventsSliderContrasteEBrilho(Slider slContraste, Slider slBrilho) {
		slContraste.valueProperty().addListener((observable, oldValue, newValue) -> {
			Util.ajustaCor(imgViewEstadoFinal, slContraste.getValue()/100, slBrilho.getValue()/100);
		});
		
		slBrilho.valueProperty().addListener((observable, oldValue, newValue) -> {
			Util.ajustaCor(imgViewEstadoFinal, slContraste.getValue()/100, slBrilho.getValue()/100);
		});
	}
	
	private void setEventSliderLimiar(Slider slLimiar) {
		slLimiar.valueProperty().addListener((observable, oldValue, newValue) -> {
			imgViewEstadoFinal.setImage(Segmentacao.limiarizacao(imgViewEstadoInicial.getImage(), slLimiar.getValue()/100));
		});
	}
	
}