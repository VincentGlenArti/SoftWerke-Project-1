package view;

import java.util.*;
import java.io.*;

import controller.parsing.InputController;

/**
 * Class used to accept user input and show back formated output.
 *
 * @version a.2
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class ConsoleView {
	
	private static String helpCommand = "-help";
	private static String exitCommand = "-exit";
	private static String userFrienldy = "-uf";
	
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
        //try {
        	output = controller.modelOperation(commandLineInput);
	        for(int i = 0; i < output.size(); i++) {
	        	System.out.println(output.get(i));
	        }
        	System.out.println("Done!");
        //} catch (IllegalArgumentException iae) {
        	//System.out.println(iae.getMessage());
        //} catch (Exception e) {
        	//System.out.println("Unknown error");
        //}
	}
	
	/**
	 * Deals with inputs that start with '-' (commands).
	 */
	private boolean interpretCommand(String commandLineInput) {
		boolean continueWorking = true;
		if (commandLineInput.equals(helpCommand)) {
    		System.out.println("Syntax is as follows: " +
    		"[operation] dataType:[type] [attributeType]:[attribute]" +
    		System.lineSeparator() +
    		"type -help to see this again or -exit to quit");
    		System.out.println("-uf:[userID] will start user-friendly interface");
    	} else if (commandLineInput.equals(exitCommand)) {
    		continueWorking = false;
    		System.out.println("Goodbye");
    	} else if (commandLineInput.startsWith(userFrienldy, 0)) {
    		//userFriendlyView(commandLineInput);
    		System.out.println("Back to console mode");
    	} else {
    		System.out.println("Unknown command");
    	}
		return continueWorking;
	}
	
	/**
	 * Finds user matching -uf request, and if it exists, initializes
	 * UserFriendlyScenario class with it and sends control there.
	 */
	/*private void userFriendlyView(String commandLineInput) {
		Client user = null;
		try {
			String[] split = commandLineInput.split(":");
			String userSearch = "select dataType:clients id:" + split[1];
			user = (Client)controller.modelOperation(userSearch).get(0);
		} catch (Exception e) {
			System.out.println("Wrong input");
		}
		
		if (user != null) {
			UserFriendlyScenario scenario = 
					new UserFriendlyScenario(controller, user);
			scenario.commenceScenario();
		}
	}*/
}
