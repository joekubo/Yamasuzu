
package yamasuzu_myshopsoft;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class QuotationPanel extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private String productid;
    private int info_id;
    public String loggeduserid;

    
    Calendar c = Calendar.getInstance();
    Manage manage = new Manage();
    public QuotationPanel() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        removeothercolumns();
        c.add(Calendar.YEAR, 0);
        chooserQuotationDate.getDateEditor().setDate(c.getTime());
    }
    private void removeothercolumns(){
            TableColumn idColumn = tableQuotationDetails.getColumn("invoiceno");
            TableColumn idColumn1 = tableQuotationDetails.getColumn("serviceid");
            TableColumn idColumn2 = tableQuotationDetails.getColumn("s");
            TableColumn idColumn3 = tableQuotationDetails.getColumn("vat");
            
            idColumn.setMaxWidth(0);
            idColumn1.setMaxWidth(0);
            idColumn2.setMaxWidth(0);
            idColumn3.setMaxWidth(0);
            
            idColumn.setMinWidth(0);
            idColumn1.setMinWidth(0);
            idColumn2.setMinWidth(0);
            idColumn3.setMinWidth(0);
            
            idColumn.setPreferredWidth(0);
            idColumn1.setPreferredWidth(0);
            idColumn2.setPreferredWidth(0);
            idColumn3.setPreferredWidth(0);
    }
    public void generateQuotation_no(){
        try{
             String sql = "SELECT quotation_no FROM quotation_table ORDER BY quotation_no DESC LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    int quotationno = rs.getInt("quotation_no");
                    txtQuotationNo.setText(String.format("%d",quotationno+1));
                        
                }else{
                    txtQuotationNo.setText("1");
                }
        }catch(Exception e){
            System.out.println(e+" generateQuotation_no");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void reset(){
        c.add(Calendar.YEAR, 0);
        chooserQuotationDate.getDateEditor().setDate(c.getTime());
        txtClientName.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtPhoneNo.setText("");
        txtEmailAddress.setText("");
        txtClientName.requestFocus();
        txtTotal.setText("0.00");
        chooserQuotationDate.setEnabled(true);
        buttonAddQuotationItems.setEnabled(false);
        lbl_total.setText("");
        lbl_vat.setText("");
        generateQuotation_no();
        findQuotation();
        buttonReset.setEnabled(true);
        buttonExit.setEnabled(true);
        buttonDelete.setEnabled(true);
        buttonPrint.setEnabled(false);
        
    }
    private void cleartablequotationInfo(){
        removeothercolumns();
        tableQuotationDetails.setModel(new DefaultTableModel(null,new String[]{"invoiceno","serviceid","Service/Item","Qty","Unit Price","Total Price","s","vat"}));
    }
    private void calc(JTextField field){
        if(field.getText().equals("")){
            txtVat.setText("0");
            txtTotalPrice.setText("0");
        }else{
            String qty = txtQty.getText();
            qty = qty.trim();
            int quantity = Integer.parseInt(qty);

            String unit = txtUnitPrice.getText();
            unit = unit.trim();
            double unitprice = Double.parseDouble(unit);

            double vat = (quantity * unitprice) * 0.16;
            txtVat.setText(String.format("%.2f", vat));

            txtTotalPrice.setText(String.format("%.2f", vat + (quantity * unitprice)));
        }
    }
    //calculate total value of all the invoice services
    private double getSumtotal(){
        int rowscount = tableQuotationDetails.getRowCount();
        double sum_totals = 0;
            for(int i = 0; i < rowscount; i++){
                sum_totals = sum_totals + Double.parseDouble(tableQuotationDetails.getValueAt(i, 5).toString());
            }
        return sum_totals;
    }
    
    private double getSumVAT(){
        int rowscount = tableQuotationDetails.getRowCount();
        double sum_vat = 0;
            for(int i = 0; i < rowscount; i++){
                sum_vat = sum_vat + Double.parseDouble(tableQuotationDetails.getValueAt(i, 7).toString());
            }
        return sum_vat;
    }
    
    
   
    private void savequotation(){
        try{
            String sql = "INSERT INTO quotation_table(quotation_no,client_name,address,city,phone_no,email,quotation_date,total,user_id,s,total_vat)VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,txtQuotationNo.getText());
            pst.setString(2, txtClientName.getText());
            pst.setString(3, txtAddress.getText());
            pst.setString(4, txtCity.getText());
            pst.setString(5, txtPhoneNo.getText());
            pst.setString(6, txtEmailAddress.getText());
            pst.setString(7, ((JTextField)chooserQuotationDate.getDateEditor().getUiComponent()).getText());
            pst.setString(8, lbl_total.getText());
            pst.setString(9, loggeduserid);
            pst.setString(10, "1");
            pst.setString(11, lbl_vat.getText());
            pst.execute();
            
            savequotation_info();
            lbl_exception.setText("Proforma Invoice Saved Successfully...");
            generateQuotation_no();
            resetInfo();
            reset();
            cleartablequotationInfo();
            DialogQuotations.dispose();
            buttonAddQuotationItems.setEnabled(false);
            chooserQuotationDate.setEnabled(true);
            buttonReset.setEnabled(true);
            buttonExit.setEnabled(true);
        }catch(Exception e){
            String query_quote = "DELETE FROM quotation_table WHERE quotation_no = '"+txtQuotationNo.getText()+"'";
            manage.delete_(query_quote);
            System.out.println(e+"savequotation");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void calc_edit(JTextField field){
        if(field.getText().equals("")){
            lbl_vatedit.setText("0");
            txtTotalpriceInfo.setText("0");
        }else{
            String qty = txtQtyInfo.getText();
            qty = qty.trim();
            int $qty = Integer.parseInt(qty);

            String unit = txtUnitpriceInfo.getText();
            unit = unit.trim();
            double $unit = Double.parseDouble(unit);
            
            double vat = 0.16 * $qty * $unit;
            lbl_vatedit.setText(String.format("%.2f", vat));
            txtTotalpriceInfo.setText(String.format("%.2f", vat + ($qty * $unit)));
        }
    }
    private void savequotation_info(){
        try{
                  int rows = tableQuotationDetails.getRowCount();
                  //conn.setAutoCommit(false);
                  String sql = "INSERT INTO quotation_info(quotation_no,service_id,price,s,qty,unit_price,vat)VALUES(?,?,?,?,?,?,?)";

                  pst = conn.prepareStatement(sql);
                      for(int row = 0; row<rows; row++){
                          String quotationno = (String)tableQuotationDetails.getValueAt(row, 0);
                          String service_id = (String)tableQuotationDetails.getValueAt(row, 1);
                          double price = (Double)tableQuotationDetails.getValueAt(row, 5);
                          String s = (String)tableQuotationDetails.getValueAt(row, 6);
                          String qty = (String)tableQuotationDetails.getValueAt(row, 3);
                          Double unitprice = (Double)tableQuotationDetails.getValueAt(row, 4); 
                          Double vat = (Double)tableQuotationDetails.getValueAt(row, 7);
                          
                          pst.setString(1, quotationno);
                          pst.setString(2, service_id);
                          pst.setDouble(3, price);
                          pst.setString(4, s);
                          pst.setString(5, qty);
                          pst.setDouble(6, unitprice);
                          pst.setDouble(7, vat);
                          pst.execute();
                      }
        }catch(Exception e){
            System.out.println( e+ "savequotation_info");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    
    private void loadquotationinfo_transaction(){

        String price = txtTotalPrice.getText();
        price = price.trim();
        double $price = Double.parseDouble(price);
        
        String unit_ = txtUnitPrice.getText();
        unit_ = unit_.trim();
        double $unit = Double.parseDouble(unit_);
        
        String vat_ = txtVat.getText();
        vat_ = vat_.trim();
        double vat = Double.parseDouble(vat_);
        
                DefaultTableModel model = (DefaultTableModel)tableQuotationDetails.getModel();
                model.addRow(new Object[]{txtQuotationNo.getText(),
                                          productid,
                                          comboProduct.getSelectedItem().toString(),
                                          txtQty.getText(),
                                          $unit,
                                          $price,
                                          "1",
                                           vat});
                                          
                
                lbl_total.setText(Double.toString(getSumtotal()));
                txtTotal.setText(Double.toString(getSumtotal()));
                lbl_vat.setText(Double.toString(getSumVAT()));
                
                
                resetInfo();
    }
    
    
    private void filldata_product(){
        comboProduct.removeAllItems();
        comboProduct.addItem("Select Service/Item");
        Manage manage = new Manage();
        String sql = "SELECT type FROM stocktable WHERE status = '1'";
        String value = "type";
        manage.fillcombo(sql, comboProduct, value);
    }
    
    public void showdialogquotation(){
        DialogQuotations.setVisible(true);
        DialogQuotations.setSize(798, 362);
        DialogQuotations.setAlwaysOnTop(true);
        DialogQuotations.setLocationRelativeTo(this);
        DialogQuotations.setResizable(false);
    }
    
   
   
    private void selectedrow(){
        try{
            int row = tableQuotation.getSelectedRow();
            String table_click = tableQuotation.getValueAt(row, 0).toString();
            String sql = "SELECT * FROM quotation_table WHERE s = '1' AND quotation_no = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    comboProductInfo.removeAllItems();
                    comboProductInfo.addItem("Select Service/Item");
                    String query_combo = "SELECT type FROM stocktable WHERE status = '1'";
                    String value = "type";
                    manage.fillcombo(query_combo, comboProductInfo, value);
                    txtQuotationNo.setText(rs.getString("quotation_no"));
                    ((JTextField)chooserQuotationDate.getDateEditor().getUiComponent()).setText(rs.getString("quotation_date"));
                    txtClientName.setText(rs.getString("client_name"));
                    txtAddress.setText(rs.getString("address"));
                    txtCity.setText(rs.getString("city"));
                    txtPhoneNo.setText(rs.getString("phone_no"));
                    txtEmailAddress.setText(rs.getString("email"));
                    txtTotal.setText(rs.getString("total"));
                    manage.showdialog(DialogEditQuote, jPanel1,679, 446);
                    lbl_exception.setText("");
                    buttonAddQuotationItems.setEnabled(false);
                    buttonReset.setEnabled(false);
                    buttonDelete.setEnabled(false);
                    buttonPrint.setEnabled(false);
                    buttonExit.setEnabled(false);
                    loadquote_items();
                }
            
        }catch(Exception e){
            System.out.println(e+" selectedrow");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loadquote_items(){
        String query = "SELECT quotation_info.id AS 'id',stocktable.type AS 'Item',stocktable.description AS 'Description',quotation_info.unit_price AS 'Price per Unit'"
                + ",quotation_info.qty AS 'Qty',quotation_info.vat AS 'VAT',quotation_info.price AS 'Total' FROM quotation_info,stocktable "
                + "WHERE quotation_info.service_id = stocktable.productcode AND quotation_info.s = '1' AND quotation_info.quotation_no "
                            + "= '"+txtQuotationNo.getText()+"'";
                    manage.update_table(query, tableInfo);
    }
    private void selectedrow_info(){
        try{
            int row = tableInfo.getSelectedRow();
            String table_click = tableInfo.getValueAt(row, 0).toString();
            String sql = "SELECT * FROM quotation_info,stocktable WHERE quotation_info.service_id = stocktable. productcode AND quotation_info.s = '1' AND"
                    + " quotation_info.id = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    info_id = rs.getInt("quotation_info.id");
                    comboProductInfo.setSelectedItem(rs.getString("stocktable.type"));
                    txtQtyInfo.setText(rs.getString("quotation_info.qty"));
                    txtUnitpriceInfo.setText(rs.getString("quotation_info.unit_price"));
                    txtTotalpriceInfo.setText(rs.getString("quotation_info.price"));
                }
        }catch(Exception e){
            System.out.println(e+" selectedrow_info");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void deleteQuote(){
        String sql = "DELETE FROM quotation_table WHERE s = '1' AND quotation_no = '"+txtQuotationNo.getText()+"'";
        manage.delete_(sql);
        String query = "DELETE FROM quotation_info WHERE s = '1' AND quotation_no = '"+txtQuotationNo.getText()+"'";
        manage.delete_(query);
        String name = "Invoice # "+txtQuotationNo.getText()+"";
        manage.loggedmessagedelete(name);
        generateQuotation_no();
        reset();
        resetInfo();
    }
    private void save_editinfo(){
        try{
            String total_ = txtTotalpriceInfo.getText();
            total_ = total_.trim();
            double total = Double.parseDouble(total_);
            
            if(comboProductInfo.getSelectedItem().toString().equals("Select Service/Item")){
                lbl_exception.setText("Please Select Service/Item...");
            }else if(txtQtyInfo.getText().equals("")){
                lbl_exception.setText("Please Enter Qty...");
                txtQtyInfo.requestFocus();
            }else if(txtUnitpriceInfo.getText().equals("")){
                lbl_exception.setText("Please Enter Unit Price...");
                txtUnitpriceInfo.requestFocus();
            }else{
                String sql = "INSERT INTO quotation_info(quotation_no,service_id,qty,unit_price,price,s,vat)VALUES(?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                
                pst.setString(1, txtQuotationNo.getText());
                pst.setString(2, productid);
                pst.setString(3, txtQtyInfo.getText());
                pst.setString(4, txtUnitpriceInfo.getText());
                pst.setString(5, txtTotalpriceInfo.getText());
                pst.setString(6, "1");
                pst.setString(7, lbl_vatedit.getText());
                
                pst.execute();
                String query_quote = "UPDATE quotation_table SET total = total + '"+total+"' WHERE  s = '1' AND quotation_no = '"+txtQuotationNo.getText()+"'";
                manage.update(query_quote);
                reset_quote_info();
                lbl_exception.setText("Proforma Invoice Item "+comboProductInfo.getSelectedItem().toString()+" Added Successfully...");
            }
        }catch(Exception e){
            System.out.println(e+" save_editinfo");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void reset_quote_info(){
        comboProductInfo.setSelectedItem("Select Service/Item");
        txtQtyInfo.setText("");
        txtUnitpriceInfo.setText("");
        txtTotalpriceInfo.setText("");
        lbl_exception.setText("");
        lbl_vatedit.setText("");
        delete_nill();
        loadquote_items();
    }
    private void delete_info(){
        String total_ = txtTotalpriceInfo.getText();
        total_ = total_.trim();
        double total = Double.parseDouble(total_);
        
        String sql = "DELETE FROM quotation_info WHERE id = '"+info_id+"'AND s = '1'";
        manage.delete_(sql);
        String quote = "UPDATE quotation_table SET total = total - '"+total+"' WHERE quotation_no = '"+txtQuotationNo.getText()+"' AND s = '1'";
        manage.update(quote);
        String name = "Service/Item "+comboProductInfo.getSelectedItem().toString()+"";
        manage.loggedmessagedelete(name);
        reset_quote_info();
        lbl_exception.setText(""+comboProductInfo.getSelectedItem().toString()+" Deleted Successfully...");
        
    }
    private void delete_nill(){
        String sql = "DELETE FROM quotation_table WHERE total = '0'";
        manage.delete_(sql);
        findQuotation();
    }
    public ArrayList<SearchQuotation> ListQuotation(String ValToSearch){
        ArrayList<SearchQuotation>viewQuotation = new ArrayList<SearchQuotation>();
        try{
            String searchQuery = "SELECT * FROM quotation_table,usertable WHERE quotation_table.user_id = usertable.id AND CONCAT(quotation_table.quotation_no,'',"
                    + "quotation_table.client_name,'',quotation_table.phone_no,'',quotation_table.email,'',quotation_table.quotation_date,'',usertable.name) "
                    + "LIKE'%"+ValToSearch+"%' AND quotation_table.s = '1'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchQuotation search;
            while(rs.next()){
                search = new SearchQuotation(
                                    rs.getString("quotation_table.quotation_no"),
                                    rs.getString("quotation_table.client_name"),
                                    rs.getString("quotation_table.address"),
                                    rs.getString("quotation_table.city"),
                                    rs.getString("quotation_table.phone_no"),
                                    rs.getString("quotation_table.email"),
                                    rs.getString("quotation_table.quotation_date"),
                                    rs.getDouble("quotation_table.total"),
                                    rs.getString("usertable.name")
                                            );
                viewQuotation.add(search);
            }
        }catch(Exception e){
            System.out.println(e+" SearchQuotation/findQuotation");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return viewQuotation;
    }
    public void findQuotation(){
        ArrayList<SearchQuotation> view= ListQuotation(txtSearch.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Quotation #","Client Name","Phone #","Email","Quotation Date","Total"});
        
        Object[] row = new Object[6];
        
            for(int i = 0; i < view.size(); i ++){
                
             row[0] = view.get(i).getQuotationno();
             row[1] = view.get(i).getClientname();
             row[2] = view.get(i).getPhoneno();
             row[3] = view.get(i).getEmail();
             row[4] = view.get(i).getInvoicedate();
             row[5] = view.get(i).getTotal();
             
             model.addRow(row);
            }
            tableQuotation.setModel(model);
    }
     public void resetInfo(){
        filldata_product();
        comboProduct.setEnabled(true);
        comboProduct.setSelectedItem("Select Service");
        txtTotalPrice.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        txtVat.setText("");
        comboProduct.requestFocus();
        buttonsave.setEnabled(true);
        buttondelete.setEnabled(false);
        buttonPrint.setEnabled(false);
        add.setEnabled(false);
        productid = "";
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogQuotations = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboProduct = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        lbl_total = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtVat = new javax.swing.JLabel();
        lbl_vat = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        buttonreset = new javax.swing.JButton();
        buttonexit = new javax.swing.JButton();
        buttonsave = new javax.swing.JButton();
        buttondelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableQuotationDetails = new javax.swing.JTable();
        DialogEditQuote = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtQtyInfo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboProductInfo = new javax.swing.JComboBox<>();
        txtUnitpriceInfo = new javax.swing.JTextField();
        txtTotalpriceInfo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_vatedit = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableInfo = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        buttonResetInfo = new javax.swing.JButton();
        buttonExitInfo = new javax.swing.JButton();
        buttonsaveInfo = new javax.swing.JButton();
        buttondeleteInfo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbl_exception = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        buttonReset = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonPrint = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtQuotationNo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtEmailAddress = new javax.swing.JTextField();
        txtTotal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        buttonAddQuotationItems = new javax.swing.JButton();
        txtClientName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        chooserQuotationDate = new com.toedter.calendar.JDateChooser();
        txtAddress = new javax.swing.JTextField();
        txtCity = new javax.swing.JTextField();
        txtPhoneNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableQuotation = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        labelserch = new javax.swing.JLabel();
        panelQuote = new javax.swing.JPanel();

        DialogQuotations.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogQuotations.setUndecorated(true);
        DialogQuotations.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                DialogQuotationsWindowOpened(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setBackground(new java.awt.Color(204, 204, 204));
        txtTotalPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalPriceFocusGained(evt);
            }
        });
        txtTotalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalPriceKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalPriceKeyPressed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(0, 0, 153));
        jLabel11.setText("Product:");

        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setText("Total Price:");

        comboProduct.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboProductPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel21.setForeground(new java.awt.Color(0, 0, 153));
        jLabel21.setText("Qty:");

        txtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtyFocusGained(evt);
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

        jLabel22.setForeground(new java.awt.Color(0, 0, 153));
        jLabel22.setText("Unit Price:");

        txtUnitPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUnitPriceFocusGained(evt);
            }
        });
        txtUnitPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQty)
                            .addComponent(comboProduct, 0, 222, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_total.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N

        jLabel15.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 153));
        jLabel15.setText("Total:");

        add.setText("Add Items");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(0, 0, 153));
        jLabel14.setText("VAT:");

        txtVat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtVat.setForeground(new java.awt.Color(0, 0, 153));

        lbl_vat.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N

        jLabel20.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 153));
        jLabel20.setText("Total VAT:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVat, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel20))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        buttonreset.setText("Reset");
        buttonreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonresetActionPerformed(evt);
            }
        });

        buttonexit.setText("Exit");
        buttonexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonexitActionPerformed(evt);
            }
        });

        buttonsave.setText("Save");
        buttonsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonsaveActionPerformed(evt);
            }
        });

        buttondelete.setText("Delete");
        buttondelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttondeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonreset, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(buttonsave, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(buttondelete, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(buttonexit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonreset)
                    .addComponent(buttonexit)
                    .addComponent(buttonsave)
                    .addComponent(buttondelete))
                .addContainerGap())
        );

        tableQuotationDetails.setBackground(new java.awt.Color(255, 255, 153));
        tableQuotationDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "invoiceno", "serviceid", "Service/Item", "Qty", "Unit Price", "Total Price", "s", "vat"
            }
        ));
        tableQuotationDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableQuotationDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableQuotationDetails);

        javax.swing.GroupLayout DialogQuotationsLayout = new javax.swing.GroupLayout(DialogQuotations.getContentPane());
        DialogQuotations.getContentPane().setLayout(DialogQuotationsLayout);
        DialogQuotationsLayout.setHorizontalGroup(
            DialogQuotationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogQuotationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogQuotationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DialogQuotationsLayout.setVerticalGroup(
            DialogQuotationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogQuotationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DialogEditQuote.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogEditQuote.setUndecorated(true);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel16.setForeground(new java.awt.Color(0, 0, 153));
        jLabel16.setText("Qty:");

        txtQtyInfo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtyInfoFocusGained(evt);
            }
        });
        txtQtyInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyInfoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyInfoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyInfoKeyReleased(evt);
            }
        });

        jLabel17.setForeground(new java.awt.Color(0, 0, 153));
        jLabel17.setText("Unit Price:");

        jLabel18.setForeground(new java.awt.Color(0, 0, 153));
        jLabel18.setText("Product:");

        comboProductInfo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboProductInfoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txtUnitpriceInfo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUnitpriceInfoFocusGained(evt);
            }
        });
        txtUnitpriceInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnitpriceInfoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnitpriceInfoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnitpriceInfoKeyReleased(evt);
            }
        });

        txtTotalpriceInfo.setEditable(false);
        txtTotalpriceInfo.setBackground(new java.awt.Color(204, 204, 204));
        txtTotalpriceInfo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalpriceInfoFocusGained(evt);
            }
        });
        txtTotalpriceInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalpriceInfoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalpriceInfoKeyPressed(evt);
            }
        });

        jLabel19.setForeground(new java.awt.Color(0, 0, 153));
        jLabel19.setText("Total Price:");

        jLabel23.setForeground(new java.awt.Color(0, 0, 153));
        jLabel23.setText("Total Price:");

        lbl_vatedit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtyInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotalpriceInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtUnitpriceInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_vatedit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(48, 48, 48))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtUnitpriceInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(comboProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtQtyInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbl_vatedit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTotalpriceInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        tableInfo.setBackground(new java.awt.Color(255, 255, 204));
        tableInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInfoMouseClicked(evt);
            }
        });
        tableInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableInfoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableInfoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableInfo);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        buttonResetInfo.setText("Reset");
        buttonResetInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetInfoActionPerformed(evt);
            }
        });

        buttonExitInfo.setText("Exit");
        buttonExitInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitInfoActionPerformed(evt);
            }
        });

        buttonsaveInfo.setText("Save");
        buttonsaveInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonsaveInfoActionPerformed(evt);
            }
        });

        buttondeleteInfo.setText("Delete");
        buttondeleteInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttondeleteInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buttonResetInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(buttonsaveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(buttondeleteInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(buttonExitInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonResetInfo)
                    .addComponent(buttonExitInfo)
                    .addComponent(buttonsaveInfo)
                    .addComponent(buttondeleteInfo))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout DialogEditQuoteLayout = new javax.swing.GroupLayout(DialogEditQuote.getContentPane());
        DialogEditQuote.getContentPane().setLayout(DialogEditQuoteLayout);
        DialogEditQuoteLayout.setHorizontalGroup(
            DialogEditQuoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogEditQuoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DialogEditQuoteLayout.setVerticalGroup(
            DialogEditQuoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogEditQuoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/quotation.png"))); // NOI18N
        jLabel1.setText("Proforma Invoice");

        lbl_exception.setFont(new java.awt.Font("SansSerif", 3, 12)); // NOI18N
        lbl_exception.setForeground(new java.awt.Color(204, 0, 0));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        buttonReset.setText("Reset");
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetActionPerformed(evt);
            }
        });

        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonPrint.setText("Print Proforma Invoice");
        buttonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(buttonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonReset)
                    .addComponent(buttonExit)
                    .addComponent(buttonDelete)
                    .addComponent(buttonPrint))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        jLabel9.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setText("Quotation #:");

        txtQuotationNo.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        txtQuotationNo.setForeground(new java.awt.Color(153, 0, 0));
        txtQuotationNo.setText("                   ");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtEmailAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailAddressActionPerformed(evt);
            }
        });
        txtEmailAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailAddressKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailAddressKeyReleased(evt);
            }
        });

        txtTotal.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        txtTotal.setText("                        ");

        jLabel8.setForeground(new java.awt.Color(0, 0, 153));
        jLabel8.setText("City:");

        buttonAddQuotationItems.setText("Add  Item");
        buttonAddQuotationItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddQuotationItemsActionPerformed(evt);
            }
        });

        txtClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientNameActionPerformed(evt);
            }
        });
        txtClientName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClientNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientNameKeyReleased(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setText("Address:");

        jLabel4.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 153));
        jLabel4.setText("Total:");

        chooserQuotationDate.setDateFormatString("yyyy-MM-dd");

        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAddressKeyReleased(evt);
            }
        });

        txtCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityActionPerformed(evt);
            }
        });
        txtCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCityKeyReleased(evt);
            }
        });

        txtPhoneNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNoActionPerformed(evt);
            }
        });
        txtPhoneNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneNoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneNoKeyReleased(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setText("Client Name:");

        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setText("Date:");

        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setText("Phone #:");

        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setText("Email Address:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooserQuotationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(27, 27, 27)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(buttonAddQuotationItems, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chooserQuotationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAddQuotationItems)
                .addGap(24, 24, 24))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tableQuotation.setBackground(new java.awt.Color(255, 255, 204));
        tableQuotation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableQuotation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableQuotationMouseClicked(evt);
            }
        });
        tableQuotation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableQuotationKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableQuotation);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(labelserch)
                                .addGap(354, 354, 354))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(199, 199, 199))))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(labelserch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 337, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuotationNo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 737, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuotationNo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Create Proforma Invoice", jPanel2);

        javax.swing.GroupLayout panelQuoteLayout = new javax.swing.GroupLayout(panelQuote);
        panelQuote.setLayout(panelQuoteLayout);
        panelQuoteLayout.setHorizontalGroup(
            panelQuoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        panelQuoteLayout.setVerticalGroup(
            panelQuoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Loading Proforma Invoice", panelQuote);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223)
                        .addComponent(lbl_exception, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_exception, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
        generateQuotation_no();
        reset();
        resetInfo();
    }//GEN-LAST:event_buttonResetActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        DialogQuotations.dispose();
        this.setVisible(false);
        lbl_exception.setText("");
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){

        }else if (response == JOptionPane.YES_OPTION){
            deleteQuote();
        }else if(response == JOptionPane.CLOSED_OPTION){

        }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrintActionPerformed
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {

                Manage manage = new Manage();
                String sql = "SELECT companytable.dealerin,companytable.phoneno AS 'company_phone',companytable.email AS 'company_email',companytable.companyname,"
                        + "quotation_table.quotation_date,usertable.name AS 'user_name',quotation_table.quotation_no,quotation_table.client_name,quotation_table."
                        + "phone_no,quotation_table.email,quotation_table.total,quotation_table.total_vat FROM companytable,usertable,quotation_table WHERE "
                        + "companytable.companyid = usertable.companyid AND usertable.id = quotation_table.user_id AND quotation_table.s = '1' AND quotation_table"
                        + ".quotation_no = '"+txtQuotationNo.getText()+"'";
                String path = "Reports/quotation.jrxml";
                manage.report(sql, path, panelQuote);
                jTabbedPane1.setSelectedIndex(1);
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_buttonPrintActionPerformed

    private void txtEmailAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailAddressActionPerformed

    private void txtEmailAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailAddressKeyPressed
        lbl_exception.setText("");
        buttonAddQuotationItems.setEnabled(true);
    }//GEN-LAST:event_txtEmailAddressKeyPressed

    private void txtEmailAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailAddressKeyReleased
        txtEmailAddress.setText(txtEmailAddress.getText().toLowerCase());
    }//GEN-LAST:event_txtEmailAddressKeyReleased

    private void buttonAddQuotationItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddQuotationItemsActionPerformed
        if(txtClientName.getText().equals("")){
            lbl_exception.setText("Please Enter Client Name...");
            txtClientName.requestFocus();
        }else if(txtAddress.getText().equals("")){
            lbl_exception.setText("Please Enter Address...");
            txtAddress.requestFocus();
        }else if(txtCity.getText().equals("")){
            lbl_exception.setText("Please Enter City...");
            txtCity.requestFocus();
        }else if(txtPhoneNo.getText().equals("")){
            lbl_exception.setText("Please Enter Phone #...");
            txtPhoneNo.requestFocus();
        }else if(txtEmailAddress.getText().equals("")){
            lbl_exception.setText("Please Enter Email Address...");
            txtEmailAddress.requestFocus();
        }else{
            manage.showdialog(DialogQuotations, jPanel1,807, 436);
            buttonAddQuotationItems.setEnabled(false);
            chooserQuotationDate.setEnabled(false);
            buttonReset.setEnabled(false);
            buttonExit.setEnabled(false);
            resetInfo();
        }
    }//GEN-LAST:event_buttonAddQuotationItemsActionPerformed

    private void txtClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientNameActionPerformed

    private void txtClientNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientNameKeyPressed
        lbl_exception.setText("");
        buttonAddQuotationItems.setEnabled(true);
    }//GEN-LAST:event_txtClientNameKeyPressed

    private void txtClientNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientNameKeyReleased
        txtClientName.setText(txtClientName.getText().toUpperCase());
    }//GEN-LAST:event_txtClientNameKeyReleased

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
        lbl_exception.setText("");
        buttonAddQuotationItems.setEnabled(true);
    }//GEN-LAST:event_txtAddressKeyPressed

    private void txtAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyReleased
        txtAddress.setText(txtAddress.getText().toUpperCase());
    }//GEN-LAST:event_txtAddressKeyReleased

    private void txtCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityActionPerformed

    private void txtCityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyPressed
        lbl_exception.setText("");
        buttonAddQuotationItems.setEnabled(true);
    }//GEN-LAST:event_txtCityKeyPressed

    private void txtCityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyReleased
        txtCity.setText(txtCity.getText().toUpperCase());
    }//GEN-LAST:event_txtCityKeyReleased

    private void txtPhoneNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNoActionPerformed

    private void txtPhoneNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNoKeyTyped
        char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneNoKeyTyped

    private void txtPhoneNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNoKeyPressed
        lbl_exception.setText("");
        buttonAddQuotationItems.setEnabled(true);
    }//GEN-LAST:event_txtPhoneNoKeyPressed

    private void txtPhoneNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNoKeyReleased
        txtPhoneNo.setText(txtPhoneNo.getText().toUpperCase());
    }//GEN-LAST:event_txtPhoneNoKeyReleased

    private void tableQuotationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableQuotationMouseClicked
        selectedrow();
    }//GEN-LAST:event_tableQuotationMouseClicked

    private void tableQuotationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableQuotationKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrow();
        }
    }//GEN-LAST:event_tableQuotationKeyReleased

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        findQuotation();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtTotalPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalPriceFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceFocusGained

    private void txtTotalPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalPriceKeyTyped
        char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtTotalPriceKeyTyped

    private void txtTotalPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalPriceKeyPressed
        if(comboProduct.getSelectedItem().toString().equals("Select Service")){
            add.setEnabled(false);
        }else if(txtTotalPrice.getText().equals("")){
            add.setEnabled(false);
        }else{
            add.setEnabled(true);
        }
    }//GEN-LAST:event_txtTotalPriceKeyPressed

    private void txtQtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyFocusGained

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
        char vchar = evt.getKeyChar();
	      if((!(Character.isDigit(vchar)))
	          ||(vchar == KeyEvent.VK_BACK_SPACE)
	          
	          || (vchar == KeyEvent.VK_DELETE)){
	        evt.consume();
	    }
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        calc(txtQty);
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtUnitPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnitPriceFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitPriceFocusGained

    private void txtUnitPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyTyped
        char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtUnitPriceKeyTyped

    private void txtUnitPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyPressed
        add.setEnabled(true);
    }//GEN-LAST:event_txtUnitPriceKeyPressed

    private void txtUnitPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyReleased
        calc(txtUnitPrice);
    }//GEN-LAST:event_txtUnitPriceKeyReleased

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        loadquotationinfo_transaction();
    }//GEN-LAST:event_addActionPerformed

    private void buttonresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonresetActionPerformed
        resetInfo();
    }//GEN-LAST:event_buttonresetActionPerformed

    private void buttonexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonexitActionPerformed
        DialogQuotations.dispose();
        cleartablequotationInfo();
        buttonAddQuotationItems.setEnabled(false);
        txtClientName.setEnabled(true);
        chooserQuotationDate.setEnabled(true);
        buttonReset.setEnabled(true);
        buttonExit.setEnabled(true);
        removeothercolumns();
        reset();
        resetInfo();
    
    }//GEN-LAST:event_buttonexitActionPerformed

    private void buttonsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonsaveActionPerformed
        savequotation();
    }//GEN-LAST:event_buttonsaveActionPerformed

    private void buttondeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttondeleteActionPerformed
        //removeothercolumns();
        int row = tableQuotationDetails.getSelectedRow();
        ((DefaultTableModel)tableQuotationDetails.getModel()).removeRow(row);
        lbl_total.setText(Double.toString(getSumtotal()));
        txtTotal.setText(Double.toString(getSumtotal()));
    }//GEN-LAST:event_buttondeleteActionPerformed

    private void tableQuotationDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableQuotationDetailsMouseClicked
        buttonsave.setEnabled(false);
        buttondelete.setEnabled(true);
        buttonPrint.setEnabled(false);
        int row = tableQuotationDetails.getSelectedRow();
        String table_click = (String)tableQuotationDetails.getValueAt(row, 1);
        productid = table_click;
    }//GEN-LAST:event_tableQuotationDetailsMouseClicked

    private void DialogQuotationsWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogQuotationsWindowOpened

    }//GEN-LAST:event_DialogQuotationsWindowOpened

    private void txtQtyInfoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyInfoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyInfoFocusGained

    private void txtQtyInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyInfoKeyTyped
        char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtQtyInfoKeyTyped

    private void txtQtyInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyInfoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyInfoKeyPressed

    private void txtQtyInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyInfoKeyReleased
        calc_edit(txtQtyInfo);
    }//GEN-LAST:event_txtQtyInfoKeyReleased

    private void comboProductInfoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboProductInfoPopupMenuWillBecomeInvisible
        try{
            String sql = "SELECT * FROM stocktable WHERE type = ? AND status = '1'";
            pst = conn.prepareStatement(sql);

            pst.setString(1, (String)comboProductInfo.getSelectedItem());
            rs = pst.executeQuery();
            if(rs.next()){
                productid = rs.getString("productcode");
                txtUnitpriceInfo.setText(rs.getString("highestsellingprice"));
                txtQtyInfo.requestFocus();
            }
        }catch(Exception e){
            System.out.println(e+" comboServiceInfoPopupMenuWillBecomeInvisible");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){

            }
        }
    }//GEN-LAST:event_comboProductInfoPopupMenuWillBecomeInvisible

    private void txtUnitpriceInfoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnitpriceInfoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitpriceInfoFocusGained

    private void txtUnitpriceInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceInfoKeyTyped
        char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtUnitpriceInfoKeyTyped

    private void txtUnitpriceInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceInfoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitpriceInfoKeyPressed

    private void txtUnitpriceInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceInfoKeyReleased
        calc_edit(txtUnitpriceInfo);
    }//GEN-LAST:event_txtUnitpriceInfoKeyReleased

    private void txtTotalpriceInfoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalpriceInfoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalpriceInfoFocusGained

    private void txtTotalpriceInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalpriceInfoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalpriceInfoKeyTyped

    private void txtTotalpriceInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalpriceInfoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalpriceInfoKeyPressed

    private void tableInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInfoMouseClicked
        selectedrow_info();
    }//GEN-LAST:event_tableInfoMouseClicked

    private void tableInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableInfoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInfoKeyPressed

    private void tableInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableInfoKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrow_info();
        }
    }//GEN-LAST:event_tableInfoKeyReleased

    private void buttonResetInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetInfoActionPerformed
        reset_quote_info();
    }//GEN-LAST:event_buttonResetInfoActionPerformed

    private void buttonExitInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitInfoActionPerformed
        DialogEditQuote.dispose();
        buttonAddQuotationItems.setEnabled(true);
        chooserQuotationDate.setEnabled(true);
        buttonReset.setEnabled(true);
        buttonExit.setEnabled(true);
        buttonDelete.setEnabled(true);
        buttonPrint.setEnabled(true);
        buttonAddQuotationItems.setEnabled(false);
        add.setEnabled(true);
        reset_quote_info();
    }//GEN-LAST:event_buttonExitInfoActionPerformed

    private void buttonsaveInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonsaveInfoActionPerformed
        try{
            String sql = "SELECT * FROM quotation_info WHERE quotation_no = '"+txtQuotationNo.getText()+"' AND s = '1' AND service_id = '"+productid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                lbl_exception.setText("Can only edit Service/Item "+comboProductInfo.getSelectedItem().toString()+". Already Exists...");
            }else{
                save_editinfo();
            }
        }catch(Exception e){
            System.out.println(e+" buttonsaveInfoActionPerformed");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){

            }
        }
    }//GEN-LAST:event_buttonsaveInfoActionPerformed

    private void buttondeleteInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttondeleteInfoActionPerformed
        delete_info();
    }//GEN-LAST:event_buttondeleteInfoActionPerformed

    private void comboProductPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboProductPopupMenuWillBecomeInvisible
        try{
            String sql = "SELECT * FROM stocktable WHERE type = ? ";
            pst = conn.prepareStatement(sql);

            pst.setString(1, (String)comboProduct.getSelectedItem());
            rs = pst.executeQuery();
            if(rs.next()){
                productid = rs.getString("productcode");
                txtUnitPrice.setText(rs.getString("highestsellingprice"));
                txtQty.requestFocus();
                if(!txtTotalPrice.getText().equals("")){
                    add.setEnabled(true);
                }else{
                    add.setEnabled(false);
                }
                add.setEnabled(true);
            }
            add.setEnabled(true);
        }catch(Exception e){
            System.out.println( e+" comboProductPopupMenuWillBecomeInvisible");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){

            }
        }
    }//GEN-LAST:event_comboProductPopupMenuWillBecomeInvisible


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDialog DialogEditQuote;
    public javax.swing.JDialog DialogQuotations;
    private javax.swing.JButton add;
    private javax.swing.JButton buttonAddQuotationItems;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonExitInfo;
    private javax.swing.JButton buttonPrint;
    private javax.swing.JButton buttonReset;
    private javax.swing.JButton buttonResetInfo;
    private javax.swing.JButton buttondelete;
    private javax.swing.JButton buttondeleteInfo;
    private javax.swing.JButton buttonexit;
    private javax.swing.JButton buttonreset;
    private javax.swing.JButton buttonsave;
    private javax.swing.JButton buttonsaveInfo;
    private com.toedter.calendar.JDateChooser chooserQuotationDate;
    private javax.swing.JComboBox<String> comboProduct;
    private javax.swing.JComboBox<String> comboProductInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
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
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelserch;
    public javax.swing.JLabel lbl_exception;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_vat;
    private javax.swing.JLabel lbl_vatedit;
    private javax.swing.JPanel panelQuote;
    private javax.swing.JTable tableInfo;
    private javax.swing.JTable tableQuotation;
    private javax.swing.JTable tableQuotationDetails;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtClientName;
    private javax.swing.JTextField txtEmailAddress;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtQtyInfo;
    private javax.swing.JLabel txtQuotationNo;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtTotalpriceInfo;
    private javax.swing.JTextField txtUnitPrice;
    private javax.swing.JTextField txtUnitpriceInfo;
    private javax.swing.JLabel txtVat;
    // End of variables declaration//GEN-END:variables
}
