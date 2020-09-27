package com.training.utils;

import java.util.Comparator;

import com.training.entity.Invoice;

public class AmountComparator implements Comparator<Invoice> {

	@Override
	public int compare(Invoice object1, Invoice object2) {
		// TODO Auto-generated method stub
		if (object1.getAmount() < object2.getAmount()) return -1;
		if (object1.getAmount() > object2.getAmount()) return 1;
		return 0;
	}

}