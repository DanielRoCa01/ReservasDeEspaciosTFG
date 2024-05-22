package Controlador;

import Modelo.AccesoSQL;
import Modelo.Reserva;
import Modelo.Usuario;
import Vista.FormularioEspacio;
import Vista.FormularioReserva;
import Vista.InformacionEspacio;
import Vista.InformacionReserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComponenteReservaController implements Initializable {
    private  Usuario usuario;
    public VBox contenedor;
    public Label fecha;
    public Label horas;
    public Label estado;

    public Button espacio;

    public Button botonUpdate;

    public Button botonCancelar;



    public StackPane panelInformacion;
    public StackPane panelFormulario;
    private Reserva reserva;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fecha.setText(reserva.getFecha().toString());
        horas.setText(reserva.getHoraInicio().toString()+"-"+reserva.getHoraFinal().toString());
        estado.setText(reserva.getEstado());
        espacio.setText(reserva.getEspacio().getNombre());
        if(reserva.getEstado().contentEquals("CANCELADA")){
            botonUpdate.setVisible(false);
            botonCancelar.setVisible(false);
        }

    }
    public ComponenteReservaController(Reserva reserva, StackPane panelInformacion, StackPane panelFormulario, Usuario usuario) {
    this.reserva=reserva;
        this.panelInformacion=panelInformacion;
        this.panelFormulario=panelFormulario;
        this.usuario=usuario;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ComponenteReserva.fxml"));
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public VBox getContenedor() {
        return contenedor;
    }

    @FXML
    public void handleAbrirConsulta(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionEspacio(reserva.getEspacio(),reserva.getFecha()));
    }
    @FXML
    public void handleAbrirInformacionReserva(ActionEvent event) {
        panelInformacion.getChildren().clear();
        panelInformacion.getChildren().add(new InformacionReserva(reserva,panelInformacion));
    }

    @FXML
    public void handleAbrirFormularioReserva(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add( new FormularioReserva(reserva));
    }
    @FXML
    public void handleCancelarReserva(ActionEvent event) {
        AccesoSQL ac= new AccesoSQL();
        if(confirmarCancelacion()){
            ac.cancelarReserva(reserva);
        }
    }
    private boolean confirmarCancelacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA DE CANCELACION ");
        alert.setHeaderText("Una vez la reserva sea cancelada ya no podra ser modificada");
        alert.setContentText("¿Desea continuar con la modificacion?");

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Mostrar el alert y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
}
