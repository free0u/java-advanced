package ru.ifmo.ctddev.evdokimov.task3;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Queue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class Bag<E> extends AbstractCollection<E> {
	private long cntElements;
	private int modCount;

	private Map<E, Queue<E>> data;
	
	public Bag() {
		data = new HashMap<E, Queue<E>>();
	}

	public Bag(Collection<? extends E> list) {
		this();
		addAll(list);
	}

	@Override
	public boolean add(E e) {
		if (data.containsKey(e)) {
			data.get(e).add(e);
		} else {
			Queue<E> newList = new LinkedList<E>();
			newList.add(e);
			data.put(e, newList);
		}
		cntElements++;
		modCount++;
		
		return true;
	}

	@Override
	public void clear() {
		cntElements = 0;
		modCount = 0;
		data.clear();
	}
	
	@Override
	public boolean contains(Object e) {
		return data.containsKey(e);
	}
	
	@Override
	public boolean remove(Object e) {
		if (!contains(e))
			return false;
		
		Queue<E> linkedList = data.get(e);
		linkedList.remove();
		if (linkedList.isEmpty()) {
			data.remove(e);
		}
		cntElements--;
		modCount++;

		return true;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new BagIterator();
	}

	@Override
	public int size() {
		return (int) Math.min(cntElements, Integer.MAX_VALUE);
	}

	private class BagIterator implements Iterator<E> {
		private int expectedModCount;
		
		private Iterator<Entry<E, Queue<E>>> keysIterator;
		private Iterator<E> listIterator;
		
		private Object currentKey;
		
		public BagIterator() {
			expectedModCount = modCount;
			keysIterator = data.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return keysIterator.hasNext() || listIterator != null && listIterator.hasNext();
		}

		@Override
		public E next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (listIterator == null || !listIterator.hasNext()) {
				Entry<E, Queue<E>> entry = keysIterator.next();
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
			
			expectedModCount = modCount;
			cntElements--;
		}

	}
	
}
