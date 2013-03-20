package ru.ifmo.ctddev.evdokimov.task3;

import java.util.Iterator;
import java.util.Random;

public class Main {
	public static void printBag(Bag bag) {
		String s = "(";
		for (Object i : bag) {
			s += i + ", ";
		}
		s += ")";
		System.out.println(s);
	}
	
	
	public static void main(String[] args) {
		Bag bag = new Bag();
		Random random = new Random();
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(i);
			}
			
		}
		
		printBag(bag);	
		for (Iterator<Object> it = bag.iterator(); it.hasNext(); ) {
			it.next();
			it.remove();
			printBag(bag);
		}
		

	}
}
