echo "slave02" > /etc/hostname
echo "172.16.0.104    master" >> /etc/hosts
echo "172.16.0.59     slave01" >> /etc/hosts
echo "172.16.0.68     slave02" >> /etc/hosts
cat ~/id_rsa.pub >> ~/.ssh/authorized_keys
rm ~/id_rsa.pub
