package Controlador;

import Modelo.Espacio;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ComponenteEspacioController implements Initializable {
    public VBox contenedor;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public ComponenteEspacioController() {


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
}
