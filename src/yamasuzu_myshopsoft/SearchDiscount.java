
package yamasuzu_myshopsoft;

public class SearchDiscount {
    private int id;
    private String discountno;
    private String client;
    private int stockingid;
    private String phonecode;
    private String type;
    private String description;
    private String cost;
    private String paidamount;
    private String balance;
    private String date;
    
    public SearchDiscount(int Id, String Discountno,String Client,int Stockingid,String Phonecode,String Type,String Description, String Cost, String Paidamount, 
                                                                                                                     String Balance, String Date){
        this.id = Id;
        this.discountno = Discountno;
        this.client = Client;
        this.stockingid = Stockingid;
        this.phonecode = Phonecode;
        this.type = Type;
        this.description = Description;
        this.cost = Cost;
        this.paidamount = Paidamount;
        this.balance = Balance;
        this.date = Date;
    }
    public int getId(){
        return id;
    }
    public String getDiscountno(){
        return discountno;
    }
    public String getClient(){
        return client;
    }
    public int getStockingid(){
        return stockingid;
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
    public String getCost(){
        return cost;
    }
    public String getPaidamount(){
        return paidamount;
    }
    public String getBalance(){
        return balance;
    }
    public String getDate(){
        return date;
    }
}
