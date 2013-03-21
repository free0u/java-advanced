package ru.ifmo.ctddev.evdokimov.task3;


public class Node {
	Object data;
	Node next, prev, pair;

	public Node(Object data) {
		this.data = data;
	}
	
	public String toString() {
		return data.toString();
	}
}