#!/bin/bash

# This is a wrapper script, to start the namenode together with the NFS sharing service. 
# 


/opt/hadoop/sbin/hadoop-daemon.sh --script hdfs namenode
/opt/hadoop/sbin/hadoop-daemon.sh --script hdfs start portmap
/opt/hadoop/sbin/hadoop-daemon.sh --script hdfs start nfs3
mount -t nfs -o vers=3,proto=tcp,nolock,noacl,sync 127.0.0.1:/ /mnt

# all the above detach, so stay alive to keep the container running.
while [ 1 ] ; do
   sleep 1d
done
