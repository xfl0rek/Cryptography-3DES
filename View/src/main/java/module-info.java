module pl.tripledes.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;


    opens pl.tripledes.view to javafx.fxml;
    exports pl.tripledes.view;
}