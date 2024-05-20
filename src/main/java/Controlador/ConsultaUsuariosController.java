package Controlador;

import Modelo.AccesoSQL;
import Modelo.Espacio;
import Modelo.Seccion;
import Modelo.Usuario;
import Vista.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultaUsuariosController implements Initializable {


    public BorderPane contenedor;
    public Button botonBuscar;
    public CheckBox todo;
    public ChoiceBox<Seccion> seccionBox;
    public ChoiceBox<String> rolBox;
    public Button nuevoUsuario;
    public Button nuevoEspacio;
    public Button nuevaSeccion;
    public FlowPane contenedorLista;


    private Usuario usuario;
    private StackPane panelnformacion;

    private StackPane panelFormulario;

    public BorderPane getContenedor() {
        return contenedor;
    }

    public ConsultaUsuariosController(Usuario usuario, StackPane panelnformacion, StackPane panelFormulario) {
        this.usuario = usuario;
        this.panelnformacion = panelnformacion;
        this.panelFormulario = panelFormulario;


        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vista/ConsultaInstalacion.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip ttSeccion = new Tooltip("Especificar la seccion  cuyos usuarios deseas consultar");
        Tooltip ttRoles = new Tooltip("Especificar los roles de los usuarios que deseas consultar");

        Tooltip ttTodo = new Tooltip("Buscar todos los espacios de la instalación");

        // Asignar el Tooltip al botón
        Tooltip.install(seccionBox, ttSeccion);
        Tooltip.install(rolBox, ttRoles);

        Tooltip.install(todo, ttTodo);
        AccesoSQL ac=new AccesoSQL();
        rolBox.getItems().addAll(Usuario.ROLES);
        seccionBox.getItems().addAll(ac.leerSecciones(usuario.getInstalacion().getIdInstalacion()));


        todo.setOnAction(event -> seleccionarTodo());
        botonBuscar.setOnAction(event -> consultar());
    }

    public void consultar(){
        AccesoSQL ac=new AccesoSQL();
        ArrayList<Usuario> listaUsuarios= new ArrayList<Usuario>();
        contenedorLista.getChildren().clear();
        if(todo.isSelected()){
            listaUsuarios=ac.leerUsuarios(usuario.getInstalacion().getIdInstalacion());
        }
        else {
            listaUsuarios=ac.leerUsuarios(seccionBox.getValue(),rolBox.getValue(),usuario.getInstalacion().getIdInstalacion());
        }
        for(Usuario user:listaUsuarios){
            contenedorLista.getChildren().add(new ComponenteUsuario(user,panelnformacion,panelFormulario));
        }
    }
    public void seleccionarTodo(){
        boolean seleccionado=todo.isSelected();
        seccionBox.setDisable(seleccionado);
        rolBox.setDisable(seleccionado);
    }

    @FXML
    public void handleAbrirFormularioEspacio(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioEspacio(usuario));
    }
    @FXML
    public void handleAbrirFormularioUsuario(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioUsuario(usuario.getInstalacion()));
    }
    @FXML
    public void handleAbrirFormularioSeccion(ActionEvent event) {
        panelFormulario.getChildren().clear();
        panelFormulario.getChildren().add(new FormularioSeccion(usuario.getInstalacion()));
    }@FXML
    public void handleAbrirFormularioSeccionMod(ActionEvent event) {
        if (seccionBox.getValue()!=null) {
            panelFormulario.getChildren().clear();
            panelFormulario.getChildren().add(new FormularioSeccion(seccionBox.getValue()));
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR DE CONSULTA");
        alert.setHeaderText("Seccion vacia");
        alert.setContentText("Seleccione seccion para modificarla");
        alert.showAndWait();
    }

}
