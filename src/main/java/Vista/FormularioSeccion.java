package Vista;

import Controlador.FormularioSeccionController;
import Controlador.FormularioUsuarioController;
import Modelo.Instalacion;
import Modelo.Seccion;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

/**
 * Componente visual que conforma un formulario para la creacion o modificacion de una seccion
 */
public class FormularioSeccion extends BorderPane {
    /**
     * Constuctor de un formulario para crear una seccion
     * @param instalacion instalacion de la seccion
     */
    public FormularioSeccion(Instalacion instalacion) {
        FormularioSeccionController controller = new FormularioSeccionController(instalacion);
        getChildren().add(controller.getContenedor());
    }

    /**
     * Constuctor de un formulario para modificar una seccion
     * @param seccion seccion a modificar
     */
    public FormularioSeccion(Seccion seccion) {
        FormularioSeccionController controller = new FormularioSeccionController(seccion);
        getChildren().add(controller.getContenedor());
    }
}
