#coding=utf-8
#!/usr/bin/env python
import pika
import MySQLdb
import json
import sys
reload(sys)
sys.setdefaultencoding('utf8')

connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
channel = connection.channel()
channel.queue_declare(queue='coupons', durable=True)

db = MySQLdb.connect('db', 'root', 'root', 'coupons0')
print (' [*] Waiting for messages. To exit press CTRL+C')

typeT = '5 yuan'

# 收到消息后,往数据库插入数据
def callback(ch, method, properties, body):
    d = json.loads(body)
    print (" [x] Received %r" % d)
    # 打开数据库连接
    # 使用cursor()获取操作游标
    sql = "INSERT INTO record(username, time, type) \
           VALUES ('%s', '%s', '%s')" % \
           (d['username'], d['time'], typeT)
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