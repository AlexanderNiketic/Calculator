package com.niketica.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.niketica.calculator.Calculator;

/**
 * This is the GUI part of the Calculator. The basic JFrame is set up and user input is handled in this class.
 * @author Alexander
 *
 */
public class CalculatorFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int NUMBER_OF_BUTTONS = 20;
	private HashMap<Integer, CalculatorButton> buttonMap;
	private JTextField currentCalculationField;
	private DisplayField currentNumberField;
	private String currentCalculationString = "";
	private Double currentNumberValue, previousNumberValue = 0.0;
	private boolean decimalMode, addOntoCurrentNumber = false;
	private FunctionType currentFunction;
	
	public CalculatorFrame(){
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setButtonMap();
		initLayout();
		
		setVisible(true);
	}
	
	/**
	 * The calculator consists of two panels, one for display and one for buttons.
	 */
	private void initLayout(){		
		JPanel container = new JPanel(new GridLayout(2,1));
		
		JPanel display = createDisplay();		
		JPanel buttons = createButtons();
		
		container.add(display);
		container.add(buttons);
		
		add(container);
	}
	
	/**
	 * Creates a JPanel with the display of the current number and the current calculation.
	 * @return A JPanel containing the display of the current number and the current calculation.
	 */
	private JPanel createDisplay(){
		JPanel display = new JPanel(new GridLayout(2,1));
		
		currentCalculationField = new JTextField();
		currentCalculationField.setEditable(false);
		
		currentNumberField = new DisplayField(new Font("Courier New", Font.BOLD, 40));

		currentCalculationField.setText("");
		currentNumberField.setText("");
		
		display.add(currentCalculationField);
		display.add(currentNumberField);
		
		return display;
	}
	
	/**
	 * Creates a JPanel with the calculator buttons.
	 * @return A JPanel containing the calculator buttons.
	 */
	private JPanel createButtons(){
		JPanel buttons = new JPanel(new GridLayout(5,4));
		
		for(int i=0 ; i<NUMBER_OF_BUTTONS ; i++){
			CalculatorButton button = buttonMap.get(i);
		    buttons.add(button);
		    button.addActionListener(this);
		}
		
		return buttons;
	}
	
	/**
	 * Maps the different calculator buttons with the corresponding index on the Grid.
	 */
	private void setButtonMap(){
		buttonMap = new HashMap<Integer, CalculatorButton>();
		
		buttonMap.put(17, new CalculatorButton(ButtonType.NUMBER, 0));
		buttonMap.put(4,  new CalculatorButton(ButtonType.NUMBER, 1));
		buttonMap.put(5,  new CalculatorButton(ButtonType.NUMBER, 2));
		buttonMap.put(6,  new CalculatorButton(ButtonType.NUMBER, 3));
		buttonMap.put(8,  new CalculatorButton(ButtonType.NUMBER, 4));
		buttonMap.put(9,  new CalculatorButton(ButtonType.NUMBER, 5));
		buttonMap.put(10, new CalculatorButton(ButtonType.NUMBER, 6));
		buttonMap.put(12, new CalculatorButton(ButtonType.NUMBER, 7));
		buttonMap.put(13, new CalculatorButton(ButtonType.NUMBER, 8));
		buttonMap.put(14, new CalculatorButton(ButtonType.NUMBER, 9));
		
		buttonMap.put(18, new CalculatorButton(ButtonType.FUNCTION, FunctionType.POS_NEG));
		buttonMap.put(16, new CalculatorButton(ButtonType.FUNCTION, FunctionType.DECIMAL));
		buttonMap.put(19, new CalculatorButton(ButtonType.FUNCTION, FunctionType.EQUALS));
		buttonMap.put(0,  new CalculatorButton(ButtonType.FUNCTION, FunctionType.C));
		buttonMap.put(1,  new CalculatorButton(ButtonType.FUNCTION, FunctionType.BACKSPACE));
		buttonMap.put(15, new CalculatorButton(ButtonType.FUNCTION, FunctionType.PLUS));
		buttonMap.put(11, new CalculatorButton(ButtonType.FUNCTION, FunctionType.MINUS));
		buttonMap.put(7,  new CalculatorButton(ButtonType.FUNCTION, FunctionType.MULTIPLY));
		buttonMap.put(3,  new CalculatorButton(ButtonType.FUNCTION, FunctionType.DIVIDE));
		buttonMap.put(2,  new CalculatorButton(ButtonType.FUNCTION, FunctionType.SQRT));
	}

	/**
	 * This function determines whether the user has pressed on a number or a function and handles the event.
	 * After the event is handles, the display will be updated.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof CalculatorButton){
			CalculatorButton b = (CalculatorButton) e.getSource();
			
			switch(b.getType()){
			case NUMBER:
				pressedNumber(b.getValue());
				break;
			case FUNCTION:
				pressedFunction(b.getFunction(), b.getText());
				break;
			default:
				currentNumberField.setText("ERROR");
			}

			currentNumberField.setText(formatNumber(currentNumberValue));
			currentCalculationField.setText(currentCalculationString);
		}
	}
	
	/**
	 * This function handles the event when a user presses a number on the calculator.
	 * @param value The number that was pressed on the calculator.
	 */
	private void pressedNumber(int value){
		if(!decimalMode){
			if(!addOntoCurrentNumber){
				currentNumberValue = (double) value;
				addOntoCurrentNumber = true;
			}else{
				currentNumberValue *= 10;
				
				if(currentNumberValue >= 0){
					currentNumberValue += value;
				}else{
					currentNumberValue -= value;					
				}
			}
		}else{			
			if(!addOntoCurrentNumber){
				currentNumberValue = value * 0.1;
				addOntoCurrentNumber = true;
			}else{
				String stringValue = formatNumber(currentNumberValue);
				if(stringValue.indexOf('.') < 0){
					stringValue += '.';
				}				
				stringValue += value;
				
				currentNumberValue = Double.parseDouble(stringValue);
			}
		}
		
		currentNumberField.setText(formatNumber(currentNumberValue));
	}
	
	/**
	 * This function handles the event when a user presses on a button that is identified as a function.
	 * @param type The type of function that is pressed.
	 * @param mathString The type of function in String format.
	 */
	private void pressedFunction(FunctionType type, String mathString){
		decimalMode = (type == FunctionType.DECIMAL);
		
		switch(type){
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
			currentCalculationString += formatNumber(currentNumberValue) + " " + mathString + " ";
			Double tempValue = currentNumberValue;
			if(currentFunction == FunctionType.PLUS
				|| currentFunction == FunctionType.MINUS
				|| currentFunction == FunctionType.MULTIPLY
				|| currentFunction == FunctionType.DIVIDE){
				doMath();
			}
			currentFunction = type;
			previousNumberValue = tempValue;
			addOntoCurrentNumber = false;
			break;
		case SQRT:
			currentCalculationString += formatNumber(currentNumberValue) + " " + mathString + " ";
			currentFunction = type;
			addOntoCurrentNumber = false;
			doMath();
			break;
		case EQUALS:
			currentCalculationString = " ";
			doMath();
			currentFunction = type;
			addOntoCurrentNumber = false;
			break;
		case C:
			clear();
			break;
		case POS_NEG:
			posNeg();
			break;
		default:
			//Do nothing.
		}
	}
	
	/**
	 * This function performs a math operation in the Calculator class.
	 */
	private void doMath(){
		switch(currentFunction){
		case PLUS:
			currentNumberValue = Calculator.MATH_PLUS(previousNumberValue, currentNumberValue);
			break;
		case MINUS:
			currentNumberValue = Calculator.MATH_MINUS(previousNumberValue, currentNumberValue);
			break;
		case MULTIPLY:
			currentNumberValue = Calculator.MATH_MULTIPLY(previousNumberValue, currentNumberValue);
			break;
		case DIVIDE:
			currentNumberValue = Calculator.MATH_DIVIDE(previousNumberValue, currentNumberValue);
			break;
		case SQRT:
			currentNumberValue = Calculator.MATH_SQRT(currentNumberValue);
			break;
		default:
			//Do nothing.
		}
	}
	
	/**
	 * Clear the display screen and stored values.
	 */
	private void clear(){
		previousNumberValue = 0.0;
		currentNumberValue = 0.0;
		currentFunction = null;
		currentCalculationString = "";
	}
	
	/**
	 * Make the current number positive or negative.
	 */
	private void posNeg(){
		if(currentNumberValue != null){
			currentNumberValue *= -1;
		}
	}
	
	/**
	 * This function removes any unnecessary zeros from a given double.
	 * @param value The double to be formatted.
	 * @return The formatted double.
	 */
	private String formatNumber(Double value){
		if(value == null){
			return "0";
		}
		
		DecimalFormat format = new DecimalFormat("0.##########");
		return format.format(value);
	}

}
