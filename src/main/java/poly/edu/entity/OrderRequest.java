package poly.edu.entity;

public class OrderRequest {
	private String receiverName;
    private String phoneNumber;
    private String address;

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String name) { this.receiverName = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phone) { this.phoneNumber = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
