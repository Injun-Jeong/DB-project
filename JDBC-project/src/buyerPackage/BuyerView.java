package buyerPackage;

public class BuyerView {
	public void printCommand() {
		System.out.println(
				"\nHello, Buyer.\nPlease select the wanted command.\n"
				+ "1. Change Account information\n"
				+ "2. See Order history\n"
				+ "3. Search Vehicles By Make\n"
				+ "4. Search Vehicles By Name\n"
				+ "5. Advanced Search\n"
				+ "6. Detailed Vehicle Information & Shopping\n"
				+ "7. Log out\n"
				+ "8. Sign Out\n"
				+ "9. Close Application\n");
		System.out.print("Input: ");
	}
}