package Vista;

import Controlador.InformacionEspacioController;
import Controlador.InformacionReservaController;
import Modelo.Espacio;
import Modelo.Reserva;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class InformacionReserva extends VBox {
    public InformacionReserva(Reserva reserva, StackPane panelInformacion){
        InformacionReservaController controller = new InformacionReservaController(reserva,panelInformacion);

        getChildren().add(controller.getContenedor());
    }
}
