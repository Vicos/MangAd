package wow.ui;

import wow.MangAd;
import wow.WoWconfig;
import javax.swing.JOptionPane;

public class WoWlogin extends javax.swing.JPanel {

    final String tabName = "Login";
    private MangAd m_app;

    public WoWlogin(MangAd app) {
        m_app = app;
        initComponents();
        
        String username = WoWconfig.self().GetS("username");
        if (username != null)
            uiUsername.setText(username);
        
        String realm = WoWconfig.self().GetS("realm");
        if (realm != null)
            uiRealmAdress.setText(realm);
                
        WoWwindow.self().addTab(tabName, this);
        WoWwindow.self().focusTab(tabName);
        
        uiPassword.requestFocus();
    }

    private void saveFields() {
        WoWconfig.self().SetS("username", uiUsername.getText());
        WoWconfig.self().SetS("realm", uiRealmAdress.getText());
        WoWconfig.self().save();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        uiUsername = new javax.swing.JTextField();
        uiPassword = new javax.swing.JPasswordField();
        uiRealmAdress = new javax.swing.JTextField();
        uiConnect = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("Username");

        uiUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uiConnectActionPerformed(evt);
            }
        });

        uiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uiConnectActionPerformed(evt);
            }
        });
        uiPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                uiPasswordFocusGained(evt);
            }
        });

        uiRealmAdress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uiConnectActionPerformed(evt);
            }
        });

        uiConnect.setText("Connect");
        uiConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uiConnectActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        jLabel3.setText("Realm adress");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uiConnect)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(uiRealmAdress, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(uiPassword, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(uiUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(302, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiRealmAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(uiConnect)
                .addContainerGap(227, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void uiConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uiConnectActionPerformed
        try {
            m_app.login(uiUsername.getText(),
                    new String(uiPassword.getPassword()),
                    uiRealmAdress.getText());
            m_app.startGame();
            saveFields();
            WoWwindow.self().removeTab(tabName);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }    
    }//GEN-LAST:event_uiConnectActionPerformed

    private void uiPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_uiPasswordFocusGained
        uiPassword.selectAll();
    }//GEN-LAST:event_uiPasswordFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton uiConnect;
    private javax.swing.JPasswordField uiPassword;
    private javax.swing.JTextField uiRealmAdress;
    private javax.swing.JTextField uiUsername;
    // End of variables declaration//GEN-END:variables
}
