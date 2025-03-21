package com.dafnis.app.controllers;

import com.dafnis.app.db.BaseDeDatos;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ControladorEscribir{
	@FXML
	private TextField textfieldTitulo;
	@FXML
	private TextField textfieldAutor;
	@FXML
	private ComboBox<String> comboboxNacionalidad;
	@FXML
	private ComboBox<String> comboboxCategoria;
	@FXML
	private TextArea textareaEscribir;
	@FXML
	private Label labelMensaje;
	@FXML
	private Label labelRestaurar;

	private PauseTransition pausa;
	private PauseTransition pausaBorrar;
	private boolean pausaBorrarActiva;
	private Stage stage;
	private BaseDeDatos baseDeDatos;

	@FXML
	public void initialize(){
		inicializarComboboxes();
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}
	private void getObjBaseDeDatos(){
		if(baseDeDatos == null){
			baseDeDatos = BaseDeDatos.getObj();
		}
	}
	public ControladorEscribir(){
		getObjBaseDeDatos();
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

	public void actionGuardar(){
		boolean estanTodosRellenos = false;
		estanTodosRellenos = verificarTodosLosCamposRellenos();
		if(estanTodosRellenos){
			String titulo = textfieldTitulo.getText();
			String autor = textfieldAutor.getText();
			String nacionalidad = (String)comboboxNacionalidad.getValue();
			String categoria = (String)comboboxCategoria.getValue();
			String contenido = textareaEscribir.getText();
			baseDeDatos.conectar();
			baseDeDatos.realizarInsercion(titulo, autor, nacionalidad, categoria, contenido);
			labelMensaje.setText("Cuento guardado con éxito.");
			pausa = new PauseTransition(Duration.seconds(3));
			pausa.setOnFinished(e -> {labelMensaje.setText("");});
			pausa.play();
			baseDeDatos.desconectar();
		} else {
			labelMensaje.setText("Hay campos vacíos.");
			pausa = new PauseTransition(Duration.seconds(3));
			pausa.setOnFinished(e -> {labelMensaje.setText("");});
			pausa.play();
		}
	}

	public void actionBorrarTodo(){
		if(pausaBorrarActiva){
			baseDeDatos.conectar();
			baseDeDatos.borrarTodosLosNuevosRegistros();
			baseDeDatos.desconectar();
			labelRestaurar.setText("Todos los cuentos añadidos han sido eliminados.");
		}else{
			labelRestaurar.setText("Pulsa restaurar de nuevo para confirmar.");
			pausaBorrar = new PauseTransition(Duration.seconds(3));
			pausaBorrar.setOnFinished(e -> {
				labelRestaurar.setText("Pulsa para borrar todos los cuentos añadidos.");
				pausaBorrarActiva = false;
			});
			pausaBorrar.play();
			pausaBorrarActiva = true;
		}
	}

	public void actionBotonCerrar(){
		stage.close();
	}

	private boolean verificarTodosLosCamposRellenos(){
		if(!textfieldTitulo.getText().equals("")
			&& !textfieldAutor.getText().equals("")
			&& comboboxNacionalidad.getValue() != null
			&& comboboxCategoria.getValue() != null
			&& !comboboxNacionalidad.getValue().isEmpty()
			&& !comboboxCategoria.getValue().isEmpty()
			&& !textareaEscribir.getText().trim().equals("")){
			return true;
		}else{
			return false;
		}
	}

	private void inicializarComboboxes(){
		comboboxNacionalidad.getItems().addAll("", "Alemania", "Alemania/Suiza", "Argentina", "Austria", "Bélgica", "Bolivia", "Brasil", "Canadá", "Chile", "China", "Colombia", "Costa Rica", "Cuba", "Checoslovaquia", "Dinamarca", "Ecuador", "Egipto", "El Líbano", "El Salvador", "España", "España/México", "Estados Unidos", "Escocia", "Francia", "Gales", "Grecia", "Guatemala", "Honduras", "Hungría", "India", "Inglaterra", "Irlanda", "Italia", "Japón", "México", "Nicaragua", "Noruega", "Nueva Zelanda", "Panamá", "Paraguay", "Perú", "Polonia", "Portugal", "Prusia", "Puerto Rico", "República Dominicana", "Rusia", "Sudáfrica", "Suecia", "Suiza", "Trinidad y Tobago", "Uruguay", "Venezuela");

		comboboxCategoria.getItems().addAll("", "Crónica", "Cuento", "Cuento folclórico", "Cuento infantil", "Cuento juvenil", "Cuento largo", "Fragmento", "Fábula", "Leyenda", "Minicuento", "Novela corta", "Poema");
	}
}
