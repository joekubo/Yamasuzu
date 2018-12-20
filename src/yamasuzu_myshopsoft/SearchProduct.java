
package yamasuzu_myshopsoft;

public class SearchProduct {
    private int id;
    private String invoiceno;
    private String productcode;
    private String type;
    private String description;
    private String imei;
    private String unitprice;
    private String invoicedate;
    private int supplierid;
    private String suppliername;
    private double lowestprice;
    private double highestprice;
    private int qty;
    
    public SearchProduct(int Id, String Invoiceno, String Productcode, String Type, String Description,String Imei, String Unitprice,
            String Invoicedate,int Supplierid,String Suppliername, double Lowestprice, double Highestprice,int Qty){
        this.id = Id;
        this.invoiceno = Invoiceno;
        this.productcode = Productcode;
        this.type = Type;
        this.description = Description;
        this.imei = Imei;
        this.unitprice = Unitprice;
        this.invoicedate = Invoicedate;
        this.supplierid = Supplierid;
        this.suppliername = Suppliername;
        this.lowestprice = Lowestprice;
        this.highestprice = Highestprice;
        this.qty = Qty;
    }
    public int getId(){
        return id;
    }
    public String getInvoiceno(){
        return invoiceno;
    }
    public String getProductcode(){
        return productcode;
    }
    public String getType(){
        return type;
    }
    public String getDescription(){
        return description;
    }
    public String getImei(){
        return imei;
    }
    public String getUnitprice(){
        return unitprice;
    }
    public String getInvoicedate(){
        return invoicedate;
    }
    public int getSupplierid(){
        return supplierid;
    }
    public String getSuppliername(){
        return suppliername;
    }
    public double getLowestprice(){
        return lowestprice;
    }
    public double getHighestprice(){
        return highestprice;
    }
    public int getQty(){
        return qty;
    }
}
