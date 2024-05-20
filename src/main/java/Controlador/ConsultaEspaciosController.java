package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Usuario;
import Vista.ComponenteEspacio;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultaEspaciosController implements Initializable {

    public DatePicker fecha;
    public ChoiceBox<Time> horaInicial;
    public ChoiceBox<Time>  horaFinal;
    public Button botonBuscar;
    public CheckBox todo;
    public FlowPane contenedorLista;
    public BorderPane contenedor;


    private Usuario usuario;
    private StackPane panelnformacion;

    private StackPane panelFormulario;

    public BorderPane getContenedor() {
        return contenedor;
    }

    public ConsultaEspaciosController(Usuario usuario, StackPane panelnformacion,StackPane panelFormulario) {
        this.usuario = usuario;
        this.panelnformacion = panelnformacion;
        this.panelFormulario = panelFormulario;


        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ConsultaEspacios.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip ttHoraInicio = new Tooltip("Hora a la que quieres empezar la reserva");
        Tooltip ttHoraFinal = new Tooltip("Hora a la que quieres acabar la reserva");
        Tooltip ttFecha = new Tooltip("Fecha en que quieres que ocurra la reserva");
        Tooltip ttTodo = new Tooltip("Buscar todos los espacios de la instalación");

        // Asignar el Tooltip al botón
        Tooltip.install(horaInicial, ttHoraInicio);
        Tooltip.install(horaFinal, ttHoraFinal);
        Tooltip.install(horaFinal, ttHoraFinal);
        Tooltip.install(fecha, ttFecha);
        Tooltip.install(todo, ttTodo);

        horaFinal.getItems().addAll(generarHorasDelDia());
        horaInicial.setValue(Time.valueOf("00:00:00"));
        horaInicial.getItems().addAll(generarHorasDelDia());
        horaFinal.setValue(Time.valueOf("23:30:00"));
        fecha.setValue(LocalDate.now());
        todo.setOnAction(event -> seleccionarTodo());
        botonBuscar.setOnAction(event -> consultar());
    }

    public void consultar(){
        AccesoSQL ac=new AccesoSQL();
        contenedorLista.getChildren().clear();
        if(todo.isSelected()){
            for(Espacio espacio:ac.leerEspacios(usuario.getInstalacion().getIdInstalacion())){
                contenedorLista.getChildren().add(new ComponenteEspacio(espacio,panelnformacion,panelFormulario,fecha.getValue(),usuario,horaInicial.getValue(),horaFinal.getValue()));
            }
        }
        else {
            for(Espacio espacio:ac.leerEspaciosLibres(usuario.getInstalacion().getIdInstalacion(),
                    fecha.getValue().toString(),horaInicial.getValue(),horaFinal.getValue())){
                contenedorLista.getChildren().add(new ComponenteEspacio(espacio,panelnformacion,panelFormulario,fecha.getValue(),usuario,horaInicial.getValue(),horaFinal.getValue()));
            }
        }
    }
    public void seleccionarTodo(){
        boolean seleccionado=todo.isSelected();
        fecha.setDisable(seleccionado);
        horaFinal.setDisable(seleccionado);
        horaInicial.setDisable(seleccionado);
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
