package dancingRobot;

import java.io.IOException;

import swiftbot.SwiftBotAPI;

class Controller {
	private SwiftBotAPI bot; //reference for swiftbot API
	private Filing filing; //reference for Filing class
	private ButtonHandler input; //reference for ButtonHandler class
	private BotParam stats; //reference for BotParam class
	private ColourDetect colour; //reference for ColourDetect class
	private UI ui; //reference for UI class
	private int[] rgb; //rgb array for setting LED colour
	private int speed; //swiftbot wheel speed
	private int time; //time for the bot to move forward for
	private String bin; //binary equivalent
	private String hex; //variable to store contents of QR code
	private int den; //Denary equivalent
	private int oct; //Octal equivalent
	private Object[] results; //Variable for values from Converter class
	private double avoid; //Distance threshold for obstacle avoidance
	private String rgbPrint = ""; //Variable to print rgb array properly

	 //assigns values to variables above
	public Controller(SwiftBotAPI bot, ButtonHandler input, Filing filing, UI ui, ColourDetect colour, String hex) {
		this.bot = bot;
		this.colour = colour;
		this.filing = filing;
		this.input = input;
		this.stats = new BotParam(hex);
		this.results = stats.get();
		this.rgb = (int[]) results[0];
		this.speed = (int) results[1];
		this.time = (int) results[2];
		this.bin = (String) results[3];
		this.oct = (int) results[4];
		this.den = (int) results[5];
		this.hex = hex;
		this.ui = ui;
		this.avoid = 17.5; //distance to avoid obstacles at
	}
	 //Start of main logic
	public void Start() throws IOException, InterruptedException {
		for(int x = 0; x < 3; x++) rgbPrint += rgb[x] + " ";
		System.out.println("Hex: "+this.hex+"\nBinary: "+this.bin+"\nOctal: "+this.oct+"\nDenary: "+this.den+"\nSpeed: "+this.speed+"\nRGB Values: "+rgbPrint+"\n");
	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        // Handle interruption gracefully, e.g., log it
	        e.printStackTrace();
	        // Optionally, reset the interrupt status
	        Thread.currentThread().interrupt();
	    }
		try {
			filing.write(hex);
			doDance();
		} catch (Exception e) {
			throw e;
		}
		ui.display();
		input.openButtons(2);
	}

	 //Performs the dance
	public void doDance() throws InterruptedException {
	    int len = this.bin.length();
	    int[] spins = new int[] { 500, 1000 };
	    Boolean right = true;

	    this.bot.fillUnderlights(this.rgb);

	    try {
	        for (int i = (len - 1); i >= 0; i--) {
	            int r = right ? -1 : 1;
	            int l = right ? 1 : -1;
	            while (this.bot.useUltrasound() <= this.avoid) {
	            	String colourInFront = colour.getColour();
	            	System.out.println(colourInFront+" object ahead!");
	            	this.bot.move(30, -30, 1000);
	            }
	            if (this.bin.charAt(i) == '1') {
	                this.bot.move(this.speed, this.speed, this.time);
	            } else {
	                this.bot.move(this.speed * l, this.speed * r, spins[this.bin.charAt(i) - '0']);
	                right = !right;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	Thread.sleep(3000);
	    	this.bot.disableUnderlights();
	        
	    }
	}


}
