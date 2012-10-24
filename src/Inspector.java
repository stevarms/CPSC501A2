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
		Class ObjClass = obj.getClass();

		System.out.println("inside inspector: "
				+ obj.getClass().getSimpleName() + " (recursive = " + recursive
				+ ")");
		System.out.println("Name of declaring class: "
				+ ObjClass.getSimpleName());
		System.out.println("Name of immediate superclass: "
				+ ObjClass.getSuperclass().getSimpleName());

		// inspectInterfaces(obj, ObjClass);
		// inspectMethods(obj, ObjClass);
		// inspectConstructor(obj, ObjClass);

		if ((ObjClass.getSuperclass() != null)
				&& (ObjClass.getSuperclass() != Object.class)) {
			// inspectSuperclass(obj, ObjClass, objectsToInspect);
		}

		inspectFields(obj, ObjClass, objectsToInspect);

		if (recursive)
			inspectFieldClasses(obj, ObjClass, objectsToInspect, recursive);
	}

	/*
	 * Inspects object's field's superclass.
	 */
	private void inspectFieldClasses(Object obj, Class ObjClass,
			Vector objectsToInspect, boolean recursive) {

		if (objectsToInspect.size() > 0)
			System.out.println("Inspecting Field Classes:");

		Enumeration e = objectsToInspect.elements();
		while (e.hasMoreElements()) {
			Field f = (Field) e.nextElement();
			System.out.println("Inspecting Field: " + f.getName());

			try {
				System.out.println("******************************************************");
				inspect(f.get(obj), recursive);
				System.out.println("******************************************************");
			} catch (NullPointerException nullExp) {
				System.out.println("Field not instantiated at runtime");
				System.out.println("******************************************************");
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	/*
	 * Inspects the fields of the class. prints the number of fields detected,
	 * their value, their type and whether it is private, public etc.
	 */
	private void inspectFields(Object obj, Class ObjClass,
			Vector objectsToInspect)

	{
		System.out.println("Inspecting " + ObjClass.getSimpleName()
				+ " Fields:");
		if (ObjClass.getDeclaredFields().length >= 1) {
			Field[] fields = ObjClass.getDeclaredFields();
			System.out.println(fields.length + " Fields detected");
			for (int i = 0; i < fields.length; i++) {
				Field aField = fields[i];
				aField.setAccessible(true);
				if (!aField.getType().isPrimitive())
					objectsToInspect.addElement(aField);

				try {
					if (aField.getType().isArray()) {
						System.out.println("Field: " + aField.getName()
								+ " [Type: "
								+ aField.getType().getComponentType()
								+ ", Modifier: "
								+ Modifier.toString(aField.getModifiers())
								+ "]");
					} else {
						System.out.println("Field: " + aField.getName() + " = "
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

		if (ObjClass.getSuperclass() != null)
			inspectFields(obj, ObjClass.getSuperclass(), objectsToInspect);
	}
}
