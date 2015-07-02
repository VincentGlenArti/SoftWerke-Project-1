package main;

import profiling.*;
import model.storing.*;
import controller.parsing.*;
import view.ConsoleView;

import java.text.ParseException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		try {
			List<String> result = Profiler.profileTestCase();
			for(String resultString : result) {
				System.out.println(resultString);
			}
		} catch (Exception pe) {
			System.out.println("error");
			System.out.println(pe.getMessage());
		}
		
		/*DataStorage data = new DataStorage();
		InputController controller = new InputController(data);
		ConsoleView view = new ConsoleView(controller);
		view.commandLineView();*/
	}

}
