package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class Bag<E> extends AbstractCollection<E> {
	protected long cntElements;
	protected int modCount;

	protected HashMap<E, LinkedList<E>> data;
	
	public Bag() {
		data = new HashMap<E, LinkedList<E>>();
	}

	public Bag(List<? extends E> list) {
		this();
		addAll(list);
	}

	private class BagIterator implements Iterator<E> {
		int expectedModCount;
		
		Iterator<Entry<E, LinkedList<E>>> keysIterator;
		Iterator<E> listIterator;
		
		Object currentKey;
		
		public BagIterator() {
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
		public E next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (listIterator == null || !listIterator.hasNext()) {
				Entry<E, LinkedList<E>> entry = keysIterator.next();
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
	public boolean add(E e) {
		if (data.containsKey(e)) {
			data.get(e).add(e);
		} else {
			LinkedList<E> LinkedList = new LinkedList<E>();
			LinkedList.add(e);
			data.put(e, LinkedList);
		}
		cntElements++;
		modCount++;
		return true;
	}

	@Override
	public void clear() {
		cntElements = 0;
		modCount = 0;
		data = new HashMap<E, LinkedList<E>>();
	}
	
	@Override
	public boolean contains(Object e) {
		return data.containsKey(e);
	}
	
	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			LinkedList<E> LinkedList = data.get(e);
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

}
