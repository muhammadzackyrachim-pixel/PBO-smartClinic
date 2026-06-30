import os
import re

view_dir = 'src/view'
pattern = re.compile(r'[ \t]*<Button[^>]*text="[^"]*Refresh[^"]*"[^>]*/>\r?\n?', re.IGNORECASE | re.DOTALL)

for filename in os.listdir(view_dir):
    if filename.endswith('.fxml'):
        filepath = os.path.join(view_dir, filename)
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        new_content = pattern.sub('', content)
        
        if content != new_content:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(new_content)
            print(f'Removed Refresh button from {filename}')
