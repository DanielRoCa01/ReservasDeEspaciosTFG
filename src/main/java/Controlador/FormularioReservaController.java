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
    //Constructor para crear una reserva
    public FormularioReservaController(Usuario usuario, Espacio espacio, LocalDate fecha, Time hora1, Time hora2) {
        this.espacio=espacio;
        this.usuario=usuario;
        this.fecha=fecha;
        this.hora1=hora1;
        this.hora2=hora2;
        seModifica=false;
        iniciarComponente();
    }
    //Constructor para modificar una reserva
    public FormularioReservaController(Reserva reserva) {
        this.reserva=reserva;
        seModifica=true;
        iniciarComponente();
    }

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
        //Si no se modifica
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
                usuarioBox.getItems().addAll(ac.leerUsuarios(usuario.getInstalacion().getIdInstalacion()));
                usuarioBox.setDisable(false);
            }
            botonEnviar.setText("Crear");
        }
        espacioBox.setOnAction(event -> cambiarHorario());

    }

    private void cambiarHorario() {
        Espacio espacio= espacioBox.getValue();
        horaInicial.getItems().clear();
        horaFinal.getItems().clear();
        horaInicial.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(espacio.getHoraApertura()),Reserva.horaAMediasHoras(espacio.getHoraCierre())));
        horaFinal.getItems().addAll(Reserva.generarHorasDelDia().subList(Reserva.horaAMediasHoras(espacio.getHoraApertura()),Reserva.horaAMediasHoras(espacio.getHoraCierre())));

    }

    private void crear() {
        //Si el espacio no esta disponible
        if(!ac.comprobarDisponibilidad(espacioBox.getValue().getIdEspacio(),fechaPicker.getValue(),horaInicial.getValue(),horaFinal.getValue())){
            alertar(Alert.AlertType.WARNING, "ERROR NO DISPONIBLE", "Cambie el espacio, la fecha o las horas de la reserva, por favor.");

            return;
        }
        //Si quedan campos sin rellenar
         if(espacioBox.getValue()==null||fechaPicker.getValue()==null
               ||horaFinal.getValue()==null||horaInicial.getValue()==null){
             alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");
            return;
        }
         //Si las horas son invalidas
        if(horaInicial.getValue().getTime()>=horaFinal.getValue().getTime()){

            alertar(Alert.AlertType.WARNING, "ERROR HORAS INVALIDAS", "La hora de inicio debe ser menor a la hora final");

            return;
        }
        ac.escribir(new Reserva(0,espacioBox.getValue(),usuarioBox.getValue(),horaInicial.getValue(),horaFinal.getValue(),fechaPicker.getValue(),"RESERVADA",descripcion.getText()),Reserva.CAMPOS_SQL);
        alertar(Alert.AlertType.INFORMATION, "CREACION CORRECTA", "La reserva se ha creado correctamente");

    }

    private static void alertar(Alert.AlertType error, String s, String s1) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(s1);
        alert.showAndWait();
    }


    public void  modificar(){
        //Si la reserva no es modificable
        if (!reserva.isUpadatable()){
            alertar(Alert.AlertType.WARNING, "ERROR DE FECHA", "Para modificar una reserva debe hacerla con mas de 1 semana de antelacion");

            return;
        }//Si el espacio no esta disponible
        if(!ac.comprobarDisponibilidad(espacioBox.getValue().getIdEspacio(),fechaPicker.getValue(),horaInicial.getValue(),horaFinal.getValue(),reserva.getId())){
            alertar(Alert.AlertType.WARNING, "ERROR NO DISPONIBLE", "Cambie el espacio, la fecha o las horas de la reserva, por favor.");

            return;
        }
        //Si quedan campos sin rellenar
         if(espacioBox.getValue()==null||fechaPicker.getValue()==null
                ||horaFinal.getValue()==null||horaInicial.getValue()==null){
             alertar(Alert.AlertType.WARNING, "ERROR CAMPOS SIN RELLENAR", "Por favor rellene todos los campos antes de continuar");

             return;
        }
        //Si las horas son invalidas
         if(horaInicial.getValue().getTime()>=horaFinal.getValue().getTime()){
             alertar(Alert.AlertType.WARNING, "ERROR HORAS INVALIDAS", "La hora de inicio debe ser menor a la hora final");

             return;
        }
         //Si se confirma la reserva
        if (confirmarModificacion()) {
            reserva=new Reserva(reserva.getId(),espacioBox.getValue(),reserva.getUsuario(),horaInicial.getValue(),horaFinal.getValue(),fechaPicker.getValue(),"RESERVADA",descripcion.getText());
            ac.modificar(reserva);

        }
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
