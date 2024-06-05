package Controlador;

import Modelo.AccesoSQL;
import Modelo.Instalacion;
import Modelo.Seccion;
import Modelo.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormularioSeccionController implements Initializable {


    public VBox contenedorFormularios;
    public TextField nombre;

    private Instalacion instalacion;
    private Seccion seccion;

    private boolean seModifica;
    public Button botonEnviar;
    public TextArea descripcion;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();
    //Constructor para modificar una seccion
    public FormularioSeccionController(Instalacion instalacion) {
        this.instalacion=instalacion;
        seModifica=false;
        iniciarComponente();
    }
    //Constructor para crear una Seccion
    public FormularioSeccionController(Seccion seccion) {
        this.seccion=seccion;
        seModifica=true;
        iniciarComponente();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (seModifica){
            nombre.setText(seccion.getNombre());
            descripcion.setText(seccion.getDescripcion());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");

        }
        //Si es un formulario de creacion
        else {
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear()  {
        //Si el nombre ya esta en uso
        if(ac.consultarSeccion(instalacion.getIdInstalacion(),nombre.getText())!=null){
            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre de la seccion y pruebe de nuevo");
            return;
        }
        //Si quedan campos sin rellenar
         if(nombre.getText().contentEquals("")||descripcion.getText().contentEquals("")){
             alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");
            return;
        }
        ac.escribirSeccion(new Seccion(0,nombre.getText(),descripcion.getText(),instalacion));
        alertar(Alert.AlertType.INFORMATION, "CREACION CORRECTA", "La seccion "+nombre.getText()+" se ha creado correctamente");
    }
    private static void alertar(Alert.AlertType error, String s, String s1) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(s1);
        alert.showAndWait();
    }


    public void  modificar() {
        //Si el nombre ya esta en uso
        if(ac.consultarSeccion(seccion.getInstalacion().getIdInstalacion(),nombre.getText())!=null&&!nombre.getText().contentEquals(seccion.getNombre())){
            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre de la seccion y pruebe de nuevo");
                return;
        }
        //Si quedan campos sin rellenar
         if(nombre.getText().contentEquals("")||descripcion.getText().contentEquals("")){
             alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");
            return;
        }
        //Si se confirma la modificacion
        if (confirmarModificacion()) {
            seccion=new Seccion(seccion.getIdSeccion(),nombre.getText(),descripcion.getText(),seccion.getInstalacion());
            ac.modificar(seccion);

        }
    }

    private boolean confirmarModificacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA DE MODIFICACION ");
        alert.setHeaderText("Cambiará permanentemente el contenido de la seccion");
        alert.setContentText("¿Desea continuar con la modificacion?");

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Mostrar el alert y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }


    private void iniciarComponente() {
        //Carga el componente visual de un archivo fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/FormularioSeccion.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public VBox getContenedor() {
        return contenedorFormularios;
    }

}
