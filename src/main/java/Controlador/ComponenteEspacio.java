package Controlador;

import javafx.scene.layout.VBox;



public class ComponenteEspacio extends VBox {
    public ComponenteEspacio() {
        ComponenteEspacioController controller = new ComponenteEspacioController();
        getChildren().add(controller.getContenedor());
    }
}
