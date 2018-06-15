import java.util.Formatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;
/*
 * [ Roulette ]
 * - Main driver class which runs the program
 * - Scans game and player files to import the data into the game
 * - Allows for multiple servers of a game version
 * - User may select which game server to join
 */
public class Roulette 
{
	static V100A chosenGameServer;
	static String gameFileName = "games.txt";
	static String playerFileName = "players.txt";

	// import game servers from game data file into this array
	static V100A serverList [];
	// import players from player data file into this queue
	static Queue<AbstractPlayer> playerList = new LinkedList<AbstractPlayer>();

	public static void main(String[] args) throws FileNotFoundException 
	{
		int userGameMenuChoice;
		int userMainMenuChoice;
		// create scanner to accept user input
		Scanner scan = new Scanner(System.in); 

		// imports game servers into the program
		Roulette.importGameFile(gameFileName);

		// imports all players into the program
		Roulette.importPlayerFile(playerFileName);

		System.out.println("Initializing games. Please wait ...\nAll games are ready.\n"
				+ "\nAvailable games: ");

		// print available game servers to join
		Roulette.displayServerList();
		// show main menu options
		Roulette.displayMainMenu();
		// obtain user main menu choice
		userMainMenuChoice = scan.nextInt();
		Roulette.validateMainMenuInput(userMainMenuChoice);

		// MAIN MENU OPTION 1: Select a game
		while(userMainMenuChoice == 1)
		{
			// match and create game server object
			Roulette.enterGameServer();
			// use that game object to show game menu options
			chosenGameServer.gameMenuOptions();
			// obtain user game menu choice
			userGameMenuChoice = scan.nextInt();
			
			// GAME MENU OPTION 1: Add a player to the game
			while(userGameMenuChoice == 1)
			{
				Roulette.addPlayerToGame();
				chosenGameServer.gameMenuOptions();
				// scan user input for their Game Menu choice
				userGameMenuChoice = scan.nextInt();
				Roulette.executeUserGameMenuChoice(userGameMenuChoice);
			}
			// GAME MENU OPTION 2: Play a round
			while(userGameMenuChoice == 2)
			{
				Roulette.playRound();
				chosenGameServer.gameMenuOptions();
				userGameMenuChoice = scan.nextInt();
				Roulette.executeUserGameMenuChoice(userGameMenuChoice);
				
			}
			// GAME MENU OPTION 3: Game Status
			while(userGameMenuChoice == 3)
			{
				chosenGameServer.displayGameStatus();
				chosenGameServer.gameMenuOptions();
				userGameMenuChoice = scan.nextInt();
				Roulette.executeUserGameMenuChoice(userGameMenuChoice);
			}
			// GAME MENU OPTION 4: Return to the main menu
			if(userGameMenuChoice == 4)
			{
				Roulette.displayMainMenu();
				userMainMenuChoice = scan.nextInt();
				Roulette.executeUserMainMenuChoice(userMainMenuChoice);
			}

			userMainMenuChoice = 0;
		}
		// MAIN MENU OPTION 2: Add a new player to the list
		while(userMainMenuChoice == 2)
		{
			Roulette.addPlayerToList();
			Roulette.displayMainMenu();
			userMainMenuChoice = scan.nextInt();
			Roulette.validateMainMenuInput(userMainMenuChoice);
			Roulette.executeUserMainMenuChoice(userMainMenuChoice);

		}
		// MAIN MENU OPTION 3: Quit
		while(userMainMenuChoice == 3)
		{
			Roulette.createTransactionsFile();
			userMainMenuChoice = scan.nextInt();
			Roulette.validateMainMenuInput(userMainMenuChoice);
		}
	} // end of main method


	public static void validateMainMenuInput(int userMainMenuChoice)
	{
		Scanner scan = new Scanner(System.in);
		while(userMainMenuChoice < 1 || userMainMenuChoice > 3)
		{
			System.out.println("\nError: Please enter a valid menu option (1-3):");
			userMainMenuChoice = scan.nextInt();
		}
	}
	public static void executeUserMainMenuChoice(int userMainMenuChoice)
	{
		Scanner scan = new Scanner(System.in);
		
		// MAIN MENU OPTION 1: Select a game
		while(userMainMenuChoice == 1)
		{
			// match and create game server object
			Roulette.enterGameServer();
			// use that game object to show game menu options
			chosenGameServer.gameMenuOptions();
			// obtain user game menu choice
			int userGameMenuChoice = scan.nextInt();
			
			
			Roulette.executeUserGameMenuChoice(userGameMenuChoice);
			
			userMainMenuChoice = 0;
		}
		// MAIN MENU OPTION 2: Add a new player to the list
		while(userMainMenuChoice == 2)
		{
			Roulette.addPlayerToList();
			Roulette.displayMainMenu();
			userMainMenuChoice = scan.nextInt();
			Roulette.validateMainMenuInput(userMainMenuChoice);

		}
		// MAIN MENU OPTION 3: Quit
		while(userMainMenuChoice == 3)
		{
			Roulette.createTransactionsFile();
			userMainMenuChoice = scan.nextInt();
			Roulette.validateMainMenuInput(userMainMenuChoice);
		}
	}
	
