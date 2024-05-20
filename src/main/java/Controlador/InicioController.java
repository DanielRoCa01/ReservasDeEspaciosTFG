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
        AccesoSQL ac=new AccesoSQL();
        try {
            Usuario usuarioLog=ac.consultarUsuario(instalacion.getText(),usuario.getText());
            if(usuarioLog!=null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inicio correcto");
                alert.setHeaderText("Usuario encontrado, Bienvenido");
                alert.showAndWait();

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inicio incorrecto");
            alert.setHeaderText("Usuario o instalacion no encontrados");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la base de datos");
            alert.setHeaderText("Ha sucedido un error con la base de datos");
            alert.showAndWait();
        }
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
