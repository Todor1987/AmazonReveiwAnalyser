package main;

import gui.Gui;

import java.io.IOException;

public class Application {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException, IOException {

		
//		Utilities ut = new Utilities();
//		ut.wordOccurencies();
//		
		Gui window = new Gui();
		window.gui();
	}
}
