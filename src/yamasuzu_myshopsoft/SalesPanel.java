package yamasuzu_myshopsoft;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class SalesPanel extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    
    Calendar c = Calendar.getInstance();
    
    public String loggedinuserid;
    public String loggedpriviledge;
    public String companyid;
    private String productcode;
    private int categoryid;
    private int clientid;
    private int salesid;
    private double bal_bf;
    private int invoiceid;
    
   
    Manage manage = new Manage();
    public SalesPanel() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        labelPhone.setToolTipText("Search Phone Details");
        buttonSave.setEnabled(false);
        removeothercolumns();
        txtReceipt.setText("1");
        comboClients.removeAllItems();
        String sql = "SELECT * FROM clientstable WHERE status = '1'";
        manage.fillcombo(sql, comboClients, "clientname");
        txtLpono.setText("-");
        comboClients.setEnabled(true);
        txtLpono.setEnabled(true);
    }
    private void removeothercolumns(){
            TableColumn idColumn = tableReceipt.getColumn("details");
            TableColumn idColumn1 = tableReceipt.getColumn("date");
            TableColumn idColumn2 = tableReceipt.getColumn("Profit");
            TableColumn idColumn3 = tableReceipt.getColumn("means");
            TableColumn idColumn4 = tableReceipt.getColumn("transactionid");
            TableColumn idColumn5 = tableReceipt.getColumn("sold");
            TableColumn idColumn6 = tableReceipt.getColumn("status");
            TableColumn idColumn7 = tableReceipt.getColumn("userid");
            TableColumn idColumn8 = tableReceipt.getColumn("companyid");
            TableColumn idColumn9 = tableReceipt.getColumn("vat");
            
            idColumn.setMaxWidth(0);
            idColumn1.setMaxWidth(0);
            idColumn2.setMaxWidth(0);
            idColumn3.setMaxWidth(0);
            idColumn4.setMaxWidth(0);
            idColumn5.setMaxWidth(0);
            idColumn6.setMaxWidth(0);
            idColumn7.setMaxWidth(0);
            idColumn8.setMaxWidth(0);
            idColumn9.setMaxWidth(0);
            
            idColumn.setMinWidth(0);
            idColumn1.setMinWidth(0);
            idColumn2.setMinWidth(0);
            idColumn3.setMinWidth(0);
            idColumn4.setMinWidth(0);
            idColumn5.setMinWidth(0);
            idColumn6.setMinWidth(0);
            idColumn7.setMinWidth(0);
            idColumn8.setMinWidth(0);
            idColumn9.setMinWidth(0);
            
            idColumn.setPreferredWidth(0);
            idColumn1.setPreferredWidth(0);
            idColumn2.setPreferredWidth(0);
            idColumn3.setPreferredWidth(0);
            idColumn4.setPreferredWidth(0);
            idColumn5.setPreferredWidth(0);
            idColumn6.setPreferredWidth(0);
            idColumn7.setPreferredWidth(0);
            idColumn8.setPreferredWidth(0);
            idColumn9.setPreferredWidth(0);
    }
    public void reset(){
        c.add(Calendar.YEAR, 0);
        chooserDate.getDateEditor().setDate(c.getTime());
        txtPaidamount.setText("");
        txtChange.setText("0.00");
        txtSearch.setText("");
        txtUnitprice.setText("");
        comboSerialno.setSelectedItem("Select Serial No");
       // comboClients.setSelectedItem("-");
        txtQty.setText("");
        txtVat.setText("");
        
        lbl_exception.setText("");
        txtIndividualTotal.setText("");
        txtUnitprice.requestFocus();
        txtQty.setText("1");
        txtQty.setEnabled(false);
        buttonSave.setEnabled(true);
        buttonDelete.setEnabled(false);
        txtPaidamount.setEnabled(true);
        tick.setVisible(false);
        findProduct();
        generatereceiptNo();
    }
    
    private double profit;
    private void calculateprofits(){
        try{
            String serial = (String)comboSerialno.getSelectedItem();
            String sql = "SELECT unitprice FROM stockingtable WHERE status = '1' AND  imei = '"+serial+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    if(txtQty.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "No Qty is Enter. Please Enter Qty!");
                        txtQty.requestFocus();
                    }else{
                        double unitprice = rs.getDouble("unitprice");
                    
                        String unit = txtUnitprice.getText();
                        unit = unit.trim();
                        double salesunitprice = Double.parseDouble(unit);
                        String qty = txtQty.getText();
                        qty = qty.trim();
                        int quantity = Integer.parseInt(qty);

                        profit = quantity * (salesunitprice - unitprice);
                    }
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salespanel.calculateprofits");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void resetsales(){
        txtQty.setText("1");
        txtUnitprice.setText("");
        comboSerialno.setSelectedItem("Select Serial No");
        comboSerialno.setEnabled(true);
        //comboClients.setSelectedItem("-");
        //clientid = 0;
        txtIndividualTotal.setText("0.00");
        txtGrandTotal.setText("0.00");
        vattxt.setText("0.00");
        txtSearch.setText("");
        findProduct();
        txtPaidamount.requestFocus();
//        txtQty.setEnabled(true);
        comboClients.removeAllItems();
        String sql = "SELECT * FROM clientstable WHERE status = '1'";
        manage.fillcombo(sql, comboClients, "clientname");
        txtLpono.setText("-");
       
    }
    private void calctotal(){
        if(txtUnitprice.getText().equals("")){
            txtVat.setText("0");
            txtIndividualTotal.setText("0");
        }else{
            double vat;
            String qty = txtQty.getText();
            qty = qty.trim();
            int quantity = Integer.parseInt(qty);

            String unit = txtUnitprice.getText();
            unit = unit.trim();
            double unitprice = Double.parseDouble(unit);

            vat = 0.16 * quantity * unitprice;
            txtVat.setText(String.format("%.2f", vat));
            txtIndividualTotal.setText(String.format("%.2f", vat + (unitprice * quantity)));
        }
        
    }
    private void selectedRowSales(){
        try{
            int row = tableReceipt.getSelectedRow();
            
            String table_click = tableReceipt.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM salestable,stocktable WHERE stocktable.productcode = salestable.productcode AND salestable.salesid = "
                    + "'"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                salesid = (rs.getInt("salestable.salesid"));
                productcode = rs.getString("stocktable.productcode");
                txtQty.setText(rs.getString("salestable.qty"));
                comboSerialno.setSelectedItem(rs.getString("salestable.imei"));
                txtUnitprice.setText(rs.getString("salestable.unitprice"));
                txtQty.setEnabled(false);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ selectedRowSales");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void grandtotal(){
        try{
            String Date = ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText();
            String sql = "SELECT COALESCE(SUM(totalprice), 0) FROM salestable WHERE details = '"+txtReceipt.getText()+"' AND date = '"+Date+"'"
                    + "AND status = '1'";
           
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                 txtGrandTotal.setText(String.format("%.2f", rs.getDouble("COALESCE(SUM(totalprice), 0)")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+(" Error @ grandtotal"));
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void generatereceiptNo(){
        try{
            String sql = "SELECT details FROM salestable ORDER BY details DESC LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    int details = rs.getInt("details");
                    txtReceipt.setText(String.format("%d",details+1));
                        
                }else{
                    txtReceipt.setText("1");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salepanel.generatereceiptNo");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    public ArrayList<SearchProductSales> ListProducts(String ValToSearch){
        ArrayList<SearchProductSales>productList = new ArrayList<SearchProductSales>();
       
        try{
            String searchQuery = "SELECT * FROM stocktable WHERE CONCAT(productcode,'',type,'',description,'',qty)  LIKE'%"+ValToSearch+"%'"
                    + "AND status = '1' AND qty > 0";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchProductSales search;
            while(rs.next()){
                search = new SearchProductSales(
                                    rs.getString("productcode"),
                                    rs.getString("type"),
                                    rs.getString("description"),
                                    rs.getInt("qty")
                                            );
                productList.add(search);
            }
        }catch(Exception e){
            System.out.println(e+" salespanel.SearchProduct/Findproduct");
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
        ArrayList<SearchProductSales> product = ListProducts(txtSearch.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"P-CODE","Type","Description","Remaining-Qty"});
        
        Object[] row = new Object[4];
        
            for(int i = 0; i < product.size(); i ++){
                
             row[0] = product.get(i).getProductcode();
             row[1] = product.get(i).getType();
             row[2] = product.get(i).getDescription();
             row[3] = product.get(i).getQty();
             
             model.addRow(row);
            }
            tableStock.setModel(model);
    }
    private void checking_category(){
        try{
            String sql = "SELECT * FROM categorytable WHERE  status = '1' AND categoryid = '"+categoryid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    String type = rs.getString("type");
                        if(type.equals("MULTIPLE")){
                            txtQty.setEnabled(true);
                            txtQty.setText("");
                            comboSerialno.setSelectedItem("---");
                            comboSerialno.setEnabled(false);
                        }else{
                            txtQty.setText("1");
                            txtQty.setEnabled(false);
                            comboSerialno.setEnabled(true);
                            comboSerialno.requestFocus();
                        }
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    String $product;
    String $description;
    private void selectedRowStock(){
        try{
//            jTabbedPane2.setSelectedIndex(0);
//            buttonSave.setEnabled(true);
            buttonReceipt.setEnabled(false);
            jPanel5.setEnabled(false);
            int row = tableStock.getSelectedRow();
            String table_click = tableStock.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM stocktable,stockingtable WHERE stockingtable.productcode = stocktable.productcode AND "
                    + "stocktable.status = '1' AND stocktable.qty > 0 AND stocktable.productcode = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txtQty.setText("1");
                comboSerialno.setSelectedItem("Select Serial No");
                txtQty.requestFocus();
                productcode = rs.getString("stocktable.productcode");
                $product = rs.getString("stocktable.type");
                $description = rs.getString("stocktable.description");
                
                txtUnitprice.setText(rs.getString("stocktable.highestsellingprice"));
                calctotal();
                categoryid = rs.getInt("stocktable.categoryid");
                checking_category();
                comboSerialno.removeAllItems();
                comboSerialno.addItem("Select Serial No");
                String query = "SELECT * FROM stockingtable WHERE  stockingtable.productcode = '"+productcode+"' AND status = '1'";
                manage.fillcombo(query, comboSerialno, "stockingtable.imei");
                jTabbedPane2.setSelectedIndex(0);
                load_client();
            }
            
        }catch(Exception e){
            System.out.println(e+" salespanel.SelectedRowStock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogInvoice = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        txtPaidInvoiceAmount = new javax.swing.JTextField();
        txtInvoiceBalance = new javax.swing.JLabel();
        txtBalbf = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtTotalInvoiceamount = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableInvoices = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        buttonSaveInvoice = new javax.swing.JButton();
        buttonDeleteInvoice = new javax.swing.JButton();
        buttonExitInvoice = new javax.swing.JButton();
        buttonUpdateInvoice = new javax.swing.JButton();
        buttonAddInvoice = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtSearchinvoice = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        buttonDelete = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableStock = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        labelPhone = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        txtUnitprice = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableReceipt = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        comboSerialno = new javax.swing.JComboBox<>();
        txtIndividualTotal = new javax.swing.JLabel();
        tick = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtVat = new javax.swing.JLabel();
        txtLpono = new javax.swing.JTextField();
        comboClients = new javax.swing.JComboBox<>();
        panelReport = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtReceipt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        chooserDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtChange = new javax.swing.JLabel();
        txtPaidamount = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtGrandTotal = new javax.swing.JLabel();
        buttonReceipt = new javax.swing.JButton();
        vattxt = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_exception = new javax.swing.JLabel();

        DialogInvoice.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(java.awt.Color.white);

        jPanel7.setBackground(java.awt.Color.white);
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel28.setText("Balance:");

        txtPaidInvoiceAmount.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtPaidInvoiceAmount.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtPaidInvoiceAmountInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtPaidInvoiceAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPaidInvoiceAmountPropertyChange(evt);
            }
        });
        txtPaidInvoiceAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaidInvoiceAmountKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidInvoiceAmountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidInvoiceAmountKeyReleased(evt);
            }
        });

        txtInvoiceBalance.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtInvoiceBalance.setForeground(java.awt.Color.blue);

        txtBalbf.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtBalbf.setForeground(java.awt.Color.blue);
        txtBalbf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBalbfKeyTyped(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel27.setText("Bal b/f:");

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel26.setText("Paid Amount:");

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel30.setText("Total Amount:");

        txtTotalInvoiceamount.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtTotalInvoiceamount.setForeground(java.awt.Color.blue);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalInvoiceamount, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel26))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtPaidInvoiceAmount)
                        .addComponent(txtBalbf, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBalbf, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalInvoiceamount, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtPaidInvoiceAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableInvoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableInvoices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInvoicesMouseClicked(evt);
            }
        });
        tableInvoices.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableInvoicesKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableInvoices);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonSaveInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSaveInvoice.setText("Save");
        buttonSaveInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveInvoiceActionPerformed(evt);
            }
        });
        buttonSaveInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonSaveInvoiceKeyPressed(evt);
            }
        });

        buttonDeleteInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDeleteInvoice.setText("Delete");
        buttonDeleteInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteInvoiceActionPerformed(evt);
            }
        });
        buttonDeleteInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonDeleteInvoiceKeyPressed(evt);
            }
        });

        buttonExitInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExitInvoice.setText("Exit");
        buttonExitInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitInvoiceActionPerformed(evt);
            }
        });

        buttonUpdateInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUpdateInvoice.setText("Update");
        buttonUpdateInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateInvoiceActionPerformed(evt);
            }
        });
        buttonUpdateInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonUpdateInvoiceKeyPressed(evt);
            }
        });

        buttonAddInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonAddInvoice.setText("Add");
        buttonAddInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddInvoiceActionPerformed(evt);
            }
        });
        buttonAddInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonAddInvoiceKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAddInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSaveInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonUpdateInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDeleteInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExitInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAddInvoice)
                    .addComponent(buttonSaveInvoice)
                    .addComponent(buttonUpdateInvoice)
                    .addComponent(buttonDeleteInvoice)
                    .addComponent(buttonExitInvoice))
                .addContainerGap())
        );

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel29.setText("Searching:");

        txtSearchinvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSearchinvoice.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSearchinvoiceInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtSearchinvoice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtSearchinvoicePropertyChange(evt);
            }
        });
        txtSearchinvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchinvoiceKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchinvoiceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchinvoiceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearchinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DialogInvoiceLayout = new javax.swing.GroupLayout(DialogInvoice.getContentPane());
        DialogInvoice.getContentPane().setLayout(DialogInvoiceLayout);
        DialogInvoiceLayout.setHorizontalGroup(
            DialogInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DialogInvoiceLayout.setVerticalGroup(
            DialogInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jPanel3.setBackground(java.awt.Color.white);
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonDelete.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        tableStock.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableStockMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStockMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableStockMouseEntered(evt);
            }
        });
        tableStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableStockKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableStock);

        txtSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        labelPhone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(labelPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        txtUnitprice.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtUnitprice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUnitpriceFocusGained(evt);
            }
        });
        txtUnitprice.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtUnitpriceInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtUnitprice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtUnitpricePropertyChange(evt);
            }
        });
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

        tableReceipt.setBackground(java.awt.Color.black);
        tableReceipt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableReceipt.setForeground(java.awt.Color.white);
        tableReceipt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "details", "date", "P-Code", "Product", "Description", "IMEI", "Qty", "Unit Price", "Total Price", "Profit", "means", "transactionid", "sold", "status", "userid", "companyid", "vat"
            }
        ));
        tableReceipt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableReceiptMouseClicked(evt);
            }
        });
        tableReceipt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableReceiptKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableReceipt);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Unit Price:");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("Qty:");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setText("Serial No.");

        txtQty.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
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

        comboSerialno.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        comboSerialno.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboSerialnoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboSerialno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comboSerialnoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboSerialno, 0, 153, Short.MAX_VALUE)
                    .addComponent(txtQty))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(13, 13, 13)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboSerialno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtIndividualTotal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        tick.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        tick.setForeground(new java.awt.Color(51, 204, 0));
        tick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/tick.png"))); // NOI18N
        tick.setText("Saved!");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setText("Customer Details:");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("VAT:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("Total:");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setText("REF/LPO No.");

        txtVat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        txtLpono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLponoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLponoKeyReleased(evt);
            }
        });

        comboClients.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        comboClients.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboClientsPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboClients.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                comboClientsPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUnitprice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tick))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVat, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(txtIndividualTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClients, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLpono)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtUnitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tick, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtVat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIndividualTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(txtLpono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboClients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sale", jPanel2);

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Load receipt...", panelReport);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Products", jPanel6);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("Rcpt/INV #:");

        txtReceipt.setEditable(false);
        txtReceipt.setBackground(new java.awt.Color(204, 204, 204));
        txtReceipt.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtReceipt.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtReceiptInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtReceipt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtReceiptPropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Date:");

        chooserDate.setDateFormatString("yyyy-MM-dd");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel1.setText("Sales");

        jPanel5.setBackground(java.awt.Color.white);
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel5MouseReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel8.setText("Change:");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel9.setText("Paid Amount :");

        txtChange.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtChange.setForeground(java.awt.Color.blue);
        txtChange.setText(" ");
        txtChange.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtChangeKeyTyped(evt);
            }
        });

        txtPaidamount.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtPaidamount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaidamountFocusGained(evt);
            }
        });
        txtPaidamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidamountActionPerformed(evt);
            }
        });
        txtPaidamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaidamountKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidamountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidamountKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setText("VAT:");

        txtGrandTotal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtGrandTotal.setForeground(java.awt.Color.blue);
        txtGrandTotal.setText(" ");

        buttonReceipt.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonReceipt.setText("Receipt");
        buttonReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReceiptActionPerformed(evt);
            }
        });

        vattxt.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel14.setText("Total:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vattxt, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(buttonReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPaidamount, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPaidamount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonReceipt))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vattxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        lbl_exception.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lbl_exception.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(2, 2, 2)
                                .addComponent(chooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_exception, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_exception, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(txtReceipt)
                        .addComponent(jLabel5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chooserDate, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        txtSearch.setText(txtSearch.getText().toUpperCase());
        findProduct();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tableStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStockMouseClicked
        selectedRowStock();
        buttonSave.setEnabled(true);
    }//GEN-LAST:event_tableStockMouseClicked

    private void tableStockKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableStockKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedRowStock();
        }
    }//GEN-LAST:event_tableStockKeyReleased

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtPaidamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidamountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidamountActionPerformed

    private double getSumtotal(){
        int rowscount = tableReceipt.getRowCount();
        double sum_totals = 0;
            for(int i = 0; i < rowscount; i++){
                sum_totals = sum_totals + Double.parseDouble(tableReceipt.getValueAt(i, 8).toString());
            }
        return sum_totals;
    }
    private double getSumVat(){
        int rowscount = tableReceipt.getRowCount();
        double sum_vat = 0;
            for(int i = 0; i < rowscount; i++){
                sum_vat = sum_vat + Double.parseDouble(tableReceipt.getValueAt(i, 16).toString());
            }
        return sum_vat;
    }
    
    private void savereceipt(){
        
                calculateprofits();
                String quantity = txtQty.getText();
                quantity = quantity.trim();
                int qty = Integer.parseInt(quantity);

                String unitprice = txtUnitprice.getText();
                unitprice = unitprice.trim();
                double unit = Double.parseDouble(unitprice);
           
                String vat = txtVat.getText();
                vat = vat.trim();
                double vaT = Double.parseDouble(vat);

                DefaultTableModel model = (DefaultTableModel)tableReceipt.getModel();
                model.addRow(new Object[]{txtReceipt.getText(),
                                          ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText(),
                                          productcode,
                                          $product,
                                          $description,
                                          comboSerialno.getSelectedItem().toString(),
                                          txtQty.getText(),
                                          txtUnitprice.getText(),
                                          (unit * qty) + vaT,
                                          profit,"CASH","---","yes","1",loggedinuserid,
                                          companyid,
                                          vat});
                vattxt.setText(Double.toString(getSumVat()));
                txtGrandTotal.setText(Double.toString(getSumtotal()));
                
                reset();
    }
    String name[];
    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        saving_receipt();
    }//GEN-LAST:event_buttonSaveActionPerformed
   
    private void savingreceipt(){
        String quantity = txtQty.getText();
        quantity = quantity.trim();
        int qty = Integer.parseInt(quantity);
        
        String unitprice = txtUnitprice.getText();
        unitprice = unitprice.trim();
        double unit = Double.parseDouble(unitprice);
        
        if(txtQty.getText().equals("") || qty < 1){
            JOptionPane.showMessageDialog(null, "Please Enter Qty");
            txtQty.setText("");
            txtQty.requestFocus();
        }else if(comboSerialno.getSelectedItem().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please Enter S/N or IMEI");
            comboSerialno.setSelectedItem("Select Serial No");
            comboSerialno.requestFocus();
        }else if(txtUnitprice.getText().equals("")){
            //JOptionPane.showMessageDialog(null, "Please Enter Unit Price");
            lbl_exception.setText("Please Enter Unit Price...");
            txtUnitprice.requestFocus();
        }else{
            try{

                String sql = "SELECT * FROM stocktable WHERE productcode = '"+productcode+"' AND status = '1'";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    if(rs.next()){
                        double lowest = rs.getDouble("lowestsellingprice");
                        int stockqty = rs.getInt("qty");

                        if(qty > stockqty){
                            JOptionPane.showMessageDialog(null, "Total Qty Stock is '"+stockqty+"'. The amount of stock you have entered"
                                    + " exceeds the stock. Please Enter correct Qty");
                            txtQty.setText("");
                            txtQty.requestFocus();
                        }else if(unit < lowest){
                            JOptionPane.showMessageDialog(null, "The Lowest Unitprice should not be below '"+lowest+"'. "
                                    + "Your entry below set value. Please Enter correct Unit Price");
                            txtUnitprice.setText("");
                            txtUnitprice.requestFocus();
                        }else{
                            if(comboSerialno.getSelectedItem().toString().equals("---") && !comboSerialno.isEnabled()){
                                savereceipt();
                            }else{
                                check_stocking();
                            }
                            comboClients.setEnabled(false);
                            txtLpono.setEnabled(false);
                        }
                    }
            }catch(Exception e){
                System.out.println(e+" salespanel.savingreceipt");
            }finally{
                try{
                    rs.close();
                    pst.close();
                }catch(Exception e){

                }
            }
       }
    }
    private void check_stocking(){
        try {
            String sql = "SELECT * FROM stockingtable WHERE status = '1' AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, (String)comboSerialno.getSelectedItem());
            rs = pst.executeQuery();
                if(rs.next()){
                    check_sales();
                }else{
                    JOptionPane.showMessageDialog(null, "Product not in system. cannot sell this product");
                    comboSerialno.setSelectedItem("Select Serial No");
                    comboSerialno.requestFocus();
                }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" salespanel.check_stocking");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void check_sales(){
        try {
            String sql = "SELECT * FROM salestable WHERE status = '1' AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, (String)comboSerialno.getSelectedItem());
            rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Product already sold. Enter correct value");
                    comboSerialno.setSelectedItem("Select Serial No");
                    comboSerialno.requestFocus();
                }else{
                    check_remove();
                }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" salespanel.check_sales");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void check_remove(){
        try {
            String sql = "SELECT * FROM removetable WHERE status = '1' AND imei = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, (String)comboSerialno.getSelectedItem());
            rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Product removed. Enter correct value");
                    comboSerialno.setSelectedItem("Select Serial No");
                    comboSerialno.requestFocus();
                }else{
                    savereceipt();
                }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" salespanel.check_remove");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void cleartablereceipts(){
        tableReceipt.setModel(new DefaultTableModel(null,new String[]{"details","date","P-Code","Product","Description","IMEI",
            "Qty","Unit Price","Total Price","Profit","means","transactionid","sold","status","userid","companyid","vat"}));
            removeothercolumns();
    }
    private void savesales(){
              try{
                  int rows = tableReceipt.getRowCount();
                  conn.setAutoCommit(false);

                  String sql = "INSERT INTO salestable(details,date,productcode,imei,qty,unitprice,totalprice,profit"
                          + ",means_of_payment,transactionid,sold,status,userid,companyid,vat)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                  String sql1 = "UPDATE stocktable SET qty = qty - ? WHERE  status = '1' AND productcode = ?";
                  String sql2 = "UPDATE stockingtable SET status = '0' WHERE status = '1' AND imei = ?";

                  pst = conn.prepareStatement(sql);
                  pst1 = conn.prepareStatement(sql1);
                  pst2 = conn.prepareStatement(sql2);
                      for(int row = 0; row<rows; row++){
                          String details = (String)tableReceipt.getValueAt(row, 0);
                          String date = (String)tableReceipt.getValueAt(row, 1);
                          String pcode = (String)tableReceipt.getValueAt(row, 2);
                          String imei$ = (String)tableReceipt.getValueAt(row, 5);
                          String qty = (String)tableReceipt.getValueAt(row, 6);
                          String unitprice = (String)tableReceipt.getValueAt(row, 7);
                          double totalprice = (Double)tableReceipt.getValueAt(row, 8);
                          double profit$ = (Double)tableReceipt.getValueAt(row, 9);
                          String means = (String)tableReceipt.getValueAt(row, 10);
                          String transactionid = (String)tableReceipt.getValueAt(row, 11);
                          String sold = (String)tableReceipt.getValueAt(row, 12);
                          String status = (String)tableReceipt.getValueAt(row, 13);
                          String userid = (String)tableReceipt.getValueAt(row, 14);
                          companyid = (String)tableReceipt.getValueAt(row,15);
                          String vat = (String)tableReceipt.getValueAt(row, 16);

                          pst.setString(1, details);
                          pst.setString(2, date);
                          pst.setString(3, pcode);
                          pst.setString(4, imei$);
                          pst.setString(5, qty);
                          pst.setString(6, unitprice);
                          pst.setDouble(7, totalprice);
                          pst.setDouble(8, profit$);
                          pst.setString(9, means);
                          pst.setString(10, transactionid);
                          pst.setString(11, sold);
                          pst.setString(12, status);
                          pst.setString(13, userid);
                          pst.setString(14, companyid);
                          pst.setString(15, vat);

                          pst1.setString(1, qty);
                          pst1.setString(2, pcode);
                          
                          pst2.setString(1, imei$);

                          
                          
                          
                          
                          
                          pst.addBatch();
                          pst2.addBatch();
                          pst1.addBatch();
                      }
                      pst.executeBatch();
                      
                      pst2.executeBatch();
                      pst1.executeBatch();
                      
                      save_to_receipt();
                      conn.commit();
                      comboClients.setEnabled(true);
                      txtLpono.setEnabled(true);
                      comboClients.removeAllItems();
                      String sql0 = "SELECT * FROM clientstable WHERE status = '1'";
                      manage.fillcombo(sql0, comboClients, "clientname");
                      txtLpono.setText("-");
                      comboClients.setEnabled(true);
                      txtLpono.setEnabled(true);
              }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e+" salespanel.savesales");
              }finally{
                  try{
                      rs.close();
                      pst.close();
                  }catch(Exception e){
                      
                  }
              }
         
    }
    private void save_to_receipt(){
        try{
            String sql = "INSERT INTO receipt_table(receipt_no,clientid,s,lpo_no,date,user_id,total,total_vat)VALUES(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtReceipt.getText());
            pst.setInt(2, clientid);
            pst.setString(3, "1");
            pst.setString(4, txtLpono.getText());
            pst.setString(5, ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText());
            pst.setString(6, loggedinuserid);
            pst.setString(7, txtGrandTotal.getText());
            pst.setString(8, vattxt.getText());
            
            pst.execute();
        }catch(Exception e){
            System.out.println(e+" save_to_receipt");
            
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void tableReceiptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableReceiptMouseClicked
        selectedRowSales();
        buttonSave.setEnabled(false);
        buttonDelete.setEnabled(true);
    }//GEN-LAST:event_tableReceiptMouseClicked
    
   
    //private int recptno;
    private void autoprint_receipt(){
        int recptno;
        try{
            String sql = "SELECT * FROM salestable  WHERE status = '1' AND default_ = '0' ORDER BY details DESC LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                recptno = rs.getInt("details");
                String query = "SELECT companytable.phoneno AS 'company_phone',companytable.email AS 'company_email',companytable.companyname,companytable.pin AS"
                        + " 'companypin',receipt_table.date,usertable.name AS 'user_name',receipt_table.lpo_no,receipt_table.total AS 'Total',receipt_table.receipt_no"
                        + ",receipt_table.lpo_no,clientstable.clientname,clientstable.clientphone,clientstable.clientemail,receipt_table.total_vat  FROM "
                        + "companytable,usertable,receipt_table,clientstable WHERE companytable.companyid = usertable.companyid AND usertable.id = receipt_table."
                        + "user_id AND clientstable.clientid = receipt_table.clientid  AND receipt_table.receipt_no = '"+recptno+"'";
                String path = "Reports/receipt.jrxml";
                manage.report_(query, path, panelReport,path);
                jTabbedPane2.setSelectedIndex(1);
                //JOptionPane.showMessageDialog(null, "Receipt Auto-Print "+recptno);//remove this - just for testing
                String query2 = "UPDATE salestable SET default_ = '1' WHERE default_ = '0' AND details = '"+recptno+"' AND status = '1'";
                manage.update(query2);
                comboClients.removeAllItems();
                String query1 = "SELECT * FROM clientstable WHERE status = '1'";
                manage.fillcombo(query1, comboClients, "clientname");
                txtLpono.setText("-");
                comboClients.setEnabled(true);
                txtLpono.setEnabled(true);
                }
        }catch(Exception e){
            System.out.println(e+" salespanel.autoprint_receipt");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
           
    }
    
    private void buttonReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReceiptActionPerformed
        
        savesales();
        cleartablereceipts();
        reset();
        findProduct();
        //update_tableSales();
        txtGrandTotal.setText("0.00");
        vattxt.setText("0.00");
        buttonReceipt.setEnabled(false);
    }//GEN-LAST:event_buttonReceiptActionPerformed

    private void txtUnitpriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyReleased
        calctotal();
    }//GEN-LAST:event_txtUnitpriceKeyReleased
    private void getchange(){
        String grandtotal = txtGrandTotal.getText();
        grandtotal = grandtotal.trim();
        double Grand = Double.parseDouble(grandtotal);
        
        String paidamount = txtPaidamount.getText();
        paidamount = paidamount.trim();
        double Paid = Double.parseDouble(paidamount);
        
        txtChange.setText(String.format("%.2f",Paid - Grand));
    }
    private void enableReceipt(){
        String grandtotal = txtGrandTotal.getText();
        grandtotal = grandtotal.trim();
        double Total = Double.parseDouble(grandtotal);
        
        
        String paidamount = txtPaidamount.getText();
        paidamount = paidamount.trim();
        double Paid = Double.parseDouble(paidamount);
        
        if(Total <= Paid){
            buttonReceipt.setEnabled(true);
        }else{
            buttonReceipt.setEnabled(false);
        }
    }
    private void txtPaidamountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidamountKeyReleased
        enableReceipt();
        getchange();
    }//GEN-LAST:event_txtPaidamountKeyReleased

    private void txtUnitpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyTyped
        char c = evt.getKeyChar();
            if(Character.isLetter(c) &&!evt.isAltDown()){
                evt.consume();
            }
    }//GEN-LAST:event_txtUnitpriceKeyTyped

    private void txtPaidamountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidamountFocusGained
        buttonSave.setEnabled(false);
    }//GEN-LAST:event_txtPaidamountFocusGained

    private void tableReceiptKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableReceiptKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
           selectedRowSales();
        }
    }//GEN-LAST:event_tableReceiptKeyReleased

    private void txtUnitpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitpriceKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            saving_receipt();        }
            comboSerialno.requestFocus();
        }
    }//GEN-LAST:event_txtUnitpriceKeyPressed

    private void txtPaidamountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidamountKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            c.add(Calendar.YEAR, 0);
            chooserDate.getDateEditor().setDate(c.getTime());
            txtPaidamount.setText("");
            txtChange.setText("0.00");
            txtSearch.setText("");
            txtUnitprice.setText("");
            comboSerialno.setSelectedItem("Select Serial No");
            txtSearch.requestFocus();
            txtQty.setText("1");
            buttonSave.setEnabled(true);
            buttonDelete.setEnabled(false);

        }
        String paidamount = txtPaidamount.getText();
        paidamount = paidamount.trim();
        double Paid = Double.parseDouble(paidamount);
        
        String grandtotal = txtGrandTotal.getText();
        grandtotal = grandtotal.trim();
        double Grand = Double.parseDouble(grandtotal);
        
        if (evt.getKeyCode()==KeyEvent.VK_ENTER ){
            if(Paid >= Grand){
                savesales();
                autoprint_receipt();
                generatereceiptNo();
                findProduct();
                cleartablereceipts();
                reset();
                findProduct();
                //update_tableSales();
                txtGrandTotal.setText("0.00");
                vattxt.setText("0.00");
                buttonReceipt.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtPaidamountKeyPressed
    private void deleteinvoice(){
        try{
            String sql = "DELETE FROM invoicetable WHERE invoiceid = '"+invoiceid+"' AND invoiceno = '"+txtReceipt.getText()+"'";
            pst = conn.prepareStatement(sql);
            DialogInvoice.setVisible(false);
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION){
                DialogInvoice.setVisible(true);
            }else if (response == JOptionPane.YES_OPTION){
            pst.execute();
            deleteinvoice_sales();
            JOptionPane.showMessageDialog(null,"Deleted");
            reset();
            findinvoice();
            DialogInvoice.setVisible(true);
            }else if(response == JOptionPane.CLOSED_OPTION){
                DialogInvoice.setVisible(true);    
            }       
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"salespanel.deleteinvoice");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    private void deleteinvoice_sales(){
        try{
            String receiptno = txtReceipt.getText();
            String sql = "DELETE FROM salestable WHERE receiptno = '"+receiptno+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            //update_tableSales();
            findProduct();
            reset();
            grandtotal();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salespanel.deleteinvoice_sales");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowinvoice(){
        try{
            buttonSaveInvoice.setEnabled(false);
            buttonUpdateInvoice.setEnabled(true);
            buttonDeleteInvoice.setEnabled(true);
            
            int row = tableInvoices.getSelectedRow();
            String table_click = tableInvoices.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM invoicetable WHERE invoiceid = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    invoiceid = rs.getInt("invoiceid");
                    clientid = rs.getInt("clientid");
                    txtReceipt.setText(rs.getString("invoiceno"));
                    txtTotalInvoiceamount.setText(rs.getString("totalamount"));
                    txtBalbf.setText(rs.getString("balancebf"));
                    txtPaidInvoiceAmount.setText(rs.getString("paidamount"));
                    txtInvoiceBalance.setText(rs.getString("balance"));
                    ((JTextField)chooserDate.getDateEditor().getUiComponent()).setText(rs.getString("invoicedate"));
                    
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salespanel.selectedrowinvoice");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        cleartablereceipts();
        resetsales();
        
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void txtChangeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChangeKeyTyped
         
    }//GEN-LAST:event_txtChangeKeyTyped

    private void txtPaidamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidamountKeyTyped
        char vchar = evt.getKeyChar();
            if((!(Character.isDigit(vchar)))
                ||(vchar == KeyEvent.VK_BACK_SPACE)

                || (vchar == KeyEvent.VK_DELETE)){
              evt.consume();
          }
    }//GEN-LAST:event_txtPaidamountKeyTyped

    private void txtUnitpriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnitpriceFocusGained
        buttonSave.setEnabled(true);
    }//GEN-LAST:event_txtUnitpriceFocusGained

    private void tableStockMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStockMouseReleased
        
    }//GEN-LAST:event_tableStockMouseReleased

    private void jPanel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseReleased

    private void txtUnitpricePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtUnitpricePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitpricePropertyChange

    private void txtUnitpriceInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtUnitpriceInputMethodTextChanged
       
    }//GEN-LAST:event_txtUnitpriceInputMethodTextChanged

   
    private void txtReceiptPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtReceiptPropertyChange
        //update_tableSales();
    }//GEN-LAST:event_txtReceiptPropertyChange

    private void txtReceiptInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtReceiptInputMethodTextChanged

    }//GEN-LAST:event_txtReceiptInputMethodTextChanged

    private void tableStockMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStockMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableStockMouseEntered

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        calctotal();
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
         char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
           savingreceipt();
        }
    }//GEN-LAST:event_txtQtyKeyPressed

   
    private void updateinvoice(){
        try{
            
            String paid = txtPaidInvoiceAmount.getText();
            String balance = txtInvoiceBalance.getText();
            
                if(paid.equals("")){
                    txtPaidInvoiceAmount.requestFocus();
                }else{
                    String sql = "UPDATE invoicetable SET paidamount = '"+paid+"',balance = '"+balance+"' WHERE invoiceid = '"+invoiceid+"'"
                            + "AND status = '1'";
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    resetinvoice();
                    findinvoice();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salespanel.updateinvoice");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
   
    private void resetinvoice(){
        txtPaidInvoiceAmount.setText("");
        txtSearchinvoice.setText("");
        txtPaidInvoiceAmount.requestFocus();
        txtInvoiceBalance.setText("0.00");
        buttonSaveInvoice.setEnabled(true);
        buttonUpdateInvoice.setEnabled(false);
        buttonDeleteInvoice.setEnabled(false);
        findinvoice();
    }
    private void showdialoginvoice(){
        DialogInvoice.setVisible(true);
        DialogInvoice.setSize(595, 425);
        DialogInvoice.setAlwaysOnTop(true);
        DialogInvoice.setLocationRelativeTo(this);
        DialogInvoice.setResizable(false);
        DialogInvoice.setTitle("Invoices");
        resetinvoice();
    }
    public ArrayList<SearchInvoice> ListInvoice(String ValToSearch){
        ArrayList<SearchInvoice>invoiceList = new ArrayList<SearchInvoice>();
       
        try{
            String searchQuery = "SELECT * FROM invoicetable,clientstable WHERE clientstable.clientid = invoicetable.clientid AND "
                    + "CONCAT(invoicetable.invoiceno,'',clientstable.clientname) LIKE'%"+ValToSearch+"%' AND invoicetable.status = '1'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchInvoice search;
            while(rs.next()){
                search = new SearchInvoice(
                                    rs.getInt("invoicetable.invoiceid"),
                                    rs.getInt("invoicetable.invoiceno"),
                                    rs.getString("clientstable.clientname"),
                                    rs.getDouble("invoicetable.totalamount"),
                                    rs.getDouble("invoicetable.balancebf"),
                                    rs.getDouble("invoicetable.paidamount"),
                                    rs.getDouble("invoicetable.balance"),
                                    rs.getString("invoicetable.invoicedate")
                                            );
                invoiceList.add(search);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null ,e+" salespanel.Searchinvoice/findinvoice");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return invoiceList;
    }
    public void findinvoice(){
        ArrayList<SearchInvoice> invoice = ListInvoice(txtSearchinvoice.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Invoice #","Client Name","Total Amount","Bal b/f","Paid amount","Balance"});
        
        Object[] row = new Object[7];
        
            for(int i = 0; i < invoice.size(); i ++){
                
             row[0] = invoice.get(i).getId();
             row[1] = invoice.get(i).getInvoiceno();
             row[2] = invoice.get(i).getClientname();
             row[3] = invoice.get(i).getTotalamount();
             row[4] = invoice.get(i).getBalbf();
             row[5] = invoice.get(i).getPaidamount();
             row[6] = invoice.get(i).getBalance();
             
             
             model.addRow(row);
            }
            tableInvoices.setModel(model);
            TableColumn idColumn = tableInvoices.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    private void saveinvoice(){
        try{
            String totalsale = txtTotalInvoiceamount.getText();
            totalsale = totalsale.trim();
            double totalsold = Double.parseDouble(totalsale);
            String invoicedate = ((JTextField)chooserDate.getDateEditor().getUiComponent()).getText();
            String invoiceno = txtReceipt.getText();
            String paid = txtPaidInvoiceAmount.getText();
            String balance = txtInvoiceBalance.getText();
            
                if(totalsold == 0){
                    DialogInvoice.dispose();
                    JOptionPane.showMessageDialog(null, "Please Note that the total amount of invoice cannot be zero(0)");
                }else if(paid.equals("")){
                    txtPaidInvoiceAmount.requestFocus();
                }else{
                String sql = "INSERT INTO invoicetable(clientid,invoiceno,totalamount,balancebf,paidamount,balance,status,userid,invoicedate)"
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, clientid);
                    pst.setString(2, invoiceno);
                    pst.setString(3, totalsale);
                    pst.setDouble(4, bal_bf);
                    pst.setString(5, paid);
                    pst.setString(6, balance);
                    pst.setString(7, "1");
                    pst.setString(8, loggedinuserid);
                    pst.setString(9, invoicedate);
                    pst.execute();
                    resetinvoice();
                    findinvoice();
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e+" salespanel.saveinvoice");
                }finally{
                    try{
                        rs.close();
                        pst.close();
                    }catch(Exception e){

                    }
            }
    }
    private void txtPaidInvoiceAmountInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtPaidInvoiceAmountInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidInvoiceAmountInputMethodTextChanged

    private void txtPaidInvoiceAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPaidInvoiceAmountPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidInvoiceAmountPropertyChange

    private void txtPaidInvoiceAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidInvoiceAmountKeyTyped
        char vchar = evt.getKeyChar();
            if((!(Character.isDigit(vchar)))
                ||(vchar == KeyEvent.VK_BACK_SPACE)

                || (vchar == KeyEvent.VK_DELETE)){
              evt.consume();
            }
    }//GEN-LAST:event_txtPaidInvoiceAmountKeyTyped

    private void txtPaidInvoiceAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidInvoiceAmountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidInvoiceAmountKeyPressed

    private void txtPaidInvoiceAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidInvoiceAmountKeyReleased
        txtPaidInvoiceAmount.setText(txtPaidInvoiceAmount.getText().toUpperCase());
    }//GEN-LAST:event_txtPaidInvoiceAmountKeyReleased

    private void buttonAddInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddInvoiceActionPerformed
        resetinvoice();
    }//GEN-LAST:event_buttonAddInvoiceActionPerformed

    private void buttonAddInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonAddInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAddInvoiceKeyPressed

    private void buttonSaveInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveInvoiceActionPerformed
     
        saveinvoice();
        generatereceiptNo();
        reset();
        findProduct();
    }//GEN-LAST:event_buttonSaveInvoiceActionPerformed

    private void buttonSaveInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonSaveInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSaveInvoiceKeyPressed

    private void buttonUpdateInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateInvoiceActionPerformed
        updateinvoice();
    }//GEN-LAST:event_buttonUpdateInvoiceActionPerformed

    private void buttonUpdateInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonUpdateInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonUpdateInvoiceKeyPressed

    private void buttonDeleteInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteInvoiceActionPerformed
        deleteinvoice();
    }//GEN-LAST:event_buttonDeleteInvoiceActionPerformed

    private void buttonDeleteInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonDeleteInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDeleteInvoiceKeyPressed

    private void buttonExitInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitInvoiceActionPerformed
        DialogInvoice.dispose();
    }//GEN-LAST:event_buttonExitInvoiceActionPerformed

    private void txtSearchinvoiceInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchinvoiceInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchinvoiceInputMethodTextChanged

    private void txtSearchinvoicePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtSearchinvoicePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchinvoicePropertyChange

    private void txtSearchinvoiceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchinvoiceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchinvoiceKeyTyped

    private void txtSearchinvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchinvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchinvoiceKeyPressed

    private void txtSearchinvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchinvoiceKeyReleased
        findinvoice();
    }//GEN-LAST:event_txtSearchinvoiceKeyReleased

    private void txtBalbfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBalbfKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalbfKeyTyped

    private void tableInvoicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInvoicesMouseClicked
        selectedrowinvoice();
    }//GEN-LAST:event_tableInvoicesMouseClicked

    private void tableInvoicesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableInvoicesKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrowinvoice();
        }
    }//GEN-LAST:event_tableInvoicesKeyReleased

    private void saving_receipt(){
        int rows = tableReceipt.getRowCount();
        if(txtUnitprice.getText().equals("")){
            lbl_exception.setText("Please Enter Unit Price...");
            txtUnitprice.requestFocus();
        }else if(comboSerialno.getSelectedItem().toString().equals("Select Serial No")){
            lbl_exception.setText("Please Enter S/N or IMEI...");
            comboSerialno.requestFocus();
        }else if(rows == 0){
            savingreceipt();
        }else{
            String imei = (String)comboSerialno.getSelectedItem();
                for(int row = 0; row < rows; row ++){
                    String serial = (String)tableReceipt.getValueAt(row, 5);
                    if(imei.equals(serial)){
                        lbl_exception.setText("Product Already Exist in receipt...");
                        comboSerialno.setSelectedItem("Select Serial No");
                        comboSerialno.requestFocus();
                    }else{
                        savingreceipt();
                    }
                }
        }
    }
    private void txtLponoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLponoKeyPressed
        lbl_exception.setText("");
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            saving_receipt();
           txtPaidamount.requestFocus();
        }
    }//GEN-LAST:event_txtLponoKeyPressed

    private void txtLponoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLponoKeyReleased
        txtLpono.setText(txtLpono.getText().toUpperCase());
    }//GEN-LAST:event_txtLponoKeyReleased

    private void comboClientsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_comboClientsPropertyChange
        
    }//GEN-LAST:event_comboClientsPropertyChange

    private void load_client(){
         try{
            String clientname = (String)comboClients.getSelectedItem();
            String sql  = "SELECT * FROM clientstable WHERE clientname = ? AND status = '1'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, clientname);
            
            rs = pst.executeQuery();
                if(rs.next()){
                    clientid = rs.getInt("clientid");
                    txtLpono.requestFocus();
                    
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" salespanel.load_client");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void comboClientsPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboClientsPopupMenuWillBecomeInvisible
       load_client();
    }//GEN-LAST:event_comboClientsPopupMenuWillBecomeInvisible

    private void comboSerialnoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboSerialnoPopupMenuWillBecomeInvisible
        txtLpono.requestFocus();
    }//GEN-LAST:event_comboSerialnoPopupMenuWillBecomeInvisible

    private void comboSerialnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboSerialnoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            saving_receipt();
        }
    }//GEN-LAST:event_comboSerialnoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDialog DialogInvoice;
    private javax.swing.JButton buttonAddInvoice;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonDeleteInvoice;
    private javax.swing.JButton buttonExitInvoice;
    public javax.swing.JButton buttonReceipt;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonSaveInvoice;
    private javax.swing.JButton buttonUpdateInvoice;
    private com.toedter.calendar.JDateChooser chooserDate;
    private javax.swing.JComboBox<String> comboClients;
    private javax.swing.JComboBox<String> comboSerialno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelPhone;
    private javax.swing.JLabel lbl_exception;
    public javax.swing.JPanel panelReport;
    private javax.swing.JTable tableInvoices;
    private javax.swing.JTable tableReceipt;
    public javax.swing.JTable tableStock;
    private javax.swing.JLabel tick;
    private javax.swing.JLabel txtBalbf;
    private javax.swing.JLabel txtChange;
    public javax.swing.JLabel txtGrandTotal;
    private javax.swing.JLabel txtIndividualTotal;
    private javax.swing.JLabel txtInvoiceBalance;
    private javax.swing.JTextField txtLpono;
    private javax.swing.JTextField txtPaidInvoiceAmount;
    private javax.swing.JTextField txtPaidamount;
    private javax.swing.JTextField txtQty;
    public javax.swing.JTextField txtReceipt;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchinvoice;
    private javax.swing.JLabel txtTotalInvoiceamount;
    private javax.swing.JTextField txtUnitprice;
    private javax.swing.JLabel txtVat;
    private javax.swing.JLabel vattxt;
    // End of variables declaration//GEN-END:variables
}
