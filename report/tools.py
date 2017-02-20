import re
import mysql.connector

def exeSQL(sql):
        config = {
                  'user':'root',
                  'password':'123456',
                  'host':'172.16.0.104',
                  'port':3306,
                  'database':'stat'}
        conn = mysql.connector.connect(**config)
        cur = conn.cursor()
        cur.execute(sql)
        conn.commit()
        cur.close()
        conn.close()

def exeSQLquery(sql):
        config = {
                  'user':'root',
                  'password':'123456',
                  'host':'172.16.0.104',
                  'port':3306,
                  'database':'stat'}
        conn = mysql.connector.connect(**config)
        cur = conn.cursor()
        cur.execute(sql)
        result_set = cur.fetchall()
        '''
        if result_set:
                for row in result_set:
                        print "%s, %s, %d" % (row[0],row[1],row[2])
        '''
        cur.close()
        conn.close()
        return result_set

def sub(string,var,new):
	return re.sub("{"+var+"}",new,string)


def sub2(string,var,new):
	return re.sub(var,new,string)
