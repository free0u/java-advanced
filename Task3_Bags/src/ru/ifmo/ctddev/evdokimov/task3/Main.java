package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Main {
	public static void print(Collection<Object> c) {
		String s = "(";
		for (Object i : c) {
			s += i + ", ";
		}
		s += ")";
		System.out.println(s);
	}
	
	
	public static void main(String[] args) {
		LinkedBag bag = new LinkedBag();
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(i);
			}
			
		}
		
		
		for (int i = 0; i < 5; ++i) {
			bag.remove(i);
		}
		for (int i = 0; i < 5; ++i) {
			bag.remove(i);
		}
		
		
		print(bag);
		bag.clear();
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(10 - i);
			}
			
		}
		
		print(bag);	
		for (Iterator<Object> it = bag.iterator(); it.hasNext(); ) {
			it.next();
			it.remove();
			print(bag);
		}
		
		
		System.out.println("=------------=");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(9 - i);
		}
		
		bag.addAll(list);
		
		for (Object i : bag) {
			System.out.println(i);
		}

	}
}
