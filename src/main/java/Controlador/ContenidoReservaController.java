package Controlador;

import Modelo.Espacio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;

public class ContenidoReservaController {
    @FXML
    public VBox contenedor;
    public Label label2;

    public ContenidoReservaController(ArrayList<Espacio> listaEspacios, Label label2) {
        this.label2=label2;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/contenidoReserva.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            inicializarBotones(listaEspacios);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void inicializarBotones(ArrayList<Espacio> listaEspacios) {


        for (Espacio espacio : listaEspacios) {
            Button boton = new Button(espacio.getNombre());
            boton.setOnAction(event -> mostrarDescripcion(espacio.getHoraApertura().toString()));
            contenedor.getChildren().add(boton);
        }
    }
    public void mostrarDescripcion(String descripcion) {
        // Suponiendo que tienes un label en tu FXML con el identificador "labelDescripcion"
        label2.setText(descripcion);
    }
    public VBox getContenedor() {
        return contenedor;
    }
}
