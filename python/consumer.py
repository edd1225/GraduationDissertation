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

# 收到消息后,往数据库插入数据
def callback(ch, method, properties, body):
    d = json.loads(body)
    print (" [x] Received %r" % d)
    # 打开数据库连接
    # 使用cursor()获取操作游标
    sql = "INSERT INTO record(username, time, type) \
           VALUES ('%s', '%s', '%s')" % \
           (d['username'], d['time'], type_arr[int(d['type'])])
    cursor = db.cursor()
    try:
        # 使用execute方法执行SQL语句
        cursor.execute(sql)
        db.commit()
        print ('提交事务成功')
    except:
        db.rollback()
        print ('回滚事务' + sql)
    ch.basic_ack(delivery_tag=method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(callback, queue='coupons')
channel.start_consuming()
