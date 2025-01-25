package dancingRobot;

//Class for handling necessary number conversions.
class Converter {
	private int den; //Variable for Denary equivalent
	private int oct; //Variable for Octal equivalent
	private String bin; //Variable for Binary equivalent
	private String hex; //Variable for Hex number

	public Converter(String hex) {
		this.hex = hex;
		this.den = hexToDen();
		this.oct = denToOctal();
		this.bin = denToBinary();

	}

	public int hexToDen() { //Converts a Hex string to integer denary
		String hexValues = "0123456789ABCDEF";
		int bits = 0;
		int den = 0;
		for (int i = this.hex.length() - 1; i >= 0; i--) { //Looking from left to right, multiplies nth digit by 2 to the power of n+4
			int power = (int) Math.pow(2, bits);
			den += hexValues.indexOf(hex.charAt(i)) * power;
			bits += 4;
		}

		return den;
	}

	public String denToBinary() { //Converts denary to binary
		String bin = "";
		int den = this.den;
		while (den != 0) {
			bin = (den % 2) + bin; //Gets the remainder of dividing by two, then halves the  
			den /= 2;              //denary until its at zero then the reverse is the binary equivalent
		}

		return bin;
	}

	public int denToOctal() { // Converts denary to octal, same process as binary but dividing by 8 instead
		String octal = "";
		int den = this.den;
		int ans;
		while (den != 0) {
			octal += den % 8;
			den /= 8;
		}
		StringBuilder str = new StringBuilder(octal);
		str.reverse();
		octal = str.toString();

		try {
			ans = Integer.parseInt(octal);
			return ans;
		} catch (NumberFormatException e) {
			System.out.println("Error: Invalid number format");

		}

		return -1;
	}

	public Object[] get() { //Returns all values in an object array
		return new Object[] { den, oct, bin };
	}
}
