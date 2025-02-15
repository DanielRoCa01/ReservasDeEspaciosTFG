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

            if(crearInstalacion()){
                Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                // Restaurar la escena original
                stageActual.setScene(escenaOriginal);
                stageActual.show();
            }
    }

    private static void alertar(Alert.AlertType error, String Error_de_creacion, String Prueba_de_nuevo) {
        Alert alert = new Alert(error);
        alert.setTitle(Error_de_creacion);
        alert.setHeaderText(Prueba_de_nuevo);
        alert.showAndWait();
    }



    private boolean crearInstalacion() {
        //Si el nombre esta en uso
        if(ac.consultarInstalacion(nombre.getText())!=null){
            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre del usuario y pruebe de nuevo");

            return false;
        }
        //Si quedan campos vacios
        if(nombre.getText().contentEquals("")||nombreAdministrador.getText().contentEquals("") ||descripcion.getText().contentEquals("")
               ){
            alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");

            return false;
        }

        ac.escribirInstalacion(new Instalacion(0,nombre.getText(),descripcion.getText()));
        Instalacion instalacion=ac.consultarInstalacion(nombre.getText());
        ac.escribirSeccion(new Seccion(0,"GENERAL","Proposito general",instalacion));
        ac.escribirUsuario(new Usuario(0,nombreAdministrador.getText(),"ADMINISTRADOR",ac.consultarSeccion(instalacion.getIdInstalacion(),"GENERAL"), instalacion));
        alertar(Alert.AlertType.INFORMATION, "Creacion correcta", "Se ha creado la instalacion " + nombre.getText());

        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void handleCerrarVentana() {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.setScene(escenaOriginal);
    }
}
