import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.StringTokenizer;

//Ricardo Mell�n and Francisco P�rez

public class Connection extends Thread {

	int port = 25, maxUsers = 50, arrayCount = 0;
	ServerSocket sServ;
	Socket sCon;
	BufferedReader input;
	PrintWriter output;
	StringTokenizer st;
	File fileLog, fileMessage;
	String email = null, rutaMessage, rutaLog;
	String[] stringHelo, stringMail, stringRcpt;
	String [] emails = new String[maxUsers];
	BufferedWriter bwLog, bwMessage;
	Boolean control = true, helo = false;
	Calendar date = Calendar.getInstance();
	Messages m = new Messages();

	public void run() {
		try {
			sServ = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				sCon = sServ.accept();
				input = new BufferedReader(new InputStreamReader(
						sCon.getInputStream()));
				output = new PrintWriter(sCon.getOutputStream(), true);
				output.print(m.serverReady);
				output.flush();
				String read = "";
				try {
					read = input.readLine();
					System.out.println(read);
					stringHelo = read.split(" ", 2);
					if (stringHelo[1] != null) {
						rutaLog = "LOGS/" + stringHelo[1] + ".txt";
					} else {
						rutaLog = "LOGS/anonymous.txt";
					}
					fileLog = new File(rutaLog);
					bwLog = new BufferedWriter(new FileWriter(fileLog, true));
					bwLog.write(" \n");
					bwLog.write(m.serverReady + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (read.startsWith("HELO ")) {
					output.print(m.serverConnection);
					output.flush();
					bwLog.write("The user says Helo.\n");
					bwLog.write(read + "\n");
					bwLog.write(m.serverConnection + "\n");
					helo = true;
				}// if HELO
				else {
					output.println(m.serverCommand);
					output.flush();
					bwLog.write(m.serverCommand + "\n");
					bwLog.close();
				}
				if (helo == true) {
					while (!read.equals("QUIT")) {
						try {
							read = input.readLine();
							System.out.println(read);
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (read.startsWith("MAIL")) {
							stringMail = read.split(" ", 3);
							if (EmailOk.checkEmail(stringMail[2])) {
								output.print(m.serverOK);
								output.flush();
								bwLog.write("The user writes Mail.\n");
								bwLog.write(read + "\n");
								bwLog.write(m.serverOK + "\n");
							} else {
								output.print(m.serverRecipients);
								output.flush();
								bwLog.write(m.serverRecipients + "\n");
								break;
							}
							try {
								read = input.readLine();
								System.out.println(read);
							} catch (IOException e) {
								e.printStackTrace();
							}
							arrayCount = 0;
							while (read.startsWith("RCPT")) {
								if (read.startsWith("RCPT")) {
									stringRcpt = read.split(" ", 3);
									if (EmailOk.checkEmail(stringRcpt[2])) {
										output.print(m.serverOK);
										output.flush();
										bwLog.write("The user writes RCPT.\n");
										bwLog.write(read + "\n");
										bwLog.write(m.serverOK + "\n");
										emails[arrayCount] = stringRcpt[2];
										arrayCount++;
									} else {
										output.print(m.serverRecipients);
										output.flush();
										bwLog.write(m.serverRecipients + "\n");
										break;
									}

									read = input.readLine();
									System.out.println(read);
								} else {
									output.println(m.serverCommand);
									output.flush();
									bwLog.write(m.serverCommand + "\n");
									bwLog.close();
									break;
								}
							}// while RCTP
							if (read.startsWith("DATA")) {
								output.println(m.serverData);
								output.flush();
								bwLog.write("The user writes Data.\n");
								bwLog.write(read + "\n");
								bwLog.write(m.serverData + "\n");
								read = input.readLine();
								saveMessage(emails,".\n\n"+ read + "\n");
								System.out.println(read);
								bwLog.write(read + "\n");
								while (!read.endsWith(".")) {
									read = input.readLine();
									if (read.equals("QUIT")) {
										System.out.println(m.serverParameters);
										bwLog.write(m.serverParameters + "\n");
										control = false;
										break;
									}
									System.out.println(read);
									bwLog.write(read + "\n");
									saveMessage(emails, read + "\n");
								}
								if (control == true) {
									output.println(m.serverOK);
									output.flush();
									bwLog.write(m.serverOK + "\n");
									read = input.readLine();
									System.out.println(read);
								}
							} // if Data
							else {
								output.println(m.serverCommand);
								output.flush();
								bwLog.write(m.serverCommand + "\n");
								bwLog.close();
								break;
							}
						}// if Mail
						else {
							output.println(m.serverCommand);
							output.flush();
							bwLog.write(m.serverCommand + "\n");
							break;
						}
					}// while QUIT
					bwLog.write("The user closes the connection.\n");
					output.println(m.serverClose);
					output.flush();
					bwLog.write(m.serverClose + "\n");
					bwLog.close();
				}// if helo
				try {
					sCon.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}// First TRY
		}// while TRUE
	}// run()

	public void saveMessage(String[] emails, String message) {
		for (int i = 0; i <= arrayCount-1; i++) {
			try {
				rutaMessage = "MESSAGE/" + emails[i] + "-" + Calendar.DATE+ "-"+Calendar.MONTH +"-"+Calendar.DAY_OF_WEEK_IN_MONTH+ ".txt";
				fileMessage = new File(rutaMessage);
				bwMessage = new BufferedWriter(
						new FileWriter(fileMessage, true));
				bwMessage.write(message);
				bwMessage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}// Class