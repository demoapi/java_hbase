# java_hbase #

## 配置Hadoop环境 ##

验证Hadoop环境是否有HBase的lib, 没有的话在HADOOP_CLASSPATH生成后添加.  

```  
$ /usr/local/hadoop-2.7.2/bin/hadoop classpath

$ sudo vim /usr/local/hadoop-2.7.2/etc/hadoop/hadoop-env.sh
HBASE_HOME=/usr/local/hbase-1.1.3

export HADOOP_CLASSPATH=${HADOOP_CLASSPATH}:${HBASE_HOME}/lib/*



$ sudo /usr/local/hadoop-2.7.2/sbin/stop-yarn.sh
$ sudo /usr/local/hadoop-2.7.2/sbin/stop-dfs.sh


$ sudo /usr/local/hadoop-2.7.2/sbin/start-dfs.sh
$ sudo /usr/local/hadoop-2.7.2/sbin/start-yarn.sh

$ sudo /usr/local/hadoop-2.7.2/bin/hadoop classpath
# /usr/local/hadoop-2.7.2/etc/hadoop:
# /usr/local/hadoop-2.7.2/share/hadoop/common/lib/*:/usr/local/hadoop-2.7.2/share/hadoop/common/*:
# /usr/local/hadoop-2.7.2/share/hadoop/hdfs:/usr/local/hadoop-2.7.2/share/hadoop/hdfs/lib/*:/usr/local/hadoop-2.7.2/share/hadoop/hdfs/*:
# /usr/local/hadoop-2.7.2/share/hadoop/yarn/lib/*:/usr/local/hadoop-2.7.2/share/hadoop/yarn/*:
# /usr/local/hadoop-2.7.2/share/hadoop/mapreduce/lib/*:/usr/local/hadoop-2.7.2/share/hadoop/mapreduce/*:/contrib/capacity-scheduler/*.jar:
# /usr/local/hbase-1.1.3/lib/*

```  

## 执行demo ##

```  
$ /usr/local/hadoop-2.7.2/bin/hadoop jar /soft/java_hbase-0.0.0.1.jar com.highill.practice.hbase.HBaseMain



```  

## 验证程序结果 ##

```  
$ sudo /usr/local/hbase-1.1.3/bin/hbase shell

hbase(main):001:0> list


hbase(main):002:0> describe 'java_table'
Table java_table is ENABLED
java_table
COLUMN FAMILIES DESCRIPTION
{NAME => 'add_a', BLOOMFILTER => 'ROW', VERSIONS => '2147483647', IN_MEMORY => 'false', KEEP_DELETED_CELL
S => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '0'
, BLOCKCACHE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0', METADATA => {'COMPRESSION_COMPACT
' => 'GZ'}}
{NAME => 'address', BLOOMFILTER => 'ROW', VERSIONS => '2147483647', IN_MEMORY => 'false', KEEP_DELETED_CE
LLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '
0', BLOCKCACHE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0', METADATA => {'COMPRESSION_COMPA
CT' => 'GZ'}}
{NAME => 'info', BLOOMFILTER => 'ROW', VERSIONS => '1', IN_MEMORY => 'false', KEEP_DELETED_CELLS => 'FALS
E', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCAC
HE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'}
{NAME => 'name', BLOOMFILTER => 'ROW', VERSIONS => '1', IN_MEMORY => 'false', KEEP_DELETED_CELLS => 'FALS
E', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCAC
HE => 'true', BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'}
4 row(s) in 0.1800 seconds



hbase(main):003:0> scan 'java_table', {COLUMNS => ['name', 'info', 'address', 'add_a']}




```  

