import sqlite3

conn = sqlite3.connect('test1.db')
c = conn.cursor()
print ("数据库打开成功")

cursor = c.execute("SELECT ID, NAME, todo, time  from COMPANY")
for row in cursor:
   print ("ID = ", row[0])
   print ("NAME = ", row[1])
   print ("todo = ", row[2])
   print ("time = ", row[3], "\n")

print ("数据操作成功")
conn.close()