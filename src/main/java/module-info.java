module com.mycompany.jasperfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.jasperfx to javafx.fxml;
    exports com.mycompany.jasperfx;
    requires jasperreports;
}
