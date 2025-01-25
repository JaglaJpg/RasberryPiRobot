package dancingRobot;

//Class to calculate necessary speed and rgb values for LEDs
class BotParam {
	private Converter converter;
	private int speed; //Variable for SwiftBot's speeed
	private int[] rgb; //Array for RGB values for the LEDs
	private int time; //Variable for how long the SwiftBot should move forward for
	private int oct;
	private int den;
	private String bin;
	private Object[] results; //array for the values from Converter

	public BotParam(String hex) {
		this.converter = new Converter(hex);
		this.results = converter.get();
		this.den = (int) results[0];
		this.oct = (int) results[1];
		this.bin = (String) results[2];
		this.time = (hex.length() == 2) ? 1000 : 500;
		this.rgb = rgbValues();
		this.speed = speed();
	}

	public int[] rgbValues() { //Calculates colour for SwiftBot's LEDs from the denary value
		int[] rgb = new int[3];
		int green = (den % 80) * 3;
		int blue = Math.max(den, green);
		rgb[0] = den;
		rgb[1] = green;
		rgb[2] = blue;
		return rgb;
	}

	public int speed() { //Calculates SwiftBot's wheel speed from the octal value
		int speed;

		if (this.oct < 50) {
			speed = this.oct + 50;
		} else if (this.oct > 100) {
			speed = 100;
		} else {
			speed = this.oct;
		}
		return speed;
	}

	public Object[] get() {
		return new Object[] { this.rgb, this.speed, this.time, this.bin, this.oct, this.den };
	}
}