	public static void executeUserGameMenuChoice(int userGameMenuChoice)
	{
		Scanner scan = new Scanner(System.in);
		// GAME MENU OPTION 1: Add a player to the game
		while(userGameMenuChoice == 1)
		{
			Roulette.addPlayerToGame();
			chosenGameServer.gameMenuOptions();
			// scan user input for their Game Menu choice
			userGameMenuChoice = scan.nextInt();
		}
		// GAME MENU OPTION 2: Play a round
		while(userGameMenuChoice == 2)
		{
			Roulette.playRound();
			chosenGameServer.gameMenuOptions();
			userGameMenuChoice = scan.nextInt();
			
		}
		// GAME MENU OPTION 3: Game Status
		while(userGameMenuChoice == 3)
		{
			chosenGameServer.displayGameStatus();
			chosenGameServer.gameMenuOptions();
			userGameMenuChoice = scan.nextInt();
		}
		// GAME MENU OPTION 4: Return to the main menu
		if(userGameMenuChoice == 4)
		{
			Roulette.displayMainMenu();
			int userMainMenuChoice = scan.nextInt();
			Roulette.executeUserMainMenuChoice(userMainMenuChoice);
		}
	}
	public static void addPlayerToList()
	{
		Scanner scan = new Scanner(System.in);
		// show player submenu screen
		System.out.println(
				"\nSelect a player type:\n"
						+ "\n	1. Regular Player"
						+ "\n	2. VIP Player"
						+ "\n	3. Super VIP Player"
						+ "\n	4. Return to main menu"); 
		int userPlayerSubMenuChoice = scan.nextInt();

		Roulette.userPlayerSubMenuChoice(userPlayerSubMenuChoice);
	}

	public static void playRound()
	{
		int playerIndex = 1;
		// iterate through entire in-game player list
		// ask each player to make a bet
		while(chosenGameServer.getPlayer(playerIndex) != null)
		{
			Scanner scan = new Scanner(System.in);
			AbstractPlayer currentPlayer = chosenGameServer.getPlayer(playerIndex);
			System.out.println("\n[ Player " + currentPlayer.getSeatNumber() + "'s Turn ] ");
			System.out.println();
			chosenGameServer.playerMenuOptions(); // show bet options
			// if user picks player menu option 1-3, that means they are making a bet
			// call makeBet
			int userPlayerMenuChoice = scan.nextInt();
			// if user selects option 1-3, show player bet options
			if(userPlayerMenuChoice <= 3)
			{
				currentPlayer.makeBet(userPlayerMenuChoice); // scan users bet option choice
			}


			// else if option 4..... add funds to balance screen pops up
			while(userPlayerMenuChoice == 4)
			{
				currentPlayer.addFunds();
				chosenGameServer.playerMenuOptions();
				userPlayerMenuChoice = scan.nextInt();
				Roulette.executeUserPlayerMenuChoice(userPlayerMenuChoice, currentPlayer);
				
			}
			// else if option 5.... skip a round
			if(userPlayerMenuChoice == 5)
			{
				currentPlayer.incrementSkipCount();
				// if player skips more than two times, then they are removed from the server
				if(currentPlayer.getSkipCount() > V100A.SKIP_MAX)
				{
					chosenGameServer.removePlayer(currentPlayer);
					System.out.println("\nYou have reached the skip limit, so you have been removed from the game.");
				}
			}
			// else if option 6... leave the game
			if(userPlayerMenuChoice == 6)
			{
				chosenGameServer.removePlayer(currentPlayer);
				// A player is able to see his/her winning amount when he or she leaves the game 
				// (include cash back credit and bonus if applicable).
				currentPlayer.printPlayerStats();
				// player .getTotalWinnings() - player .getCashBackCredit() - player .getSuperVipBonus()
			}

			playerIndex++; // increment to the next player and ask them to make a bet
		}
		// once all players in the game have made a bet, spin the wheel
		chosenGameServer.spin();
		// payoff to each player and add the payoff to each transaction object
		// print all results of each player into their own separate transaction object
		chosenGameServer.pay();
		System.out.println();
	}

