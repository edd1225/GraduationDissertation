# encoding: utf-8

import requests
import re
import json
from concurrent.futures import ThreadPoolExecutor


base_url = 'http://localhost:7777'

headers = {'Content-Type': 'application/json'}


pool = ThreadPoolExecutor(max_workers=6)

f = open('user_list.txt', 'r')
payloads = []
for line in f:
    payloads.append(re.search("{.*}", line).group())
f.close()

f = open('jwt_list.txt', 'w')
def write_token_to_file(token, index):
    f.write(str(index) + ' ' + token + '\n')

def login_request(payload, index):
    r = requests.post(base_url + '/api/authentication/login', headers=headers, data=payload)
    print (r.text)
    return json.loads(r.text)['data']['token']

for index, payload in enumerate(payloads):
    write_token_to_file((pool.submit(login_request, payload, index + 1)).result(), index + 1)

