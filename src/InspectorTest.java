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
        methods = obj.getClass().getDeclaredMethods();
        result = methods.length;
        assertEquals( result, 5);
       
        String str = methods[0].getName();
        assertEquals(str,"run");
       
        result = methods[0].getModifiers();
        assertEquals(result,1);
       
        str = methods[0].getReturnType().getSimpleName();
        assertEquals(str,"void");
       
		Class[] parameters = methods[0].getParameterTypes();
		String params = "";
		if (parameters.length == 0)
			params = "none";
		else
			for (Class aParam : parameters) {
				params += aParam.getSimpleName() + " ";
			}
        assertEquals(params,"none");
       
		Class[] exceptions = methods[0].getExceptionTypes();
		String except = "";
		if (exceptions.length == 0)
			except = "none";
		else
			for (Class aException : exceptions) {
				except += aException.getSimpleName() + " ";
			}
		assertEquals(except,"none");
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

	@Test
    public void testInspectInterfaces()
    {
            Class[] interfaces = testClass.getInterfaces();
            assertEquals(interfaces.length,2);
            assertEquals(interfaces[0].getSimpleName(),"Serializable");
    }
}
