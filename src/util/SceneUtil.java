package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneUtil {
    
    public static Stage createModal(FXMLLoader loader, String title, double w, double h) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        Parent root = loader.load();
        Scene scene = new Scene(root, w, h);
        scene.getStylesheets().add(SceneUtil.class.getResource("/view/style.css").toExternalForm());
        stage.setScene(scene);
        return stage;
    }

    public static void switchScene(Stage stage, String fxml) {
        try {
            Parent root = FXMLLoader.load(SceneUtil.class.getResource(fxml));
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(SceneUtil.class.getResource("/view/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AlertUtil.error("Gagal memuat halaman: " + e.getMessage());
            e.printStackTrace();
        }
    }
}