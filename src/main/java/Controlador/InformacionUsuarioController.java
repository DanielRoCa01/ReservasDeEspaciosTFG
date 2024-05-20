package Controlador;

import Modelo.Reserva;
import Modelo.Usuario;
import Vista.InformacionEspacio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InformacionUsuarioController implements Initializable {

    private  Usuario usuario;
    public Label nombreInstalacion;
    public Label descripcionInstalacion;
   
    public VBox contenedor;
    public Label nombreUsuario;
    public Label rol;
    public Label seccion;
    public Label descripcionSeccion;


    public InformacionUsuarioController(Usuario usuario){
        this.usuario =usuario;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/InformacionUsuario.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombreInstalacion.setText(usuario.getNombre());
        descripcionInstalacion.setText(usuario.getInstalacion().getDescripcion());
        nombreUsuario.setText(usuario.getNombre());
        rol.setText(usuario.getRol());
        seccion.setText(usuario.getSeccion().getNombre());
        descripcionSeccion.setText(usuario.getSeccion().getDescripcion());





    }


    public VBox getContenedor() {
        return contenedor;
    }
}
