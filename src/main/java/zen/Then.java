package zen;

import rjson.Rjson;
import rjson.test.NullifyDateTransformer;

public class Then {
	public Then assertThatObjectUnderTestIsNotModified() {
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		String objectUnderTestJsonAfterTestExecution = rjson.toJson(when.given().objectUnderTest());
		if (!objectUnderTestJsonAfterTestExecution.equals(when.given().objectUnderTestJsonBeforeTestExecution()))
			throw new AssertionError();
		return this;
	}

	public Then assertThatObjectUnderTestIsModifiedAs(Object expectedObject) {
		return this;
	}

	public Then assertThatInputParametersAreNotModified() {
		Rjson rjson = Rjson.newInstance().and(new NullifyDateTransformer()).andIgnoreModifiers();
		for(int i = 0; i < when.inputParams().size(); i ++) {
			Object object = when.inputParams().get(i);
			String afterExecutionJson = rjson.toJson(object);
			assertEquals(afterExecutionJson, when.inputParamJsons().get(i));			
		}
		return this;
	}

	public Then assertThatThereAreNoSideEffects() {
		this.assertThatObjectUnderTestIsNotModified();
		this.assertThatInputParametersAreNotModified();
		return this;
	}
	
	public Then assertThatReturnValueIsSameAs(Object expectedObject) {
		assertThatThereAreNoSideEffects();
		assertEquals(expectedObject, returnObject);
		return this;
	}
	
	public Then assertThatReturnJsonIsSameAsJsonFor(Object expectedObject) {
		assertThatThereAreNoSideEffects();
		assertJsonEquals(expectedObject, returnObject);
		return this;
	}

	public static Then thenAssertChanges(When when) {
		Then then = new Then();
		then.when = when;
		return then;
	}
	
	private void assertJsonEquals(Object expectedObject, Object actualObject) {
		Rjson rjson = Rjson.newInstance().with(new NullifyDateTransformer()).andIgnoreModifiers();
		assertEquals(rjson.toJson(expectedObject), rjson.toJson(actualObject));		
	}
	
	private void assertEquals(Object expectedObject, Object actualObject) {
		if(! expectedObject.equals(returnObject)) {
			//String difference = StringUtils.difference(expectedObject.toString(), actualObject.toString());
			AssertionError ae = new AssertionError("expected: <" + expectedObject +"> but was: <" + actualObject + ">");
			throw ae;
		}		
	}

	private When when;
	private Object returnObject;

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	private Then() {
	}
}