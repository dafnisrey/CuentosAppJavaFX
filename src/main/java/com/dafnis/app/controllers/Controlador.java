package com.dafnis.app.controllers;

import com.dafnis.app.db.BaseDeDatos;
import com.dafnis.app.models.Cuento;
import com.dafnis.app.auxiliares.ServicioTableView;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.animation.ParallelTransition;
import javafx.scene.input.MouseEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class Controlador{

	@FXML
	private Label labelNumResultados;
	@FXML
	private Button botonCerrar;
	@FXML
	private Button botonLeer;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private VBox vboxDcha1;
	@FXML
	private Button botonBuscar;
	@FXML
	private ToggleButton botonModoVista;
	@FXML
	private TextField textfieldTitulo;
	@FXML
	private TextField textfieldAutor;
	@FXML
	private ComboBox<String> comboboxNacionalidad;
	@FXML
	private ComboBox<String> comboboxCategoria;
    @FXML
    private TableView<Cuento> tableView;
    @FXML
    private TableColumn<Cuento, String> columnaTitulo;
    @FXML
    private TableColumn<Cuento, String> columnaAutor;
    @FXML
    private TableColumn<Cuento, String> columnaAños;
    @FXML
    private TableColumn<Cuento, String> columnaCategoria;
    @FXML
    private TableColumn<Cuento, String> columnaNacionalidad;
	@FXML
	private Label labelAbajo;
	private Stage stage;
	private Parent root;
	private TextArea textArea;
	private VBox vboxDcha2;
	private VBox vboxDcha3;
	private ControladorLeer controladorLeer;
	private BaseDeDatos baseDeDatos;
	private ServicioTableView servicioTableView = new ServicioTableView();
	private String stringNumRegistros;
	private PauseTransition pausa;

	public void setControladorLeer(ControladorLeer controladorLeer){
		this.controladorLeer = controladorLeer;
	}

	public void setVBoxDcha2(VBox vboxDcha2){
		this.vboxDcha2 = vboxDcha2;
	}

	public void setVBoxDcha3(VBox vboxDcha3){
		this.vboxDcha3 = vboxDcha3;
	}

	public void setRoot(Parent root){
		this.root = root;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	private void getObjBaseDeDatos(){
		if(baseDeDatos == null){
			baseDeDatos = BaseDeDatos.getObj();
		}
	}

	VBox getVBoxDcha1(){
		return vboxDcha1;
	}
	AnchorPane getAnchorPane(){
		return anchorPane;
	}

	@FXML
	public void initialize(){
		inicializarComboboxes();
		inicializarColumnasTabla();
		Label placeholderTabla = new Label("Realiza una búsqueda.");
		tableView.setPlaceholder(placeholderTabla);
		servicioTableView.setComponentesDeBusqueda(textfieldTitulo, textfieldAutor, comboboxNacionalidad, comboboxCategoria);
	}

	public void actionMenuEscribir(){
		if(anchorPane.getChildren().get(0) != vboxDcha3){
			transicionar(vboxDcha3);
		}
	}

	public void actionMenuLeer(){
		if(anchorPane.getChildren().get(0) != vboxDcha1){
			transicionar(vboxDcha1);
		}
	}

	public void actionMenuSalir(){
		stage.close();
	}

	public void actionBuscar(){
		ResultSet resultSet = null;
		getObjBaseDeDatos();
		baseDeDatos.conectar();
		if(!verificarConsultaVacia()){
			String consulta = servicioTableView.prepararConsulta();
			resultSet = baseDeDatos.realizarConsulta(consulta);
			ObservableList<Cuento> observableList = servicioTableView.prepararDatos(resultSet);
			mostrarDatos(observableList);
			stringNumRegistros = ServicioTableView.getNumeroDeRegistros() + " resultados.";
			labelNumResultados.setText(stringNumRegistros);
			baseDeDatos.desconectar();
		}else{
			tableView.getItems().clear();
			stringNumRegistros = "";
			labelNumResultados.setText(stringNumRegistros);
		}
	}

	public void actionLeer(){
		if(textArea == null){
			textArea = controladorLeer.getTextArea();
		}
		Cuento cuentoSeleccionado = tableView.getSelectionModel().getSelectedItem();
		if(cuentoSeleccionado != null){
			controladorLeer.mostrarCuentoSeleccionado(cuentoSeleccionado);
			controladorLeer.mostrarLabelCuentoSeleccionado(cuentoSeleccionado);
			transicionar(vboxDcha2);
		}else{
			labelAbajo.setText("No has seleccionado ningun cuento");
			pausa = new PauseTransition(Duration.seconds(3));
			pausa.setOnFinished(e -> {labelAbajo.setText("");});
			pausa.play();
		}
	}

	public void actionCambiarModoVista(){
		if(botonModoVista.isSelected()){
			root.getStylesheets().clear();
			vboxDcha2.getStylesheets().clear();
			vboxDcha3.getStylesheets().clear();
			vboxDcha2.getStylesheets().add(getClass().getResource("/ventanaLeerEstilosOscuro.css").toExternalForm());
			vboxDcha3.getStylesheets().add(getClass().getResource("/ventanaEscribirEstilosOscuro.css").toExternalForm());
			root.getStylesheets().add(getClass().getResource("/estilos1Oscuro.css").toExternalForm());
		}else{
			root.getStylesheets().clear();
			vboxDcha2.getStylesheets().clear();
			vboxDcha3.getStylesheets().clear();
			vboxDcha2.getStylesheets().add(getClass().getResource("/ventanaLeerEstilos.css").toExternalForm());
			vboxDcha3.getStylesheets().add(getClass().getResource("/ventanaEscribirEstilos.css").toExternalForm());
			root.getStylesheets().add(getClass().getResource("/estilos1.css").toExternalForm());
		}
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

	private void transicionar(VBox nuevoVbox){
		anchorPane.setTopAnchor(nuevoVbox, 0.0);
		anchorPane.setBottomAnchor(nuevoVbox, 0.0);
		anchorPane.setLeftAnchor(nuevoVbox, 0.0);
		anchorPane.setRightAnchor(nuevoVbox, 0.0);
		nuevoVbox.setOpacity(0.0);
		anchorPane.getChildren().add(nuevoVbox);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(400), anchorPane.getChildren().get(0));
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		FadeTransition fadeIn = new FadeTransition(Duration.millis(400), nuevoVbox);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		ParallelTransition transicionParalela = new ParallelTransition(fadeOut, fadeIn);
		transicionParalela.setOnFinished(e -> {
			anchorPane.getChildren().clear();
			anchorPane.getChildren().add(nuevoVbox);
		});
		transicionParalela.play();
	}

	private boolean verificarConsultaVacia(){
		boolean estaVacia = true;
		if(!textfieldTitulo.getText().trim().isEmpty()){
			estaVacia = false;
		}
		if(!textfieldAutor.getText().trim().isEmpty()){
			estaVacia = false;
		}
		if(comboboxNacionalidad.getValue() != null ){
			if(!comboboxNacionalidad.getValue().equals("")){
				estaVacia = false;
			}
		}
		if(comboboxCategoria.getValue() != null){
			if(!comboboxCategoria.getValue().equals("")){
				estaVacia = false;
			}
		}
		return estaVacia;
	}

	private void mostrarDatos(ObservableList observableList){
		tableView.setItems(observableList);

	}

	private void inicializarColumnasTabla(){
		columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		columnaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		columnaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnaNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		columnaAños.setCellValueFactory(new PropertyValueFactory<>("años"));
	}

	private void inicializarComboboxes(){
		comboboxNacionalidad.getItems().addAll("", "Alemania", "Alemania/Suiza", "Argentina", "Austria", "Bélgica", "Bolivia", "Brasil", "Canadá", "Chile", "China", "Colombia", "Costa Rica", "Cuba", "Checoslovaquia", "Dinamarca", "Ecuador", "Egipto", "El Líbano", "El Salvador", "España", "España/México", "Estados Unidos", "Escocia", "Francia", "Gales", "Grecia", "Guatemala", "Honduras", "Hungría", "India", "Inglaterra", "Irlanda", "Italia", "Japón", "México", "Nicaragua", "Noruega", "Nueva Zelanda", "Panamá", "Paraguay", "Perú", "Polonia", "Portugal", "Prusia", "Puerto Rico", "República Dominicana", "Rusia", "Sudáfrica", "Suecia", "Suiza", "Trinidad y Tobago", "Uruguay", "Venezuela");

		comboboxCategoria.getItems().addAll("", "Crónica", "Cuento", "Cuento folclórico", "Cuento infantil", "Cuento juvenil", "Cuento largo", "Fragmento", "Fábula", "Leyenda", "Minicuento", "Novela corta", "Poema");
	}

}

