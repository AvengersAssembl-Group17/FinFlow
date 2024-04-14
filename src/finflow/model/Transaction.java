package finflow.model;

import java.util.Date;

public class Transaction {
	private int id;
    private int userId;
    private String title;
    private double amount;
    private int type;
    private Date date;
    
    public Transaction() { 	
    }
    
	public Transaction(int id, int userId, String title, double amount, int type, Date date) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.amount = amount;
		this.type = type;
		this.date = date;
	}
	public int getTransactionId() {
		return id;
	}
	public void setTransactionId(int transactionId) {
		this.id = transactionId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
