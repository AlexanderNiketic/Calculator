package com.niketica.calculator;

import com.niketica.gui.CalculatorFrame;

/**
 * This class performs basic math functions.
 * @author Alexander
 *
 */
public class Calculator {
	
	public Calculator(){
		new CalculatorFrame();
	}
	
	public static double MATH_PLUS(double v1, double v2){
		return v1 + v2;
	}
	
	public static double MATH_MINUS(double v1, double v2){
		return v1 - v2;
	}
	
	public static double MATH_MULTIPLY(double v1, double v2){
		return v1 * v2;
	}
	
	public static double MATH_DIVIDE(double v1, double v2){
		if(v2 == 0){
			return 0;
		}
		
		return v1 / v2;
	}
	
	public static double MATH_SQRT(double v){
		return Math.sqrt(v);
	}
	
	public static void main(String[] args){
		new Calculator();
	}
}
