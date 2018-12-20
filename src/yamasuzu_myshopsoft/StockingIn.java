
package yamasuzu_myshopsoft;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.Timer;
import net.proteanit.sql.DbUtils;

public class StockingIn extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    ResultSet rs1 =null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    private int stockingid;
    private int id;
    private int paymentid;
    private int categoryid;
    private int previousqty;
    private int removeid;
    private double balbf;
    private double initial_total;
    private String individual_multiple;
    Calendar c = Calendar.getInstance();
    public String loggeduserid;
    public String loggedusername;
    public String loggedpriviledge;
    private String imei;
    private int qty_;
    
    int dialogOpen;
    
    private String payno;
    Manage manage = new Manage();
    
    public StockingIn() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        buttonChooseSupplier.setToolTipText("Choose Supplier");
        searchingIcon.setToolTipText("Searching Supplier");
        c.add(Calendar.YEAR, 0);
        chooserDate.getDateEditor().setDate(c.getTime());
        txtTransactionId.setToolTipText("Transaction #");
        buttonPayments.setEnabled(false);
        button_Update.setEnabled(false);
    }
    //--------------------------------------------------DialogAllAccessories-------------------------------------------------------------------
    private void showDialogAllAccessories(){
        DialogAllproducts.setVisible(true);
        DialogAllproducts.setSize(468, 370);
        DialogAllproducts.setResizable(false);
        DialogAllproducts.setTitle("Select Product");
        DialogAllproducts.setLocationRelativeTo(this);
        DialogAllproducts.setAlwaysOnTop(true);
        findallProduct();
        disablePanel();
    }
    public void enablePanel(){
            //txtInvoiceNo.setEnabled(true);
            buttonChooseSupplier.setEnabled(true);
            buttonPayments.setEnabled(true);
            chooserDate.setEnabled(true);
            txtCodeNo.setEnabled(true);
            txtProduct.setEnabled(true);
            txtDescription.setEnabled(true);
            buttonSelect.setEnabled(true);
            buttonCategory.setEnabled(true);
            txtUnitprice.setEnabled(true);
            txtLowest.setEnabled(true);
            txtHighest.setEnabled(true);
            buttonAddCategories.setEnabled(true);
            buttonReset.setEnabled(true);
            buttonSave.setEnabled(true);
            buttonUpdate.setEnabled(true);
            buttonRemove.setEnabled(true);
            buttonExit.setEnabled(true);
            tableStocking.setEnabled(true);
    }
    public void disablePanel(){
            txtInvoiceNo.setEnabled(false);
            buttonChooseSupplier.setEnabled(false);
            buttonPayments.setEnabled(false);
            chooserDate.setEnabled(false);
            txtCodeNo.setEnabled(false);
            txtProduct.setEnabled(false);
            txtDescription.setEnabled(false);
            buttonSelect.setEnabled(false);
            buttonCategory.setEnabled(false);
            txtUnitprice.setEnabled(false);
            txtLowest.setEnabled(false);
            txtHighest.setEnabled(false);
            buttonAddCategories.setEnabled(false);
            buttonReset.setEnabled(false);
            buttonSave.setEnabled(false);
            buttonUpdate.setEnabled(false);
            buttonRemove.setEnabled(false);
            buttonExit.setEnabled(false);
            tableStocking.setEnabled(false);
    }
    
    private void selectedrowallproducts(){
        try{
            int row = tableAllproducts.getSelectedRow();
            String table_click = tableAllproducts.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM stockingtable,categorytable,stocktable WHERE stocktable.categoryid = categorytable.categoryid AND "
                    + " stockingtable.productcode = stocktable.productcode AND stocktable.productcode = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    txtTotal.setText("");
                    txtCodeNo.setText(rs.getString("stocktable.productcode"));
                    categoryid = rs.getInt("categorytable.categoryid");
                    txtProduct.setText(rs.getString("stocktable.type"));
                    txtDescription.setText(rs.getString("stocktable.description"));
                    txtCategory.setText(rs.getString("categorytable.category"));
                    txtUnitprice.setText(rs.getString("stockingtable.unitprice"));
                    txtLowest.setText(rs.getString("stocktable.lowestsellingprice"));
                    txtHighest.setText(rs.getString("stocktable.highestsellingprice"));
                    txtImei.setText(rs.getString("stockingtable.imei"));
                    txtImei.setEnabled(true);
                    individual_multiple = rs.getString("categorytable.type");
                    txtQty.requestFocus();
                    DialogAllproducts.dispose();
                    buttonSave.setEnabled(true);
                    buttonUpdate.setEnabled(false);
                    buttonRemove.setEnabled(false);
                        if(individual_multiple.equals("INDIVIDUAL")){
                            txtQty.setText("1");
                            txtQty.setEnabled(false);
                            txtImei.setText("");
                            txtImei.setEnabled(true);
                            txtImei.requestFocus();
                        }else{
                            txtQty.setText("");
                            txtQty.setEnabled(true);
                            txtQty.requestFocus();
                            txtImei.setText("---");
                            txtImei.setEnabled(false);
                        }
                        enablePanel();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.selectedrowallaccessories");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        calcproducts();
    }
    public void reset(){
        previousqty = 0;
        initial_total = 0;
        txtCodeNo.setText("");
        txtProduct.setText("");
        txtDescription.setText("");
        txtCategory.setText("");
        txtUnitprice.setText("");
        txtLowest.setText("");
        txtHighest.setText("");
        txtTotal.setText("");
        txtCodeNo.requestFocus();
        txtImei.setText("");
        txtImei.setEnabled(true);
        txtQty.setEnabled(true);
        buttonSave.setEnabled(true);
        buttonUpdate.setEnabled(false);
        buttonRemove.setEnabled(false);
        buttonDel.setEnabled(false);
        button_Update.setEnabled(false);
        tick.setVisible(false);
        c.add(Calendar.YEAR, 0);
        chooserDate.getDateEditor().setDate(c.getTime());
        lbl_exception.setText("");
        findProduct();
    }
    private void semireset(){
        previousqty = 0;
        initial_total = 0;
        txtImei.setText("");
        txtImei.requestFocus();
        buttonSave.setEnabled(true);
        buttonUpdate.setEnabled(false);
        buttonRemove.setEnabled(false);
    }
   
    private void savestock(){
        try{
            String sql = "SELECT * FROM stocktable WHERE status = '1' AND productcode = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtCodeNo.getText());
            rs = pst.executeQuery();
                if(rs.next()){
                    String query = "UPDATE stocktable SET type = '"+txtProduct.getText()+"',description = '"+txtDescription.getText()+"',"
                    + "categoryid = '"+categoryid+"',qty = '"+manage.totalstock_qty(txtCodeNo.getText())+"' WHERE status = '1' AND productcode = '"+txtCodeNo.getText()+"'";
                    manage.update(query);
                }else{
                    save_updatestock();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.savestock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
   
    private void save(){
        try{
            String supplier = txtSupplierPhone.getText();
            String invoiceNo = txtInvoiceNo.getText();
            String codeno = txtCodeNo.getText();
            String product = txtProduct.getText();
            String description = txtDescription.getText();
            String category = txtCategory.getText();
            String invoicedate = ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText();
            String qty = txtQty.getText();
            String unitprice = txtUnitprice.getText();
            String lowest = txtLowest.getText();
            String highest = txtHighest.getText();
            String total = txtTotal.getText();
            String status = "1";
            String imei_ = txtImei.getText();
            
                if(categoryid < 1){
                    showdialogcat();
                }else if(supplier.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Select Supplier");
                    buttonChooseSupplier.requestFocus();
                }else if(invoiceNo.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Invoice#");
                    txtInvoiceNo.requestFocus();
                }else if(codeno.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Code#");
                    txtCodeNo.requestFocus();
                }else if(product.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Product or Select from button 'Select'");
                    txtProduct.requestFocus();
                }else if(description.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Description");
                    txtDescription.requestFocus();
                }else if(category.equals("")){ 
                    JOptionPane.showMessageDialog(null, "Please Select Category");
                    buttonCategory.requestFocus();
                }else if(qty.equals("")){ 
                    JOptionPane.showMessageDialog(null, "Please Enter Qty");
                    txtQty.requestFocus();
                }else if(unitprice.equals("")){ 
                    JOptionPane.showMessageDialog(null, "Please Enter Unit Price");
                    txtUnitprice.requestFocus();
                }else if(imei_.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter IMEI");
                    txtImei.requestFocus();
                }else if(lowest.equals("")){ 
                    JOptionPane.showMessageDialog(null, "Please Enter Lowest Selling Price");
                    txtLowest.requestFocus();
                }else if(highest.equals("")){ 
                    JOptionPane.showMessageDialog(null, "Please Enter Highest Selling Price");
                    txtHighest.requestFocus();
                }else if(total.equals("") || total.equals("0")){ 
                    JOptionPane.showMessageDialog(null, "cannot Save Null or '0' Value ");
                }else{
                    String sql = "INSERT INTO stockingtable(invoiceno,productcode,qty,imei,unitprice,total,invoicedate,supplierid,userid,status)"
                            + " VALUES (?,?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, invoiceNo);
                    pst.setString(2, codeno);
                    pst.setString(3, qty);
                    pst.setString(4, imei_);
                    pst.setString(5, unitprice);
                    pst.setString(6, total);
                    pst.setString(7, invoicedate);
                    pst.setInt(8, id);
                    pst.setString(9, loggeduserid);
                    pst.setString(10, status);
                    
                    pst.execute();
                    savestock();
                    checksavepayment();
                    tick.setVisible(true);
                    //music();
                    Timer t = new Timer(1500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tick.setVisible(false);
                        }
                    });
                    t.start();
                    if(txtImei.getText().equals("---")){
                       reset(); 
                       txtQty.setText("");
                    }else{
                       semireset();
                    }
                    findProduct();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+ "stockingin.save");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private double initialpaymentbal;
    private double finalpaymentbal;
    private double updatedquantity;
    
    private void takefinalbalancevalue(){
        try{
            String sql = "SELECT * FROM paymenttable WHERE status = '1' AND details = '"+txtInvoiceNo.getText()+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    paymentid = rs.getInt("paymentid");
                    updatedquantity = finalpaymentbal - initialpaymentbal;
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+ "stockingin.takefirstpaymentvalue");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    
    private void save_updatestock(){
        try{
            String sql = "INSERT INTO stocktable(productcode,type,description,categoryid,qty,status,currentunitprice,lowestsellingprice"
                    + ",highestsellingprice)VALUES(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtCodeNo.getText());
            pst.setString(2, txtProduct.getText());
            pst.setString(3, txtDescription.getText());
            pst.setInt(4, categoryid);
            pst.setString(5, txtQty.getText());
            pst.setString(6, "1");
            pst.setString(7, txtUnitprice.getText());
            pst.setString(8, txtLowest.getText());
            pst.setString(9, txtHighest.getText());
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+ "stockingin.save_updatestock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    
    private void generatepayno(){
        try{
            String sql = "SELECT COALESCE(paymentid,1) FROM paymenttable  WHERE status = '1' ORDER BY paymentid DESC LIMIT 1 ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    int paymentno = rs.getInt("COALESCE(paymentid,1)");
                    payno = "PYMT-"+paymentno;
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.generatepayno");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void savepayment(){
        try{
            String amount = txtAmount.getText();
            amount = amount.trim();
            double _amount = Double.parseDouble(amount);
            if(_amount < 1){
               txtAmount.setText("");
               txtAmount.requestFocus();
            }else if(comboPaymentMethod.getSelectedItem().toString().equals("Select")){
                comboPaymentMethod.requestFocus();
            }else if(txtTransactionId.getText().equals("")){
                txtTransactionId.requestFocus();
            }else{
                String sql = "INSERT INTO paymenttable (supplierid,totalamount,paidamount,datepay,paymentmethod,transactionid,status,details)"
                        + "VALUES (?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);

                pst.setInt(1, id);
                pst.setString(2, "0");
                pst.setString(3, txtAmount.getText());
                pst.setString(4, ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText());
                pst.setString(5, comboPaymentMethod.getSelectedItem().toString());
                pst.setString(6, txtTransactionId.getText());
                pst.setString(7, "1");
                pst.setString(8, payno);

                pst.execute();
                evaluatebalancepayment();
                update_tablePayments();
                resetPayments();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.savingPayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }  
    }
    
    private void checksavepayment(){
        try{
            String sql = "SELECT * FROM paymenttable WHERE details = ? AND status = '1'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtInvoiceNo.getText());
            rs = pst.executeQuery();
                if(rs.next()){
                    updatepayment0();
                }else{
                    savepayment0();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.checksavepayment");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
            
    }
    private void updatepayment0(){
        try{
            String total_ = txtTotal.getText();
            total_ = total_.trim();
            double total = Double.parseDouble(total_);
            
            String sql = "UPDATE paymenttable SET totalamount =  totalamount + '"+total+"' WHERE details = '"+txtInvoiceNo.getText()+"'"
                    + " AND status = '1' AND supplierid = '"+id+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.updatepayment0");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
                
            }
        }
    }
//    private void updatepayment0(){
//        try{
//            String total = txtTotal.getText();
//            total = total.trim();
//            double $total = Double.parseDouble(total);
//            String sql = "UPDATE paymenttable SET totalamount = (totalamount - '"+initial_total+"') + '"+$total+"', "
//                    + "balance = totalamount WHERE details = '"+txtInvoiceNo.getText()+"'"
//                    + " AND status = '1' AND supplierid = '"+id+"'";
//            pst = conn.prepareStatement(sql);
//            pst.execute();
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e+" stockingin.updatepayment0");
//        }finally{
//            try{
//                rs.close();
//                pst.close();
//            }catch(Exception e){
//                
//                
//            }
//        }
//    }
    private void updatepayment1(){
        try{
            String total = txtTotal.getText();
            total = total.trim();
            double $total = Double.parseDouble(total);
            String sql = "UPDATE paymenttable SET totalamount = (totalamount - '"+initial_total+"') + '"+$total+"', "
                    + "balance = totalamount WHERE details = '"+txtInvoiceNo.getText()+"'"
                    + " AND status = '1' AND supplierid = '"+id+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.updatepayment0");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
                
            }
        }
    }
    
    private void evaluatebalancepayment(){
        try{
            String sql = "SELECT COALESCE(SUM(totalamount),0) ,COALESCE(SUM(paidamount), 0) FROM paymenttable WHERE status = '1' AND supplierid = '"+id+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    balbf = rs.getDouble("COALESCE(SUM(totalamount),0)") - rs.getDouble("COALESCE(SUM(paidamount), 0)");
                    txtBal_b_f.setText(String.format("%.2f", balbf));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"stockingin.evaluatebalance");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void savepayment0(){
        try{
            evaluatebalancepayment();
            String sql = "INSERT INTO paymenttable (supplierid,totalamount,paidamount,datepay,paymentmethod,transactionid,status,details)"
                     + " VALUES (?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
           
            pst.setInt(1, id);
            pst.setString(2, txtTotal.getText());
            pst.setString(3, "0");
            pst.setString(4, ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText());
            pst.setString(5, "CASH");
            pst.setString(6, "---");
            pst.setString(7, "1");
            pst.setString(8, txtInvoiceNo.getText());
            
            pst.execute();
            update_tablePayments();
            resetPayments();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.savingPayments0");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }  
    }
   
    private void update(){
        try{
            String supplier = txtSupplierPhone.getText();
            String invoiceNo = txtInvoiceNo.getText();
            String codeno = txtCodeNo.getText();
            String product = txtProduct.getText();
            String description = txtDescription.getText();
            String category = txtCategory.getText();
            String invoicedate = ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText();
            String qty = txtQty.getText();
            String unitprice = txtUnitprice.getText();
            String lowest = txtLowest.getText();
            String highest = txtHighest.getText();
            String total = txtTotal.getText();
            String imeI = txtImei.getText();
                if(supplier.equals("")){
                    JOptionPane.showMessageDialog(null, "Select Supplier"); 
                    buttonChooseSupplier.requestFocus();
                }else if(invoiceNo.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Invoice#");
                    txtInvoiceNo.requestFocus();
                }else if(codeno.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter CODE#");
                    txtCodeNo.requestFocus();
                }else if(product.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Product");
                    txtProduct.requestFocus();
                }else if(description.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Description");
                    txtDescription.requestFocus();
                }else if(category.equals("")){
                    JOptionPane.showMessageDialog(null, "Select Category");
                    buttonCategory.requestFocus();
                }else if(qty.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Qty");
                    txtQty.requestFocus();
                }else if(unitprice.equals("")){
                    JOptionPane.showMessageDialog(null, "enter Unitprice");
                    txtUnitprice.requestFocus();
                }else if(imeI.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter IMEI");
                    txtImei.requestFocus();
                }else if(lowest.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Lowest Selling Price");
                    txtLowest.requestFocus();
                }else if(highest.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Highest Selling Price");
                    txtHighest.requestFocus();
                }else if(id == 0){
                    JOptionPane.showMessageDialog(null, "Error on supplier id");
                }else if(total.equals("")){
                    JOptionPane.showMessageDialog(null, "Total Is Empty, Please Enter Qty of Unitprice");
                }else{
                    
                    String sql = "UPDATE stockingtable SET invoiceno = '"+invoiceNo+"',productcode = '"+codeno+"',unitprice = "
                            + "'"+unitprice+"',qty = '"+qty+"',invoicedate= '"+invoicedate+"',supplierid = '"+id+"',userid ="
                            + " '"+loggeduserid+"',total = '"+total+"', imeI = '"+txtImei.getText()+"' WHERE stockingid = '"+stockingid+"' "
                            + "AND status = '1'";
                    pst = conn.prepareStatement(sql);
                    
                    pst.execute();
                    String query = "UPDATE stocktable SET type = '"+txtProduct.getText()+"',description = '"+txtDescription.getText()+"',"
                    + "categoryid = '"+categoryid+"',qty = '"+manage.totalstock_qty(codeno)+"' WHERE status = '1' AND productcode = '"+codeno+"'";
                    manage.update(query);
                    
                    loginmessageupdate();
                    updatepayment0();
                    takefinalbalancevalue();
                    loginmessageupdate();
                    JOptionPane.showMessageDialog(null, "Updated");
                    reset();
                    txtQty.setText("");
                    findProduct();
                    
                }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.update");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
         }
        }
     private void selectedrow(){
         
         try{
             buttonSave.setEnabled(false);
             buttonUpdate.setEnabled(true);
             buttonRemove.setEnabled(true);
             buttonDel.setEnabled(true);
             button_Update.setEnabled(true);
             lbl_exception.setText("");
             
             int row = tableStocking.getSelectedRow();
             String table_click = tableStocking.getValueAt(row, 0).toString();
             
             String sql = "SELECT * FROM stockingtable,supplierstable,categorytable,stocktable WHERE stockingtable.supplierid = "
                     + "supplierstable.id AND stocktable.categoryid = categorytable.categoryid AND stocktable.productcode = "
                     + "stockingtable.productcode AND stockingtable.status = '1' AND stockingtable.stockingid = '"+table_click+"'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
                if(rs.next()){
                    int stockingId = rs.getInt("stockingtable.stockingid");
                    stockingid = stockingId;
                    int supplierid = rs.getInt("supplierstable.id");
                    id = supplierid;
                        if(id == 1){
                            buttonPayments.setVisible(false);
                        }else{
                            buttonPayments.setVisible(true);
                        }
                    txtInvoiceNo.setText(rs.getString("stockingtable.invoiceno"));
                    txtSupplierPhone.setText(rs.getString("supplierstable.phoneno"));
                    txtSupplierName.setText(rs.getString("supplierstable.suppliername"));
                    ((JTextField)chooserDate.getDateEditor().getUiComponent()).setText(rs.getString("stockingtable.invoicedate"));
                    txtCodeNo.setText(rs.getString("stockingtable.productcode"));
                    
                    txt_codeno.setText(rs.getString("stockingtable.productcode"));
                    
                    txtProductCodeRemove.setText(rs.getString("stockingtable.productcode"));
                    txtProduct.setText(rs.getString("stocktable.type"));
                    txt_Product.setText(rs.getString("stocktable.type"));
                    txtDescription.setText(rs.getString("stocktable.description"));
                    txt_Description.setText(rs.getString("stocktable.description"));
                    txtProductRemove.setText(rs.getString("stocktable.type")+"-" +rs.getString("stocktable.description"));
                    
                    imei = rs.getString("stockingtable.imei");
                    int categoryId = rs.getInt("stocktable.categoryid");
                    categoryid = categoryId;
                    txtCategory.setText(rs.getString("categorytable.category"));
                    int qty = rs.getInt("stockingtable.qty");
                    qty_ = qty;
                    txtQty.setText(String.format("%d", qty));
                    txtUnitprice.setText(rs.getString("stockingtable.unitprice"));
                    txtLowest.setText(rs.getString("stocktable.lowestsellingprice"));
                    txt_Lowest.setText(rs.getString("stocktable.lowestsellingprice"));
                    txtHighest.setText(rs.getString("stocktable.highestsellingprice"));
                    txt_Highest.setText(rs.getString("stocktable.highestsellingprice"));
                    double initial = rs.getDouble("stockingtable.total");
                    txtTotal.setText(String.format("%.2f",initial));
                    individual_multiple = rs.getString("categorytable.type");
                    
                    previousqty = qty;
                    initial_total = initial;
                    
                        if(individual_multiple.equals("INDIVIDUAL")){
                            txtQty.setText("1");
                            txtQty.setEnabled(false);
                            txtImei.setText(rs.getString("stockingtable.imei"));
                            txtImei.setEnabled(true);
                            txtUnitprice.requestFocus();
                        }else{
                            
                            txtQty.setEnabled(true);
                            txtQty.requestFocus();
                            txtImei.setText("---");
                            txtImei.setEnabled(false);
                        }
                }
         }catch(Exception e){
             System.out.println(e+" stockingin.Selectedrow");
             //JOptionPane.showMessageDialog(null, e+" stockingin.selectedrow");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     private void showdialogremove(){
         DialogRemove.setVisible(true);
         DialogRemove.setSize(830, 470);
         DialogRemove.setLocationRelativeTo(this);
         DialogRemove.setResizable(false);
         DialogRemove.setAlwaysOnTop(true);
         DialogRemove.setTitle("Remove Item");
         resetRemove();
         txtQtyRemove.setText("0");
            if(txtimei.isEnabled()){
                txtQtyRemove.setEnabled(false);
                txtQtyRemove.setText("1");
                txtimei.requestFocus();
            }else{
                txtimei.setEnabled(false);
                txtQtyRemove.requestFocus();
                txtimei.setText("---");
            }
            disablePanel();
     }
     private void resetRemove(){
         txtQtyRemove.setText("1");
         txtimei.setText("");
         txtReason.setText("");
         buttonSaveRemove.setEnabled(true);
         buttonDeleteRemove.setEnabled(false);
         lbl_exception.setText("");
         update_tableremove();
     }
     private void update_tableremove(){
         try{
             String sql = "SELECT removetable.removeid AS 'id',stocktable.productcode AS 'P-CODE',CONCAT"
                     + "(stocktable.type,'-',stocktable.description) AS 'Product',removetable.qty AS 'QTY',"
                     + "removetable.imei AS 'Serial No',removetable.reason AS 'Reason' FROM removetable,stocktable"
                     + " WHERE stocktable.productcode = removetable.productcode AND removetable.status = '1'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             tableRemove.setModel(DbUtils.resultSetToTableModel(rs));
             TableColumn idColumn = tableRemove.getColumn("id");
             idColumn.setMaxWidth(0);
             idColumn.setMinWidth(0);
             idColumn.setPreferredWidth(0);
         }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e+" stockingin.update_tableremove");
         }finally{
             try{
                rs.close();
                pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     private void saveremove(){
         try{
             String qty = txtQtyRemove.getText();
             String imei_ = txtimei.getText();
             String reason = txtReason.getText();
             
             if(txtProductCodeRemove.getText().equals("")){
                 DialogRemove.dispose();
                 lbl_exception.setText("Please Select Product to be removed again from Stock table!");
                 tableStocking.requestFocus();
             }else if(imei.equals("")){
                 txtimei.requestFocus();
             }else if(reason.equals("")){
                 txtReason.requestFocus();
             }else{
                 String sql = "INSERT INTO removetable (productcode,qty,imei,reason,status,removedate,userid)VALUES(?,?,?,?,?,?,?)";
                 pst = conn.prepareStatement(sql);
                 
                 pst.setString(1, txtProductCodeRemove.getText());
                 pst.setString(2, qty);
                 pst.setString(3, imei_);
                 pst.setString(4, reason);
                 pst.setString(5, "1");
                 pst.setString(6, ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText());
                 pst.setString(7, loggeduserid);
                 
                 pst.execute();
                 String sql_stock = "UPDATE stocktable SET qty = '"+manage.totalstock_qty(txtProductCodeRemove.getText())+"' WHERE status = '1' AND"
                    + " productcode = '"+txtProductCodeRemove.getText()+"'";
                 manage.update(sql_stock);
                 String query = "UPDATE stockingtable SET status = '0' WHERE status = '1' AND imei = '"+txtimei.getText()+"'";
                 manage.update(query);
                 resetRemove();
                 txtimei.requestFocus();
             }
             
         }catch(Exception e){
             System.out.println(e+" stockingin.saveremove");
             DialogRemove.dispose();
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     private void deleteremove(){
         try{
             String qty = txtQtyRemove.getText();
             qty = qty.trim();
             int quantity = Integer.parseInt(qty);
             String sql = "DELETE FROM removetable WHERE removeid = '"+removeid+"' AND status = '1'";
                 pst = conn.prepareStatement(sql);
                 pst.execute();
                 String query2 = "UPDATE stockingtable SET status = '1' WHERE status = '0' AND imei = '"+txtimei.getText()+"'";
                 manage.update(query2);
                 String query = "UPDATE stocktable SET qty = '"+manage.totalstock_qty(txtProductCodeRemove.getText())+"' WHERE "
                         + "productcode = '"+txtProductCodeRemove.getText()+"' AND status = '1'";
                 manage.update(query);
                 resetRemove();
                 txtProductCodeRemove.setText("");
                 txtProductRemove.setText("");
         }catch(Exception e){
             System.out.println(e+" deleteremove");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     private void selectedrowremove(){
         try{
             buttonDeleteRemove.setEnabled(true);
             buttonSaveRemove.setEnabled(false);
             int row = tableRemove.getSelectedRow();
             String table_click = tableRemove.getValueAt(row, 0).toString();
             String sql = "SELECT * FROM removetable,stocktable WHERE stocktable.productcode = removetable.productcode AND "
                     + " removetable.status = '1' AND removetable.removeid = '"+table_click+"'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
                if(rs.next()){
                    int removeId = rs.getInt("removetable.removeid");
                    removeid = removeId;
                    txtProductCodeRemove.setText(rs.getString("removetable.productcode"));
                    txtProduct.setText(rs.getString("stocktable.type"));
                    txtQtyRemove.setText(rs.getString("removetable.qty"));
                    txtimei.setText(rs.getString("removetable.imei"));
                    txtReason.setText(rs.getString("removetable.reason"));
                }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e+" stockingin.selectedrowremove");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     private void showdialogcat(){
         DialogCat.setVisible(true);
         DialogCat.setSize(325, 430);
         DialogCat.setAlwaysOnTop(true);
         DialogCat.setLocationRelativeTo(this);
         DialogCat.setResizable(false);
         DialogCat.setTitle("Categories");
         findcat();
         txtSearchCat.setText("");
         txtSearchCat.requestFocus();
         txtInvoiceNo.setEnabled(false);
         disablePanel();
     }
     private void selectedrowcat(){
         try{
             int row = table_category.getSelectedRow();
             String table_click = table_category.getValueAt(row, 0).toString();
             
             String sql = "SELECT * FROM categorytable WHERE categoryid = '"+table_click+"'AND status = '1'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
                if(rs.next()){
                    int catid = rs.getInt("categoryid");
                    categoryid = catid;
                    txtCategory.setText(rs.getString("category"));
                    individual_multiple = rs.getString("type");
                    DialogCat.dispose();
                        if(individual_multiple.equals("INDIVIDUAL")){
                            txtQty.setText("1");
                            txtQty.setEnabled(false);
                            txtImei.setText("");
                            txtImei.setEnabled(true);
                            txtUnitprice.requestFocus();
                        }else{
                            txtQty.setText("");
                            txtQty.setEnabled(true);
                            txtQty.requestFocus();
                            txtImei.setText("---");
                            txtImei.setEnabled(false);
                        }
                        enablePanel();
                }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e+"selectedrowcat");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    private void loginmessagedeletepayments(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Delete Payments details for '"+txtInvoiceNo.getText()+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e+" Error @ loginmessagedeletepayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loginmessageupdate(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Update Stock details for '"+txtInvoiceNo.getText()+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessageupdate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loginmessagedeleteremove(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Delete Removed Products  for '"+txtProductCodeRemove.getText()+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessageupdate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void showDialogSupplier(){
        DialogSuppliers.setVisible(true);
        DialogSuppliers.setSize(587, 434);
        DialogSuppliers.setAlwaysOnTop(true);
        DialogSuppliers.setResizable(false);
        DialogSuppliers.setLocationRelativeTo(this);
        DialogSuppliers.setTitle("Suppliers");
        findSuppliers();
        buttonPayments.setEnabled(true);
        disablePanel();
    }
    private void showDialogPayments(){
        DialogPayments.setVisible(true);
        DialogPayments.setSize(726, 450);
        DialogPayments.setTitle("Payments");
        DialogPayments.setResizable(false);
        DialogPayments.setAlwaysOnTop(true);
        DialogPayments.setLocationRelativeTo(this);
        resetPayments();
        evaluatebalancepayment();
        generatepayno();
        disablePanel();
    }
    private void showDialogCategories(){
        DialogCategory.setVisible(true);
        DialogCategory.setSize(450, 285);
        DialogCategory.setAlwaysOnTop(true);
        DialogCategory.setResizable(false);
        DialogCategory.setLocationRelativeTo(this);
        DialogCategory.setTitle("Categories");
        findcategory();
        disablePanel();
    }
    private void resetcategory(){
        txtCategorys.setText("");
        comboType.setSelectedItem("Select");
        txtCategorys.requestFocus();
        buttonSaveCategory.setEnabled(true);
        buttonUpdateCategory.setEnabled(false);
        buttonDeleteCategory.setEnabled(false);
    }
    private void savecategory(){
        try{
            String category = txtCategorys.getText();
            String type = comboType.getSelectedItem().toString();
            String status = "1";
            if(category.equals("")){
                txtCategorys.requestFocus();
            }else if(type.equals("Selected")){    
                comboType.requestFocus();
            }else{
                String sql = "INSERT INTO categorytable(category,status,type)VALUES(?,?,?)";
                pst = conn.prepareStatement(sql);

                pst.setString(1, category);
                pst.setString(2, status);
                pst.setString(3, type);
                pst.execute();
                resetcategory();
                findcategory();
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" savecategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowcategory(){
        try{
            int row = tableCategory.getSelectedRow();
            String table_click = tableCategory.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM categorytable WHERE status = '1' AND categoryid = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    buttonSaveCategory.setEnabled(false);
                    buttonUpdateCategory.setEnabled(true);
                    buttonDeleteCategory.setEnabled(true);
                    int catid = rs.getInt("categoryid");
                    categoryid = catid;
                    
                    txtCategorys.setText(rs.getString("category"));
                    comboType.setSelectedItem(rs.getString("type"));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" selectedrowcategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void updatecategory(){
        try{
            String category = txtCategorys.getText();
            String type = comboType.getSelectedItem().toString();
            if(category.equals("")){
                txtCategorys.requestFocus();
            }else if(type.equals("Select")){
                comboType.requestFocus();
            }else{
                String sql = "UPDATE categorytable SET category = '"+category+"',type = '"+type+"' WHERE categoryid = '"+categoryid+"'"
                        + " AND status = '1'";
                pst = conn.prepareStatement(sql);
                pst.execute();
                resetcategory();
                findcategory();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" updatecategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void deletecategory(){
        try{
                String sql = "UPDATE categorytable SET status = '0' WHERE categoryid = '"+categoryid+"' AND status = '1'";
                pst = conn.prepareStatement(sql);
                pst.execute();
                resetcategory();
                findcategory();
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e+" deletecategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public ArrayList<SearchCategory> ListCategories(String ValToSearch){
        ArrayList<SearchCategory>categoryList = new ArrayList<SearchCategory>();
       
        try{
            String searchQuery = "SELECT * FROM categorytable WHERE CONCAT(category) LIKE'%"+ValToSearch+"%' AND status = '1'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchCategory search;
            while(rs.next()){
                search = new SearchCategory(
                                    rs.getInt("categoryid"),
                                    rs.getString("category")
                                            );
                categoryList.add(search);
            }
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null ,e+"  Searchcategory/findcategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return categoryList;
    }
    public void findcategory(){
        ArrayList<SearchCategory> categories = ListCategories(txtCategorys.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Category"});
        
        Object[] row = new Object[2];
        
            for(int i = 0; i < categories.size(); i ++){
                
             row[0] = categories.get(i).getCategoryid();
             row[1] = categories.get(i).getCategory();
             
             model.addRow(row);
            }
            tableCategory.setModel(model);
            TableColumn idColumn = tableCategory.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    public void findcat(){
        ArrayList<SearchCategory> categories = ListCategories(txtSearchCat.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Category"});
        
        Object[] row = new Object[2];
        
            for(int i = 0; i < categories.size(); i ++){
                
             row[0] = categories.get(i).getCategoryid();
             row[1] = categories.get(i).getCategory();
             
             model.addRow(row);
            }
            table_category.setModel(model);
            TableColumn idColumn = table_category.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    
    
    private void update_tablePayments(){
        try{
            String sql = "SELECT paymentid AS 'id',datepay AS 'Pay-Date',details AS 'Details #',paidamount AS 'Amount Paid',paymentmethod "
                    + "AS 'Pay Method',transactionid AS 'Transaction#' FROM paymenttable WHERE supplierid = '"+id+"' AND paidamount != 0 AND status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tablePayments.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = tablePayments.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null,e+" stockingin.update_tablePayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void resetPayments(){
        
        txtAmount.setText("0.00");
        txtBalance.setText("0.00");
        txtBal_b_f.setText("0.00");
        comboPaymentMethod.setSelectedItem("Select");
        txtTransactionId.setVisible(true);
        txtTransactionId.setText("---");
        txtAmount.requestFocus();
        buttonsave.setEnabled(true);
        buttondelete.setEnabled(false);
        buttonPayments.setEnabled(false);
        update_tablePayments();
        txtTransactionId.setEnabled(false);
    }
    private void selectedRowPayment(){
        try{
            int row = tablePayments.getSelectedRow();
            String table_click = tablePayments.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM paymenttable WHERE paymentid = '"+table_click+"' AND status = '1' AND paidamount != 0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                paymentid = rs.getInt("paymentid");
                txtAmount.setText(rs.getString("paidamount"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e+" (stockingin.selectedRowPayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    
    private void deletepayment(){
        try{
            String sql = "UPDATE paymenttable SET status = '0' WHERE paymentid = '"+paymentid+"'AND status = '1'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            loginmessagedeletepayments();
            resetPayments();
            evaluatebalancepayment();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e+" stockingin.deletepayment");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        update_tablePayments();
    }
    private void selectedRowSuppliers(){
        try{
            int row = tableSelectSupplier.getSelectedRow();
            String table_click = tableSelectSupplier.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM supplierstable WHERE id = '"+table_click+"' AND status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                id = rs.getInt("id");
                String Name = rs.getString("suppliername");
                txtSupplierName.setText(Name);
                
                String Phone = rs.getString("phoneno");
                txtSupplierPhone.setText(Phone);
                DialogSuppliers.dispose();
                txtInvoiceNo.setText("");
                txtInvoiceNo.requestFocus();
                if(id == 1){
                    buttonPayments.setVisible(false);
                    txtInvoiceNo.setEnabled(false);
                    txtInvoiceNo.setText("-");
                    
                    txtCodeNo.requestFocus();
                }else{
                    buttonPayments.setVisible(true);
                    txtInvoiceNo.setText("");
                    txtInvoiceNo.setEnabled(true);
                    txtInvoiceNo.requestFocus();
                }
            }
            enablePanel();
        }catch(Exception e){
            System.out.println(e+" stockingin.selectedRowSuppliers");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public ArrayList<SearchSupplier> ListSuppliers(String ValToSearch){
        ArrayList<SearchSupplier>supplierList = new ArrayList<SearchSupplier>();
       
        try{
            String searchQuery = "SELECT * FROM supplierstable WHERE CONCAT(id,'',suppliername,'',phoneno) LIKE'%"+ValToSearch+"%' AND status = '1'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchSupplier search;
            while(rs.next()){
                search = new SearchSupplier(
                                    rs.getInt("id"),
                                    rs.getString("suppliername"),
                                    rs.getString("phoneno")
                                            );
                supplierList.add(search);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null ,e+" (Error at SearchSupplier/findSupplier)");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return supplierList;
    }
    public void findSuppliers(){
        ArrayList<SearchSupplier> suppliers = ListSuppliers(txtSearchSupplier.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Supplier","Phone Number"});
        
        Object[] row = new Object[3];
        
            for(int i = 0; i < suppliers.size(); i ++){
                
             row[0] = suppliers.get(i).getId();
             row[1] = suppliers.get(i).getName();
             row[2] = suppliers.get(i).getPhone();
             
             
             
             model.addRow(row);
            }
            tableSelectSupplier.setModel(model);
            TableColumn idColumn = tableSelectSupplier.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    public ArrayList<SearchProduct> ListProducts(String ValToSearch){
        ArrayList<SearchProduct>productList = new ArrayList<SearchProduct>();
       
        try{
            String searchQuery = "SELECT * FROM stockingtable,supplierstable,stocktable WHERE stockingtable.supplierid = supplierstable.id"
                    + " AND stocktable.productcode = stockingtable.productcode AND CONCAT(stockingtable.productcode,''"
                    + ",stocktable.type,'',stocktable.description,'',stockingtable.imei,'',stockingtable.invoicedate,'',supplierstable.suppliername) "
                    + "LIKE'%"+ValToSearch+"%'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchProduct search;
            while(rs.next()){
                search = new SearchProduct(
                                    rs.getInt("stockingtable.stockingId"),
                                    rs.getString("stockingtable.invoiceno"),
                                    rs.getString("stockingtable.productcode"),
                                    rs.getString("stocktable.type"),
                                    rs.getString("stocktable.description"),
                                    rs.getString("stockingtable.imei"),
                                    rs.getString("stockingtable.unitprice"),
                                    rs.getString("stockingtable.invoicedate"),
                                    rs.getInt("supplierstable.id"),
                                    rs.getString("supplierstable.suppliername"),
                                    rs.getDouble("stocktable.lowestsellingprice"),
                                    rs.getDouble("stocktable.highestsellingprice"),
                                    rs.getInt("stocktable.qty")
                                            );
                productList.add(search);
            }
        }catch(Exception e){
            System.out.println(e+" (stockingin.SearchProduct/Findproduct)");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return productList;
    }
    public void findProduct(){
        ArrayList<SearchProduct> product = ListProducts(txtCodeNo.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Invoice#","Product#","Product","Description","Serial No.","Unit-Price",
            "Invoice Date","Supplier Name","Lowest S-Price","Highest S-Price"});
        Object[] row = new Object[11];
        
            for(int i = 0; i < product.size(); i ++){
                
             row[0] = product.get(i).getId();
             row[1] = product.get(i).getInvoiceno();
             row[2] = product.get(i).getProductcode();
             row[3] = product.get(i).getType();
             row[4] = product.get(i).getDescription();
             row[5] = product.get(i).getImei();
             row[6] = product.get(i).getUnitprice();
             row[7] = product.get(i).getInvoicedate();
             row[8] = product.get(i).getSuppliername();
             row[9] = product.get(i).getLowestprice();
             row[10] = product.get(i).getHighestprice();
             
             model.addRow(row);
            }
            tableStocking.setModel(model);
            TableColumn idColumn = tableStocking.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    public ArrayList<SearchProduct> ListProduct(String ValToSearch){
        ArrayList<SearchProduct>productList = new ArrayList<SearchProduct>();
       
        try{
            String searchQuery = "SELECT * FROM stocktable,supplierstable,stockingtable WHERE stocktable.productcode = stockingtable.productcode AND "
                    + "supplierstable.id = stockingtable.supplierid AND stocktable.status= '1' AND CONCAT(stocktable.productcode,'',stocktable.type"
                    + ",'',stocktable.description) LIKE'%"+ValToSearch+"%'GROUP BY stocktable.productcode";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchProduct search;
            while(rs.next()){
                search = new SearchProduct(
                                    rs.getInt("stockingtable.stockingid"),
                                    rs.getString("stockingtable.invoiceno"),
                                    rs.getString("stockingtable.productcode"),
                                    rs.getString("stocktable.type"),
                                    rs.getString("stocktable.description"),
                                    rs.getString("stockingtable.imei"),
                                    rs.getString("stockingtable.unitprice"),
                                    rs.getString("stockingtable.invoicedate"),
                                    rs.getInt("supplierstable.id"),
                                    rs.getString("supplierstable.suppliername"),
                                    rs.getDouble("stocktable.lowestsellingprice"),
                                    rs.getDouble("stocktable.highestsellingprice"),
                                    rs.getInt("stocktable.qty")
                                            );
                productList.add(search);
            }
        }catch(Exception e){
            System.out.print(e+" stockingin.SearchProduct/Findproduct Method");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return productList;
    }
    public void findallProduct(){
        ArrayList<SearchProduct> product = ListProduct(txtSearchAccessories.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Phone-code","Product","Description"});
        
        Object[] row = new Object[3];
        
            for(int i = 0; i < product.size(); i ++){
        
             row[0] = product.get(i).getProductcode();
             row[1] = product.get(i).getType();
             row[2] = product.get(i).getDescription();
             
             model.addRow(row);
            }
            tableAllproducts.setModel(model);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogSuppliers = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        searchingIcon = new javax.swing.JLabel();
        txtSearchSupplier = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSelectSupplier = new javax.swing.JTable();
        DialogPayments = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePayments = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        buttonadd = new javax.swing.JButton();
        buttondelete = new javax.swing.JButton();
        buttonsave = new javax.swing.JButton();
        buttonexit = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        comboPaymentMethod = new javax.swing.JComboBox();
        txtTransactionId = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        txtBalance = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtBal_b_f = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        DialogCategory = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCategory = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        buttonAddCategory = new javax.swing.JButton();
        buttonSaveCategory = new javax.swing.JButton();
        buttonUpdateCategory = new javax.swing.JButton();
        buttonDeleteCategory = new javax.swing.JButton();
        buttonExitCategory = new javax.swing.JButton();
        txtCategorys = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox();
        DialogCat = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        txtSearchCat = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        table_category = new javax.swing.JTable();
        DialogAllproducts = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        searchingIcon1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableAllproducts = new javax.swing.JTable();
        txtSearchAccessories = new javax.swing.JTextField();
        DialogRemove = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtQtyRemove = new javax.swing.JTextField();
        txtProductRemove = new javax.swing.JTextField();
        txtProductCodeRemove = new javax.swing.JTextField();
        txtimei = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtReason = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        buttonDeleteRemove = new javax.swing.JButton();
        buttonSaveRemove = new javax.swing.JButton();
        buttonExitRemove = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableRemove = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        StockingTab = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        buttonReset = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonUpdate = new javax.swing.JButton();
        buttonAddCategories = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();
        buttonDel = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtImei = new javax.swing.JTextField();
        tick = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        buttonSelect = new javax.swing.JButton();
        txtCodeNo = new javax.swing.JTextField();
        txtProduct = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtCategory = new javax.swing.JTextField();
        buttonCategory = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        txtUnitprice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtLowest = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtHighest = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        txt_Product = new javax.swing.JTextField();
        txt_Description = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_codeno = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        txt_Highest = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txt_Lowest = new javax.swing.JTextField();
        button_Update = new javax.swing.JButton();
        button_reset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        chooserDate = new com.toedter.calendar.JDateChooser();
        txtSupplierName = new javax.swing.JTextField();
        txtSupplierPhone = new javax.swing.JTextField();
        buttonChooseSupplier = new javax.swing.JButton();
        buttonPayments = new javax.swing.JButton();
        txtInvoiceNo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStocking = new javax.swing.JTable();
        lbl_exception = new javax.swing.JLabel();

        DialogSuppliers.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogSuppliers.setBackground(java.awt.Color.white);
        DialogSuppliers.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogSuppliersWindowClosing(evt);
            }
        });

        jPanel6.setBackground(java.awt.Color.white);

        searchingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/search.png"))); // NOI18N

        txtSearchSupplier.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSearchSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSupplierKeyReleased(evt);
            }
        });

        tableSelectSupplier.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableSelectSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableSelectSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSelectSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSelectSupplier);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchingIcon)
                        .addGap(2, 2, 2)
                        .addComponent(txtSearchSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearchSupplier)
                    .addComponent(searchingIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DialogSuppliersLayout = new javax.swing.GroupLayout(DialogSuppliers.getContentPane());
        DialogSuppliers.getContentPane().setLayout(DialogSuppliersLayout);
        DialogSuppliersLayout.setHorizontalGroup(
            DialogSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DialogSuppliersLayout.setVerticalGroup(
            DialogSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        DialogPayments.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogPayments.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                DialogPaymentsWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogPaymentsWindowClosing(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.blue));

        tablePayments.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tablePayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePayments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePaymentsMouseClicked(evt);
            }
        });
        tablePayments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePaymentsKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablePayments);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        buttonadd.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonadd.setText("Reset");
        buttonadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonaddActionPerformed(evt);
            }
        });

        buttondelete.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttondelete.setText("Delete");
        buttondelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttondeleteActionPerformed(evt);
            }
        });

        buttonsave.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonsave.setText("Save");
        buttonsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonsaveActionPerformed(evt);
            }
        });

        buttonexit.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonexit.setText("Exit");
        buttonexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonexitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonadd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(buttonsave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(buttondelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(buttonexit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonadd)
                    .addComponent(buttonsave)
                    .addComponent(buttondelete)
                    .addComponent(buttonexit))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 143, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel16.setText("Payment Method:");

        comboPaymentMethod.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        comboPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "CHEQUE", "CASH", "MPESA" }));
        comboPaymentMethod.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboPaymentMethodPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txtTransactionId.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtTransactionId.setText("---");
        txtTransactionId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransactionIdActionPerformed(evt);
            }
        });
        txtTransactionId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTransactionIdKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTransactionId)
                    .addComponent(comboPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTransactionId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        txtBalance.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel12.setText("Bal B/f:");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel13.setText("Amount:");

        txtBal_b_f.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        jLabel35.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel35.setText("Balance:");

        txtAmount.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmountKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAmountKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel35))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBal_b_f, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBal_b_f, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(81, 81, 81)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addGap(52, 52, 52))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout DialogPaymentsLayout = new javax.swing.GroupLayout(DialogPayments.getContentPane());
        DialogPayments.getContentPane().setLayout(DialogPaymentsLayout);
        DialogPaymentsLayout.setHorizontalGroup(
            DialogPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
        );
        DialogPaymentsLayout.setVerticalGroup(
            DialogPaymentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        DialogCategory.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogCategory.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogCategoryWindowClosing(evt);
            }
        });

        jPanel11.setBackground(java.awt.Color.white);
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        tableCategory.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoryMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableCategory);

        jPanel8.setBackground(java.awt.Color.white);
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonAddCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonAddCategory.setText("Reset");
        buttonAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddCategoryActionPerformed(evt);
            }
        });

        buttonSaveCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSaveCategory.setText("Save");
        buttonSaveCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveCategoryActionPerformed(evt);
            }
        });

        buttonUpdateCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUpdateCategory.setText("Update");
        buttonUpdateCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateCategoryActionPerformed(evt);
            }
        });

        buttonDeleteCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDeleteCategory.setText("Delete");
        buttonDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteCategoryActionPerformed(evt);
            }
        });

        buttonExitCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExitCategory.setText("Exit");
        buttonExitCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonAddCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSaveCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonUpdateCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(buttonDeleteCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonExitCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(buttonSaveCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonExitCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        txtCategorys.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtCategorys.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategorysKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCategorysKeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel19.setText("Category:");

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel28.setText("Type: ");

        comboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "INDIVIDUAL", "MULTIPLE" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCategorys, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(comboType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCategorys, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DialogCategoryLayout = new javax.swing.GroupLayout(DialogCategory.getContentPane());
        DialogCategory.getContentPane().setLayout(DialogCategoryLayout);
        DialogCategoryLayout.setHorizontalGroup(
            DialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DialogCategoryLayout.setVerticalGroup(
            DialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        DialogCat.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogCat.setBackground(new java.awt.Color(255, 255, 255));
        DialogCat.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogCatWindowClosing(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        txtSearchCat.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSearchCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCatActionPerformed(evt);
            }
        });
        txtSearchCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCatKeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel20.setText("Search:");

        table_category.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_categoryMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(table_category);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel22Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel20)
                        .addGap(4, 4, 4)
                        .addComponent(txtSearchCat, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DialogCatLayout = new javax.swing.GroupLayout(DialogCat.getContentPane());
        DialogCat.getContentPane().setLayout(DialogCatLayout);
        DialogCatLayout.setHorizontalGroup(
            DialogCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DialogCatLayout.setVerticalGroup(
            DialogCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        DialogAllproducts.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogAllproducts.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogAllproductsWindowClosing(evt);
            }
        });

        jPanel14.setBackground(java.awt.Color.white);

        searchingIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/search.png"))); // NOI18N

        tableAllproducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableAllproducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAllproductsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableAllproductsMouseEntered(evt);
            }
        });
        tableAllproducts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableAllproductsKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tableAllproducts);

        txtSearchAccessories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchAccessoriesActionPerformed(evt);
            }
        });
        txtSearchAccessories.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAccessoriesKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(searchingIcon1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchAccessories, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchAccessories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchingIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DialogAllproductsLayout = new javax.swing.GroupLayout(DialogAllproducts.getContentPane());
        DialogAllproducts.getContentPane().setLayout(DialogAllproductsLayout);
        DialogAllproductsLayout.setHorizontalGroup(
            DialogAllproductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DialogAllproductsLayout.setVerticalGroup(
            DialogAllproductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        DialogRemove.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogRemove.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                DialogRemoveWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogRemoveWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtQtyRemove.setEditable(false);
        txtQtyRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtQtyRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyRemoveKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyRemoveKeyReleased(evt);
            }
        });

        txtProductRemove.setEditable(false);
        txtProductRemove.setBackground(new java.awt.Color(204, 204, 204));
        txtProductRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtProductRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductRemoveKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductRemoveKeyReleased(evt);
            }
        });

        txtProductCodeRemove.setEditable(false);
        txtProductCodeRemove.setBackground(new java.awt.Color(204, 204, 204));
        txtProductCodeRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtProductCodeRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductCodeRemoveKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductCodeRemoveKeyReleased(evt);
            }
        });

        txtimei.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtimei.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtimeiKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtimeiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtimeiKeyReleased(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel29.setText("Product Code:");

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel30.setText("Product:");

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel31.setText("Qty:");

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel32.setText("Serial No.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtProductRemove)
                    .addComponent(txtProductCodeRemove)
                    .addComponent(txtQtyRemove)
                    .addComponent(txtimei, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductCodeRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtQtyRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtimei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel33.setText("Reason:");

        txtReason.setColumns(20);
        txtReason.setRows(5);
        txtReason.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtReasonKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(txtReason);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap())
        );

        jPanel12.setBackground(java.awt.Color.white);
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonDeleteRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDeleteRemove.setText("Delete");
        buttonDeleteRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteRemoveActionPerformed(evt);
            }
        });

        buttonSaveRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSaveRemove.setText("Save");
        buttonSaveRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveRemoveActionPerformed(evt);
            }
        });

        buttonExitRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExitRemove.setText("Exit");
        buttonExitRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(buttonSaveRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(buttonDeleteRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(buttonExitRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSaveRemove)
                    .addComponent(buttonDeleteRemove)
                    .addComponent(buttonExitRemove))
                .addContainerGap())
        );

        tableRemove.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        tableRemove.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRemoveMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableRemove);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DialogRemoveLayout = new javax.swing.GroupLayout(DialogRemove.getContentPane());
        DialogRemove.getContentPane().setLayout(DialogRemoveLayout);
        DialogRemoveLayout.setHorizontalGroup(
            DialogRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DialogRemoveLayout.setVerticalGroup(
            DialogRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel1.setText("Stock");

        StockingTab.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        StockingTab.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        jPanel13.setBackground(java.awt.Color.white);
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jPanel7.setBackground(java.awt.Color.white);
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel21.setText("Total:");

        txtTotal.setEditable(false);
        txtTotal.setBackground(java.awt.Color.lightGray);
        txtTotal.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalKeyPressed(evt);
            }
        });

        jPanel10.setBackground(java.awt.Color.white);
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonReset.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonReset.setText("Reset");
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetActionPerformed(evt);
            }
        });
        buttonReset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonResetKeyPressed(evt);
            }
        });

        buttonExit.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        buttonSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonSaveKeyPressed(evt);
            }
        });

        buttonUpdate.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });
        buttonUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonUpdateKeyPressed(evt);
            }
        });

        buttonAddCategories.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonAddCategories.setText("Add Categories");
        buttonAddCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddCategoriesActionPerformed(evt);
            }
        });
        buttonAddCategories.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonAddCategoriesKeyPressed(evt);
            }
        });

        buttonRemove.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonRemove.setText("Remove");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });
        buttonRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonRemoveKeyPressed(evt);
            }
        });

        buttonDel.setBackground(new java.awt.Color(255, 51, 51));
        buttonDel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDel.setText("Delete");
        buttonDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDelActionPerformed(evt);
            }
        });
        buttonDel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonDelKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAddCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(buttonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(buttonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonReset)
                    .addComponent(buttonSave)
                    .addComponent(buttonUpdate)
                    .addComponent(buttonExit)
                    .addComponent(buttonAddCategories)
                    .addComponent(buttonRemove)
                    .addComponent(buttonDel))
                .addContainerGap())
        );

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel27.setText("Serial No.");

        txtImei.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtImei.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtImeiCaretUpdate(evt);
            }
        });
        txtImei.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtImeiAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtImei.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImeiFocusLost(evt);
            }
        });
        txtImei.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtImeiInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtImeiCaretPositionChanged(evt);
            }
        });
        txtImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeiActionPerformed(evt);
            }
        });
        txtImei.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtImeiPropertyChange(evt);
            }
        });
        txtImei.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImeiKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtImeiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtImeiKeyReleased(evt);
            }
        });
        txtImei.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtImeiVetoableChange(evt);
            }
        });

        tick.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        tick.setForeground(new java.awt.Color(51, 204, 0));
        tick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/tick.png"))); // NOI18N
        tick.setText("Saved!");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tick, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(5, 5, 5)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtImei)
                        .addComponent(jLabel27)
                        .addComponent(tick, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(java.awt.Color.white);
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        buttonSelect.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonSelect.setText("Select");
        buttonSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSelectActionPerformed(evt);
            }
        });

        txtCodeNo.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtCodeNo.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCodeNoInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtCodeNo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCodeNoPropertyChange(evt);
            }
        });
        txtCodeNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeNoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodeNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodeNoKeyReleased(evt);
            }
        });

        txtProduct.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductActionPerformed(evt);
            }
        });
        txtProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProductKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductKeyReleased(evt);
            }
        });

        txtDescription.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtDescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescriptionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescriptionKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel25.setText("Description:");

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel26.setText("Code#:");

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel23.setText("Product:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtProduct)
                    .addComponent(txtDescription)
                    .addComponent(txtCodeNo, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtCodeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonSelect, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDescription))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        txtCategory.setEditable(false);
        txtCategory.setBackground(java.awt.Color.lightGray);
        txtCategory.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtCategory.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCategoryInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoryActionPerformed(evt);
            }
        });
        txtCategory.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCategoryPropertyChange(evt);
            }
        });
        txtCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCategoryKeyPressed(evt);
            }
        });

        buttonCategory.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonCategory.setText("Category");
        buttonCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCategoryActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel22.setText("Unit Price:");

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel24.setText("Qty:");

        txtQty.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        txtUnitprice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtUnitprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnitpriceKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnitpriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnitpriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24)
                    .addComponent(buttonCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtQty, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUnitprice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCategory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUnitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel17.setText("Lowest Selling Price:");

        txtLowest.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtLowest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLowestFocusLost(evt);
            }
        });
        txtLowest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLowestKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLowestKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel18.setText("Highest Selling Price:");

        txtHighest.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtHighest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHighestActionPerformed(evt);
            }
        });
        txtHighest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHighestKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHighestKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(145, 145, 145)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(67, 67, 67))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLowest, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHighest, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(148, 148, 148))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtLowest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtHighest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        StockingTab.addTab("Products", jPanel13);

        jPanel24.setBackground(java.awt.Color.white);
        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        txt_Product.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txt_Product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductActionPerformed(evt);
            }
        });
        txt_Product.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ProductKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ProductKeyReleased(evt);
            }
        });

        txt_Description.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txt_Description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_DescriptionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_DescriptionKeyReleased(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel34.setText("Description:");

        jLabel36.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel36.setText("Code#:");

        jLabel37.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel37.setText("Product:");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Product, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(txt_Description)
                    .addComponent(txt_codeno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(93, 93, 93))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_codeno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txt_Product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Description))
                .addContainerGap())
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel41.setText("Highest Selling Price:");

        txt_Highest.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txt_Highest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_HighestKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_HighestKeyPressed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel40.setText("Lowest Selling Price:");

        txt_Lowest.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txt_Lowest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_LowestFocusLost(evt);
            }
        });
        txt_Lowest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_LowestKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_LowestKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(10, 10, 10)))
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Highest, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(txt_Lowest))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Lowest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Highest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)))
        );

        button_Update.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        button_Update.setText("Update");
        button_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_UpdateActionPerformed(evt);
            }
        });
        button_Update.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                button_UpdateKeyPressed(evt);
            }
        });

        button_reset.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        button_reset.setText("Reset");
        button_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_resetActionPerformed(evt);
            }
        });
        button_reset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                button_resetKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(79, 79, 79)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(325, 325, 325)
                .addComponent(button_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_Update)
                    .addComponent(button_reset))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(8, 8, 8)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        StockingTab.addTab("Edit Product Descriptions", jPanel23);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel3.setText("Invoice#:");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel2.setText("Supplier:");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel4.setText("Date:");

        chooserDate.setDateFormatString("yyyy-MM-dd");
        chooserDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserDatePropertyChange(evt);
            }
        });
        chooserDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chooserDateKeyPressed(evt);
            }
        });

        txtSupplierName.setEditable(false);
        txtSupplierName.setBackground(java.awt.Color.lightGray);
        txtSupplierName.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupplierNameActionPerformed(evt);
            }
        });
        txtSupplierName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupplierNameKeyPressed(evt);
            }
        });

        txtSupplierPhone.setEditable(false);
        txtSupplierPhone.setBackground(java.awt.Color.lightGray);
        txtSupplierPhone.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSupplierPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupplierPhoneActionPerformed(evt);
            }
        });
        txtSupplierPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupplierPhoneKeyPressed(evt);
            }
        });

        buttonChooseSupplier.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonChooseSupplier.setText("xxxxxx");
        buttonChooseSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChooseSupplierActionPerformed(evt);
            }
        });
        buttonChooseSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonChooseSupplierKeyPressed(evt);
            }
        });

        buttonPayments.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonPayments.setText("Payment");
        buttonPayments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPaymentsActionPerformed(evt);
            }
        });

        txtInvoiceNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtInvoiceNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInvoiceNoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSupplierPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(txtInvoiceNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonChooseSupplier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(2, 2, 2)
                        .addComponent(chooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(buttonPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chooserDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtSupplierPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonChooseSupplier)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buttonPayments, javax.swing.GroupLayout.Alignment.TRAILING)))))
        );

        tableStocking.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableStocking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableStocking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStockingMouseClicked(evt);
            }
        });
        tableStocking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableStockingKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableStockingKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableStocking);

        lbl_exception.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lbl_exception.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_exception, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(StockingTab)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(lbl_exception, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StockingTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSupplierPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupplierPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupplierPhoneActionPerformed

    private void buttonChooseSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChooseSupplierActionPerformed
        showDialogSupplier();
        reset();
        buttonPayments.setEnabled(true);
        
        DialogPayments.setVisible(false);
        DialogCategory.setVisible(false);
        DialogCat.setVisible(false);
        DialogAllproducts.setVisible(false);
    }//GEN-LAST:event_buttonChooseSupplierActionPerformed

    private void txtSearchSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupplierKeyReleased
        findSuppliers();
    }//GEN-LAST:event_txtSearchSupplierKeyReleased

    private void txtSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupplierNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupplierNameActionPerformed

    private void tableSelectSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSelectSupplierMouseClicked
        selectedRowSuppliers();
    }//GEN-LAST:event_tableSelectSupplierMouseClicked
 
    private void tableStockingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStockingMouseClicked
        selectedrow();
    }//GEN-LAST:event_tableStockingMouseClicked

    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed
        
    }//GEN-LAST:event_jPanel2KeyPressed

    private void tableStockingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableStockingKeyPressed
        
    }//GEN-LAST:event_tableStockingKeyPressed

    private void chooserDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chooserDateKeyPressed
       
    }//GEN-LAST:event_chooserDateKeyPressed

    
    private void buttonChooseSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonChooseSupplierKeyPressed
       
    }//GEN-LAST:event_buttonChooseSupplierKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
    }//GEN-LAST:event_formKeyPressed

    private void txtSupplierPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierPhoneKeyPressed
        
    }//GEN-LAST:event_txtSupplierPhoneKeyPressed

    private void txtSupplierNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierNameKeyPressed
        
    }//GEN-LAST:event_txtSupplierNameKeyPressed

    private void buttonaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonaddActionPerformed
        resetPayments();
    }//GEN-LAST:event_buttonaddActionPerformed

    private void buttonexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonexitActionPerformed
        DialogPayments.dispose();
        enablePanel();
    }//GEN-LAST:event_buttonexitActionPerformed

    private void tablePaymentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePaymentsMouseClicked
        selectedRowPayment();
        buttonsave.setEnabled(false);
        buttondelete.setEnabled(true);
    }//GEN-LAST:event_tablePaymentsMouseClicked

    private void buttonsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonsaveActionPerformed
        savepayment();
    }//GEN-LAST:event_buttonsaveActionPerformed

    private void tablePaymentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePaymentsKeyReleased
       if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedRowPayment();
        }
    }//GEN-LAST:event_tablePaymentsKeyReleased

    private void calculatebalance(){
        String Balbf = txtBal_b_f.getText();
        Balbf = Balbf.trim();
        double balancebf = Double.parseDouble(Balbf);
        
        String Paid = txtAmount.getText();
        Paid = Paid.trim();
        double paidamount = Double.parseDouble(Paid);
        
        txtBalance.setText(String.format("%.2f",balancebf - paidamount));
    }
    private void txtAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyReleased
        calculatebalance();
    }//GEN-LAST:event_txtAmountKeyReleased

    private void txtAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyTyped
       char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
    }
    }//GEN-LAST:event_txtAmountKeyTyped

    private void txtTransactionIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransactionIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransactionIdActionPerformed

    private void comboPaymentMethodPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboPaymentMethodPopupMenuWillBecomeInvisible
       if(comboPaymentMethod.getSelectedItem().toString().equals("CHEQUE")){
           txtTransactionId.setVisible(true);
           txtTransactionId.setText("");
           txtTransactionId.requestFocus();
           txtTransactionId.setEnabled(true);
       }if(comboPaymentMethod.getSelectedItem().toString().equals("CASH")){
           txtTransactionId.setVisible(false);
           txtTransactionId.setText("---");
       }if(comboPaymentMethod.getSelectedItem().toString().equals("MPESA")){
           txtTransactionId.setVisible(true);
           txtTransactionId.setText("");
           txtTransactionId.requestFocus();
           txtTransactionId.setEnabled(true);
       }
    }//GEN-LAST:event_comboPaymentMethodPopupMenuWillBecomeInvisible

    private void DialogPaymentsWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogPaymentsWindowClosing
        dialogOpen = 0;
        resetPayments();
        enablePanel();
    }//GEN-LAST:event_DialogPaymentsWindowClosing

    private void tableStockingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableStockingKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrow();
        }
    }//GEN-LAST:event_tableStockingKeyReleased

    private void DialogSuppliersWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogSuppliersWindowClosing
            enablePanel();
    }//GEN-LAST:event_DialogSuppliersWindowClosing

    private void txtLowestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLowestFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLowestFocusLost

    private void txtLowestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLowestKeyTyped
        char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtLowestKeyTyped

    private void txtLowestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLowestKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
           addstock();
        }
    }//GEN-LAST:event_txtLowestKeyPressed

    private void txtHighestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHighestKeyTyped
      char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtHighestKeyTyped

    private void txtHighestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHighestKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txtHighestKeyPressed

    private void txtCategoryInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCategoryInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryInputMethodTextChanged

    private void txtCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryActionPerformed

    private void txtCategoryPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCategoryPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryPropertyChange

    private void txtCategoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoryKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryKeyPressed

    private void txtDescriptionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriptionKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txtDescriptionKeyPressed

    private void txtDescriptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriptionKeyReleased
       txtDescription.setText(txtDescription.getText().toUpperCase());
    }//GEN-LAST:event_txtDescriptionKeyReleased

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
           addstock();
        }
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtUnitpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyTyped
        char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtUnitpriceKeyTyped

    private void txtUnitpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
           addstock();
        }
    }//GEN-LAST:event_txtUnitpriceKeyPressed

    private void txtProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txtProductKeyPressed

    private void txtProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductKeyReleased
        txtProduct.setText(txtProduct.getText().toUpperCase());
    }//GEN-LAST:event_txtProductKeyReleased

    private void txtCodeNoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCodeNoInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeNoInputMethodTextChanged

    private void txtCodeNoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCodeNoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeNoPropertyChange

    private void txtCodeNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeNoKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
            fillfields();
        }
    }//GEN-LAST:event_txtCodeNoKeyPressed

    private static void music(){
        try{
            String soundName = "barcode.wav";    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Sound Error!!!");
        }
    }
    private void fillfields(){
        try{
            String codeno = txtCodeNo.getText();
            String sql = "SELECT * FROM stockingtable,categorytable,stocktable WHERE stocktable.categoryid = categorytable.categoryid AND "
                    + "stocktable.productcode = stockingtable.productcode AND stockingtable.productcode = ? GROUP BY stockingtable."
                    + "productcode ORDER BY stockingtable.stockingid DESC";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, codeno);
            rs = pst.executeQuery();
            if(rs.next()){
                txtProduct.setText(rs.getString("stocktable.type"));
                txtDescription.setText(rs.getString("stocktable.description"));
                txtCategory.setText(rs.getString("categorytable.category"));
                txtUnitprice.setText(rs.getString("stockingtable.unitprice"));
                txtLowest.setText(rs.getString("stocktable.lowestSellingprice"));
                txtHighest.setText(rs.getString("stocktable.highestSellingprice"));
                individual_multiple = rs.getString("categorytable.type");
                categoryid = rs.getInt("categorytable.categoryid");
                    if(individual_multiple.equals("INDIVIDUAL")){
                            txtQty.setText("1");
                            txtQty.setEnabled(false);
                            txtImei.setText("");
                            txtImei.setEnabled(true);
                            txtImei.requestFocus();

                        }else{
                            txtQty.setText("");
                            txtQty.setEnabled(true);
                            txtQty.requestFocus();
                            txtImei.setText("---");
                            txtImei.setEnabled(false);
                        }
            }else{
                txtProduct.setText("");
                txtDescription.setText("");
                txtCodeNo.requestFocus();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" stockingin.fillfields");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        calcproducts();
    }
    private void txtCodeNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeNoKeyReleased
        txtCodeNo.setText(txtCodeNo.getText().toUpperCase());
        findProduct();
    }//GEN-LAST:event_txtCodeNoKeyReleased
    
    private void buttonPaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPaymentsActionPerformed
        String invno = txtInvoiceNo.getText();
        showDialogPayments();
        DialogSuppliers.setVisible(false);
        DialogCategory.setVisible(false);
        DialogCat.setVisible(false);
        DialogAllproducts.setVisible(false);
        String balbf_ = txtBal_b_f.getText();
        balbf_ = balbf_.trim();
        double balancebf = Double.parseDouble(balbf_);
            if(balancebf <= 0){
                buttonsave.setEnabled(false);
            }else if(txtInvoiceNo.getText().equals("")){
                buttonPayments.setEnabled(false);
            }
    }//GEN-LAST:event_buttonPaymentsActionPerformed

    private void buttonCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCategoryActionPerformed
        showdialogcat();
        DialogSuppliers.setVisible(false);
        DialogPayments.setVisible(false);
        DialogCategory.setVisible(false);
        DialogAllproducts.setVisible(false);
    }//GEN-LAST:event_buttonCategoryActionPerformed

    private void buttonUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateCategoryActionPerformed
        
        try{
            
            String sql = "SELECT * FROM categorytable WHERE category = ? AND status = '1'";
            
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtCategorys.getText());
            
            rs = pst.executeQuery();
                if(rs.next()){
                    txtCategorys.setText("");
                    txtCategorys.requestFocus();
                }else{
                    updatecategory();
                }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e+"buttonUpdateCategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }//GEN-LAST:event_buttonUpdateCategoryActionPerformed

    private void buttonAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddCategoryActionPerformed
        resetcategory();
    }//GEN-LAST:event_buttonAddCategoryActionPerformed

    private void buttonSaveCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveCategoryActionPerformed
        try{
                if(txtCategorys.getText().equals("")){
                    txtCategorys.requestFocus();
                }else if(comboType.getSelectedItem().toString().equals("Select")){
                    comboType.requestFocus();
                }else{
                String sql = "SELECT * FROM categorytable WHERE category = ? AND status = '1'";

                pst = conn.prepareStatement(sql);
                pst.setString(1, txtCategorys.getText());

                rs = pst.executeQuery();
                    if(rs.next()){
                        txtCategorys.setText("");
                        txtCategorys.requestFocus();
                    }else{
                        savecategory();
                    }
                    }
            }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e+"buttonsavecategory");
            }finally{
                try{
                    rs.close();
                    pst.close();
                }catch(Exception e){

                }
            }
    }//GEN-LAST:event_buttonSaveCategoryActionPerformed

    private void buttonDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteCategoryActionPerformed
        deletecategory();
    }//GEN-LAST:event_buttonDeleteCategoryActionPerformed

    private void tableCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoryMouseClicked
        selectedrowcategory();
    }//GEN-LAST:event_tableCategoryMouseClicked

    private void txtCategorysKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategorysKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategorysKeyTyped

    private void txtCategorysKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategorysKeyReleased
        txtCategorys.setText(txtCategorys.getText().toUpperCase());
        findcategory();
    }//GEN-LAST:event_txtCategorysKeyReleased

    private void txtSearchCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCatActionPerformed

    private void txtSearchCatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCatKeyReleased
        findcat();
    }//GEN-LAST:event_txtSearchCatKeyReleased

    private void table_categoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_categoryMouseClicked
        selectedrowcat();
    }//GEN-LAST:event_table_categoryMouseClicked

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
      char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
      }
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
      }
    }//GEN-LAST:event_txtTotalKeyTyped

    private void txtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyPressed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void calcproducts(){
        String qty = txtQty.getText();
        qty = qty.trim();
        int quantity = Integer.parseInt(qty);
        
        String unit = txtUnitprice.getText();
        unit = unit.trim();
        double unitprice = Double.parseDouble(unit);
        if(qty.equals("")){
            txtTotal.setText(String.format("%.2f", 0 * unitprice));
        }else if(unit.equals("")){
            txtTotal.setText(String.format("%.2f", quantity * 0));
        }else if(unit.equals("") && qty.equals("")){
            txtTotal.setText(String.format("%.2f", 0 * 0));
        }else{ 
        txtTotal.setText(String.format("%.2f", quantity * unitprice));
        }
    }
    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        calcproducts();
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtUnitpriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyReleased
        calcproducts();
    }//GEN-LAST:event_txtUnitpriceKeyReleased

    private void buttonSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSelectActionPerformed
        showDialogAllAccessories();
        reset();
        DialogSuppliers.setVisible(false);
        DialogPayments.setVisible(false);
        DialogCategory.setVisible(false);
        DialogCat.setVisible(false);
    }//GEN-LAST:event_buttonSelectActionPerformed

    private void txtSearchAccessoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchAccessoriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchAccessoriesActionPerformed

    private void txtSearchAccessoriesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAccessoriesKeyReleased
        findallProduct();
    }//GEN-LAST:event_txtSearchAccessoriesKeyReleased

    private void tableAllproductsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableAllproductsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableAllproductsKeyPressed

    private void tableAllproductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAllproductsMouseClicked
        selectedrowallproducts();
    }//GEN-LAST:event_tableAllproductsMouseClicked

    private void tableAllproductsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAllproductsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableAllproductsMouseEntered

    private void txtCodeNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeNoKeyTyped
       
    }//GEN-LAST:event_txtCodeNoKeyTyped

    private void chooserDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserDatePropertyChange
        findProduct();
    }//GEN-LAST:event_chooserDatePropertyChange

    private void txtImeiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImeiKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
           addstock();
        }
    }//GEN-LAST:event_txtImeiKeyPressed

    private void txtImeiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImeiKeyReleased
       txtImei.setText(txtImei.getText().toUpperCase());
    }//GEN-LAST:event_txtImeiKeyReleased

    private void txtImeiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImeiKeyTyped
        
    }//GEN-LAST:event_txtImeiKeyTyped

    private void DialogPaymentsWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogPaymentsWindowOpened
        evaluatebalancepayment();
    }//GEN-LAST:event_DialogPaymentsWindowOpened

    
    private void txtProductCodeRemoveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductCodeRemoveKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductCodeRemoveKeyTyped

    private void txtProductCodeRemoveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductCodeRemoveKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductCodeRemoveKeyReleased

    private void txtProductRemoveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductRemoveKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductRemoveKeyTyped

    private void txtProductRemoveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductRemoveKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductRemoveKeyReleased

    private void txtQtyRemoveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyRemoveKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
    }
    }//GEN-LAST:event_txtQtyRemoveKeyTyped

    private void txtQtyRemoveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyRemoveKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyRemoveKeyReleased

    private void load_removeitem(){
        try{
            String sql = "SELECT * FROM stockingtable,stocktable WHERE stockingtable.productcode = stocktable.productcode AND stockingtable.status = '1' AND"
                    + " stockingtable.imei = '"+txtimei.getText()+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    int stockingId = rs.getInt("stockingtable.stockingid");
                    stockingid = stockingId;
                    int supplierid = rs.getInt("stockingtable.supplierid");
                    id = supplierid;
                    txtProductCodeRemove.setText(rs.getString("stocktable.productcode"));
                    txtQtyRemove.setText("1");
                    txtQtyRemove.setEnabled(false);
                    categoryid = rs.getInt("stocktable.categoryid");
                    txtProductRemove.setText(rs.getString("stocktable.type"));
                }
        }catch(Exception e){
            System.out.println(e+" load_removeitem");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void txtimeiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtimeiKeyTyped
        
    }//GEN-LAST:event_txtimeiKeyTyped

    private void txtimeiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtimeiKeyReleased
        txtimei.setText(txtimei.getText().toUpperCase());
    }//GEN-LAST:event_txtimeiKeyReleased

    private void buttonDeleteRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteRemoveActionPerformed
        dialogOpen = 1 ;
        deleteremove();
    }//GEN-LAST:event_buttonDeleteRemoveActionPerformed

    private void removeitem(){
         try{
            String sql = "SELECT * FROM salestable WHERE status = '1' AND imei = ? AND status = '1'";
            
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtimei.getText());
            rs = pst.executeQuery();
                if(rs.next()){
                    lbl_exception.setText("Product with Serial No "+txtimei.getText()+" is already Sold...");
                    txtimei.setText("");
                    txtimei.requestFocus();
                }else{
                    saveremove();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e+" stockingtable.removeitem");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    private void save_remove(){
        if(txtQtyRemove.getText().equals("")){
            txtQtyRemove.setText("1");
            txtQtyRemove.setEnabled(false);
            txtimei.requestFocus();
        }else if(txtimei.getText().equals("")){
            lbl_exception.setText("Please Enter IMEI...");
            txtimei.setText("");
            txtimei.requestFocus();
        }else if(txtReason.getText().equals("")){
            lbl_exception.setText("Please Enter Reason");
            txtimei.setText("");
            txtimei.requestFocus();
        }else{
            String query_purchases = "SELECT * FROM stockingtable,salestable WHERE  stockingtable.imei = '"+txtimei.getText()+"' AND salestable.imei = '"+txtimei.getText()+"'"
                    + " AND stockingtable.status = '1' AND salestable.status = '1'";
            String query = "SELECT * FROM stockingtable WHERE imei = '"+txtimei.getText()+"' AND status = '1'";
                if(manage.checking(query_purchases) == 1){
                    lbl_exception.setText("Cannot Remove a sold Product...");
                }else if(manage.checking(query) == 0){
                    lbl_exception.setText("Serial No "+txtimei.getText()+" is not in stock...");
                }else{
                    dialogOpen = 1 ;
                    String qty = txtQtyRemove.getText();
                    qty = qty.trim();
                    int _qty = Integer.parseInt(qty);

                    try{
                        String sql = "SELECT * FROM stocktable WHERE productcode = '"+txtProductCodeRemove.getText()+"' AND status = '1'";
                        pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                            if(rs.next()){
                                int quantity = rs.getInt("qty");
                                    if(_qty > quantity){
                                        txtQtyRemove.setText("");
                                        txtQtyRemove.requestFocus();
                                    }else{
                                        removeitem();
                                    }
                            }
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e+"stockingin.Save_Remove");
                    }finally{
                        try{
                            rs.close();
                            pst.close();
                        }catch(Exception e){

                        }
                    }
               }
       }
    }
    private void buttonSaveRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveRemoveActionPerformed
        save_remove();
    }//GEN-LAST:event_buttonSaveRemoveActionPerformed

    private void buttonExitRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitRemoveActionPerformed
        DialogRemove.dispose();
        enablePanel();
        resetRemove();
    }//GEN-LAST:event_buttonExitRemoveActionPerformed

    private void tableRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRemoveMouseClicked
        selectedrowremove();
    }//GEN-LAST:event_tableRemoveMouseClicked

    private void DialogRemoveWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogRemoveWindowClosing
            enablePanel();
            lbl_exception.setText("");
    }//GEN-LAST:event_DialogRemoveWindowClosing

    private void DialogRemoveWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogRemoveWindowOpened
        txtQty.setEnabled(false);
    }//GEN-LAST:event_DialogRemoveWindowOpened

    private void buttonRemoveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonRemoveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonRemoveKeyPressed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to continue REMOVING Products?", "Confirm",JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){
        }else if (response == JOptionPane.YES_OPTION){
            showdialogremove();
            txtReason.requestFocus();
        }else if(response == JOptionPane.CLOSED_OPTION){

        }
    }//GEN-LAST:event_buttonRemoveActionPerformed

    private void buttonAddCategoriesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonAddCategoriesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAddCategoriesKeyPressed

    private void buttonAddCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddCategoriesActionPerformed
        showDialogCategories();
        resetcategory();
    }//GEN-LAST:event_buttonAddCategoriesActionPerformed

    private void buttonUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonUpdateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonUpdateKeyPressed

    private void update_purchases(){
        try{
            if(txtSupplierPhone.getText().equals("")){
                txtSupplierPhone.requestFocus();
                lbl_exception.setText("Please Select Supplier...");
            }else if(txtInvoiceNo.getText().equals("")){
                txtInvoiceNo.requestFocus();
                lbl_exception.setText("Please Enter Invoice #...");
            }else if(txtCodeNo.getText().equals("")){
                txtCodeNo.requestFocus();
                lbl_exception.setText("Please Enter Code #");
            }else if(txtProduct.getText().equals("")){
                txtProduct.requestFocus();
                lbl_exception.setText("Please Enter Product...");
            }else if(txtDescription.getText().equals("")){
                txtDescription.requestFocus();
                lbl_exception.setText("Please Enter Description...");
            }else if(txtImei.getText().equals("")){
                txtImei.requestFocus();
                lbl_exception.setText("Please Enter Serial No...");
            }else if(txtCategory.getText().equals("")){
                txtCategory.requestFocus();
                lbl_exception.setText("Please Select Category...");
            }else if(txtQty.getText().equals("")){
                txtQty.requestFocus();
                lbl_exception.setText("Please Enter Qty...");
            }else if(txtUnitprice.getText().equals("")){
                txtUnitprice.requestFocus();
                lbl_exception.setText("Please Enter Unit Price/B.Price...");
            }else if(txtLowest.getText().equals("")){
                txtLowest.requestFocus();
                lbl_exception.setText("Please Enter Lowest Price...");
            }else if(txtHighest.getText().equals("")){
                txtHighest.requestFocus();
                lbl_exception.setText("Please Enter Highest Price...");
            }else{
            
                String qty = txtQty.getText();
                qty = qty.trim();
                int qty$ = Integer.parseInt(qty);

                String unit = txtUnitprice.getText();
                unit = unit.trim();
                double unitprice = Double.parseDouble(unit);

                String lowest = txtLowest.getText();
                lowest = lowest.trim();
                double lowestprice  = Double.parseDouble(lowest);

                String highest = txtHighest.getText();
                highest = highest.trim();
                double highestprice = Double.parseDouble(highest);

                String sql = "SELECT qty FROM stocktable WHERE productcode = '"+txtCodeNo.getText()+"' AND status = '1' ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    if(rs.next()){
                        int $qty = rs.getInt("qty");

                            if(unitprice > lowestprice ){
                                JOptionPane.showMessageDialog(null, "The Lowest price set is lower that unit price. Cannot Update that value");
                                txtLowest.setText("");
                                txtLowest.requestFocus();
                            }else if(highestprice < unitprice){
                                JOptionPane.showMessageDialog(null, "The Highest price set is lower that unit price. Cannot Update that value");
                                txtHighest.setText("");
                                txtHighest.requestFocus();
                            }else if(highestprice < lowestprice){
                                JOptionPane.showMessageDialog(null, "The Highest price set is lower that Lowest price. Cannot Update that value");
                                txtHighest.setText("");
                                txtHighest.requestFocus();
                            }else{
                                if((qty_ - qty$) > $qty){
                                    JOptionPane.showMessageDialog(null, "You cannot delete that amount of Qty!!! Try again with another value");
                                }else{
                                    update_checkkwasales();
                                }
                            } 
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e+" stockingin.buttonRemove");
            }finally{
                try{
                    rs.close();
                    pst.close();
                }catch(Exception e){

                }
            }  
    }
    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        update_purchases();
    }//GEN-LAST:event_buttonUpdateActionPerformed
    private void update_checkkwaremove(){
        try{
            String sql = "SELECT * FROM removetable WHERE status = '1' AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, imei);
            rs = pst.executeQuery();
                if(rs.next()&& !rs.getString("imei").equals("---")){
                    JOptionPane.showMessageDialog(null,"This Product Had been removed earlier. It has been used! Please Enter a new product");
                }else{
                    update();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+ " stockingin.update_checkkwasales");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void update_checkkwasales(){
        try{
            String sql = "SELECT * FROM salestable WHERE status = '1' AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, imei);
            rs = pst.executeQuery();
                if(rs.next() && !rs.getString("imei").equals("---")){
                    JOptionPane.showMessageDialog(null,"This record cannot be updated, It has been used!");
                }else{
                    update_checkkwaremove();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+ " stockingin.update_checkkwasales");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void buttonSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonSaveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSaveKeyPressed

    private void enterstock(){
        String unit = txtUnitprice.getText();
        unit = unit.trim();
        double unitprice = Double.parseDouble(unit);

        String lowest = txtLowest.getText();
        lowest = lowest.trim();
        double lowestprice  = Double.parseDouble(lowest);

        String highest = txtHighest.getText();
        highest = highest.trim();
        double highestprice = Double.parseDouble(highest);

        if(unitprice > lowestprice ){
            JOptionPane.showMessageDialog(null, "The Lowest price set is lower that unit price. Cannot save that value");
            txtLowest.setText("");
            txtLowest.requestFocus();
        }else if(highestprice < unitprice){
            JOptionPane.showMessageDialog(null, "The Highest price set is lower that unit price. Cannot save that value");
            txtHighest.setText("");
            txtHighest.requestFocus();
        }else if(highestprice < lowestprice){
            JOptionPane.showMessageDialog(null, "The Highest price set is lower that Lowest price. Cannot save that value");
            txtHighest.setText("");
            txtHighest.requestFocus();
        }else{
            save();
        }
    }
    private void addstock(){
        try{
            String sql = "SELECT * FROM stockingtable WHERE status = 1 AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtImei.getText());
            rs = pst.executeQuery();
                if(txtImei.getText().equals("---") && !txtImei.isEnabled()){
                    enterstock();
                }else if(rs.next()){
                        //JOptionPane.showMessageDialog(null, "/Serial No. already entered. Please Check and Enter the correct Values");
                        lbl_exception.setText("Serial No. already entered. Please Check and Enter the correct Values");
                        txtImei.setText("");
                        txtImei.requestFocus();
                }else{
                    enterstock();
                }
        }catch(Exception e){
            System.out.println(e+ "stockingin.addstock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void save_purchases(){
        if(txtSupplierPhone.getText().equals("")){
            txtSupplierPhone.requestFocus();
            lbl_exception.setText("Please Select Supplier...");
        }else if(txtInvoiceNo.getText().equals("")){
            txtInvoiceNo.requestFocus();
            lbl_exception.setText("Please Enter Invoice #...");
        }else if(txtCodeNo.getText().equals("")){
            txtCodeNo.requestFocus();
            lbl_exception.setText("Please Enter Code #");
        }else if(txtProduct.getText().equals("")){
            txtProduct.requestFocus();
            lbl_exception.setText("Please Enter Product...");
        }else if(txtDescription.getText().equals("")){
            txtDescription.requestFocus();
            lbl_exception.setText("Please Enter Description...");
        }else if(txtImei.getText().equals("")){
            txtImei.requestFocus();
            lbl_exception.setText("Please Enter Serial No...");
        }else if(txtCategory.getText().equals("")){
            txtCategory.requestFocus();
            lbl_exception.setText("Please Select Category...");
        }else if(txtQty.getText().equals("")){
            txtQty.requestFocus();
            lbl_exception.setText("Please Enter Qty...");
        }else if(txtUnitprice.getText().equals("")){
            txtUnitprice.requestFocus();
            lbl_exception.setText("Please Enter Unit Price/B.Price...");
        }else if(txtLowest.getText().equals("")){
            txtLowest.requestFocus();
            lbl_exception.setText("Please Enter Lowest Price...");
        }else if(txtHighest.getText().equals("")){
            txtHighest.requestFocus();
            lbl_exception.setText("Please Enter Highest Price...");
        }else{
            addstock();
        }
    }
    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        save_purchases();
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonResetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonResetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonResetKeyPressed

    private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
        reset();
        txtQty.setText("");
    }//GEN-LAST:event_buttonResetActionPerformed

    private void buttondeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttondeleteActionPerformed
        deletepayment();
    }//GEN-LAST:event_buttondeleteActionPerformed

    private void DialogCategoryWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogCategoryWindowClosing
        enablePanel();
    }//GEN-LAST:event_DialogCategoryWindowClosing

    private void DialogCatWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogCatWindowClosing
        enablePanel();
    }//GEN-LAST:event_DialogCatWindowClosing

    private void DialogAllproductsWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogAllproductsWindowClosing
        enablePanel();  
    }//GEN-LAST:event_DialogAllproductsWindowClosing

    private void buttonExitCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitCategoryActionPerformed
        DialogCategory.dispose();
        enablePanel();
    }//GEN-LAST:event_buttonExitCategoryActionPerformed

    private void txtImeiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtImeiPropertyChange
        
    }//GEN-LAST:event_txtImeiPropertyChange

    private void txtImeiInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtImeiInputMethodTextChanged
        
    }//GEN-LAST:event_txtImeiInputMethodTextChanged

    private void txtImeiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImeiFocusLost
        
    }//GEN-LAST:event_txtImeiFocusLost

    private void txtTransactionIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTransactionIdKeyReleased
        txtTransactionId.setText(txtTransactionId.getText().toUpperCase());
    }//GEN-LAST:event_txtTransactionIdKeyReleased

    private void txtImeiVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtImeiVetoableChange
       
    }//GEN-LAST:event_txtImeiVetoableChange

    private void txtImeiCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtImeiCaretPositionChanged
      
    }//GEN-LAST:event_txtImeiCaretPositionChanged

    private void txtImeiCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtImeiCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeiCaretUpdate

    private void txtImeiAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtImeiAncestorAdded
       
    }//GEN-LAST:event_txtImeiAncestorAdded

    private void txtImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeiActionPerformed

    private void txtProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductActionPerformed

    private void txtInvoiceNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInvoiceNoKeyReleased
        txtInvoiceNo.setText(txtInvoiceNo.getText().toUpperCase());
    }//GEN-LAST:event_txtInvoiceNoKeyReleased

    private void txtInvoiceNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInvoiceNoKeyPressed
        buttonPayments.setEnabled(true);
        lbl_exception.setText("");
    }//GEN-LAST:event_txtInvoiceNoKeyPressed

    private void delete(){
        String unitprice = txtUnitprice.getText();
        unitprice = unitprice.trim();
        double unit = Double.parseDouble(unitprice);
        
        String sql = "SELECT imei FROM removetable WHERE imei = '"+imei+"' AND status = '1'";
        String query = "SELECT imei FROM salestable WHERE status = '1' AND imei = '"+imei+"'";
        String delete_query = "DELETE FROM stockingtable WHERE imei = '"+imei+"' AND status = '1'";
        String update_query_payments = "UPDATE paymenttable SET totalamount = totalamount - '"+unit+"' WHERE details = '"+txtInvoiceNo.getText()+"'"
                + " AND supplierid = '"+id+"' AND status = '1'";
        if(manage.checking(sql) == 1){
            reset();
            lbl_exception.setText("Product Already Removed. Cannot be deleted...");
        }else if(manage.checking(query) == 1){
            reset();
            lbl_exception.setText("Product Already Sold. Cannot be deleted...");
        }else{
            manage.delete_(delete_query);
            String query_stock = "UPDATE stocktable SET type = '"+txtProduct.getText()+"',description = '"+txtDescription.getText()+"',"
                    + "categoryid = '"+categoryid+"',qty = '"+manage.totalstock_qty(txtCodeNo.getText())+"' WHERE status = '1' AND productcode = '"+txtCodeNo.getText()+"'";
            manage.update(query_stock);
            manage.update(update_query_payments);
            reset();
        }
    }
    private void buttonDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDelActionPerformed
        
        JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION){
                
            }else if (response == JOptionPane.YES_OPTION){
                delete();
            }else if(response == JOptionPane.CLOSED_OPTION){
                    
            }   
    }//GEN-LAST:event_buttonDelActionPerformed

    private void buttonDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonDelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDelKeyPressed

    private void txtimeiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtimeiKeyPressed
        lbl_exception.setText("");
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                String sql = "SELECT * FROM stockingtable WHERE imei = '"+txtimei.getText()+"' AND status = '1'";
                String sql1 = "SELECT * FROM removetable WHERE imei = '"+txtimei.getText()+"' AND status = '1'";
                String sql2 = "SELECT * FROM salestable WHERE imei = '"+txtimei.getText()+"' AND status = '1'";
                if(manage.checking(sql) == 0){
                    lbl_exception.setText("Product with Serial No "+txtimei.getText()+" is not registered...");
                    txtimei.setText("");
                    txtimei.requestFocus();
                }else if(manage.checking(sql1) == 1){
                    lbl_exception.setText("Cannot Remove this product. Already Removed...");
                    txtimei.setText("");
                    txtimei.requestFocus();
                }else if(manage.checking(sql2) == 1){
                    lbl_exception.setText("Cannot Remove this product. Already Sold...");
                    txtimei.setText("");
                    txtimei.requestFocus();
                }else{
                    load_removeitem();
                    save_remove();
                }
            }
    }//GEN-LAST:event_txtimeiKeyPressed

    private void txt_ProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductActionPerformed

    private void txt_ProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ProductKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txt_ProductKeyPressed

    private void txt_ProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ProductKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductKeyReleased

    private void txt_DescriptionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DescriptionKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txt_DescriptionKeyPressed

    private void txt_DescriptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DescriptionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DescriptionKeyReleased

    private void txt_LowestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_LowestFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LowestFocusLost

    private void txt_LowestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_LowestKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
      }
    }//GEN-LAST:event_txt_LowestKeyTyped

    private void txt_LowestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_LowestKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txt_LowestKeyPressed

    private void txt_HighestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_HighestKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
      }
    }//GEN-LAST:event_txt_HighestKeyTyped

    private void txt_HighestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_HighestKeyPressed
        lbl_exception.setText("");
    }//GEN-LAST:event_txt_HighestKeyPressed

    private void button_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_UpdateActionPerformed
        String lowest = txt_Lowest.getText();
        lowest = lowest.trim();
        double low = Double.parseDouble(lowest);
        
        String highest = txt_Highest.getText();
        highest = highest.trim();
        
        String unit = txtUnitprice.getText();
        unit = unit.trim();
        double unit_price = Double.parseDouble(unit);
        
        double high = Double.parseDouble(highest);
            if(unit_price > low){
                lbl_exception.setText("Check of the values of Unitprice and Lowest Prices...");
                reset_to_update();
            }else if(low >= high){
                lbl_exception.setText("Check of the values of Lowest and Highest Prices...");
                reset_to_update();
            }else{
                update_to_update();
            }
        
    }//GEN-LAST:event_button_UpdateActionPerformed

    private void button_UpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_button_UpdateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_UpdateKeyPressed

    private void update_to_update(){
        String sql = "UPDATE stocktable SET type = '"+txt_Product.getText()+"',description = '"+txt_Description.getText()+"', lowestsellingprice = "
                + "'"+txt_Lowest.getText()+"',highestsellingprice = '"+txt_Highest.getText()+"' WHERE productcode = '"+txt_codeno.getText()+"' AND status = '1'";
        manage.update(sql);
        
        reset_to_update();
        lbl_exception.setText("Updated Product "+txt_codeno.getText()+" Successfully... ");
    }
    private void reset_to_update(){
        txt_codeno.setText("");
        txt_Product.setText("");
        txt_Description.setText("");
        txt_Lowest.setText("");
        txt_Highest.setText("");
        txt_codeno.requestFocus();
    }
    private void button_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_resetActionPerformed
        reset_to_update();
    }//GEN-LAST:event_button_resetActionPerformed

    private void button_resetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_button_resetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_resetKeyPressed

    private void txtHighestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHighestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHighestActionPerformed

    private void txtReasonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReasonKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtimei.requestFocus();
        }
    }//GEN-LAST:event_txtReasonKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDialog DialogAllproducts;
    public javax.swing.JDialog DialogCat;
    public javax.swing.JDialog DialogCategory;
    public javax.swing.JDialog DialogPayments;
    public javax.swing.JDialog DialogRemove;
    public javax.swing.JDialog DialogSuppliers;
    private javax.swing.JTabbedPane StockingTab;
    private javax.swing.JButton buttonAddCategories;
    public javax.swing.JButton buttonAddCategory;
    private javax.swing.JButton buttonCategory;
    private javax.swing.JButton buttonChooseSupplier;
    private javax.swing.JButton buttonDel;
    public javax.swing.JButton buttonDeleteCategory;
    public javax.swing.JButton buttonDeleteRemove;
    private javax.swing.JButton buttonExit;
    public javax.swing.JButton buttonExitCategory;
    public javax.swing.JButton buttonExitRemove;
    private javax.swing.JButton buttonPayments;
    public javax.swing.JButton buttonRemove;
    private javax.swing.JButton buttonReset;
    private javax.swing.JButton buttonSave;
    public javax.swing.JButton buttonSaveCategory;
    public javax.swing.JButton buttonSaveRemove;
    private javax.swing.JButton buttonSelect;
    private javax.swing.JButton buttonUpdate;
    public javax.swing.JButton buttonUpdateCategory;
    private javax.swing.JButton button_Update;
    private javax.swing.JButton button_reset;
    private javax.swing.JButton buttonadd;
    private javax.swing.JButton buttondelete;
    private javax.swing.JButton buttonexit;
    private javax.swing.JButton buttonsave;
    private com.toedter.calendar.JDateChooser chooserDate;
    private javax.swing.JComboBox comboPaymentMethod;
    private javax.swing.JComboBox comboType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    public javax.swing.JLabel lbl_exception;
    private javax.swing.JLabel searchingIcon;
    private javax.swing.JLabel searchingIcon1;
    private javax.swing.JTable tableAllproducts;
    private javax.swing.JTable tableCategory;
    private javax.swing.JTable tablePayments;
    private javax.swing.JTable tableRemove;
    private javax.swing.JTable tableSelectSupplier;
    private javax.swing.JTable tableStocking;
    private javax.swing.JTable table_category;
    private javax.swing.JLabel tick;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JLabel txtBal_b_f;
    private javax.swing.JLabel txtBalance;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtCategorys;
    private javax.swing.JTextField txtCodeNo;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtHighest;
    private javax.swing.JTextField txtImei;
    private javax.swing.JTextField txtInvoiceNo;
    private javax.swing.JTextField txtLowest;
    private javax.swing.JTextField txtProduct;
    private javax.swing.JTextField txtProductCodeRemove;
    private javax.swing.JTextField txtProductRemove;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtQtyRemove;
    private javax.swing.JTextArea txtReason;
    private javax.swing.JTextField txtSearchAccessories;
    private javax.swing.JTextField txtSearchCat;
    private javax.swing.JTextField txtSearchSupplier;
    private javax.swing.JTextField txtSupplierName;
    private javax.swing.JTextField txtSupplierPhone;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTransactionId;
    private javax.swing.JTextField txtUnitprice;
    private javax.swing.JTextField txt_Description;
    private javax.swing.JTextField txt_Highest;
    private javax.swing.JTextField txt_Lowest;
    private javax.swing.JTextField txt_Product;
    private javax.swing.JLabel txt_codeno;
    private javax.swing.JTextField txtimei;
    // End of variables declaration//GEN-END:variables
}
