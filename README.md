ZEN
======

Description
===========
THIS IS IN TRIAL
Given When Then based unit testing framework

Usage
===
public class ToJsonTest {
	private Given given;
	private When when;

	@Before
	public void setup() {
		given = Given.objectUnderTestIs(RjsonUtil.completeSerializer());
		when = given.when("toJson");
	}

	@Test
	public void toJsonPrimitiveInt() throws IOException {
		when.methodIsCalledWith(123).assertThatReturnValueIsSameAs("123");
	}
}

What is the purpose?
====================
Unit test's written using the existing frameworks like junit and testng allow for the code modifications without tests and does not catch
side effects. For instance the test method would not test if the object under test is being modified or if the input parameters are being 
modified unless those are asserted explicitly. Zen aims to catch such missing scenarios.
