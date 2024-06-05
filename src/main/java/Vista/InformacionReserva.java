package Vista;

import Controlador.InformacionEspacioController;
import Controlador.InformacionReservaController;
import Modelo.Espacio;
import Modelo.Reserva;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * Componente visual que muestra la informacion de una reserva
 */
public class InformacionReserva extends VBox {
    public InformacionReserva(Reserva reserva, StackPane panelInformacion){
        InformacionReservaController controller = new InformacionReservaController(reserva,panelInformacion);

        getChildren().add(controller.getContenedor());
    }
}
