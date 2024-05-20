package Vista;

import Controlador.FormularioEspacioController;
import Controlador.FormularioUsuarioController;
import Modelo.Espacio;
import Modelo.Instalacion;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

public class FormularioUsuario extends BorderPane {

    public FormularioUsuario(Instalacion instalacion) {
        FormularioUsuarioController controller = new FormularioUsuarioController(instalacion);
        getChildren().add(controller.getContenedor());
    }
    public FormularioUsuario(Usuario usuario) {
        FormularioUsuarioController controller = new FormularioUsuarioController(usuario);
        getChildren().add(controller.getContenedor());
    }
}
