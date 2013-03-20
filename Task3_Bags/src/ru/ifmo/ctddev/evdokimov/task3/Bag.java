package ru.ifmo.ctddev.evdokimov.task3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Bag extends AbstractCollection<Object> {
	private long cntElements;
	private int modCount;

	private HashMap<Object, List<Object>> data;
	
	public Bag() {
		data = new HashMap<Object, List<Object>>();
	}

	private class BagIterator implements Iterator<Object> {
		int expectedModCount;
		Object currentValue, nextValue;
		
		public BagIterator() {
			expectedModCount = modCount;
			// TODO stub
		}

		@Override
		public boolean hasNext() {
			return nextValue != null;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			if (currentValue == null) {
				throw new IllegalStateException();
			}
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			// TODO delete
			// TODO Auto-generated method stub
		}

	}
	
	@Override
	public boolean add(Object e) {
		List<Object> list;
		if (data.containsKey(e)) {
			list = data.get(e);
		} else {
			list = new ArrayList<>();
		}
		list.add(e);
		data.put(e, list);
		
		cntElements++;
		modCount++;
		
		return true;
	}

	@Override
	public void clear() {
		cntElements = 0;
		modCount = 0;
		data = new HashMap<Object, List<Object>>();
	}
	
	@Override
	public boolean contains(Object e) {
		return data.containsKey(e);
	}
	
	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			List<Object> list = data.get(e);
			list.remove(list.size() - 1);
			if (list.isEmpty()) {
				data.remove(e);
			}
			cntElements--;
			modCount++;
			return true;
		}
		return false;
	}
	
	
	@Override
	public BagIterator iterator() {
		return new BagIterator();
	}

	@Override
	public int size() {
		if (cntElements > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else {
			return (int)cntElements;
		}
	}

}
