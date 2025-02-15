package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class InformacionEspacioController implements Initializable {

    public VBox contenedor;

    public Espacio espacio;
    public Label horaApertura;
    public Label horaCierre;
    public Label descripcion;
    public Label size;
    public Label nombre;
    public DatePicker fecha;
    public TableView<ObservableList<String>> horario;
    public TableColumn<ObservableList<String>, String> columnaHoras;
    public TableColumn<ObservableList<String>, String>disponibilidad;
    private LocalDate fechaConsulta;
    private ListView<String> listView = new ListView<>();
    public VBox contenedorTabla;

    private AccesoSQL ac = AccesoSQL.obtenerInstancia();

    public InformacionEspacioController(Espacio espacio, LocalDate fechaConsulta){
        this.espacio =espacio;
        this.fechaConsulta=fechaConsulta;

        //Carga el componete visual del archivo fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/InformacionEspacio.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inicializa los componentes visuales con los atributos del espacio
        descripcion.setText(espacio.getDescripcion());
        horaApertura.setText(espacio.getHoraApertura().toString());
        horaCierre.setText(espacio.getHoraCierre().toString());
        size.setText(espacio.getTamaño());
        nombre.setText(espacio.getNombre());
        fecha.setValue(fechaConsulta);
        construirTabla();
        fecha.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleDateChange(newValue);
        });
        contenedorTabla.getChildren().add(listView);
    }
    private void handleDateChange(LocalDate newDate) {
        fechaConsulta=fecha.getValue();
        construirTabla();

    }

    private void construirTabla(){
        boolean[] tabla = ac.consultarHorario(espacio.getIdEspacio(), fecha.getValue());
        listView.getItems().clear();
        // Generar lista de horas del día
        List<Time> horas = Reserva.generarHorasDelDia();

        //Rellena una tabla con las disponibilidades del espacio en esa fecha concreta
        for (int i = Reserva.horaAMediasHoras(espacio.getHoraApertura()); i < Reserva.horaAMediasHoras(espacio.getHoraCierre()); i++) {
            String estado = tabla[i] ?  "Reservado":"Disponible";
            String horaEstado = horas.get(i).toString() + " - " + estado;
            listView.getItems().add(horaEstado);
        }

    }

    public VBox getContenedor() {
        return contenedor;
    }
}
