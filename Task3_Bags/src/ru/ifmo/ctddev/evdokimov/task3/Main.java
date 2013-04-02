package ru.ifmo.ctddev.evdokimov.task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;


public class Main {
	public static void main(String[] args) {
		Bag bag = new Bag();
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(i);
			}
			
		}
		
		System.out.println(bag);
		
		for (int i = 0; i < 5; ++i) {
			bag.remove(i);
		}
		System.out.println(bag);
		for (int i = 0; i < 5; ++i) {
			bag.remove(i);
		}
		
		
		System.out.println(bag);
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(i);
			}
			
		}
		System.out.println(bag);
		System.out.println("=------------=");
		bag.clear();
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				bag.add(10 - i);
			}
			
		}
		
		System.out.println(bag);	
		for (Iterator<Object> it = bag.iterator(); it.hasNext(); ) {
			it.next();
			it.remove();
			System.out.println(bag);
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
