module com.example.reservasdeespaciostfg {
    requires javafx.controls;
    requires javafx.fxml;
    exports Controlador;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.reservasdeespaciostfg to javafx.fxml;
    exports com.example.reservasdeespaciostfg;
    exports Vista;
    opens Vista to javafx.fxml;
}
