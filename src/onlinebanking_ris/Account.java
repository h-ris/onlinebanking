package onlinebanking_ris;

import java.util.ArrayList;

public class Account {

	private String name;
	//private double balance;
	private String uid;
	private User holder;
	private ArrayList<Transaction> transactions;
	
	
	/**
	 * Constructs a new Account object
	 */
	public Account(String name, User holder, Bank bank) {
		
		// set the account name and holder
		this.name = name;
		this.holder = holder;
		
		// get new account user id
		this.uid = bank.getNewAccountUid();
		
		// initialize transactions
		this.transactions = new ArrayList<Transaction>();
		
//		// add the account being created to the holder and bank lists
//		holder.addAcccount(this);
//		bank.addAccount(this);
		
	}

	

	/**
	 * Return the user id
	 * @return user id
	 */
	public String getUid() {
	return this.uid;
	}


	/**
	 * Returns the summary line of the account, including the account uid, balance, and name.
	 * @return the summary line
	 */
	public String getSummaryLine() {

		// get the account's balance
		double balance = this.getBalance();
		
		// format the summary line, depending on the whether the balance is negative
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uid, balance, this.name);
		}

	}



	/**
	 * Get the balance of this account by adding the amounts of the transaction
	 * @return the balance
	 */
	public double getBalance() {

		double balance = 0;
		for(Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}



	/**
	 * Print the transaction history of the account
	 */
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.uid);
		for(int t = this.transactions.size()-1; t >=0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}



	/**
	 * Add a new transaction in this account
	 * @param amount the amount transacted
	 * @param memo the transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		
		// create new transaction object and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}

}
