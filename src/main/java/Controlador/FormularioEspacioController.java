package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Reserva;
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

public class FormularioEspacioController implements Initializable {

    public VBox contenedorFormularios;
    public TextField nombre;
    public ChoiceBox<Time> horaApertura;
    public ChoiceBox<String> tamaño;
    public TextArea descripcion;
    public ChoiceBox<Time> horaCierre;
    public Button botonEnviar;

    private boolean seModifica;
    private Espacio espacio;
    private Usuario usuario;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();

    //Constructor para modificar un espacio
    public FormularioEspacioController(Espacio espacio) {
        this.espacio=espacio;
        seModifica=true;
        iniciarComponente();
    }
    //Constructor para crear un espacio
    public FormularioEspacioController(Usuario usuario) {
        this.usuario=usuario;
        seModifica=false;
        iniciarComponente();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        horaApertura.getItems().addAll(Reserva.generarHorasDelDia());
        horaCierre.getItems().addAll(Reserva.generarHorasDelDia());
        tamaño.getItems().addAll(Espacio.TAMAÑOS);

        if (seModifica){
            nombre.setText(espacio.getNombre());
            tamaño.setValue(espacio.getTamaño());
            descripcion.setText(espacio.getDescripcion());
            horaCierre.setValue(espacio.getHoraCierre());
            horaApertura.setValue(espacio.getHoraApertura());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");
        }//Si no se modifica
        else {
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear() {
        //Si el nombre esta en uso
        if(ac.comprobarNombreEspacio(nombre.getText(),usuario.getInstalacion().getIdInstalacion())){
            alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre de la seccion y pruebe de nuevo");

            return;
        }
        //Si  quedan campos por rellenar
         if(nombre.getText().contentEquals("")||tamaño.getValue().contentEquals("") ||descripcion.getText().contentEquals("")
               ||horaApertura.getValue()==null||horaCierre.getValue()==null){
             alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");

             return;
        }
         //Si las horas son incorrectas
        if(horaApertura.getValue().getTime()>=horaCierre.getValue().getTime()){
            alertar(Alert.AlertType.WARNING, "ERROR HORAS INCORRECTAS", "La hora de apertura debe ser menor a la hora de cierre");

            return;
        }
        ac.escribirEspacio(new Espacio(0,nombre.getText(),tamaño.getValue(),horaApertura.getValue(),horaCierre.getValue(),descripcion.getText(),usuario.getInstalacion()));
        alertar(Alert.AlertType.INFORMATION, "CREACION CORRECTA", "El espacio "+nombre.getText()+" se ha creado correctamente");

    }

    private void informarDeCreacion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CREACION CORRECTA");
        alert.setHeaderText("El espacio se ha creado correctamente");

        alert.showAndWait();
    }

    public void  modificar(){
        //Si el nombre esta en uso

        if(ac.comprobarNombreEspacio(nombre.getText(),espacio.getInstalacion().getIdInstalacion())  ){
            if(!nombre.getText().contentEquals(espacio.getNombre())){
                alertar(Alert.AlertType.WARNING, "ERROR DE NOMBRE EXISTENTE", "Cambie el nombre de la seccion y pruebe de nuevo");

                return;
            }

        }
        //Si  quedan campos por rellenar

        if(nombre.getText().contentEquals("")||tamaño.getValue().contentEquals("") ||descripcion.getText().contentEquals("")
                ||horaApertura.getValue()==null||horaCierre.getValue()==null){
            alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");

            return;
        }
        //Si las horas son incorrectas
         if(horaApertura.getValue().getTime()>=horaCierre.getValue().getTime()){
             alertar(Alert.AlertType.WARNING, "ERROR HORAS INCORRECTAS", "La hora de apertura debe ser menor a la hora de cierre");

             return;
        }
        if (confirmarModificacion()) {
            espacio=new Espacio(espacio.getIdEspacio(), nombre.getText(), tamaño.getValue(), horaApertura.getValue(), horaCierre.getValue(), descripcion.getText(), espacio.getInstalacion());
            ac.modificar(espacio);
            ac.cancelarReservasProhibidas( espacio);
        }
    }

    private boolean confirmarModificacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA DE MODIFICACION ");
        alert.setHeaderText("Todas las reservas que no esten dentro del horario seran canceladas");
        alert.setContentText("¿Desea continuar con la modificacion?");

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Mostrar el alert y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    private static void alertar(Alert.AlertType error, String s, String s1) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(s1);
        alert.showAndWait();
    }



    private void iniciarComponente() {
        //Cargar componente visual del archivo fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/FormularioEspacio.fxml"));
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
