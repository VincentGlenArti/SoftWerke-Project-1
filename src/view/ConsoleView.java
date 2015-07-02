package view;

import exceptions.*;
import java.util.*;
import java.io.*;

import controller.parsing.InputController;

/**
 * Class used to accept user input and show back formated output.
 *
 * @version b.3
 * @author	Boris Gordeev
 * @since 02-07-2015
 */

public class ConsoleView {
	
	private InputController controller;
	
	public ConsoleView(InputController controller) {
		this.controller = controller;
	}

	/**
	 * Reads command line in a cycle and decides what to do with it.
	 */
	public void commandLineView() {
		System.out.println("Type -help for help or -exit to quit.");
		BufferedReader bs;
        String commandLineInput;
        boolean continueWorking = true;
		while (continueWorking) {
			bs = new BufferedReader(new InputStreamReader(System.in));
	        try {
	        	commandLineInput = bs.readLine();
	    		if(commandLineInput.length() == 0)
	    			throw new IOException("Empty comand passed");
	        } catch (Exception io) {
	            System.out.println("Wrong input");
	            continue;
	        }
	        
	        if (commandLineInput.charAt(0) == '-') {
	        	continueWorking = interpretCommand(commandLineInput);
	        	continue;
	        }
	        
	        sendInputToController(commandLineInput);
		}
	}
	
	/**
	 * Sends input string to controller and shows user results of it.
	 * This includes exception handling.
	 */
	private void sendInputToController(String commandLineInput) {
        List<Object> output = new ArrayList<Object>();
        try {
        	output = controller.modelOperation(commandLineInput);
	        for(int i = 0; i < output.size(); i++) {
	        	System.out.println(output.get(i));
	        }
        	System.out.println("Done!");
        } catch (BusinessLogicException bl) {
        	System.out.println(bl.getMessage());
        } catch (Exception e) {
        	System.out.println("Unknown error");
        }
	}
	
	/**
	 * Deals with inputs that start with '-' (commands).
	 */
	private boolean interpretCommand(String commandLineInput) {
		boolean continueWorking = true;
		
		if (commandLineInput.equals("-help")) {
    		System.out.println("Syntax is as follows: " +
    		"[operation] dataType:[type] [argumentName]:[value]");
    		System.out.println("type -help to see this again or -exit to quit");
    		System.out.println("type -dataType to see all datatypes");
    		System.out.println("type -args [dataType] to see data type args");
    		System.out.println("type -ops to see all operations");
    		
    	} else if (commandLineInput.equals("-exit")) {
    		continueWorking = false;
    		System.out.println("Goodbye");
    		
    	} else if (commandLineInput.split(" ")[0].equals("-args")) {
    		try {
    			for(String parameter : controller.
    				getDataTypeArgumentsNameList(commandLineInput)) {
    					System.out.println(parameter);
    			}
    		} catch (BusinessLogicException bl) {
    			System.out.println(bl.getMessage());
    		}
    		
    	} else if (commandLineInput.equals("-ops")) {
    		for(String operation : controller.getOperationsNameList()) {
    			System.out.println(operation);
    		}
    		
    	} else if (commandLineInput.equals("-dataType")) {
    		for(String dataType : controller.getDataTypesNameList()) {
    			System.out.println(dataType);
    		}
    		
    	} else {
    		System.out.println("Unknown command");
    	}
		return continueWorking;
	}
}
