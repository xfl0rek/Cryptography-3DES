module pl.tripledes.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.tripledes.view to javafx.fxml;
    exports pl.tripledes.view;
}