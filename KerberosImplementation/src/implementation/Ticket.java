package implementation;

public class Ticket {
	public String userId;
	public String accessableServices;
	public long currentTime;
	public String controlInformation;

	//Constructor
	public Ticket() {
		currentTime = System.currentTimeMillis();
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the accessableServices
	 */
	public String getAccessableServices() {
		return accessableServices;
	}

	/**
	 * @param accessableServices the accessableServices to set
	 */
	public void setAccessableServices(String accessableServices) {
		this.accessableServices = accessableServices;
	}

	/**
	 * @return the currentTime
	 */
	public long getCurrentTime() {
		return currentTime;
	}

	/**
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * @return the controlInformation
	 */
	public String getControlInformation() {
		return controlInformation;
	}

	/**
	 * @param controlInformation the controlInformation to set
	 */
	public void setControlInformation(String controlInformation) {
		this.controlInformation = controlInformation;
	}

	@Override
	public String toString() {
		return "userId: " + userId + " AccessableServices: "+ accessableServices + " currentTime: " + currentTime + " controlInformation: " + controlInformation;
	}

}
