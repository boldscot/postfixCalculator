/*
 * Implementation of a stack using an array of objects.
 * 
 * @author Stephen Collins
 * @Febuary 2016
 * @version 0.1
 */
public class MyStack {

	private static final int DEFAULT_INITIAL_CAPACITY = 100;  
	private Object stackArray[]; 
	private int topOfStack; 

	public MyStack () {
		this (DEFAULT_INITIAL_CAPACITY);
	}

	/*
	 * initialize stack to a max size of 100, set top of stack to -1.
	 */
	public MyStack (int initialCapacity) {
		stackArray = (Object []) new Object [initialCapacity];
		topOfStack = -1;
	}

	/*
	 * This is the push method, if the topOfStack is less then the stack size -1,
	 * increment topOfStack(will be 0 for the first push) then add the object to the stackArray 
	 * at that index.
	 */
	public void push(Object pushedObject) {  
		if(topOfStack < stackArray.length){	             
			topOfStack++;
			stackArray[topOfStack] = pushedObject; 
		}
		else System.out.println("Stack Overflow");
	}

	public Object pop () {
		Object top = null;
		if (!isEmpty () ) {
			top = stackArray [topOfStack];
			stackArray [topOfStack] = null;
			topOfStack--;
		}
		return top;
	}

	/*
	 * This is the peek method, returns the object at the top of the stack or
	 * null if it's empty.
	 */
	public Object peek () {
		Object top = null;
		if (!isEmpty ())
			top = stackArray [topOfStack];
		return top;
	}

	/*
	 * This method returns true when the topOfStack is -1.
	 */
	public boolean isEmpty (){
		return topOfStack < 0;
	}


}
