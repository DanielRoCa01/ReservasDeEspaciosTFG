package Vista;

import Controlador.InformacionReservaController;
import Controlador.InformacionUsuarioController;
import Modelo.Reserva;
import Modelo.Usuario;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InformacionUsuario extends VBox {
    public InformacionUsuario(Usuario usuario){
        InformacionUsuarioController controller = new InformacionUsuarioController(usuario);

        getChildren().add(controller.getContenedor());
    }
}
