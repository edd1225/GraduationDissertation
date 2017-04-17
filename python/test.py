# encoding: utf-8

import requests

base_url = 'http://localhost:7777'

headers = {'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJLVHZPMm5SeWcwZCIsImF1ZCI6IjE2NTc0NiIsImV4cCI6MTQ4OTI5MDc3OH0.zJOuKkgBBPdWXh809URZbNE4QuVg2fFbof1quDo6RHw'}
payload = {'type': 1}
r = requests.get(base_url + '/api/secondkill', headers=headers, params=payload)
print (r.url)
print (r.text)
