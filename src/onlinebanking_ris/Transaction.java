package onlinebanking_ris;

import java.util.Date;

public class Transaction {
	
	private double amount;
	private Date timestamp;
	private String message;
	private Account inAccount;

	
	/**
	 * Constructs a new Transaction object
	 */
	public Transaction(double amount, Account inaccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = timestamp;
		this.message = "";
	}
	
	
	/**
	 * Create a new transaction
	 * @param amount
	 * @param message
	 * @param inAccount
	 */
	public Transaction(double amount, String message, Account inAccount) {
		// call the two-arg constructor first
		this(amount, inAccount);
		
		// set the memo message
		this.message = message;
	}


	/**
	 * Get the amount of the transaction
	 * @return
	 */
	public double getAmount() {
		return this.amount;
	}


	
	/**
	 * Get a String summarizing of the transaction
	 * @return
	 */
	public String getSummaryLine() {
		if(this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.message);
		} else {
			return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.message);
		}


	}

}
