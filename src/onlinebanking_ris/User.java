package onlinebanking_ris;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	private String firstName;
	private String lastName;
	private String uid; // unique id of the user
	private byte uPin[]; // pin of the user
	
	private ArrayList<Account> accounts;

	
	/**
	 * Create a new user
	 * @param firstName first name of the user
	 * @param lastName last name of the user
	 * @param pin user pin hash code
	 * @param bank the bank object that the user is the customer of
	 */
	public User(String firstName, String lastName, String pin, Bank bank) {
		// set user name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// generate unique user id
		this.uid = bank.getNewUserId();
		
		// store pin's hash but not the original value ensuring security
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.uPin = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error occurs, no such algorithm.");
			e.printStackTrace();
			System.exit(1);
		}
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s %s with ID: %s created sucessfully.\n", firstName,
				lastName, this.uid);
	}


	
	
	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}





	/**
	 * Add an account for the user
	 * @param account a user account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}


	/**
	 * Return the user's uid
	 * @return the user id
	 */
	public String getUid() {
		return this.uid;
	}
	
	
	
	/**
	 * Check whether the given pin matches the true user pin
	 * @param pin the pin to check
	 * @return whether the pin is valid or not
	 */
	public boolean validatePin(String pin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.uPin);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error occurs, no such algorithm.");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	
	}





	public void printAccountSum() {

		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}



	/**
	 * Get the number of accounts of the user
	 * @return
	 */
	public int numAccounts() {
		return this.accounts.size();
	}


	
	/**
	 * Print transaction history for a particular account
	 * @param acctIdx the index of the account to use
	 */
	public void printAcctTransHistory(int acctIdx) {

		this.accounts.get(acctIdx).printTransHistory();
	}



	/**
	 * Get the balance of a particular account
	 * @param acctIdx the index of the account to use
	 * @return the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}




	/**
	 * Get the UID of a particular account
	 */
	public String getAcctUid(int acctIdx) {
		return this.accounts.get(acctIdx).getUid();
	}





	/**
	 * Add a transaction to a particular account
	 * @param acctIdx
	 * @param amount
	 * @param memo
	 */
	public void addAcctTrans(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

}
