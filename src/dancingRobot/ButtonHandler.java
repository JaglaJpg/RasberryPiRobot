package dancingRobot;

import java.util.concurrent.CountDownLatch;

import swiftbot.Button;
import swiftbot.SwiftBotAPI;

//Class for handling button functionality
class ButtonHandler {
	private Boolean pressed;
	private CountDownLatch latch;
	private SwiftBotAPI bot;

	public ButtonHandler(SwiftBotAPI bot) {
		this.bot = bot;
		this.pressed = null;
		this.latch = new CountDownLatch(1);
	}

	public void openButtons(int mode) { //depending on whether i pass 1 or 2 through the method it
		switch (mode) {                 //will listen for the Y button or the Y and X button
		case 1:
			oneButtonOn();
			break;
		case 2:
			twoButtonsOn();
			break;
		}
	}

	public void oneButtonOn() { //Starts listening for the Y button
		synchronized (this.bot) {
			this.bot.enableButton(Button.Y, () -> {
				bot.disableButton(Button.Y);
				this.pressed = true;
				this.latch.countDown();
				this.latch = new CountDownLatch(1);
			});
		}

		try {
			synchronized (this.latch) {
				this.latch.await(); // Wait until a button is pressed
			} 
		} catch (InterruptedException e) {
			e.printStackTrace(); // Handle interrupted exception
		}
		
		this.latch = new CountDownLatch(1);
	}

	public void twoButtonsOn() { //Starts listening for the Y and X buttons
		synchronized (this.bot) {
			this.bot.enableButton(Button.Y, () -> {
				bot.disableButton(Button.Y);
				bot.disableButton(Button.X);
				this.pressed = true;
				this.latch.countDown();
			});
			
			this.bot.enableButton(Button.X, () -> {
				bot.disableButton(Button.Y);
				bot.disableButton(Button.X);
				this.pressed = false;
				this.latch.countDown();
			});
		}

		try {
			synchronized (this.latch) {
				this.latch.await(); 
			} // Wait until a button is pressed
		} catch (InterruptedException e) {
			e.printStackTrace(); // Handle interrupted exception
		}
		
		this.latch = new CountDownLatch(1); //reset the countdown latch so it can be reused
	}

	public Boolean getPressed() { //returns a Boolean indicating which of the two buttons was pressed
		Boolean x = this.pressed;
		this.pressed = null;
		return x;
	}
}
