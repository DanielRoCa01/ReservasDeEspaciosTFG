package Vista;

import Controlador.ComponenteReservaController;
import Modelo.Reserva;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ComponenteReserva extends VBox {
    public ComponenteReserva(Reserva reserva, StackPane panelInformacion, StackPane panelFormulario) {
        ComponenteReservaController controller = new ComponenteReservaController(reserva,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
