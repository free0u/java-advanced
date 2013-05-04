package ru.ifmo.ctddev.evdokimov.task3;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;


public class LinkedBag<E> extends AbstractCollection<E> {
	private long cntElements;
	private int modCount;

	private Map<E, List<Node>> data;
	Node begin, end;
	
	private class Node {
		public E value;
		public int index;
		public Node prev, next;
	
		public Node(E value, int index, Node prev) {
			this.value = value;
			this.index = index;
			this.prev = prev;
		}
	}
	
	public LinkedBag() {
		data = new HashMap<E, List<Node>>();
		begin = new Node(null, -1, null);
		end = begin;
	}

	public LinkedBag(List<? extends E> list) {
		this();
		addAll(list);
	}

	@Override
	public boolean add(E e) {
		if (!contains(e)) {
			data.put(e,  new ArrayList<Node>());
		}
		List<Node> list = data.get(e);
		Node node = new Node(e, list.size(), end);
		end.next = node;
		end = node;
		list.add(node);
		
		cntElements++;
		modCount++;
		return true;
	}

	@Override
	public void clear() {
		cntElements = 0;
		modCount = 0;
		data.clear();
		begin = null;
		end = null;
	}
	
	@Override
	public boolean contains(Object e) {
		return data.containsKey(e);
	}
	
	@Override
	public boolean remove(Object e) {
		if (!contains(e)) {
			return false;
		}
		List<Node> list = data.get(e);
		Node node = list.get(list.size() - 1);

		if (node.next != null) {
			node.next.prev = node.prev;
		}
		node.prev.next = node.next;

		list.remove(list.size() - 1);
		if (list.isEmpty()) {
			data.remove(e);
		}

		cntElements--;
		modCount++;
		return true;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new LinkedBagIterator();
	}

	@Override
	public int size() {
		if (cntElements > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else {
			return (int)cntElements;
		}
	}

	private class LinkedBagIterator implements Iterator<E> {
		int expectedModCount;
		Node currentNode;
		
		boolean removePossible;
		
		public LinkedBagIterator() {
			expectedModCount = modCount;
			currentNode = begin;
		}

		@Override
		public boolean hasNext() {
			return currentNode.next != null;
		}

		@Override
		public E next() {
			if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			removePossible = true;
			
			currentNode = currentNode.next;
			return currentNode.value;
		}

		@Override
		public void remove() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!removePossible) {
				throw new IllegalStateException();
			}
			removePossible = false;

			if (currentNode.next != null) {
				currentNode.next.prev = currentNode.prev;
			}
			currentNode.prev.next = currentNode.next;

			List<Node> list = data.get(currentNode.value);
			list.set(currentNode.index, list.get(list.size() - 1));
			list.get(currentNode.index).index = currentNode.index;
			
			list.remove(list.size() - 1);
			if (list.isEmpty()) {
				data.remove(currentNode.value);
			}
			currentNode = currentNode.prev;
			cntElements--;
		}

	}
	
}
