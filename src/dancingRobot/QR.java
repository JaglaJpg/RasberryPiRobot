package dancingRobot;

import java.awt.image.BufferedImage;

import swiftbot.SwiftBotAPI;

//Class for handling QR functionality
class QR {
	private String hex;
	private SwiftBotAPI bot;

	public QR(SwiftBotAPI bot) {
		this.bot = bot;
		try {
			this.hex = Scan(); //Decoded QR code contents get saved to this attribute
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public String Scan() throws InterruptedException{ //Takes a picture and looks for a QR code to decode
		try {
			for (int x = 1; x < 4; x++) {
				System.out.println(x); 
				Thread.sleep(1000);
			}
			BufferedImage img = this.bot.getQRImage();
			String decoded = this.bot.decodeQRImage(img);
			if (decoded.isEmpty()) {
				System.out.println("Couldn't see a QR code, retrying..."); //If no QR code was decoded then it lets the user know
				Thread.sleep(1000);                                        //and returns null
				return null;
			} else if(!checkHex(decoded)) {
				System.out.println("QR code did not contain a valid hexadecimal, retrying..."); 
				Thread.sleep(1000);                                       
				return null;
			}
			return decoded; //Returns decoded contents of QR code

		} catch (Exception e) { //Throws an exception if scanning the QR code causes an error
			throw e;
		}

	}

	public Boolean checkHex(String hex) {
		Boolean isHex = true;
		String hexValues = "0123456789ABCDEF";
		
		if(hex == null || hex.isEmpty()) {
			 System.out.println("QR code is empty!");
			 isHex = false;
		}
		
		if (hex.length() > 2) {
			System.out.println("Too long!!!");
			isHex = false;
		} 

		for (char c : hex.toCharArray()) {
			if (hexValues.indexOf(Character.toUpperCase(c)) == -1) {
				System.out.println("QR code contains invalid characters!");
				isHex = false;
			}
		}

		
		return isHex;
	}

	public String getHex() { //returns attribute containing decoded QR code contents
		return this.hex;
	}
}
