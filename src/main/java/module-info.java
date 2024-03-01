module se.kth.alany.lab4test {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab.lab4 to javafx.fxml;
    exports lab.lab4;
}