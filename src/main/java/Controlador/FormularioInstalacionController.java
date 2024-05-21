package Controlador;

import Modelo.AccesoSQL;
import Modelo.Instalacion;
import Modelo.Seccion;
import Modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormularioInstalacionController implements Initializable {

    public TitledPane contenedorFormularios;
    public TextField nombre;
    public TextField nombreAdministrador;
    public Button botonEnviar;
    public TextArea descripcion;
    private Scene escenaOriginal;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();

    public void setEscenaOriginal(Scene escenaOriginal) {
        this.escenaOriginal = escenaOriginal;
    }

    @FXML
    public void handleVolverVentanaOriginal(ActionEvent event) {
        try {
            if(nombre.getText().contentEquals("")||nombreAdministrador.getText().contentEquals("")||descripcion.getText().contentEquals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de creacion");
                alert.setHeaderText("Rellene todos los campos");
                alert.showAndWait();
            }
            else{
            crearInstalacion();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creacion correcta");
            alert.setHeaderText("Se ha creado la instalacion "+nombre.getText());

            alert.showAndWait();
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Restaurar la escena original
            stageActual.setScene(escenaOriginal);
            stageActual.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de creacion");
            alert.setHeaderText("Prueba de nuevo");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
        // Obtener el escenario actual

    }

    private void crearInstalacion() throws SQLException {


        ac.escribirInstalacion(new Instalacion(0,nombre.getText(),descripcion.getText()));

        ac.escribirUsuario(new Usuario(0,nombreAdministrador.getText(),"ADMINISTRADOR",new Seccion(0,"GENERAL","Proposito general",ac.consultarInstalacion(nombre.getText())), ac.consultarInstalacion(nombre.getText())));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void handleCerrarVentana() {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.setScene(escenaOriginal);
    }
}
