package main;

import model.Model;
import view.ConsoleView;
import controller.InputController;

public class Main {

	public static void main(String[] args) {
		Model dataModel = new Model();
		InputController inputController = new InputController(dataModel);
		ConsoleView view = new ConsoleView(inputController);
		view.commandLineView();
	}

}
