package Vista;

import Controlador.ComponenteReservaController;
import Controlador.InformacionEspacioController;
import Modelo.Espacio;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * Componente visual que muestra la informacion de un espacio
 */
public class InformacionEspacio extends VBox {
    public InformacionEspacio(Espacio espacio, LocalDate fecha){
        InformacionEspacioController controller = new InformacionEspacioController(espacio,fecha);
        getChildren().add(controller.getContenedor());
    }
}
