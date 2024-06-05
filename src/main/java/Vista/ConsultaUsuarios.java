package Vista;

import Controlador.ConsultaReservasController;
import Controlador.ConsultaUsuariosController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Componente visual que conforma un formulario para la consulta de una lista de componentes que representan usuarios
 */
public class ConsultaUsuarios extends BorderPane {

    /**
     * Consturcto para el componente
     * @param usuario usuario actual de la aplicaicon
     * @param panelInformacion panel de informacion con el que interactuar
     * @param panelFormulario panel de formulario al que redireccionar
     */
    public ConsultaUsuarios(Usuario usuario, StackPane panelInformacion, StackPane panelFormulario) {
        ConsultaUsuariosController controller = new ConsultaUsuariosController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
