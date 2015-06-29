package main;

import model.storing.DataStorage;
import view.ConsoleView;
import controller.parsing.InputController;

public class Main {

	public static void main(String[] args) {
		DataStorage dataModel = new DataStorage();
		InputController inputController = new InputController(dataModel);
		ConsoleView view = new ConsoleView(inputController);
		view.commandLineView();
	}

}
