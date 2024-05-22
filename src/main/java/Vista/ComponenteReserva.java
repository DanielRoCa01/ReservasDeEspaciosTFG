package Vista;

import Controlador.ComponenteReservaController;
import Modelo.Reserva;
import Modelo.Usuario;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ComponenteReserva extends VBox {
    public ComponenteReserva(Reserva reserva, StackPane panelInformacion, StackPane panelFormulario, Usuario usuario) {
        ComponenteReservaController controller = new ComponenteReservaController(reserva,panelInformacion,panelFormulario,usuario);
        getChildren().add(controller.getContenedor());
    }
}
