import requests
import zipfile
import os

url = 'https://github.com/ianderdvl/chess/raw/main/chess/0-chess-moves/starter-code/chess.zip'
zip_path = 'chess.zip'

# Download the file
response = requests.get(url)
if response.status_code == 200:
    with open(zip_path, 'wb') as file:
        file.write(response.content)
else:
    print("Failed to download the file")
    exit()

# Check if the file is a valid zip
if zipfile.is_zipfile(zip_path):
    # Unzip the file
    with zipfile.ZipFile(zip_path, 'r') as zip_ref:
        zip_ref.extractall('chess_unzipped')

    # Clean up
    os.remove(zip_path)

    print('Download and extraction complete.')
else:
    print("The downloaded file is not a valid zip file.")