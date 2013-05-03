package ru.ifmo.ctddev.evdokimov.task5;

public class Invoker {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Invoker <full-class-name> <method-name> <method-arg...>");
			return;
		}
	}
}
