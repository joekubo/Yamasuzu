
package yamasuzu_myshopsoft;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import org.mindrot.jbcrypt.BCrypt;
public class UsersPanel extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public String loggeduserid;
    public String loggedusername;
    public String loggedpriviledge;
    public String companyid;
    private int id;
    
    public UsersPanel() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        this.setLocation(width , height);
    } 
    
    private void loginmessagecreate(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String Operation = "Create user details for '"+txtIdno.getText()+"' in Users";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessagecreate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void fillcomboCompany(){
        try{
            comboCompany.removeAllItems();
            comboCompany.addItem("Select");
            String sql = "SELECT companyname FROM companytable WHERE status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                while(rs.next()){
                    comboCompany.addItem(rs.getString("companyname"));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" userpanel.fillcombocompany");
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
            Date date = new Date();
            String Operation = "Updating details for '"+txtIdno.getText()+"' in Users";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" userspanel.loginmessageupdate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loginmessagedelete(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String Operation = "Deleted details for '"+txtIdno.getText()+"' in Users";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessagedelete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_table(){
        try{
            String sql = "SELECT id AS 'id',idnumber AS 'ID Number',name AS 'Name',phoneno AS 'Phone No',usertype AS 'User-Type' FROM"
                    + " usertable WHERE status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableUsers.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = tableUsers.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
        }catch(Exception e){
            System.out.println(e+"Error @ update_table");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void reset(){
        txtIdno.setText("");
        txtName.setText("");
        txtPhoneno.setText("");
        comboUsertype.setSelectedItem("Select");
        txtUsername.setText("");
        txtPassword.setText("");
        txtPassword.setText("");
        companyid = "";
        txtIdno.requestFocus();
        buttonSave.setEnabled(true);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        fillcomboCompany();
    }
    private void save(){
        try{
            String Idno = txtIdno.getText();
            String Name = txtName.getText();
            String Phoneno = txtPhoneno.getText();
            String Usertype = comboUsertype.getSelectedItem().toString();
            String Username = txtUsername.getText();
            String password = txtPassword.getText();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            String Status = "1";
            
            if(Idno.equals("")){
                JOptionPane.showMessageDialog(null, "Enter ID Number");
                txtIdno.requestFocus();
            }else if(Name.equals("")){
                JOptionPane.showMessageDialog(null, "Enter User's Name");
                txtName.requestFocus();
            }else if(Phoneno.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Phone No");
                txtPhoneno.requestFocus();
            }else if(Usertype.equals("Select")){
                JOptionPane.showMessageDialog(null, "Select User-Type");
                comboUsertype.requestFocus();
            }else if(Username.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Username");
                txtUsername.requestFocus();
            }else if(txtPassword.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Enter Password");
                txtPassword.requestFocus();
            }else{
             
            String sql = "INSERT INTO usertable(idnumber,name,phoneno,usertype,username,password,status,companyid,logged)VALUES(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, Idno);
            pst.setString(2, Name);
            pst.setString(3, Phoneno);
            pst.setString(4, Usertype);
            pst.setString(5, Username);
            pst.setObject(6, hashed);
            pst.setString(7, Status);
            pst.setString(8, companyid);
            pst.setString(9, "0");
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");
            loginmessagecreate();
            reset();
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+(" Error @ save"));
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        update_table();
    }
    private void selectedRow(){
        try{
            int row = tableUsers.getSelectedRow();
            String table_click = tableUsers.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM usertable,companytable WHERE usertable.companyid = companytable.companyid AND usertable.id = '"+table_click+"' AND usertable.status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
                txtIdno.setText(rs.getString("usertable.idnumber"));
                txtName.setText(rs.getString("usertable.name"));
                txtPhoneno.setText(rs.getString("usertable.phoneno"));
                comboUsertype.setSelectedItem(rs.getString("usertable.usertype"));
                txtUsername.setText(rs.getString("usertable.username"));
                comboCompany.setSelectedItem(rs.getString("companytable.companyname"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error @ selectedRow");
        }finally{
            try{
                
            }catch(Exception e){
                
            }
        }
    }
    private void update(){
        try{
            String Idno = txtIdno.getText();
            String Name = txtName.getText();
            String Phoneno = txtPhoneno.getText();
            String Usertype = comboUsertype.getSelectedItem().toString();
            String Username = txtUsername.getText();
            String Password = txtPassword.getText();
            
            String Confirmpassword = txtPassword.getText();
             String hashed = BCrypt.hashpw(Confirmpassword, BCrypt.gensalt());
            
            if(!Password.equals(Confirmpassword)){
                txtPassword.setText("");
                txtPassword.setText("");
                txtPassword.requestFocus();
            }else if(Password.equals("")){
                txtPassword.requestFocus();
            }else if(Confirmpassword.equals("")){
                txtPassword.requestFocus();
            
            }else{
            String sql = "UPDATE usertable SET idnumber = '"+Idno+"',name = '"+Name+"',phoneno = '"+Phoneno+"',usertype = '"+Usertype+"'"
                    + ",username =  '"+Username+"',password = '"+hashed+"',companyid = '"+companyid+"' WHERE id = '"+id+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            loginmessageupdate();
            reset();
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
                
            }catch(Exception e){
                
            }
        }
        update_table();
    }
    private void delete(){
        try{
            String sql = "UPDATE usertable SET status = '0' WHERE id = '"+id+"' AND status = '1'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted");
             loginmessagedelete();
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error @ delete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        update_table();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        buttonExit = new javax.swing.JButton();
        txtReset = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPhoneno = new javax.swing.JTextField();
        txtIdno = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboUsertype = new javax.swing.JComboBox();
        txtUsername = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboCompany = new javax.swing.JComboBox<>();

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel4.setText("Users");

        tableUsers.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        tableUsers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableUsersKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableUsers);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonExit.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        txtReset.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtReset.setText("Reset");
        txtReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResetActionPerformed(evt);
            }
        });

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        buttonDelete.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonUpdate.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txtReset, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonUpdate)
                    .addComponent(buttonDelete)
                    .addComponent(buttonExit)
                    .addComponent(buttonSave)
                    .addComponent(txtReset))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel1.setText("ID No:");

        txtPhoneno.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtPhoneno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhonenoKeyTyped(evt);
            }
        });

        txtIdno.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtIdno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdnoKeyTyped(evt);
            }
        });

        txtName.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel3.setText("Phone No:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdno)
                    .addComponent(txtName)
                    .addComponent(txtPhoneno, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtPassword.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel7.setText("UserType:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel5.setText("Username:");

        comboUsertype.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        comboUsertype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Admin", "Manager", "User" }));

        txtUsername.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel8.setText(" Password:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(comboUsertype, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboUsertype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel9.setText("Company:");

        comboCompany.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboCompanyPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(comboCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
      try{
          String sql = "SELECT * FROM usertable WHERE status = '1' AND  id = '"+id+"' AND logged = '1' AND usertype = 'Admin'";
          pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Cannot update Admin while still logged on");
                reset();
            }else{
                String Password = txtPassword.getText();
                String Confirmpassword = txtPassword.getText();
                if(Password.equals(Confirmpassword)){
                 update();
                }else{
                 txtPassword.setText("");
                 txtPassword.setText("");
                 txtPassword.requestFocus();
                }
            }
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e+" userpanel.buttonupdate");
      }finally{
          try{
              rs.close();
              pst.close();
          }catch(Exception e){
              
          }
      }
        
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void delete_users(){
        try{
          String sql = "SELECT * FROM usertable WHERE status = '1' AND  id = '"+id+"' AND logged = '1' AND usertype = 'Admin'";
          pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Cannot Deleted Admin while still logged on");
                reset();
            }else{
                String Password = txtPassword.getText();
                String Confirmpassword = txtPassword.getText();
                if(Password.equals(Confirmpassword)){
                 delete();
                }else{
                 txtPassword.setText("");
                 txtPassword.setText("");
                 txtPassword.requestFocus();
                }
            }
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e+" userpanel.buttonupdate");
      }finally{
          try{
              rs.close();
              pst.close();
          }catch(Exception e){
              
          }
      }
    }
    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed

        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){
        }else if (response == JOptionPane.YES_OPTION){
            delete_users();
        }else if(response == JOptionPane.CLOSED_OPTION){ 
            
        }       
       
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsersMouseClicked
        selectedRow();
        buttonSave.setEnabled(false);
        buttonUpdate.setEnabled(true);
        buttonDelete.setEnabled(true);
    }//GEN-LAST:event_tableUsersMouseClicked

    private void tableUsersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableUsersKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedRow();
        }
    }//GEN-LAST:event_tableUsersKeyReleased

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
       String Password = txtPassword.getText();
       String Confirmpassword = txtPassword.getText();
       if(Password.equals(Confirmpassword)){
           save();
       }else{
        txtPassword.setText("");
        txtPassword.setText("");
        txtPassword.requestFocus();
       }
   
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void txtResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResetActionPerformed
        reset();
    }//GEN-LAST:event_txtResetActionPerformed

    private void txtIdnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdnoKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
    }
    }//GEN-LAST:event_txtIdnoKeyTyped

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
       txtName.setText(txtName.getText().toUpperCase());
    }//GEN-LAST:event_txtNameKeyReleased

    private void txtPhonenoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhonenoKeyTyped
        char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
    }
    }//GEN-LAST:event_txtPhonenoKeyTyped

    private void comboCompanyPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboCompanyPopupMenuWillBecomeInvisible
        try{
            String sql = "SELECT companyid FROM companytable WHERE companyname = ? AND status = '1'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, comboCompany.getSelectedItem().toString());
            rs = pst.executeQuery();
                if(rs.next()){
                    companyid = rs.getString("companyid");
                    System.out.println(companyid);
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" userspanel.comboCompanyPopupMenuWillBecomeInvisible");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }//GEN-LAST:event_comboCompanyPopupMenuWillBecomeInvisible


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JComboBox<String> comboCompany;
    private javax.swing.JComboBox comboUsertype;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableUsers;
    private javax.swing.JTextField txtIdno;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhoneno;
    private javax.swing.JButton txtReset;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
