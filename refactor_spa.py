import os
import re

ctrl_dir = 'src/controller'

mappings = {
    'Pasien': 'pasien.fxml',
    'Dokter': 'dokter.fxml',
    'Petugas': 'petugas.fxml',
    'Obat': 'obat.fxml',
    'Poli': 'poli.fxml',
    'Pendaftaran': 'pendaftaran.fxml',
    'Pemeriksaan': 'pemeriksaan.fxml',
    'RekamMedis': 'rekam_medis.fxml',
    'ResepObat': 'resep_obat.fxml'
}

# 1. Modify List Controllers
for key, fxml in mappings.items():
    filename = f"{key}Controller.java"
    filepath = os.path.join(ctrl_dir, filename)
    if os.path.exists(filepath):
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        # Add import for DashboardController if not exists
        if 'import controller.DashboardController;' not in content:
            content = content.replace('import util.SceneUtil;', 'import util.SceneUtil;\nimport controller.DashboardController;')
            content = content.replace('import util.AlertUtil;', 'import util.AlertUtil;\nimport controller.DashboardController;')

        # Refactor handleTambah
        tambah_regex = re.compile(
            r'Stage stage = SceneUtil\.createModal\(loader, .*?\);\s*'
            r'Form' + key + r'Controller ctrl = loader\.getController\(\);\s*'
            r'ctrl\.setModeTambah\(\);\s*'
            r'stage\.showAndWait\(\);\s*'
            r'loadData\(\);', re.DOTALL
        )
        tambah_replacement = (
            r'javafx.scene.Node node = loader.load();\n'
            r'            Form' + key + r'Controller ctrl = loader.getController();\n'
            r'            ctrl.setModeTambah();\n'
            r'            DashboardController.getInstance().setCenterContent(node);'
        )
        content = tambah_regex.sub(tambah_replacement, content)

        # Refactor handleEdit
        edit_regex = re.compile(
            r'Stage stage = SceneUtil\.createModal\(loader, .*?\);\s*'
            r'Form' + key + r'Controller ctrl = loader\.getController\(\);\s*'
            r'ctrl\.setModeEdit\((.*?)\);\s*'
            r'stage\.showAndWait\(\);\s*'
            r'loadData\(\);', re.DOTALL
        )
        edit_replacement = (
            r'javafx.scene.Node node = loader.load();\n'
            r'            Form' + key + r'Controller ctrl = loader.getController();\n'
            r'            ctrl.setModeEdit(\1);\n'
            r'            DashboardController.getInstance().setCenterContent(node);'
        )
        content = edit_regex.sub(edit_replacement, content)

        # Write back
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Refactored {filename}")

# 2. Modify Form Controllers
for key, fxml in mappings.items():
    filename = f"Form{key}Controller.java"
    filepath = os.path.join(ctrl_dir, filename)
    if os.path.exists(filepath):
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        # Replace stage.close() with DashboardController navigation
        close_regex = re.compile(r'\(\(Stage\).*?\.getScene\(\)\.getWindow\(\)\)\.close\(\);')
        close_replacement = f'DashboardController.getInstance().setCenterContent("/view/{fxml}");'
        content = close_regex.sub(close_replacement, content)

        # Add import for DashboardController if not exists
        if 'import controller.DashboardController;' not in content and close_replacement in content:
            content = content.replace('import javafx.fxml.FXML;', 'import javafx.fxml.FXML;\nimport controller.DashboardController;')

        # Write back
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Refactored {filename}")
