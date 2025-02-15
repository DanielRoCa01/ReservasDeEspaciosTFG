package Vista;

import Controlador.ComponenteEspacioController;
import Controlador.ComponenteUsuarioController;
import Modelo.Espacio;
import Modelo.Usuario;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Time;
import java.time.LocalDate;

/**
 * Componente visual que representa un Usuario
 * Redirecciona a un formulario y aun panel de informacion del usuario
 */
public class ComponenteUsuario extends VBox {
    public ComponenteUsuario(Usuario usuario, StackPane stackPane, StackPane panelFormulario) {
        ComponenteUsuarioController controller = new ComponenteUsuarioController(usuario,stackPane,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
