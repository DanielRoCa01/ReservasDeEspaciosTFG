package Vista;

import Controlador.FormularioEspacioController;
import Controlador.FormularioReservaController;
import Modelo.Espacio;
import Modelo.Reserva;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

import java.sql.Time;
import java.time.LocalDate;

public class FormularioReserva extends BorderPane {

    public FormularioReserva(Usuario usuario, Espacio espacio, LocalDate fecha, Time hora1, Time hora2) {
        FormularioReservaController controller = new FormularioReservaController(usuario, espacio,fecha,hora1,hora2);
        getChildren().add(controller.getContenedor());
    }
    public FormularioReserva(Reserva reserva) {
        FormularioReservaController controller = new FormularioReservaController(reserva);
        getChildren().add(controller.getContenedor());
    }
}
