package Vista;

import Controlador.ConsultaEspaciosController;
import Controlador.ConsultaReservasController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Componente visual que conforma un formulario para la consulta de una lista de componentes que representan reservas
 */
public class ConsultaReservas extends BorderPane {

    /**
     * Consturcto para el componente
     * @param usuario usuario actual de la aplicaicon
     * @param panelInformacion panel de informacion con el que interactuar
     * @param panelFormulario panel de formulario al que redireccionar
     */
    public ConsultaReservas(Usuario usuario, StackPane panelInformacion, StackPane panelFormulario) {
        ConsultaReservasController controller = new ConsultaReservasController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
