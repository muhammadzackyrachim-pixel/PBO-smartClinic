package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

public class AutoCompleteUtil {

    /**
     * Membuat ComboBox menjadi searchable (bisa diketik untuk Auto-Complete).
     * Pastikan memanggil method ini SETELAH comboBox diisi dengan data awal (getItems().addAll(...)).
     */
    public static <T> void setup(ComboBox<T> comboBox) {
        ObservableList<T> originalItems = FXCollections.observableArrayList(comboBox.getItems());
        comboBox.setEditable(true);

        // StringConverter penting agar ComboBox tetap mengembalikan Object (bukan sekadar teks String)
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public T fromString(String string) {
                if (string == null || string.isEmpty()) return null;
                for (T item : originalItems) {
                    if (item.toString().equals(string)) {
                        return item;
                    }
                }
                return null;
            }
        });

        comboBox.getEditor().setOnKeyReleased(event -> {
            // Abaikan tombol navigasi agar user tetap bisa menggunakan panah keyboard
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT ||
                event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END ||
                event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
                return;
            }

            if (event.getCode() == KeyCode.ESCAPE) {
                comboBox.hide();
                return;
            }

            String filter = comboBox.getEditor().getText();
            if (filter == null) filter = "";
            
            ObservableList<T> filteredList = FXCollections.observableArrayList();

            for (T item : originalItems) {
                if (item.toString().toLowerCase().contains(filter.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            // Simpan posisi kursor saat ini agar tidak melompat ke awal setelah filter
            int caretPosition = comboBox.getEditor().getCaretPosition();
            
            comboBox.setItems(filteredList);
            comboBox.getEditor().setText(filter);
            comboBox.getEditor().positionCaret(caretPosition);
            
            if (!filteredList.isEmpty()) {
                comboBox.show();
            } else {
                comboBox.hide();
            }
        });
    }
}
