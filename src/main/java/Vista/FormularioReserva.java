package Vista;

import Controlador.FormularioEspacioController;
import Controlador.FormularioReservaController;
import Modelo.Espacio;
import Modelo.Reserva;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

import java.sql.Time;
import java.time.LocalDate;

/**
 * Componente visual que conforma un formulario para la creacion o modificacion de una reserva
 */
public class FormularioReserva extends BorderPane {


    /**
     * Constuctor de un formulario para crear una reserva
     * @param usuario usuario actual en la aplicacion
     * @param espacio espacio seleccionado para la reserva
     * @param fecha fecha de busqueda de espacios disponibles
     * @param hora1 hora de inicio de la reserva ha hacer
     * @param hora2 hora de finalizacion de la reserva ha hacer
     */
    public FormularioReserva(Usuario usuario, Espacio espacio, LocalDate fecha, Time hora1, Time hora2) {
        FormularioReservaController controller = new FormularioReservaController(usuario, espacio,fecha,hora1,hora2);
        getChildren().add(controller.getContenedor());
    }

    /**
     * Constuctor de un formulario para modificar una reserva
     * @param reserva reserva a modificar
     */
    public FormularioReserva(Reserva reserva) {
        FormularioReservaController controller = new FormularioReservaController(reserva);
        getChildren().add(controller.getContenedor());
    }
}
