module com.adventure.textbasedgui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.adventure.textbasedgui to javafx.fxml;
    exports com.adventure.textbasedgui;
}
