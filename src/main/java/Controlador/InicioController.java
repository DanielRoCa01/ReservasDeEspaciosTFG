package Controlador;

import Modelo.AccesoSQL;
import Modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InicioController implements Initializable {


    public TextField usuario;
    public Button botonInicio;
    public Button botonNuevaInstalacion;
    public TextField instalacion;


    private Scene escenaOriginal;

    public void setEscenaOriginal(Scene escenaOriginal) {
        this.escenaOriginal = escenaOriginal;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        botonInicio.setOnAction(event -> iniciar());
    }

    private void iniciar(){
        AccesoSQL ac=AccesoSQL.obtenerInstancia();
        try {
            Usuario usuarioLog=ac.consultarUsuario(instalacion.getText(),usuario.getText());
            if(usuarioLog!=null) {
                alertar(Alert.AlertType.INFORMATION, "Inicio correcto", "Usuario encontrado, Bienvenido");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Dashboard.fxml"));

                Parent root = loader.load();

                // Obtener el escenario actual (ventana actual)
                Stage stageActual = (Stage)  usuario.getScene().getWindow();



                // Obtener el controlador de la nueva ventana y pasarle la escena original
                DashBoardController dashBoardController = loader.getController();
                dashBoardController.setUsuario(usuarioLog);
                Scene scene = new Scene(root);

                // Configurar la nueva escena en el escenario actual y mostrarla
                stageActual.setScene(scene);
                stageActual.show();
                return;

            }
            alertar(Alert.AlertType.ERROR, "Inicio incorrecto", "Usuario o instalacion no encontrados");

        } catch (IOException e) {
            alertar(Alert.AlertType.ERROR, "Error en la ejecucion ", "Ha sucedido un error con el apartado visual");
        }
    }

    private static void alertar(Alert.AlertType error, String s, String s1) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(s1);
        alert.showAndWait();
    }

    @FXML
    public void handleAbrirNuevaInstalacion(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la nueva ventana

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/FormularioInstalacion.fxml"));

            Parent root = loader.load();

            // Obtener el escenario actual (ventana actual)
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/resources/Estilos/estilo1.css");
            // Obtener el controlador de la nueva ventana y pasarle la escena original
            FormularioInstalacionController nuevaVentanaController = loader.getController();
            nuevaVentanaController.setEscenaOriginal(escenaOriginal);



            // Crear una nueva escena con el contenido cargado



            stageActual.setOnCloseRequest((WindowEvent we) -> {
                stageActual.setScene(escenaOriginal);

            });
            // Configurar la nueva escena en el escenario actual y mostrarla
            stageActual.setScene(scene);
            stageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
