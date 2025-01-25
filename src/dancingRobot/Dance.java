package dancingRobot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.StringBuilder;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.awt.Color;

import swiftbot.*;

//Main class serves as entry and exit point of program, sets up initial resources
public class Dance {
	static SwiftBotAPI bot;

	public static void main(String[] args) throws IOException, InterruptedException {
		final Object lock = new Object();
        synchronized(lock){ //Synchronised blocks for whatever might interrupt processes being done in the saem thread
        	try {
                bot = new SwiftBotAPI();
            } catch (Exception e) {
                /*
                 * Outputs a warning if I2C is disabled. This only needs to be turned on once,
                 * so you won't need to worry about this problem again!
                 */
            	e.printStackTrace();
                System.out.println("\nI2C disabled!");
                System.out.println("Run the following command:");
                System.out.println("sudo raspi-config nonint do_i2c 0\n");
                System.exit(5);
            }
        }
		Filing filing = new Filing();
		UI ui = new UI();
		ColourDetect colour = new ColourDetect(bot);
		ButtonHandler input = new ButtonHandler(bot);
		Controller run;
		QR qr;
		List<String> history = new ArrayList<>();
		String dir;
		Object[] hisdir;
		String hex = "";
		int runs = 0; //counter for times program was looped during runtime
		Boolean temp;  //initialises all objects and variables needed for the main logic

		while (true) { //Loop for the program
			ui.display();
			input.openButtons(1);
			ui.inputHandler(input.getPressed());
			while(hex == null || hex.isEmpty()) {
				try { //makes sure the QR code was decoded and hex obtained
					qr = new QR(bot);
					hex = qr.getHex();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			run = new Controller(bot, input, filing, ui, colour, hex);
			run.Start(); //Runs main logic
			runs++;
			temp = input.getPressed();
			
			if (!temp) {  //Checks whether user wants to restart or end
				ui.inputHandler(temp);
				break;
			} else {
				hex = "";
				ui.inputHandler(temp);
			}
		}

		hisdir = filing.get(); //obtains history and directory from Filing class
		history = (List<String>) hisdir[1];
		dir = (String) hisdir[0];
		System.out.println("History: " + history + "\nQR Codes Scanned: " + runs + "\nHistory Directory: " + dir); 
		filing.clear();  //Prints all hex numbers used and how many times the program looped as well as the directory
		System.exit(0);
	}

}