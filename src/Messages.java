//Ricardo Mell�n and Francisco P�rez

public class Messages {

	// Connection
	static String serverReady = "220  RicardoServer Service Ready \r\n";
	static String serverConnection = "250 Requested mail action okay, completed \r\n";

	// Sending an e-mail
	static String serverOK = "250 OK \r\n";
	static String serverData = "354 Start mail input; end with <CRLF>.<CRLF> \r\n";

	// Closing the connection
	static String serverClose = "221 RicardoServer Service closing transmission channel \r\n";

	// Error reply codes
	static String serverCommand = "502 Syntax error, command unrecognized \r\n";
	static String serverParameters = "501 Syntax error in parameters or arguments \r\n";
	static String serverSequence = "503 Bad sequence of commands \r\n";
	static String serverRecipients = "554 Not valid recipients (Only after DATA command) \r\n";
	
	static String logs = "LOGS/";
	static String txt = ".txt";
}
