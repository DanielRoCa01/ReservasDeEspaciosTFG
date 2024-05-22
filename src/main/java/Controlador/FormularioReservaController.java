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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormularioReservaController implements Initializable {
    public  DatePicker fechaPicker ;
    private final List<String> tamanos = List.of("XS", "S", "M", "L", "XL");
    public ChoiceBox<Espacio> espacioBox;
    public ChoiceBox<Time> horaInicial;
    public TextArea descripcion;
    public ChoiceBox<Time> horaFinal;
    public Button botonEnviar;
    public ChoiceBox<Usuario> usuarioBox;
    private  Time hora1;
    private  Time hora2;
    private  LocalDate fecha;
    private  Reserva reserva;

    public VBox contenedorFormularios;


    private boolean seModifica;
    private Espacio espacio;
    private Usuario usuario;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (seModifica){
            horaInicial.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(reserva.getEspacio().getHoraApertura()),Reserva.horaAMediasHoras(reserva.getEspacio().getHoraCierre())));
            horaFinal.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(reserva.getEspacio().getHoraApertura()),Reserva.horaAMediasHoras(reserva.getEspacio().getHoraCierre())));
            espacioBox.getItems().addAll(ac.leerEspacios(reserva.getEspacio().getInstalacion().getIdInstalacion()));
            usuarioBox.setValue(reserva.getUsuario());
            espacioBox.setValue(reserva.getEspacio());
            fechaPicker.setValue(reserva.getFecha());
            horaInicial.setValue(reserva.getHoraInicio());
            horaFinal.setValue(reserva.getHoraFinal());
            descripcion.setText(reserva.getDescripcion());
            botonEnviar.setOnAction(event -> modificar());
            botonEnviar.setText("Modificar");
        }
        else {
            usuarioBox.setValue(usuario);
            espacioBox.setValue(espacio);
            fechaPicker.setValue(fecha);
            horaInicial.setValue(hora1);
            horaFinal.setValue(hora2);

            horaInicial.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(espacio.getHoraApertura()),Reserva.horaAMediasHoras(espacio.getHoraCierre())));
            horaFinal.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(espacio.getHoraApertura()),Reserva.horaAMediasHoras(espacio.getHoraCierre())));
            espacioBox.getItems().addAll(ac.leerEspacios(usuario.getInstalacion().getIdInstalacion()));
            botonEnviar.setOnAction(event -> crear());
            if(usuario.getRol().contentEquals("ADMINISTRADOR")){
                usuarioBox.getItems().addAll(ac.consultarUsuario(usuario.getInstalacion().getIdInstalacion()));
                usuarioBox.setDisable(false);
            }
            botonEnviar.setText("Crear");
        }

    }

    private void crear() {
        if(!ac.comprobarDisponibilidad(espacioBox.getValue().getIdEspacio(),fechaPicker.getValue(),horaInicial.getValue(),horaFinal.getValue())){
            alertarEspacio();
            return;
        }
        else if(espacioBox.getValue()==null||fechaPicker.getValue()==null
               ||horaFinal.getValue()==null||horaInicial.getValue()==null){
            alertarVacio();
            return;
        }
        else if(horaInicial.getValue().getTime()>=horaFinal.getValue().getTime()){
            alertarHoras();
            return;
        }
        ac.escribirReserva(new Reserva(0,espacioBox.getValue(),usuarioBox.getValue(),horaInicial.getValue(),horaFinal.getValue(),fechaPicker.getValue(),descripcion.getText(),"RESERVADA"));
        informarDeCreacion();
    }

    private void informarDeCreacion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CREACION CORRECTA");
        alert.setHeaderText("La reserva se ha creado correctamente");

        alert.showAndWait();
    }

    public void  modificar(){

        if (!reserva.isUpadatable()){
            alertarFecha();
            return;
        }
        if(ac.comprobarDisponibilidad(espacioBox.getValue().getIdEspacio(),fechaPicker.getValue(),horaInicial.getValue(),horaFinal.getValue())){
            alertarEspacio();
            return;
        }
        else if(espacioBox.getValue()==null||fechaPicker.getValue()==null
                ||horaFinal.getValue()==null||horaInicial.getValue()==null){
            alertarVacio();
            return;
        }
        else if(horaInicial.getValue().getTime()>=horaFinal.getValue().getTime()){
            alertarHoras();
            return;
        }
        if (confirmarModificacion()) {
            reserva=new Reserva(reserva.getId(),espacioBox.getValue(),reserva.getUsuario(),horaInicial.getValue(),horaFinal.getValue(),fechaPicker.getValue(),descripcion.getText(),"RESERVADA");
            ac.modificar(reserva);

        }
    }

    private void alertarFecha() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("No es posible modificar la reserva");
        alert.setContentText("Para modificar una reserva debe hacerla con mas de 1 semana de antelacion");
        alert.showAndWait();
    }

    private boolean confirmarModificacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA DE MODIFICACION ");
        alert.setHeaderText("La reserva dejara cambiara");
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
        alert.setContentText("La hora de inicio debe ser menor a la hora final");
        alert.showAndWait();
    }

    private void alertarVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Campos sin rellenar");
        alert.setContentText("Por favor rellene todos los campos(descripcion opcional) antes de continuar");
        alert.showAndWait();
    }

    private void alertarEspacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE FORMULARIO");
        alert.setHeaderText("Espacio no disponible");
        alert.setContentText("Cambie el espacio, la fecha o las horas de la reserva, por favor.");
        alert.showAndWait();
    }

    public FormularioReservaController(Usuario usuario, Espacio espacio, LocalDate fecha, Time hora1, Time hora2) {
    this.espacio=espacio;
        this.usuario=usuario;
        this.fecha=fecha;
        this.hora1=hora1;
        this.hora2=hora2;
    seModifica=false;
        iniciarComponente();
    }
    public FormularioReservaController(Reserva reserva) {
        this.reserva=reserva;
        seModifica=true;
        iniciarComponente();
    }

    private void iniciarComponente() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/FormularioReserva.fxml"));
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
