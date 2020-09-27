package com.training.utils;

import java.util.Comparator;

import com.training.entity.Invoice;

public class CustomerNameComparator implements Comparator<Invoice> {

	@Override
	public int compare(Invoice object1, Invoice object2) {
		// TODO Auto-generated method stub
		return object1.getCustomerName().compareTo(object2.getCustomerName());
	}

}