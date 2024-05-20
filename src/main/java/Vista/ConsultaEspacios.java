package Vista;

import Controlador.ComponenteEspacioController;
import Controlador.ConsultaEspaciosController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ConsultaEspacios extends BorderPane {

    public ConsultaEspacios(Usuario usuario, StackPane panelInformacion,StackPane panelFormulario) {
        ConsultaEspaciosController controller = new ConsultaEspaciosController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
