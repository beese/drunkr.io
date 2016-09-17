package drunkr.api;

public class APIError {
	public enum APIErrorCode {
		UsernameAlreadyTaken,
		UnhandledException,
		TooManyResultsFound,
		CombinationNotFound
	}
	
	public APIErrorCode code;
	public String message;
	
	public APIError(APIErrorCode c, String message) {
		code = c;
		this.message = message;
	}
}
