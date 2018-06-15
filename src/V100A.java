/*
 * [ GAME VERSION 100A ]
 * - Allows up to 5 players per game
 * - Allows multiple games. Up to 5.
 * - Contains 38 positions on the wheel: 00, 0, 1 to 36
 * - Payoff for number bet: 35 to 1
 * - Tracks own house/casino money
 * - Tracks active players of the game
 * - Tracks each player's transactions for each round
 * 
 * 
 * 
 * 
 * 
 * hows it track active players
 * hows it track its own transaction - dvd collection
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class V100A extends AbstractWheel {

	// values specific to V100A
	protected int max_positions = 39; // 38 positions on the wheel
	protected int number_payoff = 35; // payoff for number bet is 35 to 1
	public static int minNum   =  1;			// smallest number to bet // removed static ***
	public static int maxNum   = 36;
	public static int minBet;
	public static int maxBet;
	public static int SKIP_MAX = 2;
	int MAX_SEATS = 5; // max of 5 players allowed per game
	int playerCount = 0;
	int transactionNumber = 1;
	
	// make array of transaction items which keep track of game records for each round per player
	ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
	AbstractPlayer inGamePlayerList[] = new AbstractPlayer[MAX_SEATS+1];
	// create game object with the specified values from data file
	public V100A(int minBet, int maxBet, int houseBalance, String gameID)
	{
		this.minBet = minBet;
		this.maxBet = maxBet;
		this.houseBalance = houseBalance;
		this.gameID = gameID;
	}

	public AbstractPlayer getPlayer(int index)
	{
		return this.inGamePlayerList[index];
	}

	public void addPlayer(AbstractPlayer player)
	{
		if(playerCount == MAX_SEATS)
			System.out.println("Error: Cannot add anymore players. Game is full.");
		// if the game server is not full, add the player
		if(playerCount != MAX_SEATS)
		{

			int counter = 1;
			while(inGamePlayerList[counter] != null)
			{
				counter++;
			}
			player.setSeatNumber(counter); // increments seat count... used as ID

			// add players to in-game player list in order to access them later for making bets
			inGamePlayerList[playerCount+1] = player;
			playerCount++;
		}
	}

	public void gameMenuOptions() {
		System.out.println("Game Menu:");
		System.out.println("    1. Add a player to the game"); // add player from queue
		System.out.println("    2. Play one round"); // player menu will be available - bet options menu
		System.out.println("    3. Game Status"); 	// players and their money, house money, and results of up to last three spins
		System.out.println("    4. Return to the main menu");
		System.out.println();
	}

	public void playerMenuOptions()
	{
		System.out.println("Player Menu:");
		System.out.println("	1. Bet on black (even numbers)");
		System.out.println("	2. Bet on red (odd numbers)");	
		System.out.println("	3. Bet on a number between 1 and 36");
		System.out.println("	4. Add funds to current balance");
		System.out.println("	5. Skip a Round");
		System.out.println("	6. Leave the game");
		// multiple bets
	}

	@Override
	// TODO: fix spin values
	// Spins the roulette wheel and outputs to console where the ball landed: color & number
	public void spin() {
		Random rand = new Random();
		ballPosition = rand.nextInt(39);
		System.out.println();
		// 0 and 38 represent green 0 & green 00
		if (ballPosition == 38 || ballPosition == 0) 
		{
			color = green;
			if(ballPosition == 38)
				System.out.println("The ball landed on 00 Green!"  );
			else 
				System.out.println("The ball landed on 0 Green!"  );
		}
		else if(ballPosition % 2 == 0) // if ball lands on even
		{
			color = black;
			System.out.println("The ball landed on " + ballPosition + " Black!" );
		} 
		else if(ballPosition % 2 != 0) // if ball lands on odd
		{
			color = red;
			System.out.println("The ball landed on " + ballPosition + " Red!" );
		}
		System.out.println();
		roundCount++;
	}

	// call payoff to pay player
	public void pay()
	{
		// iterate through array of players to pay each individual person
		for(int i = 1; i < inGamePlayerList.length; i++) {
			if(inGamePlayerList[i] != null)
			{
				AbstractPlayer currentPlayer = inGamePlayerList[i];
				// determine how much to pay each player who is in the game using payoff method
				int winnings = payoff(currentPlayer.getBetAmount(),
										  currentPlayer.getBetMenuChoice(), 
										  currentPlayer.getBetNumber());
				
				// create transaction for each player
				transactionsList.add(new Transaction(transactionNumber++, 
														currentPlayer.getSeatNumber(),
														currentPlayer.getBetAmount(), 
														currentPlayer.getPlayerBetType(), 
														winnings, roundCount, ballPosition));
				
				currentPlayer.setTotalWinnings(winnings);
				// if they won nothing, add their bet amount to total losings
				if(winnings == 0)
				{
					// accumulate current round losings into totalLosings variable of each player object
					currentPlayer.setTotalLosings(currentPlayer.getBetAmount());
					// print player losses
					System.out.println("Player " + currentPlayer.getSeatNumber() + "\t\tlost $" + currentPlayer.getBetAmount() + "!");
				} else {
					// accumulate current round winnings into totalWinnings variable of each player object
					currentPlayer.setTotalWinnings(winnings); 	// if they won something, add to total wins
					System.out.println("Player " + currentPlayer.getSeatNumber() + "\t\twon $" + winnings + "!");
				}
				//and adds it to available funds
				currentPlayer.setPlayerBalance(winnings);
			}
		}
	
	}
	
	public String printTransactions()
	{
		
		// iterate through transactions array
		for(Transaction transaction : transactionsList)
		{
			int transactionNumber = transaction.getTransactionNumber();
			int playerID = transaction.getSeatNumber();
			int playerBetAmount = transaction.getPlayerBetAmount();
			String playerBetType = transaction.getPlayerBetType();
			int playerPayOff = transaction.getPlayerPayOff();
			
			return transactionNumber + "\t" + playerID + "\t\t" + playerBetAmount + "\t\t" 
					+ playerBetType + "\t\t" + playerPayOff;
		}
		return "";
	}
	@Override
	// Determines how much to pay the player at the end of each round 
	public int payoff(int betAmount, int betMenuChoice, int betNumber) {
		// 38 stands for 00. 0 or 00 = lose
		if(ballPosition == 0 || ballPosition == 38)
		{
			return 0;
		} 
		else if(betMenuChoice == 3) // number bet. player chooses to bet on a number
		{
			// if ball falls on the number the player bet on...
			// then payoff is 14 times the bet. otherwise payoff is zero. NUMBER_PAYOFF
			if(betNumber == ballPosition)
				return betAmount * number_payoff;
			else
				return 0;
		}
		// if ball falls on color that the player bet on...
		// then payoff is 2 times the bet. otherwise payoff is zero. COLOR_PAYOFF
		else if(betMenuChoice == 1)
		{
			if(color == black) // if ball lands on black
				return betAmount * color_payoff;
		}
		// if player bets on red
		else if(betMenuChoice == 2)
		{
			if(color == red) // if ball lands on red
				return betAmount * color_payoff;
		}
		return 0;
	
	}

	public void removePlayer(AbstractPlayer player)
	{
		int count = 1;
		while(inGamePlayerList[count].getSeatNumber() != player.getSeatNumber())
		{
			count++;
			System.out.println(count);
		}
		System.out.println("Player " + player.getSeatNumber() + " has been removed from the game.");
		inGamePlayerList[count] = null;
	}
	
	public void displayGameStatus()
	{
		// print header with player balances and house balance
		System.out.println("    [ GAME STATUS ]\n\n" + 
				"Current Balance of Players & House:\n");
		for(int i = 1; i < inGamePlayerList.length; i++)
		{
			if(inGamePlayerList[i] != null)
				System.out.println("Player " + i + " Balance: $" + inGamePlayerList[i].getPlayerBalance());
		}
		System.out.println(gameID + " Balance: $" + houseBalance + "\n\n");
		
		// print last 3 rounds
		int last3RoundsCounter = 3;
		int roundTracker = roundCount;
		// round count to get the last round
		while(last3RoundsCounter != 0)
		{
			//check the entire transactions array
			for(int i = transactionsList.size()-1; i > 0; i--)
			{
				Transaction currentTransaction = transactionsList.get(i);
				
				// while the current transaction object round is the same, keep printing those transactions
				if(currentTransaction.getRound() == roundTracker)
					System.out.println(currentTransaction.printRoundResults());
				
				last3RoundsCounter--;
				roundTracker--;
			}
		}
	}

	@Override
	public String getGameID() {	return gameID; }
	public static int getMinBet() { return minBet; }
	public static int getMaxBet() { return maxBet; }


	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}

	
