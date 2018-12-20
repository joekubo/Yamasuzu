
package yamasuzu_myshopsoft;

public class SearchProduct2 {
    private int id;
    private String phonecode;
    private String type;
    private String description;
    
    public SearchProduct2(int Id,String Phonecode, String Type, String Description){
        this.id = Id;
        this.phonecode = Phonecode;
        this.type = Type;
        this.description = Description;
    }
    public int getId(){
        return id;
    }
    public String getPhonecode(){
        return phonecode;
    }
    public String getType(){
        return type;
    }
    public String getDescription(){
        return description;
    }
}