	public static void executeUserPlayerMenuChoice(int userPlayerMenuChoice, AbstractPlayer currentPlayer)
	{
		Scanner scan = new Scanner(System.in);
		if(userPlayerMenuChoice <= 3)
		{
			currentPlayer.makeBet(userPlayerMenuChoice); // scan users bet option choice
		}


		// else if option 4..... add funds to balance screen pops up
		while(userPlayerMenuChoice == 4)
		{
			currentPlayer.addFunds();
			chosenGameServer.playerMenuOptions();
			userPlayerMenuChoice = scan.nextInt();
			Roulette.executeUserGameMenuChoice(userPlayerMenuChoice);
			
		}
		// else if option 5.... skip a round
		while(userPlayerMenuChoice == 5)
		{
			currentPlayer.incrementSkipCount();
			// if player skips more than two times, then they are removed from the server
			
			if(currentPlayer.getSkipCount() > V100A.SKIP_MAX)
			{
				chosenGameServer.removePlayer(currentPlayer);
				System.out.println("\nYou have reached the skip limit, so you have been removed from the game.");
			}
		}
		// else if option 6... leave the game
		while(userPlayerMenuChoice == 6)
		{
			chosenGameServer.removePlayer(currentPlayer);
			// A player is able to see his/her winning amount when he or she leaves the game 
			// (include cash back credit and bonus if applicable).
			currentPlayer.printPlayerStats();
			// player .getTotalWinnings() - player .getCashBackCredit() - player .getSuperVipBonus()
		}
	}
	public static void createTransactionsFile()
	{
		// export game data to text file
		String outputName = "game-results.txt";
		System.out.println("\nGenerating report ...\nShutting down all games.");
		try
		{
			// set up the output file stream
			File file = new File(outputName);
			FileWriter writer = new FileWriter(file, true);
			PrintWriter outFile = new PrintWriter(writer);
			
			// print header to the output file
			outFile.println();
			outFile.println("Game: " + chosenGameServer.getGameID()
			+ "\nInitial game balance: " + chosenGameServer.getStartBalance()
			+ "\nEnding game balance: " + chosenGameServer.getEndBalance()
			+ "\n\nWinning/Losing amount: " + chosenGameServer.getNetBalance()
			+ "\nRound " + chosenGameServer.getRoundCount() + " (" + chosenGameServer.getColor() + " " +chosenGameServer.getBallPosition() + ")");
			
			outFile.println("\nTrans\tPlayer\t\tBet Amount\tBet Type\tPay");
			// print transaction objects toString
			outFile.println(chosenGameServer.printTransactions());

			System.out.println("\n" + outputName + " generated.");

			outFile.close();
		}
		catch(IOException e)
		{
			System.out.println("Error: Problem occured during creation of " + outputName);
		}
	}
	public static void addPlayerToGame()
	{
		if(playerList.peek() == null)
			System.out.println("Error: There are no more available players to add to the game.");
		if(playerList.peek() != null)
		{
			// dequeue player from queue
			AbstractPlayer newPlayer = playerList.remove();
			// add them to the game by creating new transaction and assigning them a seat id
			chosenGameServer.addPlayer(newPlayer); // store this method call ??

			// print to console which kind of player was added
			System.out.println("\nNew player was added to the game! \n" 
						+ newPlayer + "\nAssigned to Seat #" + newPlayer.getSeatNumber() + "\n");
		}
	}
	public static void importGameFile(String gameFileName) throws FileNotFoundException
	{
		// import game data file
		// creates object of games data file
		Scanner gameFileScan = new Scanner(new File(gameFileName));
		// stores game version from game object
		String versionName = gameFileScan.next();
		// stores number of games from game object
		int numberOfGames = gameFileScan.nextInt();

		// array of game servers. Note: index 0 contains nothing
		serverList = new V100A[numberOfGames+1];

		// iterate and create game objects for each server 
		// of the data file and store them into array for later referencing
		for (int i = 1; i <= numberOfGames; i++) 
		{
			int minBet = gameFileScan.nextInt();
			int maxBet = gameFileScan.nextInt();
			int houseBalance =  gameFileScan.nextInt();
			String gameID = "100A" + i;
			serverList[i] = new V100A(minBet, maxBet, houseBalance, gameID);
		}
	}

