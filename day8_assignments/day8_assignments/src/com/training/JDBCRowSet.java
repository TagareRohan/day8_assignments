package com.training;

import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;
import javax.sql.rowset.spi.*;

import com.training.entity.Invoice;
import com.training.utils.ConnectionUtility;

public class JDBCRowSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con=ConnectionUtility.getDerbyConnection();

		RowSetFactory fact;
		
		String derbyUrl="jdbc:derby:SampleDB;create=true";
		
		try {
			fact=RowSetProvider.newFactory();
			CachedRowSet rowSet=fact.createCachedRowSet();
			
			rowSet.setUrl(derbyUrl);
			rowSet.setPageSize(2);
			
			String sql="select * from invoice";
			
			rowSet.setCommand(sql);
			
			rowSet.execute();
			
			int i=1;
			do
			{
				while(rowSet.next())
				{
					int invoiceNumber=rowSet.getInt("invoiceNumber");
					String customerName=rowSet.getString("customerName");
					double amount=rowSet.getDouble("amount");
					
					Invoice inv=new Invoice(invoiceNumber,customerName,amount);
				}
				i++;
			}while(rowSet.nextPage());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
