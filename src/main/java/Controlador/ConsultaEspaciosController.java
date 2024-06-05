package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Reserva;
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

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();

    public BorderPane getContenedor() {
        return contenedor;
    }

    public ConsultaEspaciosController(Usuario usuario, StackPane panelnformacion,StackPane panelFormulario) {
        this.usuario = usuario;
        this.panelnformacion = panelnformacion;
        this.panelFormulario = panelFormulario;


        try {
            //Cargar el componente visual del archivo fxml
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

        horaFinal.getItems().addAll(Reserva.generarHorasDelDia());
        horaInicial.setValue(Time.valueOf("00:00:00"));
        horaInicial.getItems().addAll(Reserva.generarHorasDelDia());
        horaFinal.setValue(Time.valueOf("23:30:00"));
        fecha.setValue(LocalDate.now());
        todo.setOnAction(event -> seleccionarTodo());
        botonBuscar.setOnAction(event -> consultar());
    }

    /**
     * Listar por componentes los espacios consultados
     */
    public void consultar(){

        contenedorLista.getChildren().clear();
        //Consultar sin filtro
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

    /**
     * Funcion para indicar una consulta sin filtro
     */
    public void seleccionarTodo(){
        boolean seleccionado=todo.isSelected();
        fecha.setDisable(seleccionado);
        horaFinal.setDisable(seleccionado);
        horaInicial.setDisable(seleccionado);
    }

}
