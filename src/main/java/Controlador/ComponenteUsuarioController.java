package Controlador;

import Modelo.Usuario;
import Vista.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComponenteUsuarioController implements Initializable {

    private  Usuario usuario;
    public VBox contenedor;



    public Button botonNombre;

    public Button botonUpdate;


    public StackPane panelInformacion;
    public StackPane panelFormulario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        botonNombre.setText(usuario.getNombre());

    }
    public ComponenteUsuarioController(Usuario usuario, StackPane panelInformacion, StackPane panelFormulario) {

        this.panelInformacion=panelInformacion;
        this.panelFormulario=panelFormulario;

        this.usuario=usuario;

        //Carga el componente visual del archivo fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ComponenteUsuario.fxml"));
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
    /**
     * Evento para abrir la informacion del usuario
     */
    public void handleAbrirInformacionUsuario(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionUsuario(usuario));
    }

    @FXML
    /**
     * Evento para abrir el formulario de modificacion de usuario
     */
    public void handleAbrirFormularioUsuario(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioUsuario(usuario));
    }

}
