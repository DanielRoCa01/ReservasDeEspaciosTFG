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
    public InformacionEspacioController(Espacio espacio, LocalDate fechaConsulta){
        this.espacio =espacio;
        this.fechaConsulta=fechaConsulta;
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
        // Handle the date change event
        fechaConsulta=fecha.getValue();
        construirTabla();
        // You can add your custom logic here
        // For example, update other UI components based on the selected date
    }

    private void construirTabla(){
        AccesoSQL ac = new AccesoSQL();

        boolean[] tabla = ac.consultarHorario(espacio.getIdEspacio(), fecha.getValue());
        
        listView.getItems().clear();
        // Generar lista de horas del día
        List<Time> horas = generarHorasDelDia();


        for (int i = horaAMediasHoras(espacio.getHoraApertura()); i < horaAMediasHoras(espacio.getHoraCierre()); i++) {
            String estado = tabla[i] ?  "Reservado":"Disponible";
            String horaEstado = horas.get(i).toString() + " - " + estado;
            listView.getItems().add(horaEstado);
        }

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
    private static int horaAMediasHoras(Time hora) {
        // Convertimos la hora a minutos y luego dividimos por 30 minutos (una media hora)
        LocalTime localTime = hora.toLocalTime();

        // Calcular minutos desde la medianoche
        int minutos = localTime.getHour() * 60 + localTime.getMinute();

        // Convertir a medias horas y retornar como int
        return minutos / 30;
    }
    public VBox getContenedor() {
        return contenedor;
    }
}
