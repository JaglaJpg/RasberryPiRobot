package dancingRobot;

import java.awt.Color;
import java.awt.image.BufferedImage;

import swiftbot.ImageSize;
import swiftbot.SwiftBotAPI;

//Class for detecting colour of obstacle detected by ultrasound
class ColourDetect{
	private SwiftBotAPI bot;
	
	public ColourDetect(SwiftBotAPI bot) {
		this.bot = bot;
	}
	
	public float[] takePicture() { //Takes a picture and retrieves the RGB data of the middle pixel
		int[] rgb = new int[3];
		float[] hsb = new float[3];
		BufferedImage img = this.bot.takeStill(ImageSize.SQUARE_1080x1080);
		int rgbData = img.getRGB(540, 540);
		rgb[0] = (rgbData >> 16) & 0xFF;
		rgb[1] = (rgbData >> 8) & 0xFF;
		rgb[2] = rgbData & 0xFF;
		
		hsb = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null); //RGB data converted to HSB (Hue, Saturation, Brightness)
		return hsb;
	}
	
	public String processRGB(float [] hsb) {
		String ans = "";
		
		if      (hsb[1] < 0.1 && hsb[2] > 0.9) ans = "White";
		else if (hsb[2] < 0.1) ans = "Black";
		else {
		    float deg = hsb[0]*360; //Hue in degrees
		    if      (deg >=   0 && deg <  30) ans = "Red";
		    else if (deg >=  30 && deg <  90) ans = "Yellow";
		    else if (deg >=  90 && deg < 150) ans = "Green";
		    else if (deg >= 150 && deg < 210) ans = "Cyan";
		    else if (deg >= 210 && deg < 270) ans = "Blue";
		    else if (deg >= 270 && deg < 330) ans = "Magenta";
		    else ans = "Red";
		}
		
		return ans; //This checks the HSB against ranges which indicate which colour it is
	}
	
	public String getColour() {
		float[] hsb = takePicture();
		String i = processRGB(hsb);
		
		return i; //returns the name of the colour as a String
	}
}
