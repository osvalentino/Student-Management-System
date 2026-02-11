package roles;

/**
 * Abstract base class representing a user in the student management system.
 * Contains common attributes and methods shared by Student, Professor, and Admin.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public abstract class User {
	
    // instance variables
    /**
     * User's unique ID.
     */
    private String id;

    /**
     * User's full name.
     */
    private String name;

    /**
     * User's login username.
     */
    private String username;

    /**
     * User's login password.
     */
    private String password;


    // methods
    // constructor
    /**
     * Constructs a new User with the specified parameters.
     * @param id User's unique ID
     * @param name User's full name
     * @param username User's login username
     * @param password User's login password
     */
    public User(String id, String name, String username, String password){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // getters
    /**
     * Gets the user's ID.
     * @return User's ID
     */
    public String getID(){
        return this.id;
    }

    /**
     * Gets the user's name.
     * @return User's full name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the user's username.
     * @return User's login username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Gets the user's password.
     * @return User's login password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Returns string representation of the user.
     * @return User's ID and name
     */
    public String toString(){
        return getID() + " " + getName();
    }
}
