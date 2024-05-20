package Vista;

import Controlador.FormularioSeccionController;
import Controlador.FormularioUsuarioController;
import Modelo.Instalacion;
import Modelo.Seccion;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;

public class FormularioSeccion extends BorderPane {

    public FormularioSeccion(Instalacion instalacion) {
        FormularioSeccionController controller = new FormularioSeccionController(instalacion);
        getChildren().add(controller.getContenedor());
    }
    public FormularioSeccion(Seccion seccion) {
        FormularioSeccionController controller = new FormularioSeccionController(seccion);
        getChildren().add(controller.getContenedor());
    }
}
