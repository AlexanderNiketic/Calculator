package com.niketica.gui;

import javax.swing.JButton;

/**
 * The CalculatorButton extends the JButton and has a ButtonType attribute to identify it's purpose.
 * @author Alexander
 *
 */
public class CalculatorButton extends JButton {
	private static final long serialVersionUID = 1L;
	private ButtonType type;
	private int value;
	private FunctionType function;
	
	public CalculatorButton(ButtonType type, int value){
		this.type = type;
		this.value = value;
		
		setText(value + "");
	}
	
	public CalculatorButton(ButtonType type, FunctionType function){
		this.type = type;
		this.function = function;
		setText(getFunctionText());
	}
	
	public ButtonType getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public FunctionType getFunction() {
		return function;
	}
	
	private String getFunctionText(){
		switch(function){
		case POS_NEG:
			return "+/-";
		case EQUALS:
			return "=";
		case DECIMAL:
			return ".";
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case MULTIPLY:
			return "*";
		case DIVIDE:
			return ":";
		case SQRT:
			return "V";
		case BACKSPACE:
			return "<";
		case C:
			return "C";
		default:
			return "?";
		}
	}
}
