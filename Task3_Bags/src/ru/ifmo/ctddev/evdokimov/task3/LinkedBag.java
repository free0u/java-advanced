package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedBag extends Bag {
	private MyLinkedList order;
	
	public LinkedBag() {
		super();
		order = new MyLinkedList();
	}

	private class LinkedBagIterator implements Iterator<Object> {
		int expectedModCount;
		Node currentResult;
		Iterator<Object> iterator;
		
		boolean removePossible;
		
		public LinkedBagIterator() {
			expectedModCount = modCount;
			
			iterator = order.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Object next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			currentResult = (Node) iterator.next();
			removePossible = true;
			
			return currentResult.data;
		}

		@Override
		public void remove() {
			if (!removePossible) {
				throw new IllegalStateException();
			}
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			Node pair = currentResult.pair;
			
			data.get(currentResult.data).remove(pair);
			iterator.remove();
			
			removePossible = false;
		}

	}
	
	@Override
	public boolean add(Object e) {
		MyLinkedList list;
		
		Node nodeInMap = new Node(e);
		Node nodeInOrder = new Node(e);
		
		nodeInMap.pair = nodeInOrder;
		nodeInOrder.pair = nodeInMap;
		
		if (data.containsKey(e)) {
			list = data.get(e);
		} else {
			list = new MyLinkedList();
		}
		list.add(nodeInMap);
		data.put(e, list);
		
		order.add(nodeInOrder);
		
		cntElements++;
		modCount++;
		
		return true;
	}

	@Override
	public void clear() {
		super.clear();
		order = new MyLinkedList();
	}
		
	@Override
	public boolean remove(Object e) {
		if (contains(e)) {
			MyLinkedList list = data.get(e);
			
			Iterator<Object> it = list.iterator();
			Node node = (Node) it.next();
			Node nodePair = node.pair;

			it.remove();
			order.remove(nodePair);
			
			cntElements--;
			modCount++;
			return true;
		}
		return false;
	}
	
	
	@Override
	public Iterator<Object> iterator() {
		return new LinkedBagIterator();
	}
}
