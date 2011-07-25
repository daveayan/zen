package zen;

import rjson.Rjson;
import zen.rjson.NullifyDateTransformer;

public class Given {
	public static Given classUnderTestIs(Class<?> classUnderTest) {
		Given given = new Given();
		given.classUnderTest = classUnderTest;
		return given;
	}

	public static Given objectUnderTestIs(Object objectUnderTest) {
		Given given = new Given();
		given.objectUnderTest = objectUnderTest;
		given.classUnderTest = objectUnderTest.getClass();
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		given.objectUnderTestJsonBeforeTestExecution = rjson.toJson(objectUnderTest);
		return given;
	}

	public When when(String methodName) {
		When when = When.methodCalledIs(this, methodName);
		return when;
	}

	private Class<?> classUnderTest;
	private Object objectUnderTest;
	private String objectUnderTestJsonBeforeTestExecution;

	public String objectUnderTestJsonBeforeTestExecution() {
		return objectUnderTestJsonBeforeTestExecution;
	}

	public Object objectUnderTest() {
		return objectUnderTest;
	}

	public Class<?> classUnderTest() {
		return classUnderTest;
	}

	private Given() {
	}
}