package com.highill.practice.hbase;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

public class HBaseMain {

	private static final String TABLE_NAME = "java_table";

	public static void main(String[] args)
	{

		Configuration config = HBaseConfiguration.create();
		try
		{
			Connection connection = ConnectionFactory.createConnection(config);
			Admin admin = connection.getAdmin();

			// create table
			TableName tableName = TableName.valueOf(TABLE_NAME);
			HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);

			HColumnDescriptor columnName = new HColumnDescriptor("name");
			HColumnDescriptor columnInfo = new HColumnDescriptor("info");
			HColumnDescriptor columnAddress = new HColumnDescriptor("address");

			tableDescriptor.addFamily(columnName);
			tableDescriptor.addFamily(columnInfo);
			tableDescriptor.addFamily(columnAddress);

			if (admin.tableExists(tableName))
			{
				admin.disableTable(tableName);
				admin.deleteTable(tableName);
				System.out.println("----- ----- Delete table ");
			}
			admin.createTable(tableDescriptor);
			System.out.println("----- ----- Create table");

			// modify table
			admin.disableTable(tableName);
			HColumnDescriptor addColumnA = new HColumnDescriptor("add_a");
			addColumnA.setCompactionCompressionType(Algorithm.GZ);
			addColumnA.setMaxVersions(HConstants.ALL_VERSIONS);
			tableDescriptor.addFamily(addColumnA);

			HColumnDescriptor updateColumnAddress = new HColumnDescriptor("address");
			updateColumnAddress.setCompactionCompressionType(Algorithm.GZ);
			updateColumnAddress.setMaxVersions(HConstants.ALL_VERSIONS);
			tableDescriptor.modifyFamily(updateColumnAddress);

			admin.modifyTable(tableName, tableDescriptor);
			admin.enableTable(tableName);
			System.out.println("----- ----- Modify table columns");

			// add data
			Table table = connection.getTable(tableName);

			Put put1 = new Put("key_2016_1001".getBytes());
			put1.addColumn("name".getBytes(), "n1".getBytes(), "Name Value1".getBytes());
			table.put(put1);

			Put put2 = new Put("key_2016_1002".getBytes());
			put2.addColumn("name".getBytes(), "n2".getBytes(), "Name Value1".getBytes());
			put2.addColumn("info".getBytes(), "info2_1".getBytes(), "Info 2_1".getBytes());
			put2.addColumn("info".getBytes(), "info2_2".getBytes(), "Info 2_2".getBytes());
			table.put(put2);

			Put put1_update = new Put("key_2016_1001".getBytes());
			put1_update.addColumn("name".getBytes(), "n1".getBytes(), "Name Value1 Update".getBytes());
			table.put(put1_update);

			System.out.println("----- ----- Add data finish");

			// get data
			System.out.println("----- ----- Start get");
			Get get = new Get("key_2016_1001".getBytes());
			Result getResult = table.get(get);
			System.out.println("----- ----- getResult=" + getResult);

			// scan data
			System.out.println("----- ----- Start scan");
			Scan scan = new Scan();
			scan.addColumn("name".getBytes(), "n1".getBytes());
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> resultIterator = resultScanner.iterator();
			for (; resultIterator.hasNext();)
			{
				Result result = resultIterator.next();
				System.out.println("----- result=" + result);
				System.out.println("----- row=" + new String(result.getRow()) + ", value="
				        + new String(result.getValue("name".getBytes(), "n1".getBytes())));
			}

			admin.close();
			connection.close();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
