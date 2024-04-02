package finflow.model;

import java.util.Date;

public class Transaction {
	private int transactionId;
    private int userId;
    private double amount;
    private String type;
    private Date date;
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Transaction(int transactionId, int userId, double amount, String type, Date date) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.amount = amount;
		this.type = type;
		this.date = date;
	}
}
