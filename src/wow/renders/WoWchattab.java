
package wow.renders;

import wow.*;
import wow.ui.WoWwindow;

public class WoWchattab extends javax.swing.JPanel {

    private String targetName;
    
    public WoWchattab(String _targetName) {
        targetName = _targetName;
        initComponents();
        WoWwindow.self().addTab(targetName, this);
    }

    public void displayMsg(WoWchat msg) {
        uiChatText.append(msg.name()+": "+msg.text()+"\n");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        uiChatText = new javax.swing.JTextArea();
        uiMsgInput = new javax.swing.JTextField();
        uiSend = new javax.swing.JButton();

        uiChatText.setColumns(20);
        uiChatText.setRows(5);
        jScrollPane1.setViewportView(uiChatText);

        uiMsgInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendboxHandler(evt);
            }
        });

        uiSend.setText("Send");
        uiSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendboxHandler(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uiMsgInput, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uiSend, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uiSend)
                    .addComponent(uiMsgInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendboxHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendboxHandler
        WoWchat.SendChatMessage(uiMsgInput.getText(), WoWchat.MSG_WHISPER, targetName);
        uiChatText.append(WoWgame.self().player().name()+": "+uiMsgInput.getText()+"\n");
        uiMsgInput.setText("");
    }//GEN-LAST:event_sendboxHandler

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea uiChatText;
    private javax.swing.JTextField uiMsgInput;
    private javax.swing.JButton uiSend;
    // End of variables declaration//GEN-END:variables
}
