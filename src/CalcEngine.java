//import java.util.Stack;

/*
 * The main part of the calculator doing the calculations.
 * 
 * @author  M. Kolling 
 * @Edited by Stephen Collins
 * @Febuary 2016
 * @version 0.2
 */
public class CalcEngine
{
	private boolean DEBUG = true;
	private char operator;
	private double displayValue, operand1;
	private MyStack myStack;
	private MyStack numberStack;
	private String infix, postfix;

	/*
	 * Create a CalcEngine instance. Initialise its state so that it is ready 
	 * for use.
	 */
	public CalcEngine()
	{
		myStack = new MyStack();
		numberStack = new MyStack();
		infix = "";
		postfix = "";
		operator =' ';
		displayValue=0;
		operand1 = 0;
	}

	/*
	 * Return the value that should currently be displayed on the calculator
	 * display.
	 */
	public double getDisplayValue()
	{
		return(displayValue);
	}

	/*
	 * A number button was pressed. Do whatever you have to do to handle it.
	 * The number value of the button is given as a parameter.
	 */
	public void numberPressed(int number)
	{
		displayValue = displayValue *10 + number;
		infix += number;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The 'plus' button was pressed. 
	 */
	public void plus()
	{
		operand1 = displayValue;
		displayValue = 0;
		operator = '+';
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The 'minus' button was pressed.
	 */
	public void minus()
	{
		operand1 = displayValue;
		displayValue = 0;
		operator = '-'; 
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	public void multiply()
	{
		operand1 = displayValue;
		displayValue = 0;
		operator = '*'; 
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	public void divide()
	{
		operand1 = displayValue;
		displayValue = 0;
		operator = '/'; 
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The '^' button was pressed.
	 */
	public void toThePowerOf() {
		operand1 = displayValue;
		displayValue = 0;
		operator = '^';
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The '.' button was pressed.
	 */
	public void decimalPoint() {
		operand1 = displayValue;
		displayValue = 0;
		operator = '.';
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The '(' button was pressed.
	 */
	public void openingBracket() {
		operand1 = displayValue;
		displayValue = 0;
		operator = '(';
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The ')' button was pressed.
	 */
	public void closingBracket() {
		operand1 = displayValue;
		displayValue = 0;
		operator = ')';
		infix += operator;
		if (DEBUG)System.out.println("The infix values are:" + infix);
	}

	/*
	 * The '=' button was pressed.
	 */
	public String equals() {
		return infix;
	}

	/*
	 * This method converts an infix expression into a postfix expression.
	 */
	public String convertToPostfix (String infixString) {

		char ch=' ';
		char topOfStack = ' ';
		int iter = 0;
		String delimitedInfix = delimitTheString(infixString);

		if (DEBUG)System.out.println("The final infix values are:" + delimitedInfix);

		while ( !(iter > delimitedInfix.length()-1)) {
			//if there is a character on top of the stack store it.
			if (!myStack.isEmpty()) topOfStack = (char) myStack.peek();

			ch = delimitedInfix.charAt(iter);

			if (ch == '|' || ch == '.' || ( ch>='0' && ch<='9') ) {
				postfix+=ch;
				iter++;
			}
			//if the character is not a number and the stack is empty
			//push it onto the stack then go to the next character.
			else if (myStack.isEmpty() ) {
				myStack.push(ch);
				iter++;
			}
			//if the character is an opening bracket push it to the stack and go to the next character.
			else if (ch =='(') {
				myStack.push(ch);
				iter++;
			}
			//if the ch is a closing bracket pop from the stack until topOfStack is the opening bracket.
			else if (ch == ')') {
				iter++;
				while (topOfStack != '(') {
					topOfStack = (char) myStack.peek();
					if (topOfStack  != '(' )postfix += topOfStack;
					myStack.pop();
				} 
			}
			//if the stack is not empty and the character has a higher priority
			//push it onto the stack then go to the next character.
			else if ( findPriority(ch) > findPriority(topOfStack )){
				myStack.push(ch);
				iter++; 
			}
			//if the characters priority is less then or equal to the character on top of the stack
			//pop characters off the stack until the stack is empty then push the character 
			//onto the stack.
			else if (findPriority(ch) <= findPriority(topOfStack) ) {
				while (!myStack.isEmpty()) {
					topOfStack = (char) myStack.peek();
					postfix += topOfStack;
					myStack.pop();	
				}
				myStack.push(ch);
				iter++; 
			}
		}
		//pop the rest of the stack when finished evaluating the characters.
		while (!myStack.isEmpty()) {
			topOfStack = (char) myStack.pop();
			postfix += topOfStack;
		}

		if (DEBUG)System.out.println("The postfix values are :" + postfix);
		return postfix;
	}

	/*
	 * This method evalates a postfix expression.
	 */
	public void evaluatePostfix(String postfixString) {

		char ch = ' ';
		double result = 0;
		double operand_1 = 0;
		double operand_2 = 0;
		int iter = 0;
		String number= "";

		while ( !(iter > postfixString.length()-1)) {
			ch = postfixString.charAt(iter);
			//if the character is an |, this signals the start of a number. Increase iterator by
			//one to get to the first number, then while the character is not a | add the character to 
			//the number string and advance to the next character. When the second | is found
			//end the loop, parse the string to an double and push to the stack and reset the double digit 
			//string to an empty string.
			if (ch =='|') {
				iter++;
				ch = postfixString.charAt(iter);
				while (ch != '|') {
					if(!(iter > postfixString.length()-1)) ch = postfixString.charAt(iter);
					if (ch != '|') number+=ch;
					iter++;
				}
				if (DEBUG)System.out.println("The values in number is: " + number);

				numberStack.push(Double.parseDouble(number));
				number = "";
			}
			//character will be an operator, pop the last two characters from the stack and
			//pass the operands and operator character to the doMath method. Then push the result
			//onto the stack.
			else {
				if (!numberStack.isEmpty())operand_2 = (double) numberStack.pop();
				if (!numberStack.isEmpty())operand_1 = (double) numberStack.pop();
				result =  doMath(operand_1, operand_2, ch);
				numberStack.push(result); 
				iter++;
			}
		}
		displayValue = result;
		if (DEBUG)System.out.println("The answer is : " + result);
	}

	/*
	 * This method does all the calcualtions.
	 */
	public double doMath (double num1, double num2, char operator) {

		char currentOperator = operator;
		double operand1 = num1;
		double operand2 = num2;
		double answer = 0;

		if (currentOperator == '+') answer = (operand1 + operand2);
		else if (currentOperator == '-') answer = (operand1 - operand2);
		else if (currentOperator == '*') answer = (operand1 * operand2);
		else if (currentOperator== '/') answer = (operand1 / operand2);
		else if (currentOperator == '^') answer += Math.pow(operand1, operand2);

		if (DEBUG)System.out.println("The value in result is :" + answer);
		return  answer;
	}

	/*
	 * This method returns an integer value that determines the priority of the operator.
	 */
	public int findPriority(char operator) {
		if ( operator == '^') {
			return 80;
		}
		else if ( operator == '*' || operator == '/' ) {
			return 60;
		}
		else if ( operator == '+' || operator == '-' ) {
			return 40;
		}
		else
			return 0;
	}
	/*
	 * This method takes in a string of numbers and operators and adds delimiters
	 * around the numbers.
	 */
	public String delimitTheString(String stringToDelimit) {
		char delimiter = '|';
		char ch = ' ';
		String numberString = "|";
		String delimitedString = "";

		for (int iter = 0; iter <= stringToDelimit.length()-1; iter++) {
			ch = stringToDelimit.charAt(iter);
			//if ch is not an operand add it to the numberString.
			if ( (ch >= '0' && ch <= '9') || ch=='.') {
				numberString+=ch;
			}
			else if (ch == '(') {
				delimitedString+=ch;
			}
			else if (ch == ')') {
				numberString+= "|";
				numberString+=ch;
				delimitedString+=numberString;
				numberString = "|";
			}
			//ch is an operator but there is nothing in numberstring so just add
			//the ch to the delimitedString.
			else if (numberString.length()==1){
				delimitedString+=ch;
			}
			//ch is an operator, add a delimiter to the numberString,
			//add the numberString and the operator to the delimitedString then 
			//set the numberString back to it's default state.
			else {
				numberString+=delimiter;
				delimitedString+=numberString;
				delimitedString+=ch;
				numberString = "|";
			}
		}

		if (DEBUG)System.out.println(delimitedString);
		return delimitedString;
	}

	/*
	 * The 'C' (clear) button was pressed.
	 */
	public void clear()
	{
		infix = "";
		postfix = "";
		displayValue = 0;
		operand1 = 0;

	}

	/*
	 * Return the title of this calculation engine.
	 */
	public String getTitle()
	{
		return("My Calculator");
	}

	/*
	 * Return the author of this engine. This string is displayed as it is,
	 * so it should say something like "Written by H. Simpson".
	 */
	public String getAuthor()
	{
		return("Stephen Collins");
	}

	/*
	 * Return the version number of this engine. This string is displayed as 
	 * it is, so it should say something like "Version 1.1".
	 */
	public String getVersion()
	{
		return("Ver. 1.0");
	}

}