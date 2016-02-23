import java.util.Arrays;
import java.util.Stack;

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
	boolean DEBUG = true;
	char operator;
	int displayValue, operand1;
	Stack<Character> myStack;
	Stack<Integer> numberStack;
	String infix, postfix;

	/*
	 * Create a CalcEngine instance. Initialise its state so that it is ready 
	 * for use.
	 */
	public CalcEngine()
	{
		myStack = new Stack<Character>();
		numberStack = new Stack<Integer>();
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
	public int getDisplayValue()
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
		char nextCh=' ';
		char topOfStack = ' ';
		int iterator = 0;
		String doubleDigit = "|";

		if (DEBUG)System.out.println("The final infix values are:" + infixString);

		while ( !(iterator > infixString.length()-1)) {
			//if there is a character on top of the stack store it.
			if (!myStack.empty()) topOfStack = myStack.peek();

			ch = infixString.charAt(iterator);

			//if the character is a number check to see if the next character is also a number,
			//if it is make a string surrounded by || and add it to the postfixString. If the next
			//character is not a number then add it to postfix string and go to the next character.
			if (ch == '0' || ch == '1' || ch == '2' || ch == '3' ||
					ch == '4' || ch == '5' || ch == '6' || ch == '7' ||
					ch == '8' || ch == '9') {
				//nextCh will be the character that follows ch.
				if (iterator != infixString.length()-1) nextCh = infixString.charAt(iterator + 1);

				if (nextCh == '0' || nextCh == '1' || nextCh == '2' || nextCh == '3' ||
						nextCh == '4' || nextCh == '5' || nextCh == '6' || nextCh == '7' ||
						nextCh == '8' || nextCh == '9') {
					//make the string version of the double digit number enclosed in ||.
					doubleDigit +=ch;
					doubleDigit +=nextCh;
					doubleDigit+="|";

					//add the double digit to the postfix string.
					postfix += doubleDigit;
					iterator +=2;

					if (DEBUG)System.out.println("The values in double digit is: " + doubleDigit);

					//set string back to default
					doubleDigit = "|";
				}
				else {
					postfix += ch;
					iterator ++;
				}
			}
			//if the character is not a number and the stack is empty
			//push it onto the stack then go to the next character.
			else if (myStack.empty() ) {
				myStack.push(ch);
				iterator++;
			}
			//if the character is an opening bracket push it to the stack and go to the next character.
			else if (ch =='(') {
				myStack.push(ch);
				iterator++;
			}
			//if the ch is a closing bracket pop from the stack until topOfStack is the opening bracket.
			else if (ch == ')') {
				iterator++;
				while (topOfStack != '(') {
					topOfStack = myStack.peek();
					if (topOfStack  != '(' )postfix += topOfStack;
					myStack.pop();
				} 
			}
			//if the stack is not empty and the character has a higher priority
			//push it onto the stack then go to the next character.
			else if (!myStack.empty() && (findPriority(ch) > findPriority(topOfStack)) ) {
				myStack.push(ch);
				iterator++; 
			}
			//if the characters priority is less then or equal to the character on top of the stack
			//pop characters off the stack until the stack is empty then push the character 
			//onto the stack.
			else if (findPriority(ch) <= findPriority(topOfStack) ) {
				while (!myStack.empty()) {
					topOfStack = myStack.peek();
					postfix += topOfStack;
					myStack.pop();	
				}
				myStack.push(ch);
				iterator++; 
			}
		}
		//pop the rest of the stack when finished evaluating the characters.
		while (!myStack.empty()) {
			topOfStack = myStack.pop();
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
		int result = 0;
		int operand_1 = 0;
		int operand_2 = 0;
		int iterator = 0;
		String doubleDigit= "";

		while ( !(iterator > postfixString.length()-1)) {
			ch = postfixString.charAt(iterator);
			//if the character is a number push to stack then
			//go to next character.
			if (ch == '0' || ch == '1' || ch == '2' || ch == '3' ||
					ch == '4' || ch == '5' || ch == '6' || ch == '7' ||
					ch == '8' || ch == '9') {
				numberStack.push(Character.getNumericValue(ch));
				iterator ++;
			}
			//if the character is an |, this signals the start of a double digit. Increase iterator by
			//one to get to the first number, then while the character is not a | add the character to 
			//the double digit string and advance to the next character. When the second | is found
			//end the loop, parse the string to an int and push to the stack and reaet the double digit 
			//string to an empty string;
			else if (ch =='|') {
				iterator++;
				ch = postfixString.charAt(iterator);
				while (ch != '|') {
					ch = postfixString.charAt(iterator);
					if (ch != '|')doubleDigit+=ch;
					iterator++;
				}
				if (DEBUG)System.out.println("The values in double digit is: " + doubleDigit);

				numberStack.push(Integer.parseInt(doubleDigit) );
				doubleDigit = "";
			}
			//character will be an operator, pop the last two characters from the stack and
			//pass the operands and operator character to the doMath method. Then push the result
			//onto the stack.
			else {
				if (!numberStack.empty())operand_2 = numberStack.pop();
				if (!numberStack.empty())operand_1 = numberStack.pop();
				result =  doMath(operand_1, operand_2, ch);
				numberStack.push(result); 
				iterator++;
			}
		}
		displayValue = result;
		if (DEBUG)System.out.println("The answer is : " + result);
	}

	/*
	 * This method does all the calcualtions.
	 */
	public int doMath (int num1, int num2, char operator) {

		char currentOperator = operator;
		int operand1 = num1;
		int operand2 = num2;
		int answer = 0;

		if (currentOperator == '+') answer = (operand1 + operand2);
		else if (currentOperator == '-') answer = (operand1 - operand2);
		else if (currentOperator == '*') answer = (operand1 * operand2);
		else if (currentOperator== '/') answer = (operand1 / operand2);
		else if (currentOperator == '^') answer += (int) Math.pow(operand1, operand2);

		if (DEBUG)System.out.println("The value in result is :" + answer);
		return answer;
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