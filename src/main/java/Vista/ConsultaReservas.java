package Vista;

import Controlador.ConsultaEspaciosController;
import Controlador.ConsultaReservasController;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ConsultaReservas extends BorderPane {

    public ConsultaReservas(Usuario usuario, StackPane panelInformacion, StackPane panelFormulario) {
        ConsultaReservasController controller = new ConsultaReservasController(usuario,panelInformacion,panelFormulario);
        getChildren().add(controller.getContenedor());
    }
}
