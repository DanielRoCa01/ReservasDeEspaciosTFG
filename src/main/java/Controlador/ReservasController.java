package Controlador;

import Modelo.AccesoSQL;
import Modelo.Reserva;
import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ReservasController implements Initializable {

    @FXML
    public Label label;
    public Button button;
    public VBox contenedor;
    public VBox contenedor2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccesoSQL ac=new AccesoSQL();
        ContenidoReserva contenidoReserva= new ContenidoReserva (ac.leerEspacios(2),label);
        contenedor2.getChildren().add(new ComponenteReserva());
        label.setText("helloWorld");

        for (Reserva reserva : ac.leerReservas(null,null,null,null,null,"TODOS")) {
            Button botonReserva = new Button(reserva.getId()+"");
            botonReserva.setPrefWidth(182);
            botonReserva.minWidth(180);
            botonReserva.setOnAction(event -> mostrarDescripcion(reserva.getDescripcion()));
            contenedor.getChildren().add(botonReserva);
        }
    }
    @FXML
    public void handleButtonAction(ActionEvent evento){



    }
    public void mostrarDescripcion(String descripcion) {
        // Suponiendo que tienes un label en tu FXML con el identificador "labelDescripcion"
        label.setText(descripcion);
    }
}
