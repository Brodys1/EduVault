module cs151.application {
    requires transitive javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires com.dlsc.formsfx;

    opens cs151.application to javafx.fxml;
    opens cs151.ui to javafx.fxml;

    exports cs151.application;
    exports cs151.ui;
}