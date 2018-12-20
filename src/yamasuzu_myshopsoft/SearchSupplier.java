
package yamasuzu_myshopsoft;

public class SearchSupplier {
    private int id;
    private String name;
    private String phone;
    
    public SearchSupplier(int Id, String Name,String Phone){
        this.id = Id;
        this.name = Name;
        this.phone = Phone;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
}
