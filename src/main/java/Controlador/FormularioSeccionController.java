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

    public FormularioSeccionController(Instalacion instalacion) {
        this.instalacion=instalacion;
        seModifica=false;
        iniciarComponente();
    }
    public FormularioSeccionController(Seccion seccion) {
        this.seccion=seccion;
        seModifica=true;
        iniciarComponente();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccesoSQL ac=new AccesoSQL();
        if (seModifica){
            nombre.setText(seccion.getNombre());
            descripcion.setText(seccion.getDescripcion());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");

        }
        else {
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear()  {
        AccesoSQL ac =new AccesoSQL();
        if(ac.consultarSeccion(instalacion.getIdInstalacion(),nombre.getText())!=null){
            alertarNombre();
            return;
        }
        else if(nombre.getText().contentEquals("")||descripcion.getText().contentEquals("")){
            alertarVacio();
            return;
        }
        ac.escribirSeccion(new Seccion(0,nombre.getText(),descripcion.getText(),instalacion));
        informarDeCreacion();
    }

    private void informarDeCreacion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CREACION CORRECTA");
        alert.setHeaderText("El usuario"+nombre.getText()+" se ha creado correctamente");

        alert.showAndWait();
    }

    public void  modificar() {
        AccesoSQL ac =new AccesoSQL();
        if(ac.consultarSeccion(seccion.getInstalacion().getIdInstalacion(),nombre.getText())!=null&&!nombre.getText().contentEquals(seccion.getNombre())){
                alertarNombre();
                return;
        }
        else if(nombre.getText().contentEquals("")||descripcion.getText().contentEquals("")){
            alertarVacio();
            return;
        }

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



    private void alertarVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Campos sin rellenar");
        alert.setContentText("Por favor rellene todos los campos antes de continuar");
        alert.showAndWait();
    }

    private void alertarNombre() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Nombre existente");
        alert.setContentText("Cambie el nombre del usuario y pruebe de nuevo");
        alert.showAndWait();
    }



    private void iniciarComponente() {
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
