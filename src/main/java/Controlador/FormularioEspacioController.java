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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        horaApertura.getItems().addAll(generarHorasDelDia());
        horaCierre.getItems().addAll(generarHorasDelDia());
        tamaño.getItems().addAll(Espacio.TAMAÑOS);

        if (seModifica){
            nombre.setText(espacio.getNombre());
            tamaño.setValue(espacio.getTamaño());
            descripcion.setText(espacio.getDescripcion());
            horaCierre.setValue(espacio.getHoraCierre());
            horaApertura.setValue(espacio.getHoraApertura());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");
        }
        else {
            botonEnviar.setOnAction(event -> crear());
            botonEnviar.setText("Crear");
        }
    }

    private void crear() {
        AccesoSQL ac =new AccesoSQL();
        if(ac.comprobarNombreEspacio(nombre.getText(),usuario.getInstalacion().getIdInstalacion())){
            alertarEspacio();
            return;
        }
        else if(nombre.getText().contentEquals("")||tamaño.getValue().contentEquals("") ||descripcion.getText().contentEquals("")
               ||horaApertura.getValue()==null||horaCierre.getValue()==null){
            alertarVacio();
            return;
        }
        else if(horaApertura.getValue().getTime()>=horaCierre.getValue().getTime()){
            alertarHoras();
            return;
        }
        ac.escribirEspacio(new Espacio(0,nombre.getText(),tamaño.getValue(),horaApertura.getValue(),horaCierre.getValue(),descripcion.getText(),usuario.getInstalacion()));
        informarDeCreacion();
    }

    private void informarDeCreacion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CREACION CORRECTA");
        alert.setHeaderText("El espacio se ha creado correctamente");

        alert.showAndWait();
    }

    public void  modificar(){
        AccesoSQL ac =new AccesoSQL();
        if(ac.comprobarNombreEspacio(nombre.getText(),espacio.getInstalacion().getIdInstalacion())  ){
            if(!nombre.getText().contentEquals(espacio.getNombre())){
                alertarEspacio();
                return;
            }

        }
        else if(nombre.getText().contentEquals("")||tamaño.getValue().contentEquals("") ||descripcion.getText().contentEquals("")
                ||horaApertura.getValue()==null||horaCierre.getValue()==null){
            alertarVacio();
            return;
        }
        else if(horaApertura.getValue().getTime()>=horaCierre.getValue().getTime()){
            alertarHoras();
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

    private void alertarHoras() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Horas incorrectas");
        alert.setContentText("La hora de apertura debe ser menor a la hora de cierre");
        alert.showAndWait();
    }

    private void alertarVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Campos sin rellenar");
        alert.setContentText("Por favor rellene todos los campos antes de continuar");
        alert.showAndWait();
    }

    private void alertarEspacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Espacio existente");
        alert.setContentText("Cambie el nombre del espacio y pruebe de nuevo");
        alert.showAndWait();
    }

    public FormularioEspacioController(Espacio espacio) {
    this.espacio=espacio;
    seModifica=true;
        iniciarComponente();
    }
    public FormularioEspacioController(Usuario usuario) {
        this.usuario=usuario;
        seModifica=false;
        iniciarComponente();
    }

    private void iniciarComponente() {
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
