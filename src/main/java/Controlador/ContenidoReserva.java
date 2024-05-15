package Controlador;

import Modelo.Espacio;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ContenidoReserva extends VBox {
    public ContenidoReserva(ArrayList<Espacio> listaEspacios, Label label) {
        ContenidoReservaController controller = new ContenidoReservaController(listaEspacios,label);
        getChildren().add(controller.getContenedor());
    }
}
