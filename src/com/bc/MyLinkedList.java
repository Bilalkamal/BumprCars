package com.bc;

import java.util.Comparator;



public class MyLinkedList<T> {
	private Node<T> head;
	private int size; 
	
	
	InvoiceCalculator iCalculator = new InvoiceCalculator();
	Comparator<T> invoiceComparator;
	
	
	public MyLinkedList(Comparator<T> invoiceComparator)
	{
		this.invoiceComparator = invoiceComparator;
		this.head = null;
		this.size = 0;
		
		
	}
	
	
	
	
	
	
	public void insertItem(T item)
	{
		Node<T> temp = new Node<T>(item); // step 0
		
//		Case 1: Size is 0 	--> add this element
	if(size ==0){
		temp.setNext(this.head); 
		this.size++;
	}

//	Case 2: Size is 1 	--> compare it to the head  (Uses Comparator)
	else if (size == 1) {
			if (invoiceComparator.compare(this.head.getItem(), item) >= 0){
				insertAtEnd(item);
			}else {
				insertAtStart(item);
			}
		}
		
//		Case 3: Size is 2+ 	--> 
		
		
		
	}
	
	
	
	
	private void insertAtStart(T item)
	{
		Node<T> temp = new Node<T>(item); // step 0 
		temp.setNext(this.head); // step 1
		this.head = temp; //step 2
		this.size++;
	}
	
	public int getsize()
	{
		return this.size;
	}
	
	public int getCount()
	{
		if(this.head==null) {
			return 0;
		}
		int count = 1;
		Node<T> curr = this.head;
		while(curr.hasNext())
		{
			count++;
			curr=curr.getNext();
		}
		
		return count;
	}
	
	public T retrieveItemAtIndex(int index)
	{
		return retrieveNodeAtIndex(index).getItem();
	}
	
	private Node<T> retrieveNodeAtIndex(int index)
	{
		if(index < 0 || index >= this.size)
		{
			throw new IndexOutOfBoundsException("Index is not valuid!!");
		}
		
		Node<T> curr = this.head;
		for(int i=0; i<index; i++)
		{			
			curr=curr.getNext();
		}
		return curr;
	}
	
	
	
	
	private void insertAtIndex(int index, T item)
	{
		if(index < 0 || index >= this.size)
		{
			throw new IndexOutOfBoundsException("Index is not valuid!!");
		}
		if(index == 0)
		{
			this.insertAtStart(item);
			return;
		}
		Node<T> curr = this.retrieveNodeAtIndex(index-1);
		Node<T> newNode = new Node<T>(item);
		newNode.setNext(curr.getNext());
		curr.setNext(newNode);
		size++;
		
	}
	
	
	private T removeAtIndex(int index)
	{
		if(this.head == null)
		{
			throw new IllegalArgumentException("No element to remove!!!");
		}
		if(index == 0)
		{
			return this.removeFromStart();			
		}
		
		Node<T> prev = this.retrieveNodeAtIndex(index-1);			
		Node<T> curr = prev.getNext();
		
		
		prev.setNext(curr.getNext());
		this.size--;
		return curr.getItem();
	}
	
	public String toString()
	{
		if(this.head == null)
		{
			return "Empty List!!";
		}
		StringBuilder sb = new StringBuilder();
		Node<T> curr = this.head;
		while(curr!=null)
		{
			sb.append(curr.getItem().toString() + ", ");
			curr=curr.getNext();
		}
		return sb.toString();
	}
	
	public boolean empty()
	{
		return this.size == 0;
	}
	
	private void insertAtEnd(T item)
	{
		if(this.size == 0)
		{
			this.insertAtStart(item);
			return;
		}
		Node<T> curr = this.head;
		while(curr.getNext()!=null)
		{
			curr=curr.getNext();
		}
		
		Node<T> newEnd = new Node<T>(item);
		curr.setNext(newEnd);
		this.size++;
	}
	
	private T removeFromStart()
	{
		if(this.empty()) {
			throw new IllegalStateException("You are removing from an empty list!!!");
		}
		
		T item = this.head.getItem(); 
		
		this.head = this.head.getNext();
		
		this.size--;
		
		return item;
	}

	

}
