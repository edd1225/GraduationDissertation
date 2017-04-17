# encoding: utf-8

import requests
import json
import random
from concurrent.futures import ThreadPoolExecutor


base_url = 'http://localhost:7777'


pool = ThreadPoolExecutor(max_workers=10)

# 读取所有登录过后的token
f = open('jwt_list.txt', 'r')
tokens = []
for line in f:
    tokens.append(line.split()[1])
f.close()


SUCCESS = 0
FAILURE = 0

# 执行秒杀请求
def secondkill_request(token, typeNum, index):
    global SUCCESS
    global FAILURE
    headers = {'Authorization': 'Bearer ' + token}
    payload = {'type': typeNum}
    r = requests.get(base_url + '/api/secondkill', headers=headers, params=payload)
    if json.loads(r.text) == True:
        SUCCESS = SUCCESS + 1
    else:
        FAILURE = FAILURE + 1
    print ("Success: " + str(SUCCESS) + " Failure: " + str(FAILURE))
    return index, json.loads(r.text)

#for index, token in enumerate(tokens):
#    print (pool.submit(secondkill_request, token, index + 1).result())

weight_types = [0, 1, 1, 2]

for index, token in enumerate(tokens):
    print(pool.submit(secondkill_request, token, weight_types[random.randint(0, 3)], index + 1).result())
