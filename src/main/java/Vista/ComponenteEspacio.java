package Vista;

import Controlador.ComponenteEspacioController;
import Modelo.Espacio;
import Modelo.Usuario;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Time;
import java.time.LocalDate;


public class ComponenteEspacio extends VBox {
    public ComponenteEspacio(Espacio espacio, StackPane stackPane, StackPane panelFormulario, LocalDate fecha, Usuario usuario, Time hora1, Time hora2) {
        ComponenteEspacioController controller = new ComponenteEspacioController(espacio,stackPane,panelFormulario,fecha,usuario, hora1,  hora2);
        getChildren().add(controller.getContenedor());
    }
}
