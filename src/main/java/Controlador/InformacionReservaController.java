package Controlador;

import Modelo.Reserva;
import Vista.InformacionEspacio;
import Vista.InformacionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InformacionReservaController implements Initializable {

    private  StackPane panelInformacion;
    private  Reserva reserva;
    public VBox contenedor;

    public Button espacio;
    public Button usuario;

    public Label descripcion;

    public Label fecha;


    public Label horaCierre;
    public Label horaApertura;
    public Label estado;


    public InformacionReservaController(Reserva reserva, StackPane panelInformacion){
        this.reserva =reserva;
        this.panelInformacion =panelInformacion;
        //Cargar componente en el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/InformacionReserva.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Se inicializan los componentes visuales con los atributos de la reserva
        descripcion.setText(reserva.getDescripcion());
        horaApertura.setText(reserva.getHoraInicio().toString());
        horaCierre.setText(reserva.getHoraFinal().toString());
        estado.setText(reserva.getEstado());
        espacio.setText(reserva.getEspacio().toString());
        usuario.setText(reserva.getUsuario().toString());
        fecha.setText(reserva.getFecha().toString());




    }
    @FXML
    public void handleAbrirEspacio(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionEspacio(reserva.getEspacio(),reserva.getFecha()));
    }
    @FXML
    public void handleAbrirUsuario(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionUsuario(reserva.getUsuario()));
    }

    public VBox getContenedor() {
        return contenedor;
    }
}
