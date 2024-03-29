package finflow.model;

/**
 * User entity class
 **/
public class User {
	// Unique identifier for a user
	private int id;
	
	// User's userName
    private String username;
    
    // User's password
    private String password;
    
    // User's first name
    private String firstName;
    
    // User's last name
    private String lastName;
    
    // User's phone
    private String phone;
    
    /**
     * Default constructor.
     */
    public User(){
    	
    }
    
    /**
     * Parameterized constructor to initialize a user with all attributes.
     * @param id user's ID
     * @param username user's username
     * @param password user's password
     * @param fname user's first name
     * @param lname user's last name
     * @param phone user's phone number
     */
	public User(int id, String username, String password, String firstName, String lastName, String phone) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}  
	
	// Getter and Setter methods for all attributes 
	/**
     * Get user's id.
     * @return user's id
     */
	public int getId() {
		return id;
	}
	/**
     * Set user's id.
     * @param user's id
     */
	public void setId(int id) {
		this.id = id;
	}
	/**
     * Get user's userName.
     * @return user's userName
     */
	public String getUsername() {
		return username;
	}
	/**
     * Set user's userName.
     * @param user's userName
     */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
     * Get user's password.
     * @return user's password
     */
	public String getPassword() {
		return password;
	}
	/**
     * Set user's password.
     * @param user's password
     */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
     * Get user's firstName.
     * @return user's firstName
     */
	public String getFirstName() {
		return firstName;
	}
	/**
     * Set user's firstName.
     * @param user's firstName
     */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
     * Get user's lastName.
     * @return user's lastName
     */
	public String getLastName() {
		return lastName;
	}
	/**
     * Set user's lastName.
     * @param user's lastName
     */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
     * Get user's phone.
     * @return user's phone
     */
	public String getPhone() {
		return phone;
	}
	/**
     * Set user's phone.
     * @param user's phone
     */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
     * Override of the toString method to provide a string representation of the User object.
     * @return String representation of the User object
     */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + "]";
	}
}
