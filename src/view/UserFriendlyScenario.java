package view;

import java.io.*;
import java.util.*;

import model.data.auxiliary.ServiceAmountTuple;
import model.data.datatypes.Client;
import controller.parsing.InputController;

/**
 * This class is not finished.
 * A class that helps client step-by-step set up his order.
 * 
 * @version a.0 (not suitable for use)
 * @author	Boris Gordeev
 * @since 22-06-2015
 */

public class UserFriendlyScenario {
	
	private static String exitCommand = "-exit";
	private static String helpCommand = "-help";
	private static String cartCommand = "-cart";
	private static String buyCommand = "-buy";
	
	private static String idAttribute = "id";
	private static String sortAttribute = "sort";
	private static String manufacturerNameAttribute = "manufacturerName";
	private static String modelNameAttribute = "modelName";
	private static String colorAttribute = "color";
	private static String productTypeAttribute = "productType";
	private static String releaseDateAttribute = "releaseDate";
	
	private List<ServiceAmountTuple> cart;
	private InputController controller;
	private Client user;
	
	private String id = "";
	private String manufacturerName = "";
	private String modelName = "";
	private String color = "";
	private String productType = "";
	private String releaseDate = "";
	private String sort = "";
	
	
	public UserFriendlyScenario(InputController controller, Client user) {
		this.controller = controller;
		this.user = user;
		cart = new ArrayList<ServiceAmountTuple>();
	}
	
	public void commenceScenario() {
		greet();
		help();
		mainMenu();
	}
	
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
    	} else {
    		System.out.println("Unknown command");
    	}
		return continueWorking;
	}
	
	private void greet() {
		System.out.println("Hello, " + user.getName() + " " +
				user.getLastName() + "!");
		System.out.println("This programm will help you " +
				"make your order in a few easy steps!");
	}
	
	private void help() {
		System.out.println(
		"At any point type -help to see this message again.");
		System.out.println(
				"At any point type -exit to quit.");
		System.out.println(
				"In order to search you need to type [Attribute]:[Value]");
		System.out.println(
				"In order to drop search criteria need to type -drop:[Attribute]");
		System.out.println(
				"To add to cart, type -add:[ID]");
		System.out.println(
				"To buy, type -buy");
		showAttributes();
	}
	
	private void mainMenu() {
		BufferedReader bs;
        String commandLineInput;
        boolean continueWorking = true;
		while (continueWorking) {
			bs = new BufferedReader(new InputStreamReader(System.in));
	        try {
	        	commandLineInput = bs.readLine();
	    		if(commandLineInput.length() == 0)
	    			throw new IOException("Empty comand passed");
	        } catch (IOException io) {
	            System.out.println("Wrong input");
	            continue;
	        }
	        
	        if (commandLineInput.charAt(0) == '-') {
	        	continueWorking = interpretCommand(commandLineInput);
	        	continue;
	        }
		}
	}
	
	private void showActiveQuerry() {
		
	}
	
	private void showAttributes() {
		System.out.println("Here are usable attributes:");
		System.out.println(idAttribute);
		System.out.println(sortAttribute);
		System.out.println(manufacturerNameAttribute);
		System.out.println(modelNameAttribute);
		System.out.println(colorAttribute);
		System.out.println(productTypeAttribute);
		System.out.println(releaseDateAttribute);
	}
	
	private void showCart() {
		if (cart.isEmpty()) System.out.println("Your cart is empty");
		else {
			for(int i = 0; i < cart.size(); i++) {
				System.out.println(cart.get(i));
			}
		}
	}
}
