package com.dafnis.app.controllers;

import com.dafnis.app.models.Cuento;
import com.dafnis.app.db.BaseDeDatos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public class ControladorLeer{

	@FXML
	private TextArea textArea;
	@FXML
	private Label labelInfoCuento;
	private Stage stage;
	private AnchorPane anchorPane;
	private VBox vboxDcha1;
	private VBox vboxDcha2;
	private Controlador controlador;
	private BaseDeDatos baseDeDatos;

	@FXML
	public void initialize(){
		textArea.setWrapText(true);
	}

	public void setControlador(Controlador controlador){
		this.controlador = controlador;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	public void setVBoxDcha2(VBox vboxDcha2){
		this.vboxDcha2 = vboxDcha2;
	}

	private void getObjBaseDeDatos(){
		if(baseDeDatos == null){
			baseDeDatos = BaseDeDatos.getObj();
		}
	}

	TextArea getTextArea(){
		return textArea;
	}

	public void actionVolver(){
		if(vboxDcha1 == null){
			anchorPane = controlador.getAnchorPane();
			vboxDcha1 = controlador.getVBoxDcha1();
		}
		anchorPane.setTopAnchor(vboxDcha1, 0.0);
		anchorPane.setBottomAnchor(vboxDcha1, 0.0);
		anchorPane.setLeftAnchor(vboxDcha1, 0.0);
		anchorPane.setRightAnchor(vboxDcha1, 0.0);

		vboxDcha1.setOpacity(0.0);
		anchorPane.getChildren().add(vboxDcha1);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(400), anchorPane.getChildren().get(0));
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		FadeTransition fadeIn = new FadeTransition(Duration.millis(400), vboxDcha1);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		ParallelTransition transicionParalela = new ParallelTransition(fadeOut, fadeIn);
		transicionParalela.setOnFinished(e -> {
			anchorPane.getChildren().clear();
			anchorPane.getChildren().add(vboxDcha1);
		});
		transicionParalela.play();
	}

	public void actionBotonCerrar(){
		stage.close();
	}
	public void actionRatonApretado(MouseEvent event){
		Button boton = (Button) event.getSource();
		boton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
	}
	public void actionRatonSoltado(MouseEvent event){
		Button boton = (Button) event.getSource();
		boton.setStyle("-fx-background-color: white");
	}

	public void actionRatonEntra(MouseEvent event){
		Button boton = (Button) event.getSource();
		boton.setStyle("-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
	}

	public void actionRatonSale(MouseEvent event){
		Button boton = (Button) event.getSource();
		boton.setStyle("-fx-scale-x: 1; -fx-scale-y: 1;");
	}

	public void mostrarCuentoSeleccionado(Cuento cuento){
		getObjBaseDeDatos();
		String consulta = "select content from cuentos where id = " + cuento.getId() + ";";
		baseDeDatos.conectar();
		ResultSet resultSet = baseDeDatos.realizarConsulta(consulta);
		try{
			textArea.setText(resultSet.getString("content"));
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		baseDeDatos.desconectar();
	}

	public void mostrarLabelCuentoSeleccionado(Cuento cuento){
		labelInfoCuento.setText("\"" + cuento.getTitulo() + "\"" + " de " + cuento.getAutor() + ".");
	}
}
