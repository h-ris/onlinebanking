package onlinebanking_ris;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	

	public Bank() {
	}


	
	/**
	 * Create a new Bank object with empty lists of users and accounts
	 * @param name the name of the bank
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	/**
	 * Generate a new user ID for user
	 * @return the uid
	 */
	public String getNewUserId() {
		// initialize the generator and generate a new user ID
		String uid;
		Random rng = new Random();
		
		// set the length of the ID to 8
		int length = 8;
		boolean nonUnique = false;
		
		// continue looping until we get a unique user ID
		do {
			uid = "";
			for(int i = 0; i < length; i++) {
				uid+=((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure it is unique
			for (User u : this.users) {
				if (uid.compareTo(u.getUid()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
		return uid;
	}


	public String getNewAccountUid() {
		// initialize the generator and generate a new user ID
		String uid;
		Random rng = new Random();
				
		// set the length of the ID to 10
		int length = 10;
		boolean nonUnique = false;
				
		// continue looping until we get a unique user ID
		do {
			uid = "";
			for(int i = 0; i < length; i++) {
				uid+=((Integer)rng.nextInt(10)).toString();
			}
					
			// check to make sure it is unique
			for (Account a : this.accounts) {
				if (uid.compareTo(a.getUid()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
		return uid;
	}


	/**
	 * Add an account for the user
	 * @param account a user account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	
	public User addUser(String firstName, String lastName, String pin) {
		// create a new User object and add it to the list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create a savings account for the user and add to User and Bank accounts lists
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	
	
	/**
	 * Get the User object associated with a particular user ID and pin, if they are valid
	 * @param uid user ID
	 * @param pin pin of the user
	 * @return the User object, if the login is successful or null if not
	 */
	public User userLogIn(String uid, String pin) {
		
		// search through the list of users
		for(User u : this.users) {
			
			// check user ID is correct or not
			if (u.getUid().compareTo(uid) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		// if have not found the user or have an incorrect pin
		return null;
	}

}
