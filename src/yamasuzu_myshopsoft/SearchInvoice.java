
package yamasuzu_myshopsoft;

public class SearchInvoice {
    private int id;
    private int invoiceno;
    private String clientname;
    private double totalamount;
    private double balbf;
    private double paidamount;
    private double balance;
    private String invoicedate;

    public SearchInvoice(int id, int invoiceno, String clientname, double totalamount, double balbf, double paidamount, double balance,
                                                                                                                            String invoicedate) {
        this.id = id;
        this.invoiceno = invoiceno;
        this.clientname = clientname;
        this.totalamount = totalamount;
        this.balbf = balbf;
        this.paidamount = paidamount;
        this.balance = balance;
        this.invoicedate = invoicedate;
    }

    public int getId() {
        return id;
    }

    public int getInvoiceno() {
        return invoiceno;
    }

    public String getClientname() {
        return clientname;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public double getBalbf() {
        return balbf;
    }

    public double getPaidamount() {
        return paidamount;
    }

    public double getBalance() {
        return balance;
    }
    
    public String getInvoicedate(){
        return invoicedate;
    }
}
