package Vista;

import Controlador.InicioController;
import Modelo.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {





        // ac.escribirEspacio(new Espacio(2,"laboratorio","xl",new Time(9000000),new Time(1000000),"aupa",ac.consultarInstalacion(2)));
//        ac.modificarReserva(new Reserva(3,ac.consultarEspacio(1),ac.consultarUsuario(4),new Time(30000000),new Time(32000000), LocalDate.parse("2024-05-19"),"Modificacion","RESERVADA"));
//
//        //ac.escribirUsuario(new Usuario(2,"Juan","Usuario","Centro",ac.consultarInstalacion(2)));
//        for(Instalacion instalacion:ac.leerInstalaciones()){
//            System.out.println(instalacion.toString());
//        }
//        for(Usuario instalacion:ac.leerUsuarios(1)){
//            System.out.println(instalacion.toString());
//        }
//        for(Espacio instalacion:ac.leerEspaciosLibres(1,new Date(1000000000),new Time(1200000),new Time(1500000))){
//            System.out.println(instalacion.toString());
//        }
//        for(Reserva instalacion:ac.leerReservas(null,null,null,null,null,"TODOS")){
//            System.out.println(instalacion.toString());
//        }


        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Inicio.fxml"));
            Parent root = loader.load();



            Scene scene = new Scene(root, 300, 350);

            InicioController controlador = loader.getController();
            controlador.setEscenaOriginal(scene);

            // Establecer la escena en la ventana principal (primaryStage)
            primaryStage.setScene(scene);

            // Establecer el título de la ventana
            primaryStage.setTitle("SpaceSync");

            // Mostrar la ventana
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de la excepción (puede mostrar un mensaje de error o realizar otra acción adecuada)
        }



    }
    public static void main(String[] args){
        launch(args);
    }
}
