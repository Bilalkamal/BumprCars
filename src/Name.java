


public class Name {
	
	private String firstName ;   
	private String lastName ;

	
//	Constructor Method
	
	
    public Name(String firstName, String lastName) {
    	this.firstName = firstName;
    	this.lastName = lastName;

    }
    
    

    /**
     * Getter methods for Name
     * @param Name
     */
    
    public String getFirstName() {
        return this.firstName;
        
    }
    public String getlastName() {
        return this.lastName;
        
    }
    
    public String getLastFirst() {
    	return this.lastName + ", " + this.firstName;
    }


    /**
     * Setter method for Name
     * @param Name
     */
    
    
    public void setFirstName(String firstName) {
    	if (firstName != "" ||firstName != null) {
  		  this.firstName = firstName;
  	}else {
  		System.out.println("Invalid firstName");
  	}
    	
    
    }
    public void setLastName(String lastName) {
    	
    	if (lastName != "" ||lastName != null) {
    		  this.lastName = lastName;
    	}else {
    		System.out.println("Invalid lastname");
    	}
    	
      
    }

    
	
}
