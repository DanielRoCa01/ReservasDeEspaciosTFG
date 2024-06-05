package Controlador;

import Modelo.*;
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

public class FormularioUsuarioController implements Initializable {


    public VBox contenedorFormularios;
    public TextField nombre;
    public ChoiceBox<Seccion> seccion;
    public ChoiceBox<String> rol;
    private Instalacion instalacion;
    private Usuario usuario;

    private boolean seModifica;
    public Button botonEnviar;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();
    //Constructor para modificar un usuario
    public FormularioUsuarioController(Usuario usuario) {
        this.usuario=usuario;
        seModifica=true;
        iniciarComponente();
    }
    //Constructor para crea un usuario
    public FormularioUsuarioController(Instalacion instalacion) {
        this.instalacion=instalacion;
        seModifica=false;
        iniciarComponente();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rol.getItems().addAll(Usuario.ROLES);

        if (seModifica){
            seccion.getItems().addAll(ac.leerSecciones(usuario.getInstalacion().getIdInstalacion()));
            seccion.setValue(usuario.getSeccion());
            nombre.setText(usuario.getNombre());
            rol.setValue(usuario.getRol());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");

        }
        //Si es un formulario de creacion
        else {
            seccion.getItems().addAll(ac.leerSecciones(instalacion.getIdInstalacion()));
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear()  {
        //Si el nombre del usuario ya esta en uso
        if(ac.consultarUsuario(instalacion.getNombre(),nombre.getText())!=null){

            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre del usuario y pruebe de nuevo");
            return;
        }
        //Si quedan campos sin rellenar
        if(nombre.getText().contentEquals("")||rol.getValue().contentEquals("")
                ||seccion.getValue()==null){
            alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");
            return;
        }
        ac.escribirUsuario(new Usuario(0,nombre.getText(),rol.getValue(),seccion.getValue(),instalacion));

        alertar(Alert.AlertType.INFORMATION, "CREACION CORRECTA", "El usuario"+nombre.getText()+" se ha creado correctamente");
    }


    public void  modificar() {
        //Si el nombre del usuario ya esta en uso
        if(ac.consultarUsuario(usuario.getInstalacion().getNombre(),nombre.getText())!=null&&!nombre.getText().contentEquals(usuario.getNombre())){

            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre del usuario y pruebe de nuevo");
                return;
        }
        //Si quedan campos sin rellenar
         if(nombre.getText().contentEquals("")||rol.getValue().contentEquals("")
                ||seccion.getValue()==null){

            alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");
            return;
        }
        //Si se confirma la modificacion
        if (confirmarModificacion()) {
            usuario=new Usuario(usuario.getIdUsuario(), nombre.getText(),rol.getValue(),seccion.getValue(),usuario.getInstalacion());
            ac.modificar(usuario);

        }
    }
    private static void alertar(Alert.AlertType error, String s, String s1) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(s1);
        alert.showAndWait();
    }
    private boolean confirmarModificacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA DE MODIFICACION ");
        alert.setHeaderText("El acceso en la aplicacion del usuario cambiara");
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/FormularioUsuario.fxml"));
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
