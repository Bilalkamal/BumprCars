package com.bc;



public class Node<T> {
	
	private Invoice invoice;
	private Node<Invoice> next;
	
	public Node(Invoice invoice) {
	
		this.invoice = invoice;
		this.next = null;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Node<Invoice> getNext() {
		return next;
	}

	public void setNext(Node<Invoice> next) {
		this.next = next;
	}
	
	public boolean hasNext()
	{
		return this.next!=null;
	}
	
	
	
	

}
