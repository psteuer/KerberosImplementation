package implementation;


import java.io.IOException;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

public class Kerberos {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		// This is an implementation of the Kerberos Security Protocol
		// ____________________________________________________________________________________
		// 1. The User sends its identity (UserId) to the Kerberos Server. This is sent
		// via plaintext
		System.out.println(" ___________________________________________________________________________________");
		String userID = "IamUser";
		System.out.println("User sends their userid to the Kerberos Sever: " + userID);
		// User Id is sent to the Kerberos Sever in plaintext via an open channel
		System.out.println(" ___________________________________________________________________________________");
		// 2. The Sever sends the user a Session Key S and Ticket T encrypted with the
		// user’s password. We’ll represent this as: Epwd(Sg,Tg)
		String sessionKey = "distinctSessionKey"; // create distinct key
		byte[] sessionKeyBytes = sessionKey.getBytes();
		System.out.println("Created sessionkey = " + sessionKey + " that has " + sessionKeyBytes.length + " bytes");
		Ticket ticket = new Ticket();
		ticket.setUserId(userID);
		ticket.setControlInformation("controlInfo");
		ticket.setAccessableServices("file1");
		System.out.println("Created Ticket: " + ticket.toString());
		byte[] ticketBytes = SerializationUtils.serialize(ticket);

		System.out.println("Serialized the ticket into bytes, ticket is " + ticketBytes.length + " bytes long");
		System.out.println("Appending the ticket and session key");
		byte[] sessionTicket = ArrayUtils.addAll(sessionKeyBytes, ticketBytes);
		// encrypt using the password that corresponds to the user
		String password = "password";
		String eSessionTicket = AES.encrypt(sessionTicket, password);
		System.out.println("Encrypted SessionKey and Ticket using password: " + password);
		System.out.println("The enrypted eSessionTicket is =" + eSessionTicket);
		System.out.println(" ___________________________________________________________________________________");

		// 3. The Kerberos Server will send the Ticket-Granting Server (TGS) the Session
		// key S encrypted with the KS-TGS key (shared key).
		// Kerberos crates Ek(KS-TGS)(Sg, (UserId)) to send to the TGS
		String ks_tgs_key = "ThisIsTheSharedKeyKeepMeSecret";
		System.out.println("The shared key between KS and the TGS is =" + ks_tgs_key);
		String sessionAndUserID = sessionKey + userID;
		String esessionKeyForTGS = AES.encrypt(sessionAndUserID.getBytes(), ks_tgs_key);
		System.out.println("Will encrypt this the sessionAndUserID with the KS_TGS password: " + ks_tgs_key);
		System.out.println("The encrypted sessionAndUserID is: " + esessionKeyForTGS);
		System.out.println(" ___________________________________________________________________________________");

		// 4. The User will request access via the Ticket-Granting Server (TGS) to file
		// F. This message sent by the User to the TGS will be encrypted by Sg
		System.out.println("User wants to access the file: " + ticket.getAccessableServices());
		System.out.println("User Decrypts the Epwd(Sg,Tg) to get the session key");

		System.out.println(
				"User will send Es(g) (Request for Access file F) to the TGS the request for file will be encrypted via the sessionKey");
		String userToTGS = "file1";
		String decryptedSessionTicket = AES.decrypt(eSessionTicket, password);
		// Parse out the ticket and the Session key

		byte[] tempBytes = decryptedSessionTicket.getBytes();
		// Create ticket from bytes array
		byte[] recievedTicketBytes = Arrays.copyOfRange(tempBytes, 0, ticketBytes.length);
		System.out.println("Recieved Ticket Bytes: " + recievedTicketBytes.length);
		// Ticket recievedTicket = SerializationUtils.deserialize(recievedTicketBytes);
		// //This should work but its not :(
		// System.out.println("Ticket Recieved: " + recievedTicket.toString());
		tempBytes = Arrays.copyOfRange(tempBytes, ticketBytes.length + 1, tempBytes.length);
		String decryptedSessionKey = new String(tempBytes, "UTF-8");
		System.out.println("decryptedSessionKey is: " + decryptedSessionKey);
		String EuserToTGS = AES.encrypt(userToTGS.getBytes(), decryptedSessionKey);
		System.out.println("User will send " + userToTGS + " to the TGS");
		System.out.println("The Encrypted message the user will send to the TGS is = " + EuserToTGS);

		// 5. If approved, the TGS will send the user a ticket to the User
		System.out.println("TGS Check for approval");
		// TODO This should be recievedTicket
		if (ticket.getAccessableServices().equals(ticket.getAccessableServices())) {
			// Send ticket to user encrypted like Es(g)( Ek(TGS-FS) ( T, Sf )
			String validTicket = AES.encrypt((ticketBytes + decryptedSessionKey).getBytes(), decryptedSessionKey);
			// Send validTicket to user
			System.out.println("Sending the user a valid ticket to access the resources");
		} else {
			// Tell user their not approved
			System.out.println("User not approved for file " + ticket.getAccessableServices());
		}

	}
}
