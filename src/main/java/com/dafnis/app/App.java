package com.dafnis.app;

import com.dafnis.app.db.BaseDeDatos;
import com.dafnis.app.models.Cuento;
import com.dafnis.app.controllers.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

//EJECUTAR CON: mvn clean compile exec:java -Dexec.mainClass="com.dafnis.app.App"

public class App extends Application{

    private double xOffset = 0;
    private double yOffset = 0;

    public void start(Stage stage)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/diseño1.fxml"));
        Controlador controlador = new Controlador();
        controlador.setStage(stage);
        loader.setController(controlador);
        Parent root = loader.load();
        controlador.setRoot(root);

        FXMLLoader loaderLeer = new FXMLLoader();
        loaderLeer.setLocation(getClass().getResource("/ventanaLeer.fxml"));
        ControladorLeer controladorLeer = new ControladorLeer();
        controladorLeer.setStage(stage);
        controladorLeer.setControlador(controlador);
        loaderLeer.setController(controladorLeer);
        VBox vboxDcha2 = loaderLeer.load();

        FXMLLoader loaderEscribir = new FXMLLoader();
        loaderEscribir.setLocation(getClass().getResource("/ventanaEscribir.fxml"));
        ControladorEscribir controladorEscribir = new ControladorEscribir();
        controladorEscribir.setStage(stage);
        loaderEscribir.setController(controladorEscribir);
        VBox vboxDcha3 = loaderEscribir.load();

        controlador.setControladorLeer(controladorLeer);
        controlador.setVBoxDcha2(vboxDcha2);
        controlador.setVBoxDcha3(vboxDcha3);
        controladorLeer.setVBoxDcha2(vboxDcha2);

        Scene escena = new Scene(root, 1000, 800);
        escena.setFill(Color.TRANSPARENT);
        stage.setScene(escena);
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getScreenX() - stage.getX();
            yOffset = e.getScreenY() - stage.getY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
