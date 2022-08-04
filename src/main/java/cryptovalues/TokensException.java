package cryptovalues;

public class TokensException extends Exception {

	private static final long serialVersionUID = 1L;
	private enum errorMessages {
		EMPTY_FILE {
		      public String toString() {
		          return "EMPTY_FILE";
		      }
		},
		ERROR_BALANCE {
		      public String toString() {
		          return "ERROR_BALANCE";
		      }
		},      
		NO_CONNECTION {
		      public String toString() {
		          return "NO_CONNECTION";
		      }
		}
	}
	private String error;
    
    public TokensException(String pError)
    {
        super();
        this.error=pError;
    }
     
    @Override
    public String getMessage(){
                 
        String message="";
        
        if (error.equals(errorMessages.EMPTY_FILE.toString()))
        	message="The configuration tokens file is empty";
        else if (error.equals(errorMessages.ERROR_BALANCE.toString()))
        	message="Error balance in tokens";
        else if (error.equals(errorMessages.NO_CONNECTION.toString()))
        	message="No connection to API for tokens";
        
        return message;
        
    }  
	
}
