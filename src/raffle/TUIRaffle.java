/**
 * 
 */
package raffle;

import java.util.Map;
import java.util.Scanner;

/**
 * A simulator for the running of a raffle. 
 * This simulator uses a text-based user interface (TUI). 
 * It simulates:
 *  - the setting up of a raffle and its prizes,
 * 	- making a number of transaction (i.e. selling raffle tickets),
 *  - the announcement of winners. 
 *  
 * @author S H S Wong
 * @version 27-09-2020
 */
public class TUIRaffle {

	private Raffle raffle;
	private Scanner keyboard;
	private Currency ticketCost;
	private Currency revenue;
		
	/**
	 * Constructor
	 * @param raffle
	 */
	public TUIRaffle(Raffle raffle) {
		
		this.raffle = raffle;
		
		// create a Scanner object for obtaining input from keyboard
		keyboard = new Scanner(System.in);

		revenue = new Currency(0);

		setPriceOfTicket();
		setUpPrizes();
	}
	
	/*
	 * Set up all the prizes for this raffle
	 */
	private void setUpPrizes() {
		
		String more = "yes";

		System.out.println("Enter the details of the prizes for this raffle.");
		
		/*
		 * Prompt the user to enter the details of each prize.
		 * This routine will continue until the user said that 
		 * 	no more prizes is to be entered. 
		 */
		while ((more.trim().toLowerCase()).equals("yes")) {
			
			System.out.println("Enter the details of a prize... ");
			System.out.print("Name of prize: ");
			String name = keyboard.nextLine();
			String prompt = "How much in £ does it worth? ";
			System.out.println(prompt);
			int value = getInt(prompt);
			raffle.addPrize(name, value);
			
			// Ask the user if more prize is to be added.
			System.out.print("More prizes (yes or no)? ");
			more = keyboard.nextLine();
		}
	}

	/*
		Allow user to set the price of each ticket in the given raffle
	 */
	private void setPriceOfTicket() {
		System.out.println("> Setting price of each ticket in raffle...");
		String prompt = "How much is each ticket in the raffle?";
		System.out.println(prompt);
		int value = getInt(prompt);
		ticketCost = new Currency(value);
	}

	/**
	 * Sell tickets to a buyer.
	 * The buyer can buy many tickets in a single transaction.
	 */
	public void sellTickets() {

		String more = "yes";

		/*
		 * Prompt the user to enter the details of each prize.
		 * This routine will continue until the user said that
		 * 	no more prizes is to be entered.
		 */
		while ((more.trim().toLowerCase()).equals("yes")) {

			System.out.println("What is your name? ");
			String buyer = keyboard.nextLine();
			String prompt = "How many tickets do you want to buy? ";
			System.out.println(prompt);
			int howMany = getInt(prompt);

			// Sell multiple tickets...
			while (howMany > 0) {
				raffle.sellTicket(buyer);
				revenue.addToAmount(ticketCost.getAmount());
				howMany--;
			}

			// Ask the user if more tickets are to be added.
			System.out.print("More tickets (yes or no)? ");
			more = keyboard.nextLine();
		}
	}
	
	/**
	 * Displays the results to the console.
	 */
	public void results() {
		// Announce the title of the raffle...
		System.out.println(raffle.title() + '\n' + "The winners are... ");
		
		// Get info about who has won what.
		Map<Prize, Ticket> winners = raffle.luckyDraw();
		
		/* !!!! Make use of an enhanced for statement to obtain the 
		 * prizes and their winners one-by-one and output an announcement 
		 * for each prize and its winner. 
		 */
		for(Map.Entry<Prize, Ticket> prizeAndTicket: winners.entrySet()) {
			System.out.println("WINNER!");
			System.out.println(prizeAndTicket.getKey().toString());
			System.out.println(prizeAndTicket.getValue().toString());
		}
		
		
		
		System.out.println("Many Congratulations!!");
	}

	/*
	 * Get an integer from the specified input medium.
	 * This method will persist until the input is an integer.
	 * 
	 * @param prompt	a message to prompt for the input 
	 */
	private int getInt(String prompt) {
		try {
			return Integer.parseInt(keyboard.nextLine());
		}
		catch (NumberFormatException e) {
			System.out.println("The value must be a whole number." + prompt);
			return getInt(prompt);
		}
	}
	
	/**
	 * Simulates the running of a raffle
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Set up the raffle
		System.out.println("> Setting up the raffle...");
		TUIRaffle tui = new TUIRaffle(new Raffle("Cancer Research"));
//		tui.raffle.addPrize("Family holiday at Disneyland Paris (3 nights)", 1500);
//		tui.raffle.addPrize("Family holiday at Legoland Windsor (1 night)", 450);
//		tui.raffle.addPrize("Tickets to Tumble Jungle (x4)", 25);

		// Simulate selling tickets in 5 transactions
		System.out.println("> Selling tickets...");
		tui.sellTickets();
		
		// Draw prizes
		System.out.println("> Drawing prizes...");
		tui.results();

		System.out.println("> Total revenue");
		System.out.println("Total revenue for this raffle was: £" + tui.revenue.getAmount());
	}
}
