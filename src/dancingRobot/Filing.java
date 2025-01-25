package dancingRobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Class for handling file reading and writing.
class Filing {
	private List<String> history; //List for all scanned QR codes during runtime
	private String dir; //variable for default directory

	public Filing() {
		try {
			this.history = hexBubble(read(), hexToDen(read()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.dir = System.getProperty("user.dir");
	}

	public List<Integer> hexToDen(List<String> hexes) { //Converts a list of hex numbers into denary numbers
		List<Integer> nums = new ArrayList<>();
		String hexValues = "0123456789ABCDEF";
		int temp = 0;
		int bits = 0;
		for (int x = 0; x < hexes.size(); x++) {
			String y = hexes.get(x);
			bits = 0;
			temp = 0;
			for (int i = y.length() - 1; i >= 0; i--) {
				int power = (int) Math.pow(2, bits);
				temp += hexValues.indexOf(y.charAt(i)) * power;
				bits += 4;
			}
			nums.add(temp);
		}
		return nums;
	}

	public List<String> hexBubble(List<String> hexes, List<Integer> nums) { //Sorts a denary list while making 
		int changes = 0;                                                    //the same changes to a hex list

		while (true) {
			changes = 0;
			for (int x = 0; x < hexes.size() - 1; x++) {
				if (nums.get(x) > nums.get(x + 1)) {
					Collections.swap(nums, x, x + 1);
					Collections.swap(hexes, x, x + 1);
					changes += 1;
				}

			}
			if (changes == 0) {
				break;
			}
		}

		return hexes;
	}

	public void write(String hex) throws IOException { //Writes an inputed String to a txt file called hex.txt
		try (RandomAccessFile file = new RandomAccessFile("hex.txt", "rw")) {
			file.seek(file.length());
			if (file.length() != 0) {
				file.writeBytes("\n" + hex);
				this.history = hexBubble(read(), hexToDen(read()));
			} else {
				file.writeBytes(hex);
				this.history = hexBubble(read(), hexToDen(read()));
			}

		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("No file found, creating now...");
				File hexes = new File("hex.txt");

				if (hexes.createNewFile()) {
					System.out.println("File created.");
				} else {
					System.out.println("Failed to create file.");
				}

				write(hex);
			} else {
				System.out.println("Error encountered!");
			}

		}
	}

	public List<String> read() throws IOException { //Reads the item on each line of the txt file and puts them in a List
		List<String> ans = new ArrayList<String>();

		try (RandomAccessFile file = new RandomAccessFile("hex.txt", "r")) {
			String line;
			while ((line = file.readLine()) != null) {
				ans.add(line);
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("No file found, creating now...");
				File hex = new File("hex.txt");

				if (hex.createNewFile()) {
					System.out.println("File created.");
				} else {
					System.out.println("Failed to create file.");
				}

				ans = read();
			} else {
				System.out.println("Error encountered!");
			}

		}

		return ans;
	}
	
	public void clear() { //Clears the txt file

      try (RandomAccessFile file = new RandomAccessFile("hex.txt", "rw")) {
          file.setLength(0);

      } catch (IOException e) {
          e.printStackTrace();
      }
	}

	public Object[] get() { //Returns sorted list of all heex numbers used and the txt files directory
		return new Object[] { this.dir, this.history };
	}

}
