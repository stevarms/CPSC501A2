import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.junit.Test;


public class InspectorTest extends TestCase{
    Inspector testInspector;
    Object obj;
    Class testClass;
    
    public InspectorTest()
    {
            testInspector = new Inspector();
            obj = new ClassA();
            testClass = obj.getClass();
    }
    
	@Test
	public void testInspectMethods() {
		Inspector testInspector = new Inspector();
		Object obj = new ClassA();
		Method[] methods = obj.getClass().getMethods();
		int result = methods.length;
		assertEquals(result,12);
	}
	
	@Test
	public void testInspectConstructor(){
		Inspector testInspector = new Inspector();
		Object obj = new ClassA();
		Constructor[] constructors = obj.getClass().getConstructors();
		int result = constructors.length;
		assertEquals(result,2);
		assertEquals(constructors[0].toString(), "public ClassA()");
		assertEquals(constructors[1].toString(), "public ClassA(int)");
	}
	
	@Test
	public void testInspectSuperclass(){
		Inspector testInspector = new Inspector();
		Object obj = new ClassA();
		Class objClass = obj.getClass();
		Class superclass = objClass.getSuperclass();
		assertEquals(superclass.toString(),"class java.lang.Object");
	}

}
