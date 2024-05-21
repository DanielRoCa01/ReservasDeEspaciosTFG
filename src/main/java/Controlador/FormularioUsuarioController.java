package Controlador;

import Modelo.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccesoSQL ac=new AccesoSQL();


        rol.getItems().addAll(Usuario.ROLES);

        if (seModifica){
            seccion.getItems().addAll(ac.leerSecciones(usuario.getInstalacion().getIdInstalacion()));
            seccion.setValue(usuario.getSeccion());
            nombre.setText(usuario.getNombre());
            rol.setValue(usuario.getRol());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");

        }
        else {
            seccion.getItems().addAll(ac.leerSecciones(instalacion.getIdInstalacion()));
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear()  {
        AccesoSQL ac =new AccesoSQL();
        if(ac.consultarUsuario(instalacion.getName(),nombre.getText())!=null){

            alertarNombre();
            return;


        }
        else if(nombre.getText().contentEquals("")||rol.getValue().contentEquals("")
                ||seccion.getValue()==null){
            alertarVacio();
            return;
        }
        ac.escribirUsuario(new Usuario(0,nombre.getText(),rol.getValue(),seccion.getValue(),instalacion));
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
        if(ac.consultarUsuario(usuario.getInstalacion().getName(),nombre.getText())!=null&&!nombre.getText().contentEquals(usuario.getNombre())){

                alertarNombre();
                return;


        }
        else if(nombre.getText().contentEquals("")||rol.getValue().contentEquals("")
                ||seccion.getValue()==null){
            alertarVacio();
            return;
        }

        if (confirmarModificacion()) {
            usuario=new Usuario(usuario.getIdUsuario(), nombre.getText(),rol.getValue(),seccion.getValue(),usuario.getInstalacion());
            ac.modificar(usuario);

        }
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

    public FormularioUsuarioController(Usuario usuario) {
    this.usuario=usuario;
        seModifica=true;
        iniciarComponente();
    }
    public FormularioUsuarioController(Instalacion instalacion) {
        this.instalacion=instalacion;
        seModifica=false;
        iniciarComponente();
    }

    private void iniciarComponente() {
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
    public static List<Time> generarHorasDelDia() {
        List<Time> horasDelDia = new ArrayList<>();

        for (int hora = 0; hora < 24; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                horasDelDia.add(Time.valueOf(String.format("%02d:%02d:00", hora, minuto)));
            }
        }

        return horasDelDia;
    }
}
