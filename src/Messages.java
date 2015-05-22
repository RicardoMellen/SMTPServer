//Ricardo Mell�n and Francisco P�rez

public class Messages {

	// Connection
	String serverReady = "220  RicardoServer Service Ready \r\n";
	String serverConnection = "250 Requested mail action okay, completed \r\n";

	// Sending an e-mail
	String serverOK = "250 OK \r\n";
	String serverData = "354 Start mail input; end with <CRLF>.<CRLF> \r\n";

	// Closing the connection
	String serverClose = "221 RicardoServer Service closing transmission channel \r\n";

	// Error reply codes
	String serverCommand = "502 Syntax error, command unrecognized \r\n";
	String serverParameters = "501 Syntax error in parameters or arguments \r\n";
	String serverSequence = "503 Bad sequence of commands \r\n";
	String serverRecipients = "554 Not valid recipients (Only after DATA command) \r\n";
}