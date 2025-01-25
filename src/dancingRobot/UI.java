package dancingRobot;

//Class that wipes console and prints UI. Decides what screen to show
class UI {
	private int state;

	public UI() {
		this.state = 1; //state of the class
	}

	public void inputHandler(Boolean pressed) { //outputs and changes state of class based on button input
		if (this.state == 1 && pressed == true) {
			System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
			this.state = 2;
		} else if (this.state == 2 && pressed == true) {
			this.state = 1;
		} else if (this.state == 2 && pressed == false) {
			System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
		}
	}

	public void display() { //displays appropriate screen based on state of class
		switch (this.state) {
		case 1:
			screen1();
			break;
		case 2:
			screen2();
			break;
		default:
			System.out.println("Invalid state");
		}
	}

	public void screen1() { //prints main menu
		System.out.println("████████▄      ▄████████                                ");
		System.out.println("███    ███    ███    ███                                ");
		System.out.println("███    ███    ███    ███                                ");
		System.out.println("███    ███   ▄███▄▄▄▄██▀                                ");
		System.out.println("███    ███  ▀▀███▀▀▀▀▀                                  ");
		System.out.println("███    ███  ▀███████████                                ");
		System.out.println("███  ▀ ███    ███    ███                                ");
		System.out.println("▀██████▀▄█   ███    ███                                ");
		System.out.println("             ███    ███                                ");
		System.out.println("                                                      ");
		System.out.println("████████▄     ▄████████ ███▄▄▄▄    ▄████████    ▄████████");
		System.out.println("███   ▀███   ███    ███ ███▀▀▀██▄ ███    ███   ███    ███");
		System.out.println("███    ███   ███    ███ ███   ███ ███    █▀    ███    █▀ ");
		System.out.println("███    ███   ███    ███ ███   ███ ███         ▄███▄▄▄   ");
		System.out.println("███    ███ ▀███████████ ███   ███ ███        ▀▀███▀▀▀   ");
		System.out.println("███    ███   ███    ███ ███   ███ ███    █▄    ███    █▄");
		System.out.println("███   ▄███   ███    ███ ███   ███ ███    ███   ███    ███");
		System.out.println("████████▀    ███    █▀   ▀█   █▀  ████████▀    ██████████\n");
		System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
		System.out
				.println("Scan a QR code containing a 1 or 2 digit hexadecimal and \nwatch me make a dance from it :)");
		System.out.println("Press my Y button when im in position to scan!\n");
	}

	public void screen2() { //prints end screen
		System.out.println("   _O/                   ,");
		System.out.println("     \\                  /           \\O_");
		System.out.println("     /\\_             `\\_\\        ,/\\/");
		System.out.println("     \\  `       ,        \\         /");
		System.out.println("     `       O/ /       /O\\        \\");
		System.out.println("             /\\|/\\.");
		System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
		System.out.println("Would you like to go again?\n");
		System.out.println("Y - Restart\n");
		System.out.println("X - End\n");
	}

}