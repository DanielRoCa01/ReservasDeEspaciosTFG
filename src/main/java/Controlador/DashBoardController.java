package Controlador;

import Modelo.AccesoSQL;
import Modelo.Usuario;
import Vista.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    public StackPane contenedorInformacion;
    public StackPane contenedorFormularios;
    public StackPane contemedorConsulta;
    public Button botonEspacios;
    public Button botonReservas;
    public Button botonInstalacion;
    public BorderPane contenedor;

    private Usuario usuario;

private FXMLLoader loader;

    public DashBoardController(){

    }
    public BorderPane getContenedor() {
        return contenedor;
    }



    public FXMLLoader getroot(){
        return loader;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
    @FXML
    public void handleAbrirConsulta(ActionEvent event) {
        contemedorConsulta.getChildren().clear();
        contemedorConsulta.getChildren().add(new ConsultaEspacios(usuario,contenedorInformacion,contenedorFormularios));
    }
    @FXML
    public void handleAbrirConsultaReservas(ActionEvent event) {
        contemedorConsulta.getChildren().clear();
        contemedorConsulta.getChildren().add(new ConsultaReservas(usuario,contenedorInformacion,contenedorFormularios));

    }

    @FXML
    public void handleAbrirConsultaInstalacion(ActionEvent event) {
        if(usuario.getRol().contentEquals("ADMINISTRADOR")) {
            contemedorConsulta.getChildren().clear();
            contemedorConsulta.getChildren().add(new ConsultaUsuarios(usuario, contenedorInformacion, contenedorFormularios));
        }
        contenedorInformacion.getChildren().clear();
        contenedorInformacion.getChildren().add(new InformacionUsuario(usuario));

    }
}
