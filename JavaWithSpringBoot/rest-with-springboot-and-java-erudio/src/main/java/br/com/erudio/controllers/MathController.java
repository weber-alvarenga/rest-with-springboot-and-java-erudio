package br.com.erudio.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.utils.SimpleMath;
import br.com.erudio.utils.Util;

@RestController
public class MathController {

	private final AtomicLong counter = new AtomicLong();
	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
					  @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		
		if (!Util.isNumeric(numberOne) || !Util.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.sum(Util.convertToDouble(numberOne), Util.convertToDouble(numberTwo));
		
	}
	
	
	@RequestMapping(value = "/subtract/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtract(@PathVariable(value = "numberOne") String numberOne,
			  @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!Util.isNumeric(numberOne) || !Util.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.subtract(Util.convertToDouble(numberOne), Util.convertToDouble(numberTwo));

	}

	
	@RequestMapping(value = "/multiply/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiply(@PathVariable(value = "numberOne") String numberOne,
			  @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!Util.isNumeric(numberOne) || !Util.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.multiply(Util.convertToDouble(numberOne), Util.convertToDouble(numberTwo));

	}


	@RequestMapping(value = "/divide/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double divide(@PathVariable(value = "numberOne") String numberOne,
			  @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!Util.isNumeric(numberOne) || !Util.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.divide(Util.convertToDouble(numberOne), Util.convertToDouble(numberTwo));

	}

	
	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double mean(@PathVariable(value = "numberOne") String numberOne,
					  @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		
		if (!Util.isNumeric(numberOne) || !Util.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.mean(Util.convertToDouble(numberOne), Util.convertToDouble(numberTwo));
		
	}


	@RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)
	public Double mean(@PathVariable(value = "number") String number) throws Exception {
		
		if (!Util.isNumeric(number)) {
			throw new UnsupportedMathOperationException("Recebido um valor não numérico.");
		}
		
		return math.squareRoot(Util.convertToDouble(number));
		
	}


}
