package bookstore;

public abstract class setCustomer {
    
    private String username;
    private String password;
    private int points=0;

    public setCustomer(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public setCustomer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
