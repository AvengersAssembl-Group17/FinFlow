package finflow.model;

import java.util.Date;

public class Transaction {
	private int transactionId;
    private int userId;
    private String title;
    private double amount;
    private String type;
    private Date date;
    private String notes;
    
	public Transaction(int transactionId, int userId, String title, double amount, String type, Date date,
			String notes) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.title = title;
		this.amount = amount;
		this.type = type;
		this.date = date;
		this.notes = notes;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
