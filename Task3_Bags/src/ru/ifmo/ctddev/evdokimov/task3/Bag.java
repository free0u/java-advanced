package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ConcurrentModificationException;
import java.util.Queue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Bag<E> extends AbstractCollection<E> {
	protected long cntElements;
	protected int modCount;

	protected Map<E, Queue<E>> data;
	
	public Bag() {
		data = new HashMap<E, Queue<E>>();
	}

	public Bag(List<? extends E> list) {
		this();
		addAll(list);
	}

	@Override
	public boolean add(E e) {
		if (data.containsKey(e)) {
			data.get(e).add(e);
		} else {
			Queue<E> linkedList = new LinkedList<E>();
			linkedList.add(e);
			data.put(e, linkedList);
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
		if (contains(e)) {
			Queue<E> linkedList = data.get(e);
			linkedList.remove();
			if (linkedList.isEmpty()) {
				data.remove(e);
			}
			cntElements--;
			modCount++;
			return true;
		}
		return false;
	}
	
	@Override
	public Iterator<E> iterator() {
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

	private class BagIterator implements Iterator<E> {
		int expectedModCount;
		
		Iterator<Entry<E, Queue<E>>> keysIterator;
		Iterator<E> listIterator;
		
		Object currentKey;
		
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
			
			cntElements--;
		}

	}
	
}
