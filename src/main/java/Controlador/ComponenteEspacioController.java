package Controlador;

import Modelo.Espacio;
import Modelo.Usuario;
import Vista.ConsultaEspacios;
import Vista.FormularioEspacio;
import Vista.FormularioReserva;
import Vista.InformacionEspacio;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ComponenteEspacioController implements Initializable {
    private  Time hora2;
    private  Time hora1;
    private  Usuario usuario;
    public VBox contenedor;

    public Espacio espacio;

    public Button botonNombre;

    public Button botonUpdate;
    public Button botonReservar;

    public StackPane panelInformacion;
    public StackPane panelFormulario;
    private LocalDate fechaConsulta;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contenedor.getStylesheets().add("/Estilos/estilo1.css");
        botonNombre.setText(espacio.getNombre());
        if(!usuario.getRol().contentEquals("ADMINISTRADOR")){
            botonUpdate.setVisible(false);
        }

    }
    public ComponenteEspacioController(Espacio espacio, StackPane panelInformacion, StackPane panelFormulario, LocalDate fechaConsulta, Usuario usuario, Time hora1, Time hora2) {
        this.espacio=espacio;
        this.panelInformacion=panelInformacion;
        this.panelFormulario=panelFormulario;
        this.fechaConsulta=fechaConsulta;
        this.usuario=usuario;
        this.hora1=hora1;
        this.hora2=hora2;

        // Registrar un ChangeListener en el campo nombre del objeto Espacio
        espacio.nombreProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                botonNombre.setText(newValue);
            }
        });
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ComponenteEspacio.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public VBox getContenedor() {
        return contenedor;
    }
    @FXML
    public void handleAbrirConsulta(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionEspacio(espacio,fechaConsulta));
    }

    @FXML
    public void handleAbrirFormularioEspacio(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioEspacio(espacio));
    }
    @FXML
    public void handleAbrirFormularioReserva(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioReserva(usuario,espacio,fechaConsulta,hora1,hora2));
    }

}
