
package yamasuzu_myshopsoft;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
public class ViewSales extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    Calendar c = Calendar.getInstance();
     public String loggeduserid;
     public String loggedusername;
     public String loggedpriviledge;
     public String companyid;
     
     public String compnyid;
     private int salesid;
     private String details;
     private int clientid ;
     private String meansof_payments;
     private int qty;
     private String productcode;
     private String receiptno;
                    
     Manage manage = new Manage();
    public ViewSales() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        lblSearch.setToolTipText("Searching");
    }
    private void loginmessagedelete(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Delete Sales details for Rcpt#/Invoice#'"+details+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.loginmessagedelete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void saveinvoicepayment(){
        try{
            String paid = txtPaidamount.getText();
            String balbf = txtBalbf.getText();
            String totalamount = txtTotalAmount.getText();
            String bal = txtBalance.getText();
            String date = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
                if(paid.equals("")){
                    txtPaidamount.requestFocus();
                }else{
                    String sql = "INSERT INTO invoicetable(clientid,invoiceno,totalamount,balancebf,paidamount,balance,invoicedate,status,userid)"
                            + "VALUES(?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    
                    pst.setInt(1, clientid);
                    pst.setString(2, details);
                    pst.setString(3, totalamount);
                    pst.setString(4, balbf);
                    pst.setString(5, paid);
                    pst.setString(6, bal);
                    pst.setString(7, date);
                    pst.setString(8, "1");
                    pst.setString(9, loggeduserid);
                    
                    pst.execute();
                    resetinvoices();
                    update_tablepayinvoice();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.saveinvoicepayment");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void removereport(){
        String from = ((JTextField)chooserFromRemove.getDateEditor().getUiComponent()).getText();
        String to = ((JTextField)chooserToRemove.getDateEditor().getUiComponent()).getText();
        String sql = "SELECT companytable.companyname,companytable.location,companytable.address,companytable.city,"
                + "companytable.phoneno,companytable.email,companytable.dealerin,removetable.removedate,removetable.productcode,"
                + "CONCAT(stocktable.type,'-',stocktable.description,'-',removetable.imei),removetable.qty,removetable.reason,"
                + "usertable.name FROM companytable,usertable,stocktable,removetable WHERE usertable.companyid = companytable."
                + "companyid AND stocktable.productcode = removetable.productcode AND removetable.userid = usertable.id AND "
                + "removetable.status = '1' AND removedate BETWEEN '"+from+"' AND '"+to+"'";
        String path = "Reports/removedReport.jrxml";
        manage.report(sql, path, panelReport);
        jTabbedPane1.setSelectedIndex(2);
    }
    
    private void selectedrowinvoice(){
        try{
            int row = tableInvoice.getSelectedRow();
            String table_click = tableInvoice.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM invoicetable WHERE status = '1' AND invoiceid = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    clientid = rs.getInt("clientid");
                    details = rs.getString("invoiceno");
                    txtTotalAmount.setText(rs.getString("totalamount"));
                    txtBalbf.setText(rs.getString("balancebf"));
                    txtPaidamount.setText(rs.getString("paidamount"));
                    txtBalance.setText(rs.getString("balance"));
                    ((JTextField)chooserTo.getDateEditor().getUiComponent()).setText(rs.getString("invoicedate"));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.selectedrowinvoice");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void showdialogeditinvoice(){
        DialogEditInvoice.setVisible(true);
        DialogEditInvoice.setSize(917, 505);
        DialogEditInvoice.setResizable(false);
        DialogEditInvoice.setAlwaysOnTop(true);
        DialogEditInvoice.setLocationRelativeTo(this);
        DialogEditInvoice.setTitle("Edit/Payment of Invoices");
        loadinvoicedetails();
        update_tablepayinvoice();
        resetinvoices();
    }
    public void reset(){
        txtSearching.setText("");
        c.add(Calendar.YEAR, 0);
        chooserFrom.getDateEditor().setDate(c.getTime());
        chooserTo.getDateEditor().setDate(c.getTime());
        chooserFromRemove.getDateEditor().setDate(c.getTime());
        chooserToRemove.getDateEditor().setDate(c.getTime());
        salesid = 0;
        details = "";
        clientid = 0;
        meansof_payments = "";
        qty = 1;
        imei = "";
        productcode ="";
        receiptno = "";
        txtSearching.setText("");
        txtSearching.requestFocus();
        findviewsales();
        buttonEditInvoice.setEnabled(false);
        buttonDelete.setEnabled(false);
        buttonReprintInvoice.setEnabled(false);
        buttonReprintReceipt.setEnabled(false);
       }
    public ArrayList<SearchViewSales> ListView(String ValToSearch){
        ArrayList<SearchViewSales>viewList = new ArrayList<SearchViewSales>();
       
        try{
            String searchQuery = "SELECT * FROM salestable,stocktable WHERE stocktable.productcode = salestable.productcode AND "
                    + "CONCAT(salestable.details,'',stocktable.productcode,'',stocktable.type,'',stocktable.description,'',salestable.imei,'',salestable.date"
                    + ",'',salestable.means_of_payment) LIKE'%"+ValToSearch+"%' AND salestable.date BETWEEN '"+
                    ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText()+"'AND '"+((JTextField)chooserTo
                       .getDateEditor().getUiComponent()).getText()+"'AND salestable.sold = 'yes' ORDER BY salestable.salesid ";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            SearchViewSales search;
            while(rs.next()){
                search = new SearchViewSales(
                                    rs.getInt("salestable.salesid"),
                                    rs.getString("salestable.details"),
                                    rs.getString("stocktable.productcode"),
                                    rs.getString("stocktable.type"),
                                    rs.getString("stocktable.description"),
                                    rs.getString("salestable.imei"),
                                    rs.getInt("salestable.qty"),
                                    rs.getString("salestable.unitprice"),
                                    rs.getString("salestable.date"),
                                    rs.getString("salestable.means_of_payment")
                                            );
                viewList.add(search);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null ,e+" SearchProduct/Findproduct Method");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return viewList;
    }
    public void findviewsales(){
        ArrayList<SearchViewSales> view= ListView(txtSearching.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Receipt#/Invoice#","Product #","Product","Description","Serial No.","Unit-Price","Date","Means of Pay"});
        
        Object[] row = new Object[9];
        
            for(int i = 0; i < view.size(); i ++){
                
             row[0] = view.get(i).getSalesid();
             row[1] = view.get(i).getReceiptno();
             row[2] = view.get(i).getProductcode();
             row[3] = view.get(i).getProduct();
             row[4] = view.get(i).getDescription();
             row[5] = view.get(i).getImei();
             row[6] = view.get(i).getUnitprice();
             row[7] = view.get(i).getSaledate();
             row[8] = view.get(i).getMeans_of_payment();
             
             model.addRow(row);
            }
            tableViewSales.setModel(model);
            TableColumn idColumn = tableViewSales.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    
    private void resetinvoices(){
        loadinvoicedetails();
        txtBalance.setText("0.00");
        txtPaidamount.setText("");
        txtPaidamount.requestFocus();
    }
    private void update_tablepayinvoice(){
        try{
            String sql = "SELECT invoiceid AS 'id',invoiceno AS 'Invoice #',balancebf AS 'Bal b/f',paidamount AS 'Paid Amount',balance AS 'Balance'"
                    + ",invoicedate AS 'Payment Date' FROM invoicetable WHERE status = '1' AND clientid = '"+clientid+"' ORDER BY invoiceno";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableInvoice.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = tableInvoice.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.update_tablepayinvoice");
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

        DialogEditInvoice = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        txtInvoiceno = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtClientname = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTotalAmount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtBalbf = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JLabel();
        txtPaidamount = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableInvoice = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        buttonExit3 = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonDelete1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        buttonReprintReceipt = new javax.swing.JButton();
        buttonEditInvoice = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearching = new javax.swing.JTextField();
        chooserTo = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableViewSales = new javax.swing.JTable();
        chooserFrom = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        buttonReprintInvoice = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        chooserFromRemove = new com.toedter.calendar.JDateChooser();
        chooserToRemove = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRemove = new javax.swing.JTable();
        buttonReceipt = new javax.swing.JButton();
        panelReport = new javax.swing.JPanel();

        jPanel1.setBackground(java.awt.Color.white);

        txtInvoiceno.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Invoice #:");

        txtClientname.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Total Amount:");

        txtTotalAmount.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Balance b/f:");

        txtBalbf.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Paid Amount:");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Balance:");

        txtBalance.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        txtPaidamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaidamountKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidamountKeyReleased(evt);
            }
        });

        tableInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInvoiceMouseClicked(evt);
            }
        });
        tableInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableInvoiceKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableInvoice);

        jPanel5.setBackground(java.awt.Color.white);
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonExit3.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExit3.setText("Exit");
        buttonExit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExit3ActionPerformed(evt);
            }
        });

        buttonAdd.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonAdd.setText("Add");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        buttonDelete1.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDelete1.setText("Delete");
        buttonDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDelete1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(buttonExit3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonDelete1)
                    .addComponent(buttonExit3)
                    .addComponent(buttonSave)
                    .addComponent(buttonAdd))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPaidamount, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtInvoiceno, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBalbf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtClientname, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceno, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClientname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBalbf, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaidamount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DialogEditInvoiceLayout = new javax.swing.GroupLayout(DialogEditInvoice.getContentPane());
        DialogEditInvoice.getContentPane().setLayout(DialogEditInvoiceLayout);
        DialogEditInvoiceLayout.setHorizontalGroup(
            DialogEditInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogEditInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DialogEditInvoiceLayout.setVerticalGroup(
            DialogEditInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogEditInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/search.png"))); // NOI18N

        buttonReprintReceipt.setText("Reprint Receipt");
        buttonReprintReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReprintReceiptActionPerformed(evt);
            }
        });

        buttonEditInvoice.setText("Edit/Payments");
        buttonEditInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditInvoiceActionPerformed(evt);
            }
        });

        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        jLabel2.setText("To:");

        txtSearching.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchingFocusGained(evt);
            }
        });
        txtSearching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchingKeyReleased(evt);
            }
        });

        chooserTo.setDateFormatString("yyyy-MM-dd");
        chooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserToPropertyChange(evt);
            }
        });

        tableViewSales.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        tableViewSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableViewSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableViewSalesMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableViewSalesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableViewSalesMouseEntered(evt);
            }
        });
        tableViewSales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableViewSalesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableViewSales);

        chooserFrom.setDateFormatString("yyyy-MM-dd");
        chooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserFromPropertyChange(evt);
            }
        });

        jLabel1.setText("From:");

        buttonReprintInvoice.setText("Print INVOICE");
        buttonReprintInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReprintInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(lblSearch)
                        .addGap(3, 3, 3)
                        .addComponent(txtSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(chooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(chooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(buttonEditInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(42, 42, 42)
                            .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonReprintInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(buttonReprintReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearching, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonReprintReceipt)
                        .addComponent(buttonReprintInvoice))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonEditInvoice)
                        .addComponent(buttonDelete)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sales", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        chooserFromRemove.setDateFormatString("yyyy-MM-dd");
        chooserFromRemove.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserFromRemovePropertyChange(evt);
            }
        });

        chooserToRemove.setDateFormatString("yyyy-MM-dd");
        chooserToRemove.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserToRemovePropertyChange(evt);
            }
        });

        jLabel8.setText("To:");

        jLabel10.setText("From:");

        tableRemove.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableRemoveMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRemoveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableRemoveMouseEntered(evt);
            }
        });
        tableRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableRemoveKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableRemove);

        buttonReceipt.setText("Print Report");
        buttonReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReceiptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(451, 451, 451)
                        .addComponent(jLabel10)
                        .addGap(3, 3, 3)
                        .addComponent(chooserFromRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(chooserToRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooserToRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooserFromRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonReceipt)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Remove Items", jPanel3);

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 914, Short.MAX_VALUE)
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Loading Report...", panelReport);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchingKeyReleased
        findviewsales();
        txtSearching.setText(txtSearching.getText().toUpperCase());
    }//GEN-LAST:event_txtSearchingKeyReleased

    private void chooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserFromPropertyChange
        findviewsales();
    }//GEN-LAST:event_chooserFromPropertyChange

    private void chooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserToPropertyChange
        findviewsales();
    }//GEN-LAST:event_chooserToPropertyChange
    public void receipt(){
                
                String sql = "SELECT companytable.phoneno AS 'company_phone',companytable.email AS 'company_email',companytable.companyname,companytable.pin "
                        + "AS 'companypin',receipt_table.date,usertable.name AS 'user_name',receipt_table.lpo_no,receipt_table.total AS 'Total',receipt_table."
                        + "receipt_no,receipt_table.lpo_no,clientstable.clientname,clientstable.clientphone,clientstable.clientemail,receipt_table.total_vat "
                        + " FROM companytable,usertable,receipt_table,clientstable WHERE companytable.companyid = usertable.companyid AND usertable.id = receipt_table"
                        + ".user_id AND clientstable.clientid = receipt_table.clientid AND receipt_table.receipt_no = '"+receiptno+"'";
                
                String path = "Reports/receipt.jrxml";
                manage.report(sql, path, panelReport);
                jTabbedPane1.setSelectedIndex(2);
       
    }
    public void invoice_sales(){
                
                String sql = "SELECT companytable.phoneno AS 'company_phone',companytable.email AS 'company_email',companytable.companyname,companytable.pin AS '"
                        + "companypin',receipt_table.date,usertable.name AS 'user_name',receipt_table.lpo_no,receipt_table.total AS 'Total',receipt_table.receipt_no"
                        + ",receipt_table.lpo_no,clientstable.clientname,clientstable.clientphone,clientstable.clientemail,receipt_table.total_vat  FROM companytable,"
                        + "usertable,receipt_table,clientstable WHERE companytable.companyid = usertable.companyid AND usertable.id = receipt_table.user_id AND "
                        + "clientstable.clientid = receipt_table.clientid  AND receipt_table.receipt_no = '"+receiptno+"'";
                
                String path = "Reports/invoice.jrxml";
                manage.report(sql, path, panelReport);
                jTabbedPane1.setSelectedIndex(2);
       
    }
    private String imei;
    private void selectedrow(){
        try{
            
            int row = tableViewSales.getSelectedRow();
            String table_click = tableViewSales.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM stocktable,salestable WHERE stocktable.productcode = salestable.productcode AND salestable.salesid = "
                    + "'"+table_click+"' AND salestable.sold = 'yes' AND salestable.status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                compnyid = rs.getString("salestable.companyid");
                salesid = rs.getInt("salestable.salesid");
                ((JTextField)chooserTo.getDateEditor().getUiComponent()).setText(rs.getString("salestable.date"));
                qty = rs.getInt("salestable.qty");
                productcode = rs.getString("stocktable.productcode");
                receiptno = rs.getString("salestable.details");
                buttonReprintInvoice.setEnabled(true);
                imei = rs.getString("salestable.imei");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.selectedrow");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_tableremove(){
        try{
            String from = ((JTextField)chooserFromRemove.getDateEditor().getUiComponent()).getText();
            String to = ((JTextField)chooserToRemove.getDateEditor().getUiComponent()).getText();
            
            String sql = "SELECT removetable.removeid AS 'id',removetable.removedate AS 'Date',removetable.productcode AS 'P-CODE',"
                    + "CONCAT(stocktable.type ,'-',stocktable.description) AS 'Product description',removetable.imei AS 'S/N or IMEI'"
                    + ",removetable.reason AS 'Reason' FROM stocktable,removetable WHERE stocktable.productcode = removetable.productcode "
                    + "AND removetable.status = '1' AND removedate BETWEEN '"+from+"' AND '"+to+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableRemove.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = tableRemove.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" update_tableremove");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loadinvoicedetails(){
        try{
            String sql = "SELECT balance,totalamount FROM invoicetable WHERE clientid = '"+clientid+"' AND status = '1' AND "
                    + "invoiceno = '"+details+"' ORDER BY "
                    + "invoiceid DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    txtBalbf.setText(rs.getString("balance"));
                    txtTotalAmount.setText(rs.getString("totalamount"));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" viewsales.loadinvoicedetails");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void delete(){
        try{
            int rows = tableViewSales.getRowCount();
            String sql = "DELETE FROM salestable WHERE salesid = '"+salesid+"' AND status = '1'";
            
            pst = conn.prepareStatement(sql);
            pst.execute();
                if(rows < 2){
                    String sql_receipt = "DELETE FROM receipt_table WHERE receipt_no = '"+receiptno+"'";
                    manage.delete_(sql_receipt);
                }
            String query_purchase = "UPDATE stockingtable SET status = '1' WHERE status = '0' AND imei = '"+imei+"'";
            manage.update(query_purchase);
            String query = "UPDATE stocktable SET qty = qty + '"+qty+"' WHERE status = '1' AND productcode = '"+productcode+"'";
            manage.update(query);
            loginmessagedelete();
            JOptionPane.showMessageDialog(null, "Deleted");
            
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"viewsales.delete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void tableViewSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableViewSalesMouseClicked
        selectedrow();
        buttonDelete.setEnabled(true);
        buttonReprintReceipt.setEnabled(true);
    }//GEN-LAST:event_tableViewSalesMouseClicked

    private void tableViewSalesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableViewSalesKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrow();
        }

    }//GEN-LAST:event_tableViewSalesKeyReleased

    private void buttonReprintReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReprintReceiptActionPerformed
       
       SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
               receipt();
               return null;
           }
           
       };
       worker.execute();        
    }//GEN-LAST:event_buttonReprintReceiptActionPerformed

    private void txtSearchingFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchingFocusGained
        
    }//GEN-LAST:event_txtSearchingFocusGained

    private void tableViewSalesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableViewSalesMouseReleased
       
    }//GEN-LAST:event_tableViewSalesMouseReleased

    private void tableViewSalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableViewSalesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableViewSalesMouseEntered

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        int rows = tableViewSales.getRowCount();
            if(rows > 0){
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION){

                }else if (response == JOptionPane.YES_OPTION){
                    
                            delete();
                }else if(response == JOptionPane.CLOSED_OPTION){

                } 
            }else{
                        buttonDelete.setEnabled(false);
                        buttonReprintInvoice.setEnabled(false);
                        buttonReprintReceipt.setEnabled(false);
            }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonEditInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditInvoiceActionPerformed
        if(!meansof_payments.equals("INSTALLMENTS")){
            JOptionPane.showMessageDialog(null, "Please Select transaction for Creditors");
            buttonEditInvoice.requestFocus();
        }else{
            txtPaidamount.setEnabled(true);
            showdialogeditinvoice();
            String bal = txtBalbf.getText();
            bal = bal.trim();
            double balBf = Double.parseDouble(bal);
                if(balBf <= 0 ){
                    txtPaidamount.setEnabled(false);
                }
        }
    }//GEN-LAST:event_buttonEditInvoiceActionPerformed

    private void buttonExit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExit3ActionPerformed
       
    }//GEN-LAST:event_buttonExit3ActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        resetinvoices();
    }//GEN-LAST:event_buttonAddActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        saveinvoicepayment();
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDelete1ActionPerformed
       
    }//GEN-LAST:event_buttonDelete1ActionPerformed

    private void calculatebal(){
        String balancebf = txtBalbf.getText();
        balancebf = balancebf.trim();
        double balBF = Double.parseDouble(balancebf);
        
        String paidamount = txtPaidamount.getText();
        paidamount = paidamount.trim();
        double paid = Double.parseDouble(paidamount);
        
        txtBalance.setText(String.format("%.0f", balBF - paid));
    }
    private void txtPaidamountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidamountKeyReleased
        calculatebal();
    }//GEN-LAST:event_txtPaidamountKeyReleased

    private void tableInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInvoiceMouseClicked
        selectedrowinvoice();
    }//GEN-LAST:event_tableInvoiceMouseClicked

    private void txtPaidamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidamountKeyTyped
      char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
      }
    }//GEN-LAST:event_txtPaidamountKeyTyped

    private void tableInvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableInvoiceKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrowinvoice();
        }
    }//GEN-LAST:event_tableInvoiceKeyReleased

    private void chooserFromRemovePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserFromRemovePropertyChange
        update_tableremove();
    }//GEN-LAST:event_chooserFromRemovePropertyChange

    private void chooserToRemovePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserToRemovePropertyChange
        update_tableremove();
    }//GEN-LAST:event_chooserToRemovePropertyChange

    private void tableRemoveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRemoveMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRemoveMouseReleased

    private void tableRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRemoveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRemoveMouseClicked

    private void tableRemoveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRemoveMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRemoveMouseEntered

    private void tableRemoveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableRemoveKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRemoveKeyReleased

    private void buttonReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReceiptActionPerformed
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
               removereport();
               return null;
           }
           
       };
       worker.execute();     
    }//GEN-LAST:event_buttonReceiptActionPerformed

    private void buttonReprintInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReprintInvoiceActionPerformed
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
               invoice_sales();
               return null;
           }
           
       };
       worker.execute();   
    }//GEN-LAST:event_buttonReprintInvoiceActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogEditInvoice;
    private javax.swing.JButton buttonAdd;
    public javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonDelete1;
    public javax.swing.JButton buttonEditInvoice;
    private javax.swing.JButton buttonExit3;
    public javax.swing.JButton buttonReceipt;
    public javax.swing.JButton buttonReprintInvoice;
    public javax.swing.JButton buttonReprintReceipt;
    private javax.swing.JButton buttonSave;
    private com.toedter.calendar.JDateChooser chooserFrom;
    private com.toedter.calendar.JDateChooser chooserFromRemove;
    private com.toedter.calendar.JDateChooser chooserTo;
    private com.toedter.calendar.JDateChooser chooserToRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JPanel panelReport;
    private javax.swing.JTable tableInvoice;
    private javax.swing.JTable tableRemove;
    private javax.swing.JTable tableViewSales;
    private javax.swing.JLabel txtBalance;
    private javax.swing.JLabel txtBalbf;
    private javax.swing.JLabel txtClientname;
    private javax.swing.JLabel txtInvoiceno;
    private javax.swing.JTextField txtPaidamount;
    private javax.swing.JTextField txtSearching;
    private javax.swing.JLabel txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
