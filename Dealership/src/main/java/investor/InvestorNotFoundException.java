package investor;

public class InvestorNotFoundException extends RuntimeException{
	
	public InvestorNotFoundException(Long id)  {
		super("User with id: " + id + "not found!");
	}

}
