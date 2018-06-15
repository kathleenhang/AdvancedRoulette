/*
 * [ Transaction ]
 * - Tracks each individual player's personal records per round
 */
public class Transaction {
	int transactionNumber;
	int seatNumber; // playerNumber
	int playerPayOff;
	int playerBetAmount;
	String playerBetType; // red, black, number
	int ballPosition;
	int round;
	

	// TODO: print transactions of each player to data file
	
	// create transaction once player is added to the game from queue
	public Transaction(int transactionNumber, int seatNumber, int playerBetAmount, 
										String playerBetType, int playerPayOff, int round, int ballPosition) 
	{
		this.transactionNumber = transactionNumber;
		this.seatNumber = seatNumber;
		this.playerBetAmount = playerBetAmount;
		this.playerBetType = playerBetType;
		this.playerPayOff = playerPayOff;
		this.ballPosition = ballPosition;
		this.round = round;
	}
	
	public int getTransactionNumber() { return transactionNumber; }
	public int getSeatNumber() { return seatNumber; }
	public int getPlayerBetAmount() { return playerBetAmount; }
	public String getPlayerBetType() { return playerBetType; }
	public int getPlayerPayOff() { return playerPayOff; }
	public int getBallPosition() { return ballPosition; }
	public int getRound() { return round; }
	
	public String getPlayerBetTypeString()
	{
		if(playerBetType == "R")
			return "Red";
		else if(playerBetType == "B")
			return "Black";
		else
			return "Number";
	}
	

	// print round result for each player
	public String printRoundResults()
	{
		if(playerPayOff > 0)
			return "Round " + round + ": Player " + seatNumber + " bet on " + getPlayerBetTypeString() + " - won $" + playerPayOff + "\n";
		else
			return  "Round " + round + ": Player " + seatNumber + " bet on " + getPlayerBetTypeString() + " - lost $" + playerBetAmount + "\n";
	}
	public String toString()
	{
		
		return transactionNumber + "\t" + seatNumber + "\t" + playerBetAmount +
				"\t" + playerBetType + "\t" + playerPayOff;
					
	}
	
}
