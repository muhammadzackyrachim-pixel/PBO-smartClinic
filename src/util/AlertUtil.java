package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtil {
    public static void success(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        
        alert.show();
        
        // Menutup pop-up otomatis secara aman
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));
        delay.setOnFinished(e -> {
            if (alert.isShowing()) {
                // Memberikan result OK secara programmatis agar JavaFX mengizinkan pop-up ditutup
                alert.setResult(ButtonType.OK); 
                alert.close();
            }
        });
        delay.play();
    }

    public static void warning(String msg) {
        show(Alert.AlertType.WARNING, "Peringatan", msg);
    }

    public static void error(String msg) {
        show(Alert.AlertType.ERROR, "Error", msg);
    }

    public static boolean confirm(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Konfirmasi");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void show(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}