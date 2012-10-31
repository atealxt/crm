package org.claros.commons.auth.models;

/**
 * @author Umut Gokbayrak
 *
 */
public class AuthProfile {
    private String username;
    private String password;

    public AuthProfile() {
        super();
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
