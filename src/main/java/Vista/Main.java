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
            primaryStage.setResizable(false);
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
