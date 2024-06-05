package Vista;

import Controlador.ComponenteEspacioController;
import Controlador.ConsultaEspaciosController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Componente visual que conforma un formulario para la consulta de una lista de componentes que representan espacios
 */
public class ConsultaEspacios extends BorderPane {

    /**
     * Consturcto para el componente
     * @param usuario usuario actual de la aplicaicon
     * @param panelInformacion panel de informacion con el que interactuar
     * @param panelFormulario panel de formulario al que redireccionar
     */
    public ConsultaEspacios(Usuario usuario, StackPane panelInformacion,StackPane panelFormulario) {
        ConsultaEspaciosController controller = new ConsultaEspaciosController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
