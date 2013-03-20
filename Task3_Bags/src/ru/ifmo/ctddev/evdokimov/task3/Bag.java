package ru.ifmo.ctddev.evdokimov.task3;

import java.util.Iterator;
import java.util.AbstractCollection;

public class Bag extends AbstractCollection<Object> {

	class BagIterator implements Iterator<Object> {

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public boolean add(Object e) {
		// TODO realisation
		return false;
	}
	
	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
