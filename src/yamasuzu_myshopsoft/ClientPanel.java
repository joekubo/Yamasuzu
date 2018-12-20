
package yamasuzu_myshopsoft;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ClientPanel extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public String loggeduserid;
    public String loggedusername;
    public String loggedpriviledge;
    private int clientid;
    Manage manage = new Manage();
    public ClientPanel() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
    }
    public ArrayList<SearchClient> ListClients(String ValToSearch){
        ArrayList<SearchClient> clientList = new ArrayList<SearchClient>();
        try{
            String searchQuery = "SELECT * FROM clientstable WHERE CONCAT(clientname,'',clientaddress,'',clientcity,'',clientphone,'',"
                    + "clientemail,'',clientpin)LIKE'%"+ValToSearch+"%' AND status = '1'";
            pst = conn.prepareStatement(searchQuery);
            rs = pst.executeQuery();
            
            SearchClient search;
                while(rs.next()){
                    search = new SearchClient(
                                        rs.getInt("clientid"),
                                        rs.getString("clientname"),
                                        rs.getString("clientaddress"),
                                        rs.getString("clientcity"),
                                        rs.getString("clientphone"),
                                        rs.getString("clientemail"),
                                        rs.getString("clientpin")
                                                );
                    clientList.add(search);
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.searchclient/findclient");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return clientList;
    }
    public void findclient(){
        ArrayList<SearchClient> clients = ListClients(txtClientname.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id","Client Name","Address","City","Phone #","Email Address","PIN"});
        
        Object[] row = new Object[7];
        
            for(int i = 0; i < clients.size(); i ++){
                
             row[0] = clients.get(i).getClientid();
             row[1] = clients.get(i).getName();
             row[2] = clients.get(i).getAddress();
             row[3] = clients.get(i).getCity();
             row[4] = clients.get(i).getPhone();
             row[5] = clients.get(i).getEmail();
             row[6] = clients.get(i).getPin();
             model.addRow(row);
            }
            tableClients.setModel(model);
            TableColumn idColumn = tableClients.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
    }
    public void reset(){
        txtClientname.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtPin.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtClientname.requestFocus();
        buttonSave.setEnabled(true);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }
    private void save(){
        try{
            String name = txtClientname.getText();
            String address = txtAddress.getText();
            String city = txtCity.getText();
            String pin = txtPin.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            boolean check = ValidateEmail.validateEmail(txtEmail.getText());
                if(name.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Client Name");
                    txtClientname.requestFocus();
                }else if(address.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Address");
                    txtAddress.requestFocus();
                }else if(city.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter City");
                    txtCity.requestFocus();
                }else if(pin.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter PIN");
                    txtPin.requestFocus();
                }else if(phone.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Phone #");
                    txtPhone.requestFocus();
                }else if(email.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Email");
                    txtEmail.requestFocus();
                }else if(!check){
                    JOptionPane.showMessageDialog(null,"Invalid Email Address!!!");
                    txtEmail.setText("");
                    txtEmail.requestFocus();
                }else{
                    String sql = "INSERT INTO clientstable(clientname,clientaddress,clientcity,clientphone,clientemail,clientpin,status,balance)VALUES"
                            + "(?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    
                    pst.setString(1, name);
                    pst.setString(2, address);
                    pst.setString(3, city);
                    pst.setString(4, phone);
                    pst.setString(5, email);
                    pst.setString(6, pin);
                    pst.setString(7, "1");
                    pst.setString(8, "0");
                    
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Saved");
                    reset();
                    findclient();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.save");
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

        jLabel26 = new javax.swing.JLabel();
        txtClientname = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtPin = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClients = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        buttonDelete = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonUpdate = new javax.swing.JButton();

        setBackground(java.awt.Color.white);

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel26.setText("Clients");

        txtClientname.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtClientname.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtClientnameInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtClientname.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtClientnamePropertyChange(evt);
            }
        });
        txtClientname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClientnameKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClientnameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientnameKeyReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel27.setText("Address:");

        txtAddress.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtAddress.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtAddressInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtAddress.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAddressPropertyChange(evt);
            }
        });
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAddressKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAddressKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel28.setText("City:");

        txtCity.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtCity.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCityInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtCity.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCityPropertyChange(evt);
            }
        });
        txtCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCityKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCityKeyReleased(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel29.setText("Phone #:");

        txtPhone.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtPhone.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtPhoneInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtPhone.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPhonePropertyChange(evt);
            }
        });
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneKeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel30.setText("Email Address:");

        txtEmail.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtEmail.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtEmailInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtEmail.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtEmailPropertyChange(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel31.setText("PIN:");

        txtPin.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtPin.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtPinInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtPin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPinPropertyChange(evt);
            }
        });
        txtPin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPinKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPinKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPinKeyReleased(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel32.setText("Client Name:");

        tableClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsMouseClicked(evt);
            }
        });
        tableClients.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableClientsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableClients);

        jPanel10.setBackground(java.awt.Color.white);
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonDelete.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });
        buttonDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonDeleteKeyPressed(evt);
            }
        });

        buttonAdd.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonAdd.setText("Add");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });
        buttonAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonAddKeyPressed(evt);
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAdd)
                    .addComponent(buttonSave)
                    .addComponent(buttonUpdate)
                    .addComponent(buttonDelete)
                    .addComponent(buttonExit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtClientname, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPin, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 64, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClientname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtClientnameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtClientnameInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientnameInputMethodTextChanged

    private void txtClientnamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtClientnamePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientnamePropertyChange

    private void txtClientnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientnameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientnameKeyTyped

    private void txtClientnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientnameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientnameKeyPressed

    private void txtClientnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientnameKeyReleased
       txtClientname.setText(txtClientname.getText().toUpperCase());
       findclient();
    }//GEN-LAST:event_txtClientnameKeyReleased

    private void txtAddressInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtAddressInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressInputMethodTextChanged

    private void txtAddressPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAddressPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressPropertyChange

    private void txtAddressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressKeyTyped

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressKeyPressed

    private void txtAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyReleased
        txtAddress.setText(txtAddress.getText().toUpperCase());
    }//GEN-LAST:event_txtAddressKeyReleased

    private void txtCityInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCityInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityInputMethodTextChanged

    private void txtCityPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCityPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityPropertyChange

    private void txtCityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityKeyTyped

    private void txtCityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCityKeyPressed

    private void txtCityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyReleased
        txtCity.setText(txtCity.getText().toUpperCase());
    }//GEN-LAST:event_txtCityKeyReleased

    private void txtPhoneInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtPhoneInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneInputMethodTextChanged

    private void txtPhonePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPhonePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhonePropertyChange

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
       char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
          evt.consume();
      }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneKeyReleased

    private void txtEmailInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtEmailInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailInputMethodTextChanged

    private void txtEmailPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtEmailPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailPropertyChange

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
       txtEmail.setText(txtEmail.getText().toLowerCase());
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtPinInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtPinInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinInputMethodTextChanged

    private void txtPinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPinPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinPropertyChange

    private void txtPinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPinKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinKeyTyped

    private void txtPinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPinKeyPressed

    private void txtPinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPinKeyReleased
        txtPin.setText(txtPin.getText().toUpperCase());
    }//GEN-LAST:event_txtPinKeyReleased

    private void usersprevileges(){
        if(loggedpriviledge.equals("MANAGER") || loggedpriviledge.equals("USER")){
            
        }
    }
    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        
        String sql = "SELECT * FROM clientstable WHERE status = '1' AND clientid = '1'";
        if(manage.checking(sql) == 1){
            reset();
        }else{
            delete();
        }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonDeleteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDeleteKeyPressed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        reset();
    }//GEN-LAST:event_buttonAddActionPerformed

    private void buttonAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAddKeyPressed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        
        try{
            String name = txtClientname.getText();
            String phone = txtPhone.getText();
            String pin = txtPin.getText();
            String sql = "SELECT * FROM clientstable WHERE status = 1 AND clientname = ? OR clientphone= ? OR clientpin = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, pin);
            rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Either Client Name, Phone # or Client Pin exist. Please Check on those");
                    txtClientname.setText("");
                    txtPhone.setText("");
                    txtPin.setText("");
                    txtClientname.requestFocus();
                }else{
                    save();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" clientpanel.buttonSave");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }//GEN-LAST:event_buttonSaveActionPerformed
    private void selectedrow(){
        try{
            int row = tableClients.getSelectedRow();
            String table_click = tableClients.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM clientstable WHERE status = '1' AND clientid = '"+table_click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    buttonSave.setEnabled(false);
                    buttonUpdate.setEnabled(true);
                    buttonDelete.setEnabled(true);
                    clientid = rs.getInt("clientid");
                    txtClientname.setText(rs.getString("clientname"));
                    txtAddress.setText(rs.getString("clientaddress"));
                    txtCity.setText(rs.getString("clientcity"));
                    txtPhone.setText(rs.getString("clientphone"));
                    txtEmail.setText(rs.getString("clientemail"));
                    txtPin.setText(rs.getString("clientpin"));
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.selectedrow");
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
            String name = txtClientname.getText();
            String address = txtAddress.getText();
            String city = txtCity.getText();
            String pin = txtPin.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            boolean check = ValidateEmail.validateEmail(txtEmail.getText());
                if(name.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Client Name");
                    txtClientname.requestFocus();
                }else if(address.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Address");
                    txtAddress.requestFocus();
                }else if(city.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter City");
                    txtCity.requestFocus();
                }else if(pin.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter PIN");
                    txtPin.requestFocus();
                }else if(phone.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Phone #");
                    txtPhone.requestFocus();
                }else if(email.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Email");
                    txtEmail.requestFocus();
                 }else if(!check){
                    JOptionPane.showMessageDialog(null,"Invalid Email Address!!!");
                    txtEmail.setText("");
                    txtEmail.requestFocus();    
                }else{
                    String sql = "UPDATE clientstable SET clientname = '"+name+"',clientaddress = '"+address+"',clientcity = '"+city+"',"
                            + "clientpin = '"+pin+"',clientphone = '"+phone+"',clientemail = '"+email+"' WHERE status = '1' AND clientid = "
                            + "'"+clientid+"'";
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Updated");
                    loginmessageupdate();
                    reset();
                    findclient();
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" clientpanel.update");
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
            String Operation = "Updating details for '"+txtClientname.getText()+"' (Client)";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.loginmessageupdate");
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
            String Operation = "Delete details for '"+txtClientname.getText()+"' (Client)";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.loginmessagedelete");
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
            String sql = "UPDATE clientstable SET status = '0' WHERE status = '1' AND clientid = '"+clientid+"'";
            pst = conn.prepareStatement(sql);
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted");
            loginmessagedelete();
            reset();
            findclient();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"clientpanel.delete");
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

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        update();
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonUpdateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonUpdateKeyPressed

    private void tableClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientsMouseClicked
        selectedrow();
    }//GEN-LAST:event_tableClientsMouseClicked

    private void tableClientsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableClientsKeyReleased
       if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            selectedrow();
        }
    }//GEN-LAST:event_tableClientsKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    public javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableClients;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtClientname;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPin;
    // End of variables declaration//GEN-END:variables
}
