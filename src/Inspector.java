/* -----------------------------------------------------------------------------------------------------------
 * CPSC 501 Assignment 2
 * Stephen Armstrong
 * 00306924
 * Methods inspect, inspectFieldClasses, and inspectFields based off of Jordan Kidney's ObjectInspector.java.
 * -----------------------------------------------------------------------------------------------------------*/

import java.util.*;
import java.lang.reflect.*;

public class Inspector {
	public Inspector() {
	}

	/*
	 * inspects the object returning information about methods, constructor,
	 * interfaces, fields and the super class. If recursive is set to true then
	 * the function will also inspect the superclass
	 */
	public void inspect(Object obj, boolean recursive) {
		Vector objectsToInspect = new Vector();
		Class objClass = obj.getClass();

		System.out.println("inside inspector: "
				+ obj.getClass().getSimpleName() + " (recursive = " + recursive
				+ ")");
		System.out.println("Name of declaring class: "
				+ objClass.getSimpleName());
		System.out.println("Name of immediate superclass: "
				+ objClass.getSuperclass().getSimpleName());

		inspectInterfaces(obj, objClass);
		// inspectMethods(obj, objClass);
		inspectConstructor(obj, objClass);

		if ((objClass.getSuperclass() != null)
				&& (objClass.getSuperclass() != Object.class)) {
			 inspectSuperclass(obj, objClass, objectsToInspect);
		}

		inspectFields(obj, objClass, objectsToInspect);

		if (recursive)
			inspectFieldClasses(obj, objClass, objectsToInspect, recursive);
	}
	
	private void inspectInterfaces(Object obj, Class objClass) {
		System.out.println();
		System.out.println("Inspecting '"+objClass.getSimpleName()+"' Interfaces:");
		Class[] interfaces = objClass.getInterfaces();
		if (interfaces.length > 0){
			System.out.println(interfaces.length + " Interface(s) found");
			for (int i = 0;i<interfaces.length;i++){
				System.out.println();
				System.out.println("Interface: "+interfaces[i].getName());
				System.out.println("Inspecting interface '"+interfaces[i].getSimpleName()+"':");
				//inspectMethods(obj,interfaces[i]);
				inspectConstructor(obj,interfaces[i]);
			}
		}else{
			System.out.println("No interfaces found");
		}
	}

	/*
	 * Inspects the constructor(s) of an object
	 */
	private void inspectConstructor(Object obj, Class objClass) {
		System.out.println();
		System.out.println("Inspecting '"+objClass.getSimpleName()+"' Constructors:");
		Constructor[] constructors = objClass.getConstructors();
		if (constructors.length > 0){
			System.out.println(constructors.length+" Constructor(s) Detected");
			for (int i=0;i<constructors.length;i++){
				System.out.println("Constructor: "+constructors[i].toString());
			}
		}else{
			System.out.println("No constructors Detected");
		}
	}
	
	/*
	 * Inspects the superclass of an object
	 */
	private void inspectSuperclass(Object obj, Class objClass,
			Vector objectsToInspect) {
		System.out.println();
		System.out.println("Inspecting '"+objClass.getSimpleName()+"' Superclasses:");
		Class superclass = objClass.getSuperclass();
        //inspectMethods(obj, superclass);
        inspectConstructor(obj, superclass);
        inspectFields(obj, superclass, new Vector());
	}
	
	/*
	 * Inspects the fields of the class. prints the number of fields detected,
	 * their value, their type and whether it is private, public etc.
	 */
	private void inspectFields(Object obj, Class objClass,
			Vector objectsToInspect) {
		System.out.println();
		System.out.println("Inspecting '" + objClass.getSimpleName()
				+ "' Fields:");
		if (objClass.getDeclaredFields().length >= 1) {
			Field[] fields = objClass.getDeclaredFields();
			System.out.println(fields.length + " Fields detected");
			for (int i = 0; i < fields.length; i++) {
				Field aField = fields[i];
				aField.setAccessible(true);
				if (!aField.getType().isPrimitive())
					objectsToInspect.addElement(aField);

				try {
					if (aField.getType().isArray()) {
						System.out.println("Field: '" + aField.getName()
								+ "' [Type: "
								+ aField.getType().getComponentType()
								+ ", Modifier: "
								+ Modifier.toString(aField.getModifiers())
								+ "]");
					} else {
						System.out.println("Field: '" + aField.getName() + "' = "
								+ aField.get(obj) + " [Type: "
								+ aField.getType() + ", Modifier: "
								+ Modifier.toString(aField.getModifiers())
								+ "]");
					}
				} catch (Exception e) {
				}
			}
		} else {
			System.out.println("No fields detected");
		}

		if (objClass.getSuperclass() != null)
			inspectFields(obj, objClass.getSuperclass(), objectsToInspect);
	}

	/*
	 * Inspects object's field's which are also objects.
	 */
	private void inspectFieldClasses(Object obj, Class objClass,
			Vector objectsToInspect, boolean recursive) {

		if (objectsToInspect.size() > 0)
			System.out.println("Inspecting '" + objClass.getSimpleName() + "' Field Classes:");

		Enumeration e = objectsToInspect.elements();
		while (e.hasMoreElements()) {
			Field f = (Field) e.nextElement();
			System.out.println("Inspecting Field: '" + f.getName()+"'");

			try {
				System.out
						.println("******************************************************");
				inspect(f.get(obj), recursive);
				System.out
						.println("******************************************************");
			} catch (NullPointerException nullExp) {
				System.out.println("Field not instantiated at runtime");
				System.out
						.println("******************************************************");
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
}
