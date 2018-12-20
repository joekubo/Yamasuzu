package yamasuzu_myshopsoft;

public class SearchRemaining {
    private String modelno;
    private String type;
    private String description;
    private int remaining;
    
    public SearchRemaining(String Modelno, String Type,String Description, int Remaining){
        this.modelno = Modelno;
        this.type = Type;
        this.description = Description;
        this.remaining = Remaining;
    }
    public String getModelno(){
        return modelno;
    }
    public String getType(){
        return type;
    }
    public String getDescription(){
        return description;
    }
    public int getRemaining(){
        return remaining;
    }
}
