package drunkr.api;

public class APIError {
	public enum APIErrorCode {
		UsernameAlreadyTaken,
		InvalidUsername,
		InvalidPassword,
		InvalidEmail,
		UnhandledException,
		TooManyResultsFound,
		CombinationNotFound,
		InvalidName,
		EmptyIngredients,
		InvalidIngredient,
		InvalidBACInput
	}
	
	public APIErrorCode code;
	public String message;
	
	public APIError(APIErrorCode c, String message) {
		code = c;
		this.message = message;
	}
}
