import sqlite3
from tokenize import String

conn = sqlite3.connect('test1.db')
c = conn.cursor()
print ("数据库打开成功")

#stri="睡覺"
#query = """
#     INSERT INTO COMPANY
#         (NAME, todo, time)
#     VALUES
#          (?, ?, ?)
#        """
#data =  ['黃婉瑜', stri, '10:22']

#c.execute(query, data)

t='沒有那個10點01'
dele='DELETE from COMPANY where time=?'
c.execute(dele,(t,))
#c.execute("DELETE from COMPANY where ID=1;")
#c.execute("DELETE from COMPANY where ID=2;")
#c.execute("DELETE from COMPANY where ID=4;")

conn.commit()
print ("数据插入成功")
conn.close()