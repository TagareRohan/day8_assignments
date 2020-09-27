package com.training.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.training.entity.Invoice;
import com.training.ifaces.InvoiceDAO;
import com.training.utils.AmountComparator;
import com.training.utils.ConnectionUtility;
import com.training.utils.CustomerNameComparator;
import com.training.utils.InvoiceNumberComparator;


public class InvoiceDaoImpl implements InvoiceDAO {

	private List<Invoice> invList;
	private Connection derbyConnection;
	
	
	
	public InvoiceDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		
		this.invList = new ArrayList<Invoice>();
		
		this.derbyConnection=ConnectionUtility.getDerbyConnection();
	}

	@Override
	public Collection<Invoice> findAll() {
		// TODO Auto-generated method stub
		
		String sql="select * from invoice";
		
		PreparedStatement pstmt = null;
		
		try
		{
			pstmt=this.derbyConnection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
			ResultSetMetaData rstmt=result.getMetaData();
			int columnCount=rstmt.getColumnCount();
			
			for(int i=1;i<=columnCount;i++)
			{
				System.out.println(rstmt.getColumnName(i));
			}
			
			DatabaseMetaData dbinfo=this.derbyConnection.getMetaData();
			
			System.out.println(dbinfo.getDriverName());
			
			while(result.next())
			{
				int invoiceNumber=result.getInt("invoiceNumber");
				String customerName=result.getString("customerName");
				double amount=result.getDouble("amount");
				
				Invoice inv=new Invoice(invoiceNumber,customerName,amount);
				
				this.invList.add(inv);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return this.invList;
	}

	@Override
	public boolean add(Invoice entity) {
		// TODO Auto-generated method stub
		
		String sql="insert into invoice values(?,?,?)";
		PreparedStatement pstmt = null;
		
		try
		{
			pstmt.setInt(1,entity.getInvoiceNumber());
			pstmt.setString(2, entity.getCustomerName());
			pstmt.setDouble(3, entity.getAmount());
			pstmt=this.derbyConnection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return this.invList.add(entity);
	}

//	private Invoice getObject(ResultSet result)
//	{
//		Invoice inv=null;
//		try
//		{
//			int invoiceNumber=result.getInt("invoiceNumber");
//			String customerName=result.getString("customerName");
//			double amount=result.getDouble("amount");
//			
//			inv=new Invoice(invoiceNumber,customerName,amount);
//		}
//		catch (SQLException ex)
//		{
//			Logger.getLogger(InvoiceDaoImpl.class.getName()).log(Level.SEVERE,null,ex);
//		}
//		
//		return inv;
//	}
	
	
	
	@Override
	public Invoice findById(int id) {
		// TODO Auto-generated method stub
		
		String sql="select * from invoice where invoiceNumber=?";
		PreparedStatement pstmt=null;
		ResultSet result=null;
		Invoice inv=null;
		
		try {
			pstmt.setInt(1, id);
			pstmt=this.derbyConnection.prepareStatement(sql);
			result = pstmt.executeQuery();
			
			
			while(result.next())
			{
				int invoiceNumber=result.getInt("invoiceNumber");
				String customerName=result.getString("customerName");
				double amount=result.getDouble("amount");
				
				inv=new Invoice(invoiceNumber,customerName,amount);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return inv;
	}

	@Override
	public boolean add(Invoice... invoices) {
		// TODO Auto-generated method stub
		boolean result = false;
		for (Invoice eachInvoice : invoices) {
			add(eachInvoice);
			result = true;
		}
		return result;
	}

	@Override
	public boolean remove(Invoice entity) {
		// TODO Auto-generated method stub
		
		String sql="delete from invoice values(?,?,?)";
		PreparedStatement pstmt = null;
		
		try
		{
			pstmt.setInt(1,entity.getInvoiceNumber());
			pstmt.setString(2, entity.getCustomerName());
			pstmt.setDouble(3, entity.getAmount());
			pstmt=this.derbyConnection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return this.invList.remove(entity);
	}

	@Override
	public boolean update(Invoice oldEntity, Invoice newEntity) {
		// TODO Auto-generated method stub
		
		try {
			PreparedStatement pstmt=this.derbyConnection.prepareStatement("update invoice set invoiceNumber=?"
					+ ", customerName=?, amount=? where invoiceNumber=?");
			
			pstmt.setInt(1, newEntity.getInvoiceNumber());
			pstmt.setString(2, newEntity.getCustomerName());
			pstmt.setDouble(3, newEntity.getAmount());
			pstmt.setInt(4, oldEntity.getInvoiceNumber());
			
			 
			  
			int i=pstmt.executeUpdate();
			
			if(i!=0)
			{
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
		
//		int index = 0;
//		boolean result = false;
//		if(this.invList.contains(oldEntity)) {
//			index = this.invList.indexOf(oldEntity);
//			this.invList.set(index, newEntity);
//			result = true;
//		}
//		return result;
	}

	@Override
	public Collection<Invoice> sortByInvoiceNumber() {
		// TODO Auto-generated method stub
		
		
		
		Collections.sort(this.invList);
		return this.invList;
	}

	@Override
	public Collection<Invoice> sortBy(String propName) {
		// Try and use a different factory method to do this.
		String lowerPropName = propName.toLowerCase();
		switch(lowerPropName) {
		case "customername" :
			CustomerNameComparator customerNameComparator = new CustomerNameComparator();
			Collections.sort(this.invList, customerNameComparator);
			break;
		case "invoicenumber" :
			InvoiceNumberComparator invoiceNumberComparator = new InvoiceNumberComparator();
			Collections.sort(this.invList, invoiceNumberComparator);
			break;
		case "amount" :
			AmountComparator amountComparator = new AmountComparator();
			Collections.sort(this.invList, amountComparator);
			break;
		default:
			System.out.println("Enter a valid field.");
		}
		return this.invList;
	}

}