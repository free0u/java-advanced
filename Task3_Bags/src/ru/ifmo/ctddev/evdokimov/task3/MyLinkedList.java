package ru.ifmo.ctddev.evdokimov.task3;
import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList extends AbstractCollection<Object>{
	private Node start, end;
	
	private int modCount, cntElements;
	
	
	public MyLinkedList() {
		start = new Node(null);
		end = start;
	}
	
	public void add(Node e) {
		end.next = e;
		e.prev = end;
		end = e;
		modCount++;
		cntElements++;
	}
	
	@Override
	public boolean add(Object e) {
		add(new Node(e));
		return true;
	}
	
	void remove(Node e) { // Node is part of List!
		Node left = e.prev;
		Node rigth = e.next;
		
		left.next = rigth;
		if (rigth != null) {
			rigth.prev = left;
		} else {
			end = left;
		}
		modCount++;
		cntElements--;
	}
	
	private class MyIterator implements Iterator<Object> {
		Node cur;
		int expectedModCount;
		
		boolean removePossible;
		
		public MyIterator() {
			cur = start;
			expectedModCount = modCount;
		}
		
		@Override
		public boolean hasNext() {
			return cur.next != null; 
		}

		@Override
		public Object next() {
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			removePossible = true;
			
			cur = cur.next;
			
			return cur;
		}

		@Override
		public void remove() {
			if (!removePossible) {
				throw new IllegalStateException();
			}
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			MyLinkedList.this.remove(cur);
			expectedModCount = modCount;
		}
		
	}
	public Iterator<Object> iterator() {
		return new MyIterator();
	}

	@Override
	public int size() {
		return cntElements;
	}

}
