package com.bc;

import java.util.Comparator;

public class InvoiceComparator implements Comparator<Invoice> {

	InvoiceCalculator iCalculator = new InvoiceCalculator();
	@Override
	public int compare(Invoice inv1, Invoice inv2) {
		// TODO Auto-generated method stub
		
		if (iCalculator.calculateGrandTotal(inv1) > iCalculator.calculateGrandTotal(inv2)) {
			return 1;
		}else if (iCalculator.calculateGrandTotal(inv1) < iCalculator.calculateGrandTotal(inv2)) {
			return -1;
		} else {
			return 0;
		}
		
		
	}

	
	
	
}
