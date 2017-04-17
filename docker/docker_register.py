# encoding: utf-8

import requests
import json
from random import Random
from concurrent.futures import ThreadPoolExecutor

def generate_random_str(length=10):
    str = ''
    chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'
    charslength = len(chars) - 1
    random = Random()
    for i in range(length):
        str += chars[random.randint(0, charslength)]
    return str


base_url = 'http://localhost:81'

headers = {'Content-Type': 'application/json'}

user_num = 100000

pool = ThreadPoolExecutor(max_workers=20)

f = open('user_list.txt', 'w')

def write_json_to_file(payload, index):
    f.write(str(index) + ' ' + json.dumps(payload) + '\n')


def register_request(payload, index):
    r = requests.post(base_url + '/api/user/register', headers=headers, data=json.dumps(payload))
    print (str(index) + ' 请求完成 ' + r.text)

for i in range(user_num):
    username = generate_random_str(11)
    password = generate_random_str(11)
    nickname = generate_random_str(11)
    payload = {}
    payload['username'] = username
    payload['password'] = password
    payload['nickname'] = nickname
    write_json_to_file(payload, i + 1)
    pool.submit(register_request, payload, i + 1)
f.close()
