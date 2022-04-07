module com.example.chatapp_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.chatapp_2 to javafx.fxml;
    exports com.example.chatapp_2;
}