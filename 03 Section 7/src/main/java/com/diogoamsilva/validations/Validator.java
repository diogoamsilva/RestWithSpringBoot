package com.diogoamsilva.validations;

import com.diogoamsilva.exception.UnsuportedMathOperationException;
import com.diogoamsilva.utils.MathUtils;

public class Validator {

	public static void checkIfIsNumeric(String numberOne, String numberTwo) {
		checkIfIsNumeric(numberOne);
		checkIfIsNumeric(numberTwo);
	}
	
	public static void checkIfIsNumeric(String number) {
		if(!MathUtils.isNumeric(number)) {
			throw new UnsuportedMathOperationException("Please set a numeric value");
		}
	}
}
