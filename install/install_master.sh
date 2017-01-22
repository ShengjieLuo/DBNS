source env.rc
echo "master" > /etc/hostname
echo "172.16.0.104    master" >> /etc/hosts
echo "172.16.0.59     slave01" >> /etc/hosts
echo "172.16.0.68     slave02" >> /etc/hosts
scp ~/.ssh/id_rsa.pub root@slave01:/root/
scp ~/.ssh/id_rsa.pub root@slave02:/root/
scp -r /usr/lib/jvm/java-8-oracle slave01:/usr/local
scp -r /usr/lib/jvm/java-8-oracle slave02:/usr/local
rm -rf $HADOOP_HOME/logs/*
rm -rf $HADOOP_HOME/tmp
hdfs namenode -format
scp -r /usr/local/hadoop root@slave01:/usr/local
scp -r /usr/local/hadoop root@slave02:/usr/local
