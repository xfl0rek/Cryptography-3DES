module pl.tripledes.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires org.apache.commons.io;


    opens pl.tripledes.view to javafx.fxml;
    exports pl.tripledes.view;
}