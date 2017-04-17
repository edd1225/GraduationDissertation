#coding=utf-8
#!/usr/bin/env python
import pika
import MySQLdb
import json
import sys
reload(sys)
sys.setdefaultencoding('utf8')

connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))
channel = connection.channel()

db = MySQLdb.connect('localhost', 'root', 'root', 'coupons')
print (' [*] Waiting for messages. To exit press CTRL+C')

type_arr = ['5 yuan', '10 yuan', '15 yuan']

LIMIT = 10
tmp_list = []

# 收到消息后,往数据库插入数据
def callback(ch, method, properties, body):
    global tmp_list
    global LIMIT
    d = json.loads(body)
    print (" [x] Received %r" % d)
    if len(tmp_list) >= LIMIT:
        # 打开数据库连接，使用cursor()获取操作游标
        cursor = db.cursor()
        sql = "INSERT INTO record(username, time, type) \
            VALUES (%s, %s, %s)"
        params = tuple(tmp_list)
        try:
            # 使用execute方法执行SQL语句
            cursor.executemany(sql, params)
            db.commit()
            print ('提交事务成功')
            tmp_list = []
        except:
            db.rollback()
            print ('回滚事务' + sql)    
    else:
        tmp_list.append((d['username'], d['time'], type_arr[int(d['type'])]))
        print("缓存长度为: " + str(len(tmp_list)))
        

channel.basic_consume(callback, queue='coupons', no_ack=True)

channel.start_consuming()
