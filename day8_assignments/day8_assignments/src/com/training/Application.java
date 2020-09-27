package com.training;

import com.training.daos.InvoiceDaoImpl;
import com.training.entity.Invoice;
import com.training.ifaces.InvoiceDAO;
import com.training.utils.ConnectionUtility;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//System.out.println(ConnectionUtility.getDerbyConnection());
		
		Invoice ram = new Invoice(101,"ram",56000);
		
		InvoiceDAO dao = new InvoiceDaoImpl();
		
		boolean result = dao.add(ram);
		
		if (result)
		{
			System.out.println("row added");
		}
		
		System.out.println(dao.findAll());
		
	}

}