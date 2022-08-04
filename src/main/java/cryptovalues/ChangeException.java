package cryptovalues;

public class ChangeException extends Exception {

	private static final long serialVersionUID = 1L;
	private enum errorMessages {
		EMPTY_FILE {
		      public String toString() {
		          return "EMPTY_FILE";
		      }
		}
	}
	private String error;
	
    public ChangeException(String pError)
    {
        super();
        this.error=pError;
    }
     
    @Override
    public String getMessage(){
         
        String message="";
        
        
        if (error.equals(errorMessages.EMPTY_FILE.toString()))
        	message="The configuration change file is empty";
        
        return message;
        
    }
	
}
