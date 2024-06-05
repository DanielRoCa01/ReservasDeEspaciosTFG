package Vista;

import Controlador.FormularioEspacioController;
import Controlador.FormularioUsuarioController;
import Modelo.Espacio;
import Modelo.Instalacion;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

/**
 * Componente visual que conforma un formulario para la creacion o modificacion de un usuario
 */
public class FormularioUsuario extends BorderPane {

    /**
     * Constuctor de un formulario para crear un usuario
     * @param instalacion instalacion del usuario
     */
    public FormularioUsuario(Instalacion instalacion) {
        FormularioUsuarioController controller = new FormularioUsuarioController(instalacion);
        getChildren().add(controller.getContenedor());
    }

    /**
     * Constuctor de un formulario para modificar un usuario
     * @param usuario usuario que se modifica
     */
    public FormularioUsuario(Usuario usuario) {
        FormularioUsuarioController controller = new FormularioUsuarioController(usuario);
        getChildren().add(controller.getContenedor());
    }
}
