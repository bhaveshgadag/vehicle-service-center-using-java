/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehservicemgmt;

import com.sun.glass.events.KeyEvent;
import static com.sun.javafx.tk.Toolkit.getToolkit;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CustomMenuItemBuilder;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author bhave
 */
public class CustomerDialog extends javax.swing.JDialog {
    
    private int custId = -1;

    /**
     * Creates new form CustomerDialog
     */
    public CustomerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        custID.setEditable(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        custID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        custName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        address = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        contactNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        custAdd = new javax.swing.JButton();
        custCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        custID.setText("Automatic");

        jLabel1.setText("Customer ID");

        jLabel2.setText("Customer Name");

        custName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                custNameKeyTyped(evt);
            }
        });

        jLabel3.setText("Address");

        address.setColumns(20);
        address.setRows(5);
        jScrollPane1.setViewportView(address);

        jLabel4.setText("Contact no.");

        contactNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactNoKeyTyped(evt);
            }
        });

        jLabel5.setText("Email");

        email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailKeyTyped(evt);
            }
        });

        custAdd.setText("Add");
        custAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                custAddActionPerformed(evt);
            }
        });

        custCancel.setText("Cancel");
        custCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                custCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(custID))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(custName))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactNo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(email)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(custAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(custCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(custID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(custName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(custAdd)
                    .addComponent(custCancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void InfoDialog(String message){
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, message);
    }
    
    public void updateDetails(int cID) throws SQLException{
        custId = cID;
        Vector custDetails = dbconn.custSearch(cID);
        custID.setText(custDetails.get(0).toString());
        custName.setText((String)custDetails.get(1));
        address.setText((String)custDetails.get(2));
        contactNo.setText((String)custDetails.get(3));
        email.setText((String)custDetails.get(4));
    }
    
    private void custAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_custAddActionPerformed
        try {
            Vector values = new Vector();
            if (!custName.getText().isEmpty() && !address.getText().isEmpty() &&
                    !contactNo.getText().isEmpty() && !email.getText().isEmpty()) {
                values.add(custName.getText());
                values.add(address.getText());
                values.add(contactNo.getText());
                values.add(email.getText());
                if (!((String)custName.getText()).equals("") &&  ((String)custName.getText()).matches("^[A-Za-z\\s]+$"))
                {
                     if (!((String)address.getText()).equals("") && ((String)address.getText()).matches("^[#.0-9a-zA-Z\\s,-]+$"))
                     {
                         if (!((String)contactNo.getText()).equals("") && ((String)contactNo.getText()).matches("^[789]\\d{9}$"))
                         {
                             if (!((String)email.getText()).equals("") && ((String)email.getText()).matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$"))
                             {
                                if(custId == -1){
                                    dbconn.custInsert(values);
                                    this.dispose();
                                }
                                else{
                                    values.add(custId);
                                    dbconn.custUpdate(values);
                                    this.dispose();
                                }
                             }
                             else{
                                 JOptionPane.showMessageDialog(null,"Incorrect Email");
                             }

                             }
                             else{
                             JOptionPane.showMessageDialog(null,"Incorrect Contact");
                             }
                         }
                         else{
                           JOptionPane.showMessageDialog(null,"Incorrect Address");
                         }
                     }
                     else{
                       JOptionPane.showMessageDialog(null,"Incorrect  Name");
                     }
                
            }
        } catch (Exception e) {
            System.out.println("vehAddActionPerformed block:"+e);
            InfoDialog("Fill all information.");
        }
    }//GEN-LAST:event_custAddActionPerformed
    
    private void custCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_custCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_custCancelActionPerformed

    private void contactNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactNoKeyTyped
        // TODO add your handling code here:
        MainFrame.onlyNumber(evt);
    }//GEN-LAST:event_contactNoKeyTyped
    
    private void custNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_custNameKeyTyped
        MainFrame.letterSpace(evt);
    }//GEN-LAST:event_custNameKeyTyped

    private void emailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_emailKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CustomerDialog dialog = new CustomerDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea address;
    private javax.swing.JTextField contactNo;
    public javax.swing.JButton custAdd;
    private javax.swing.JButton custCancel;
    private javax.swing.JTextField custID;
    private javax.swing.JTextField custName;
    private javax.swing.JTextField email;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
