import os
import re

directory = 'src/view'
pattern = re.compile(r'[ \t]*<Button[^>]*Kembali[^>]*/>\r?\n?', re.IGNORECASE)

for filename in os.listdir(directory):
    if filename.endswith(".fxml") and filename != "dashboard.fxml" and filename != "login.fxml":
        filepath = os.path.join(directory, filename)
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        if pattern.search(content):
            new_content = pattern.sub('', content)
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(new_content)
            print(f"Removed Kembali from {filename}")
