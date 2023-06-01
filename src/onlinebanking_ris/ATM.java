package onlinebanking_ris;

import java.util.Scanner;

public class ATM {


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Bank bank = new Bank("Bank of Ottawa");
		
		
		// add a user to the bank, also creates a savings account
		User user = bank.addUser("Ris", "Xu", "1234");
		
		
		// add a checking account for user
		Account account = new Account("Checking", user, bank);
		user.addAccount(account);
		bank.addAccount(account);
		
		
		User curUser;
		while(true) {
			
			// stay in login prompt until success login
			curUser = ATM.mainMenuPrompt(bank, sc);
			
			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
		
		
		
	}


	private static User mainMenuPrompt(Bank bank, Scanner sc) {

		// initializing user prompt
		String userID;
		String pin;
		User authUser;
		
		
		// prompt the user for their ID/pin combo until a successful login
		do {
			System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			// try to get the user object corresponding to the ID and pin combo
			authUser = bank.userLogIn(userID, pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination, please try again.");
			}
			
		} while(authUser == null); // continue looping until successful login
		
		
		return authUser;
	}
	
	

	/**
	 * Print user menu for users to choose the kind of service
	 */
	private static void printUserMenu(User user, Scanner sc) {

		// print a summary of the user's account
		user.printAccountSum();
		
		// inits
		int choice;
		
		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", user.getFirstName());
			
			System.out.println("   1) Show account transaction history");
			System.out.println("   2) Withdraw");
			System.out.println("   3) Deposit");
			System.out.println("   4) Transfer");
			System.out.println("   5) Quit");
			System.out.println();
			
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid choice, please choose 1-5");
			}
		}while(choice < 1 || choice > 5);
		
		
		// process the choice
		switch(choice) {
		
		case 1:
			ATM.showTransHistory(user, sc);
			break;
		case 2:
			ATM.witihdrawFunds(user, sc);
			break;
		case 3:
			ATM.depositFunds(user, sc);
			break;
		case 4:
			ATM.transferFunds(user, sc);
			break;
		case 5:
			// gobble up rest of previous input
			sc.nextLine();
			break;
		}
		
		
		// redisplay this menu unless user wants to quit
		if(choice != 5) {
			ATM.printUserMenu(user, sc);
		}
	}

	
	
	
	/**
	 * Process a fund deposit to an account
	 * @param user
	 * @param sc
	 */
	private static void depositFunds(User user, Scanner sc) {
		
		// inits
		int toAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to deposit in: ", user.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(toAcct < 0 || toAcct >= user.numAccounts());
		acctBal = user.getAcctBalance(toAcct);


		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max: $%.02f): ", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0.");
			} 
		}while(amount < 0);


		// gobble up rest of previous input
		sc.nextLine();

		// get a memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdraw
		user.addAcctTrans(toAcct, amount, memo);
		
	}

	
	

	/**
	 * Process a fund withdraw from an account
	 * @param user the logged-in User object
	 * @param sc the Scanner object user for user input
	 */
	private static void witihdrawFunds(User user, Scanner sc) {

		// inits
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to withdraw from: ");
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(fromAcct < 0 || fromAcct >= user.numAccounts());
		acctBal = user.getAcctBalance(fromAcct);
		

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max: $%.02f): ", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance of $%.02f.\n", acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		// do the withdraw
		user.addAcctTrans(fromAcct, -1*amount, memo);
	}


	
	
	/**
	 * Transfer funds from one account to another
	 */
	private static void transferFunds(User user, Scanner sc) {

		// inits
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to transfer from: ");
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(fromAcct < 0 || fromAcct >= user.numAccounts());
		acctBal = user.getAcctBalance(fromAcct);
		
		// get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account to transfer to: ");
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(toAcct < 0 || toAcct >= user.numAccounts());

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max: $%.02f): ", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance of $%.02f.\n", acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		
		// do the transfer finally
		user.addAcctTrans(fromAcct, -1*amount, String.format("Transfer to account %s", user.getAcctUid(toAcct)));
		user.addAcctTrans(toAcct, amount, String.format("Transfer to account %s", user.getAcctUid(fromAcct)));
	}


	
	
	/**
	 * Show the transaction history for an account
	 * @param user
	 * @param sc
	 */
	private static void showTransHistory(User user, Scanner sc) {

		int theAccount;
		
		// get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", user.numAccounts());
			
			theAccount = sc.nextInt()-1;
			if(theAccount < 0 || theAccount >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAccount < 0 || theAccount >= user.numAccounts());
		
		
		// prints the transaction history
		user.printAcctTransHistory(theAccount);
		
	}

	
	
	
}
