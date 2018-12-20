
package yamasuzu_myshopsoft;

public class SearchClient {
    private int clientid;
    private String name;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String pin;

    public SearchClient(int clientid, String name, String address, String city, String phone, String email, String pin) {
        this.clientid = clientid;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.pin = pin;
    }

    public int getClientid() {
        return clientid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPin() {
        return pin;
    }
    
    
}
