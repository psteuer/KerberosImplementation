package implementation;

public class Kerberos {

	public static void main(String[] args) {

		// This is an implementation of the Kerberos Security Protocol
		// ____________________________________________________________________________________
		// 1. The User sends its identity (UserId) to the Kerberos Server. This is sent
		// via plaintext
		String userID = "IamUser";
		// User Id is sent to the Kerberos Sever in plaintext via an open channel
		// ____________________________________________________________________________________
		// 2. The Sever sends the user a Session Key S and Ticket T encrypted with the
		// user’s password. We’ll represent this as: Epwd(Sg,Tg)
		//TODO create session key S
		Ticket ticket = new Ticket();

	}

}
