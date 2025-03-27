import json
import requests

url = 'http://localhost:8080/api/documents/1'
data = {'pdfUrl' : "c://endowments/test.pdf", 'date' : "2020-01-01", 'panel': 'panel-1'}
json_data = json.dumps(data)

try:
    # req = requests.post(url=url, json=json_data, auth=('admin', 'password'))
    req = requests.get(url=url, auth=('admin', 'password'))
    if req.status_code == 200:
        print('Document fetched successfully')
        print(req.json())
    else:
        print('Error uploading document')
        print(req.json())
except Exception as e:
    print('Error occureced while uploading document : ', e)