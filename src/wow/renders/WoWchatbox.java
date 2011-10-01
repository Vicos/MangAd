
package wow.renders;

import java.util.HashMap;
import java.util.Map;
import wow.*;
import wow.ui.WoWwindow;

public class WoWchatbox extends javax.swing.JPanel implements WoWrender {

    private Map<String,WoWchattab> whisperTabs;
    
    public WoWchatbox() {
        initComponents();
        WoWwindow.self().addTab("Chat", this);
        whisperTabs = new HashMap();
    }

    @Override
    public boolean netEvent(WoWpacket pkt) {
        switch (pkt.code()) {
        case WoWpacket.SMSG_MESSAGECHAT:
            WoWchat c = new WoWchat(pkt);
            if(c.name() != null) {
                switch(c.type()) {
                    case WoWchat.MSG_SAY:
                        uiChatText.append(c.name()+": "+c.text()+"\n");
                        break;
                    case WoWchat.MSG_WHISPER:
                        if (!whisperTabs.containsKey(c.name())) {
                            WoWchattab tab = new WoWchattab(c.name());
                            whisperTabs.put(c.name(), tab);
                        }
                        whisperTabs.get(c.name()).displayMsg(c);
                        break;
                    case WoWchat.MSG_CHANNEL:
                        break;
                }
            }
            return true;
        }
        return false;
    }   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        uiChatText = new javax.swing.JTextArea();
        uiMsgInput = new javax.swing.JTextField();
        uiSend = new javax.swing.JButton();

        uiChatText.setColumns(20);
        uiChatText.setEditable(false);
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(uiMsgInput, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uiSend, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uiSend)
                    .addComponent(uiMsgInput, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendboxHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendboxHandler
        WoWchat.SendChatMessage(uiMsgInput.getText(), WoWchat.MSG_SAY);
        uiMsgInput.setText("");
    }//GEN-LAST:event_sendboxHandler

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea uiChatText;
    private javax.swing.JTextField uiMsgInput;
    private javax.swing.JButton uiSend;
    // End of variables declaration//GEN-END:variables

}
