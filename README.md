ZEN
===

Description
===========
THIS IS IN TRIAL
Given When Then based unit testing framework

Another framework?
==================
Zen does not replace junit or testng or anyother framework of choice for unit testing java programs. It can be used along with any of these frameworks. Zen is aimed towards filling in a gap in the TDD practice left by these frameworks.

What is wrong with junit or testng?
===================================
junit and testng are very good unit testing frameworks. Coupled with good mocking framework they are very powerful in TDD practice. However these frameworks don't catch any unintended side effects that the method under test may cause. For example:
We have this class and a method within it:
public class Person() {
	List<Address> list_of_addresses = new ArrayList<Address>();
	public Address getPrefferedAddress() { 
		...
		return address;
	}
}
public class PersonTest() {
	@Test public void testGetPrefferedAddress() {
		Person person = ... // some person object is created here with a list of addresses;
		Address expectedAddress = ... // the preferred address object that is expected;
		Address actualAddress = person.getPrefferedAddress();
		Assert.assertEquals(expectedAddress, actualAddress);
	}
}

Now this test method will assert on the return value and that satisfies the test. Now this good developer moves on and a new, less TDD experienced developer comes on to the project and decides to modify Person class something like this but never cares about the unit test case:
public class Person() {
	List<Address> list_of_addresses = new ArrayList<Address>();
	Address preferred_address = null;
	public Address getPrefferedAddress() { 
		if(preferred_address != null) return preferred_address;
		...
		preferred_address = address;
		return preferred_address;
	}
}

With this change the existing unit test case would still pass and the new developer will be happy to move on. However this is a design change that clearly needs to be caught for lack of unit test cases. The current unit testing frameworks don't do that.

Hmmm, So how does Zen handle this?
==================================
Zen comes with 3 classes that provide a Given, When, Then kind of a structure to unit test cases. With Zen the method under test is not called directly. Instead we instruct Zen to call the method. In the example above this is how we would write the unit test case:
public class PersonTest() {
	@Test public void testGetPrefferedAddress() {
		Person person = ... // some person object is created here with a list of addresses;
		Address expectedAddress = ... // the preferred address object that is expected;
		
		Given given = Given.objectUnderTestIs(person);
		When when = given.when("getPrefferedAddress");
		Then then = when.methodIsCalled();
		
		Assert.assertEquals(expectedAddress, then.getReturnObject());
	}
}
By doing the above the When class of the Zen framework will call the method using reflection and then get the return object. However before the method is called Zen would record the state of method under test. After the execution of the method completes Zen would compare the latest object under test and all input parameters with the recorded state. By default it is assumed by Zen that these are not modified. If Zen, however, finds that the object is modified (which would happen after the Person class is modified by the new developer) Zen would complain. There is also a way to instruct Zen to expect object under test to be modified.

This same mechanism also applies to all the method input parameters to ensure that the input parameters are not modified by the code.

Usage
=====
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