package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Reserva;
import Modelo.Usuario;
import Vista.ComponenteEspacio;
import Vista.ComponenteReserva;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultaReservasController implements Initializable {

    public DatePicker fecha;
    public ChoiceBox<Time> horaInicial;
    public ChoiceBox<Time>  horaFinal;

    public ChoiceBox<Espacio>  espacio;

    public ChoiceBox<Usuario>  usuarioConsulta;

    public ChoiceBox<String>  estado;

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

    public ConsultaReservasController(Usuario usuario, StackPane panelnformacion, StackPane panelFormulario) {
        this.usuario = usuario;
        this.panelnformacion = panelnformacion;
        this.panelFormulario = panelFormulario;


        try {
            //Cargar el componente visual del archivo fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ConsultaReserva.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        horaFinal.getItems().addAll(Reserva.generarHorasDelDia());
        horaInicial.setValue(Time.valueOf("00:00:00"));
        horaInicial.getItems().addAll(Reserva.generarHorasDelDia());
        horaFinal.setValue(Time.valueOf("23:30:00"));
        fecha.setValue(LocalDate.now());
        todo.setOnAction(event -> seleccionarTodo());
        botonBuscar.setOnAction(event -> consultar());

        espacio.getItems().addAll(ac.leerEspacios(usuario.getInstalacion().getIdInstalacion()));
        usuarioConsulta.setValue(usuario);
        estado.getItems().addAll(Reserva.ESTADOS);
        estado.setValue("TODOS");
        if(usuario.getRol().contentEquals("ADMINISTRADOR")){
            usuarioConsulta.setDisable(false);
            usuarioConsulta.getItems().addAll(ac.leerUsuarios(usuario.getInstalacion().getIdInstalacion()));
        }
    }

    /**
     * Rellenar de componentes en funcion a las reservas consultadas
     */
    public void consultar(){

        contenedorLista.getChildren().clear();
        //Consultar sin filtro
        if(todo.isSelected()){
            Usuario user=usuario.getRol().contentEquals("ADMINISTRADOR")?null:usuario;

            for(Reserva reserva:ac.leerReservas(null,null,null,null,user,"TODOS",usuario.getInstalacion().getIdInstalacion())){
                contenedorLista.getChildren().add(new ComponenteReserva(reserva,panelnformacion,panelFormulario,usuario));
            }
        }
        else {
            for(Reserva reserva:ac.leerReservas(fecha.getValue(),horaInicial.getValue(),horaFinal.getValue(),espacio.getValue(),usuarioConsulta.getValue(),estado.getValue(),usuario.getInstalacion().getIdInstalacion())){
                contenedorLista.getChildren().add(new ComponenteReserva(reserva,panelnformacion,panelFormulario,usuario));
            }
        }
    }
    //Boton para indicar que sea desea realizar una consulta sin filtros
    public void seleccionarTodo(){
        boolean seleccionado=todo.isSelected();
        fecha.setDisable(seleccionado);
        horaFinal.setDisable(seleccionado);
        horaInicial.setDisable(seleccionado);

        estado.setDisable(seleccionado);
        espacio.setDisable(seleccionado);

        horaInicial.setValue(null);
        horaFinal.setValue(null);
        fecha.setValue(null);
        espacio.setValue(null);
        if(usuario.getRol().contentEquals("ADMINISTRADOR")){
            usuarioConsulta.setDisable(seleccionado);
            usuarioConsulta.setValue(null);
        }

        estado.setValue("TODOS");

    }

}
