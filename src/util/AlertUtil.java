package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtil {
    public static void success(String msg) {
        show(Alert.AlertType.INFORMATION, "Sukses", msg);
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