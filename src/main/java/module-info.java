module com.spacewars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens com.spacewars to javafx.fxml;
    exports com.spacewars;
    exports com.spacewars.control;
    opens com.spacewars.control to javafx.fxml;
}