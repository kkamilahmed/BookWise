package bookstore;
public class Customer {
    setCustomer registeredCustomer;

    public Customer(String username, String password)
    {
       registeredCustomer = new silverCustomer(username,password);
    }
    
    public Customer(String username, String password,int points)
    {
       registeredCustomer = new silverCustomer(username,password,points);
    } 
    
    public void setPoints(int points)
    {
       registeredCustomer.setPoints(points);
           if(registeredCustomer.getPoints()>=1000)
    {
        registeredCustomer = new goldCustomer(this.getUsername(),this.getPassword(),this.getPoints());
    }
                      if(registeredCustomer.getPoints()<1000)
    {
        registeredCustomer = new silverCustomer(this.getUsername(),this.getPassword(),this.getPoints());
    }
    }
    
    public String getUsername() {
        return registeredCustomer.getUsername();
    }

    public String getPassword() {
        return registeredCustomer.getPassword();
    }
    
    public int getPoints() {
        return registeredCustomer.getPoints();
    }
   
    public void updatePoints(double totalcost)
    {
    this.setPoints(this.getPoints() + (int)(10 * totalcost));
    if(registeredCustomer.getPoints()>=1000)
    {
        registeredCustomer = new goldCustomer(this.getUsername(),this.getPassword(),this.getPoints());
    }
    
        if(registeredCustomer.getPoints()<1000)
    {
        registeredCustomer = new silverCustomer(this.getUsername(),this.getPassword(),this.getPoints());
    }

    }
    
    public String getCustomerType() {
    if (registeredCustomer instanceof goldCustomer) {
        return "Gold";
    } else {
        return "Silver";
    }
}

    
}
