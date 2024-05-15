package Controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComponenteReservaController implements Initializable {
    public VBox contenedor;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public ComponenteReservaController() {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ComponenteReserva.fxml"));
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
