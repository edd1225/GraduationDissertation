#coding=utf-8
#!/usr/bin/env python
import pika
import MySQLdb

db = MySQLdb.connect('localhost', 'root', 'root', 'coupons')


tmp_list = ((u'KTvO2nRyg0d', u'2017-03-05 06:51:16', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:51:47', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:25', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:26', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:26', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:26', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:27', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:28', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:28', '10 yuan'), (u'KTvO2nRyg0d', u'2017-03-05 06:52:30', '10 yuan'))

sql = "INSERT INTO record(username, time, type) VALUES (%s, %s, %s)"
params = tuple(tmp_list)

cursor = db.cursor()
try:
    # 使用execute方法执行SQL语句
    print(params)
    cursor.executemany(sql, params)
    db.commit()
    print ('提交事务成功')
    tmp_list = []
except Exception as e:
    print(e)
    db.rollback()
    print ('回滚事务' + sql)    