	public static void importPlayerFile(String playerFile) throws FileNotFoundException
	{
		// import player data file
		Scanner playerFileScan = new Scanner(new File(playerFile));
		while(playerFileScan.hasNextLine())
		{
			int playerType = playerFileScan.nextInt(); // regular/vip/super vip

			// if player is REGULAR
			if(playerType == 0)
			{
				int startingBalance = playerFileScan.nextInt();
				RegularPlayer regularPlayer = new RegularPlayer(startingBalance);
				playerList.add(regularPlayer);
			}
			// if player is VIP
			else if(playerType == 1)
			{
				int startingBalance = playerFileScan.nextInt();
				int vipID = playerFileScan.nextInt();
				String firstName = playerFileScan.next();
				String lastName = playerFileScan.next();
				VipPlayer vipPlayer = new VipPlayer(startingBalance, vipID, firstName, lastName);
				playerList.add(vipPlayer);
			}
			// if player is SUPER VIP
			else if(playerType == 2)
			{
				int startingBalance = playerFileScan.nextInt();
				int vipID = playerFileScan.nextInt();
				String firstName = playerFileScan.next();
				String lastName = playerFileScan.next();
				SuperVipPlayer superVipPlayer = new SuperVipPlayer(startingBalance, vipID, firstName, lastName);
				playerList.add(superVipPlayer);
			}
		}
	}

	public static void displayMainMenu()
	{
		System.out.println("Main Menu:");
		System.out.println("	1. Select a game "
						+ "\n	2. Add a new player to the list"
						+ "\n	3. Quit");
	}

	public static void displayServerList()
	{
		for(int i = 1; i < serverList.length; i++)
		{
			System.out.println("100A" + i);
		}
		System.out.println();
	}

	public static void enterGameServer()
	{
		Scanner scan = new Scanner(System.in);
		// prompt to select a game server
		System.out.println("\nSelect a game -->");
		// user types in game server name they wish to join. Example: 100A1
		// TODO: exception: if user types invalid game server name
		String userGameSelection = scan.next();

		for(int i = 1; i < serverList.length; i++)
		{
			// find matching server based off user input
			if(userGameSelection.equals(serverList[i].getGameID()))
			{
				// join that server
				chosenGameServer = serverList[i];
			}
		}
	}

	public static void userPlayerSubMenuChoice(int userPlayerSubMenuChoice)
	{
		Scanner scan = new Scanner(System.in);

		int startingBalance;
		int vipID;
		String firstName, lastName;

		// if user selects to add REGULAR player...
		if(userPlayerSubMenuChoice==1)
		{
			System.out.println("\nEnter Regular player's starting balance:\n");
			startingBalance = scan.nextInt();

			playerList.add(new RegularPlayer(startingBalance));

			System.out.println("\nA regular player has been added to the game!\n");
		}
		// if user selects to add VIP player...
		else if(userPlayerSubMenuChoice==2)
		{
			System.out.println("\nEnter VIP player's first name:\n");
			firstName = scan.next();
			System.out.println("\nEnter VIP player's last name:\n");
			lastName = scan.next();
			System.out.println("\nEnter VIP player's 4-digit number:\n");
			vipID = scan.nextInt();
			System.out.println("\nEnter VIP player's starting balance:\n");
			startingBalance = scan.nextInt();

			playerList.add(new VipPlayer(startingBalance, vipID, firstName, lastName));

			System.out.println("\nVIP Player " + firstName + " " + lastName + " has been added to the game!\n");
		}
		// if user selects to add SUPER VIP player...
		else if(userPlayerSubMenuChoice==3)
		{
			System.out.println("\nEnter Super VIP player's first name:\n");
			firstName = scan.next();
			System.out.println("\nEnter Super VIP player's last name:\n");
			lastName = scan.next();
			System.out.println("\nEnter VIP player's 4-digit ID number:\n");
			vipID = scan.nextInt();
			System.out.println("\nEnter VIP player's starting balance:\n");
			startingBalance = scan.nextInt();

			playerList.add(new SuperVipPlayer(startingBalance, vipID, firstName, lastName));

			System.out.println("\nSuper Vip " + firstName + " " + lastName + " has been added to the game!\n");
		}
		// return to main menu
		else if(userPlayerSubMenuChoice==4)
		{
			Roulette.displayMainMenu();
			int userMainMenuChoice = scan.nextInt();
			Roulette.executeUserMainMenuChoice(userMainMenuChoice);
		}
	}
}

