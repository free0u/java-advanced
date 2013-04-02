package ru.ifmo.ctddev.evdokimov.task3;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import ru.ifmo.ctddev.evdokimov.task3.Bag.BagIterator;

public class LinkedBag extends AbstractCollection<Object> {
	private long cntElements;
	private int modCount;

	private HashMap<Object, ArrayList<Object>> data;
	Node begin, end;
	
	private class Node {
		public Object value;
		public int index;
		public Node prev, next;
	
		public Node(Object value, int index, Node prev) {
			this.value = value;
			this.index = index;
			this.prev = prev;
		}
	}
	
	
	public LinkedBag() {
		data = new HashMap<Object, ArrayList<Object>>();

		Node begin = new Node(null, -1, null);
		Node end = begin;
	}

	public LinkedBag(List<?> list) {
		this();
		addAll(list);
	}

	private class LinkedBagIterator implements Iterator<Object> {
		int expectedModCount;
		
		Iterator<Entry<Object, LinkedList<Object>>> keysIterator;
		Iterator<Object> listIterator;
		
		Object currentKey;
		
		public LinkedBagIterator() {
			expectedModCount = modCount;
			keysIterator = data.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			if (keysIterator.hasNext()) {
				return true;
			} else {
				if (listIterator != null && listIterator.hasNext()) {
					return true;
				}
			}
			
			return false;
		}

		@Override
		public Object next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (listIterator == null || !listIterator.hasNext()) {
				Entry<Object, LinkedList<Object>> entry = keysIterator.next();
				currentKey = entry.getKey();
				listIterator = entry.getValue().iterator();
			}
			
			return listIterator.next();
		}

		@Override
		public void remove() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			if (listIterator == null) {
				throw new IllegalStateException();
			}
			listIterator.remove();
			
			if (data.get(currentKey).isEmpty()) {
				keysIterator.remove();
			}
			
			Bag.this.cntElements--;
		}

	}
	
	@Override
	public boolean add(Object e) {
		// TODO add
		
		
		cntElements++;
		modCount++;
		return true;
	}

	@Override
	public void clear() {
		cntElements = 0;
		modCount = 0;
		data = new HashMap<Object, LinkedList<Object>>();
	}
	
	@Override
	public boolean contains(Object e) {
		return data.containsKey(e);
	}
	
	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			LinkedList<Object> LinkedList = (LinkedList<Object>) data.get(e);
			LinkedList.removeLast();
			if (LinkedList.isEmpty()) {
				data.remove(e);
			}
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
