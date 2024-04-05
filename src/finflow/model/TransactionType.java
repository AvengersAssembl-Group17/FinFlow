package finflow.model;

public class TransactionType {
	private int id;
	private String type;
	private String category;
	public TransactionType() {
		
	}
	
	public TransactionType(int id, String type, String category) {
		this.id = id;
		this.type = type;
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
