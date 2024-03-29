package ru.ifmo.ctddev.evdokimov.task5;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Invoker {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Invoker <full-class-name> <method-name> <method-arg...>");
			return;
		}
		
		String className = args[0];
		String methodName = args[1];
		
		Object[] newArgs = Arrays.copyOfRange(args, 2, args.length);
		
		try {
			URL jar = null;
			try {
				jar = new File(".").toURI().toURL();
			} catch (MalformedURLException ignore) {
			}

			ClassLoader cl = new URLClassLoader(new URL[]{jar});
			Class<?> c = cl.loadClass(className);
			
			int mod = c.getModifiers();
			
			if (Modifier.isInterface(mod)) {
				System.out.println(String.format("Class %s is interface", className));
			} else if (Modifier.isAbstract(mod)) {
				System.out.println(String.format("Class %s is abstract", className));
			} else {
				Object loadClass;
				try {
					loadClass = c.newInstance();
				} catch (InstantiationException e) {
					System.out.format("Class %s don't have ctor", className);
					e.printStackTrace();
					return;
				} catch (IllegalAccessException e) {
					System.out.println(String.format("Class %s or its nullary ctor is not accessible", className));
					e.printStackTrace();
					return;
				}
				
				Set<List<Class<?>>> calledMethods = new HashSet<>();
				
				for (Method method : c.getMethods()) {
					if (method.getName().equals(methodName)) {
						Class<?>[] ps = method.getParameterTypes();
						if (calledMethods.contains(Arrays.asList(ps)))
							continue;
						
						boolean correctArgs = false;
						if (newArgs.length == ps.length) {
							correctArgs = true;
							for (int i = 0; i < ps.length; i++) {
								if (!ps[i].isAssignableFrom(String.class)) {
									correctArgs = false;
									break;
								}
							}
						}
						
						if (!correctArgs) {
							continue;
						}
	
						calledMethods.add(Arrays.asList(ps));
						
						System.out.println(method);
						
						try {
							method.invoke(loadClass, newArgs);
							System.out.println(loadClass);
						} catch (IllegalAccessException ignore) {
							ignore.printStackTrace();
						} catch (IllegalArgumentException ignore) {
							ignore.printStackTrace();
						} catch (InvocationTargetException e) {
							System.out.println(String.format("Method %s throws an exception", method));
							e.printStackTrace();
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println(String.format("Class %s not found", className));
		}
	}
}
