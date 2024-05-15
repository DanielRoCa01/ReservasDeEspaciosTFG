package Controlador;

import javafx.scene.layout.VBox;

public class ComponenteReserva extends VBox {
    public ComponenteReserva() {
        ComponenteReservaController controller = new ComponenteReservaController();
        getChildren().add(controller.getContenedor());
    }
}
