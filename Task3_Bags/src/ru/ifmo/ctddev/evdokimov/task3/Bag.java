package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class Bag extends AbstractCollection<Object> {
	protected long cntElements;
	protected int modCount;

	protected HashMap<Object, List<Object>> data;
	
	public Bag() {
		data = new HashMap<Object, List<Object>>();
	}

	public Bag(List<?> list) {
		this();
		addAll(list);
	}

	private class BagIterator implements Iterator<Object> {
		int expectedModCount;
		Object currentValue;
		
		Iterator<Entry<Object, List<Object>>> keysIterator;
		Iterator<Object> listIterator, currentListIterator;
		
		boolean removePossible, hasNextValue;
		
		public BagIterator() {
			expectedModCount = modCount;
			
			hasNextValue = false;
			for (keysIterator = data.entrySet().iterator(); keysIterator.hasNext(); ) {
				Entry<Object, List<Object>> entry = keysIterator.next();
				List<Object> list = entry.getValue();
				
				if (!list.isEmpty()) {
					listIterator = list.iterator();
					hasNextValue = true;
					break;
				}
			}
		}
		
		public BagIterator(Object e) {
			expectedModCount = modCount;
			
			hasNextValue = false;
			for (keysIterator = data.entrySet().iterator(); keysIterator.hasNext(); ) {
				Entry<Object, List<Object>> entry = keysIterator.next();
				if (entry.getKey() != e) {
					continue;
				}
				List<Object> list = entry.getValue();
				
				if (!list.isEmpty()) {
					listIterator = list.iterator();
					hasNextValue = true;
					break;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return hasNextValue;
		}

		@Override
		public Object next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			currentValue = listIterator.next();
			currentListIterator = listIterator;
			
			hasNextValue = false;
			if (listIterator.hasNext()) {
				hasNextValue = true;
			} else {
				while (keysIterator.hasNext()) {
					Entry<Object, List<Object>> entry = keysIterator.next();
					List<Object> list = entry.getValue();
					
					if (!list.isEmpty()) {
						listIterator = list.iterator();
						hasNextValue = true;
						break;
					}
				}
			}
			
			removePossible = true;
			return currentValue;
		}

		@Override
		public void remove() {
			if (!removePossible) {
				throw new IllegalStateException();
			}
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			currentListIterator.remove(); 
			Bag.this.cntElements--;
			removePossible = false;
		}

	}
	
	@Override
	public boolean add(Object e) {
		List<Object> list;
		if (data.containsKey(e)) {
			list = data.get(e);
		} else {
			list = new LinkedList<Object>();
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
		if (data.containsKey(e)) {
			return !data.get(e).isEmpty();
		}
		return false;
	}
	
	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			BagIterator it = new BagIterator(e);
			it.next();
			it.remove();
			cntElements--;
			modCount++;
			return true;
		}
		return false;
	}
	
	
	@Override
	public Iterator<Object> iterator() {
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
