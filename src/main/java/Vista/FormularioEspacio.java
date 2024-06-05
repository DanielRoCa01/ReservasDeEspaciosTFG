package Vista;

import Controlador.ConsultaEspaciosController;
import Controlador.FormularioEspacioController;
import Modelo.Espacio;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Componente visual que conforma un formulario para la creacion o modificacion de un espacio
 */
public class FormularioEspacio extends BorderPane {


    /**
     * Constuctor de un formulario para crear un espacio
     * @param usuario usuario actual en la aplicacion
     */
    public FormularioEspacio(Usuario usuario) {
        FormularioEspacioController controller = new FormularioEspacioController(usuario);
        getChildren().add(controller.getContenedor());
    }

    /**
     * Constuctor de un formulario para modificar un espacio
     * @param espacio espacio a modificar
     */
    public FormularioEspacio(Espacio espacio) {
        FormularioEspacioController controller = new FormularioEspacioController(espacio);
        getChildren().add(controller.getContenedor());
    }
}
