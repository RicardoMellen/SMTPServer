import java.util.regex.*;

//Ricardo Mell�n and Francisco P�rez

public class EmailOk {
	
	private static final String PATTERN_EMAIL = "<"+"[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*@"
	+"([0-9]+)(\\.[0-9]+)(\\.[0-9]+)(\\.[0-9]+)>";
	
	public static boolean checkEmail (String email) 
	{
	    Pattern p = Pattern.compile(PATTERN_EMAIL);
	    Matcher m = p.matcher(email.toLowerCase());

	    return m.matches;
	}
}
