package util;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ResponsiveUtil {
    
    /**
     * Membuat tombol menjadi responsif.
     * Saat lebar container kurang dari threshold, teks tombol hanya akan menampilkan icon/emoji (kata pertama).
     * Saat lebih besar, akan menampilkan teks utuh.
     * Tombol juga diatur untuk memanjang mengisi ruang yang tersedia secara proporsional.
     * 
     * @param btn Tombol yang akan dibuat responsif
     * @param container Container pembungkus (misal HBox) yang lebarnya akan dipantau
     * @param threshold Batas lebar (pixel) kapan teks akan diciutkan
     */
    public static void makeResponsiveButton(Button btn, HBox container, double threshold, String icon, String fullText) {
        if (btn == null || container == null) return;
        
        // Mencegah JavaFX menyisipkan titik tiga (...) saat terhimpit
        btn.setTextOverrun(OverrunStyle.CLIP);
        
        // Mengunci lebar minimum agar tombol tidak menjadi "ramping/kurus" saat berubah jadi logo
        javafx.application.Platform.runLater(() -> {
            if (btn.getWidth() > 0) {
                btn.setMinWidth(btn.getWidth());
            } else {
                btn.setMinWidth(120);
            }
        });
        
        // Dynamic binding untuk merubah teks berdasarkan lebar container
        btn.textProperty().bind(
            Bindings.when(container.widthProperty().lessThan(threshold))
                .then(icon)
                .otherwise(fullText)
        );
        
        // Mengurangi padding ketika tombol menciut menjadi icon saja agar tidak terpotong
        btn.paddingProperty().bind(
            Bindings.when(container.widthProperty().lessThan(threshold))
                .then(new Insets(5, 5, 5, 5))
                .otherwise(new Insets(5, 15, 5, 15))
        );
    }
}
