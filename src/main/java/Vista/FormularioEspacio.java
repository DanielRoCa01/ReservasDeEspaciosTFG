package Vista;

import Controlador.ConsultaEspaciosController;
import Controlador.FormularioEspacioController;
import Modelo.Espacio;
import Modelo.Usuario;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class FormularioEspacio extends BorderPane {

    public FormularioEspacio(Usuario usuario) {
        FormularioEspacioController controller = new FormularioEspacioController(usuario);
        getChildren().add(controller.getContenedor());
    }
    public FormularioEspacio(Espacio espacio) {
        FormularioEspacioController controller = new FormularioEspacioController(espacio);
        getChildren().add(controller.getContenedor());
    }
}
