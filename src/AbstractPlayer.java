import java.util.Scanner;
/*
 * [ AbstractPlayer ]
 * - Regular, Vip, and Super Vip Player will be built upon this template
 */
public abstract class AbstractPlayer {
	int numberOfBets = 0;
	boolean activePlayer = false; // need to track this because a player can only be in 1 game at a time
	int playerBalance; // each player tracks their own money
	int betMenuChoice; // which menu option the player selected
	int betAmount; // how much money player decides to bet
	int wheelNumberChoice; // which number on the wheel the player bet on
	int totalWinnings = 0;
	int totalLosings = 0;
	int skipCount = 0; // player can skip up to 2 rounds before being removed from the game
	int betNumber;
	//int playerStartBalance;
	//int playerEndBalance;
	int playerNetGain;
	int seatNumber = 0;
	int RELOAD_AMOUNT = 50;
	String playerType;
	
	
	public String getPlayerBetType()
	{
		if(betMenuChoice == 1)
			return "B";
		else if(betMenuChoice == 2)
			return "R";
		else
			return "N";
	}
	public abstract boolean playAgain(Scanner scan);
	public int getTotalWinnings() { return totalWinnings; }
	public void setTotalWinnings(int winnings) { totalWinnings += winnings; }
	public int getTotalLosings() { return totalLosings; }
	public void setTotalLosings(int losings) { totalLosings += losings; }
	
	public void setPlayerBalance(int cashAmount) { playerBalance += cashAmount; }
	public int getPlayerBalance() { return playerBalance; }
	public int getBetMenuChoice() { return betMenuChoice; }
	public int getBetNumber() { return betNumber; }
	public int getBetAmount() { return betAmount; }
	public abstract String getPlayerType();
	public abstract String toString();
	
	public String getBetChoice()
	{
		if(betMenuChoice == 1)
			return "Black";
		else if(betMenuChoice == 2)
			return "Red";
		else
			return "number " + betNumber;
	}
	
	// used as player identification
	public int getSeatNumber() { return seatNumber; }
	public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
	public void setSeatNumber() { seatNumber++; }

	/*
	 *  - Allows the player to make a bet
	 *  - Displays their current balance
	 *  - Bet menu options: color, number, 
	 */
	public void makeBet(int userPlayerMenuChoice)
	{
		Scanner scan = new Scanner(System.in);
		betMenuChoice = userPlayerMenuChoice;
		while(betMenuChoice < 1 || betMenuChoice > 3)
		{
			System.out.println(seatNumber + "\tError: Please enter menu option 1 - 3.");
			betMenuChoice = scan.nextInt();
		}
		// print ID along with cash available to spend
		System.out.println("Player " + seatNumber + "\t\tCash Available: " + playerBalance);
		System.out.print("Player " + seatNumber + "\t\tEnter bet amount (" + V100A.getMinBet() + "-" + V100A.getMaxBet() + "): ");
		betAmount = scan.nextInt();

		// while the bet amount is out of bounds
		while(betAmount > V100A.getMaxBet() || betAmount < V100A.getMinBet())
		{
			System.out.println("Player " + seatNumber + "\t\tError: Please make a bet from " + V100A.getMinBet() + "-" + V100A.getMaxBet() + ".");
			System.out.print("Player " + seatNumber + "\t\tHow much to bet: ");
			betAmount = scan.nextInt();
			System.out.println();
		}
		// if player tries to bet more than they currently have
		if(playerBalance < betAmount)
		{
			System.out.println("Player " + seatNumber + "\t\tInsufficient funds! $50 was added.");
			playerBalance += RELOAD_AMOUNT;
			System.out.println("Player " + seatNumber + "\t\tCash Available: " + (playerBalance - betAmount));
		}
		System.out.println("\nPlayer " + seatNumber + " placed a $" + betAmount + " bet on " + getBetChoice() + ".");
		playerBalance = playerBalance - betAmount;

		// if player chooses to bet on a number
		if(betMenuChoice == AbstractWheel.number)
		{
			System.out.println("Player " + seatNumber + "\t\tPlease enter a number from " + V100A.getMinBet() + " - " + V100A.getMaxBet() + ".");
			wheelNumberChoice = scan.nextInt();
			while(wheelNumberChoice < V100A.minNum || wheelNumberChoice > V100A.maxNum)
			{
				System.out.println("Player " + seatNumber + "\t\tPlease enter a number from " + V100A.getMinBet() + " - " + V100A.getMaxBet() + ".");
				wheelNumberChoice = scan.nextInt();
			}
		}
		numberOfBets++;
	}
	
	public void addFunds()
	{
		Scanner scan = new Scanner(System.in);
		int fundAmount;
		System.out.println("\nHow much would you like to add?\n");
		fundAmount = scan.nextInt();
		playerBalance += fundAmount;
		System.out.println("Player " + seatNumber + "'s new balance is: $" + playerBalance + "\n");
	}

	public void incrementSkipCount() 
	{ 
		skipCount++;
		System.out.println("Player " + seatNumber + " skipped the round.");
		
	}
	public int getSkipCount() { return skipCount; }
	public abstract String printPlayerStats();

}
