package Vista;

import Controlador.ConsultaReservasController;
import Controlador.ConsultaUsuariosController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ConsultaUsuarios extends BorderPane {

    public ConsultaUsuarios(Usuario usuario, StackPane panelInformacion, StackPane panelFormulario) {
        ConsultaUsuariosController controller = new ConsultaUsuariosController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
