package wow.renders;

import javax.swing.*;
import wow.*;

public class WoWcharselect extends javax.swing.JPanel implements WoWrender {

    private final String tabName = "Characters";
    
    private int m_selected;
    private int m_charCount;
    private WoWchar m_chars[];
    
    private DefaultListModel charListModel;
        
    public WoWcharselect() {
        charListModel = new DefaultListModel();
        initComponents();
        WoWwindow.self().addTab(tabName, this);
        WoWwindow.self().focusTab(tabName);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uiConnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        uiCharList = new javax.swing.JList();

        uiConnect.setText("Connect");
        uiConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectHandler(evt);
            }
        });

        uiCharList.setModel(charListModel);
        jScrollPane1.setViewportView(uiCharList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uiConnect, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiConnect)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void connectHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectHandler
        if(evt.getSource().equals(uiConnect)) {
            selectChar(uiCharList.getSelectedIndex());
        }
    }//GEN-LAST:event_connectHandler

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList uiCharList;
    private javax.swing.JButton uiConnect;
    // End of variables declaration//GEN-END:variables

    
    private void updateCharList() {
        for(WoWchar chr : m_chars) {
            charListModel.addElement(chr);
        }
    }
    
    public boolean netEvent(WoWpacket pkt) {
        switch (pkt.code()) {
        case WoWpacket.SMSG_AUTH_RESPONSE:
            if (WoWgame.self().conn() != null)
                WoWgame.self().conn().writePacket(WoWpacket.CMSG_CHAR_ENUM);
            return true;
        case WoWpacket.SMSG_CHAR_ENUM:
            int n = pkt.getByte();
            if (n > 0) {
                WoWchar chars[] = new WoWchar[n];
                for (int i = 0; i < n; i++)
                    chars[i] = parseChar(pkt);
                if (!pkt.eof())
                    return false;
                m_chars = chars;
                m_charCount = n;
                WoWgame.self().setDebug("Server chars: "+n);
                updateCharList();
            }
            return true;
        }
        return false;
    }

    public int idle() {
        return 0;
    }

    public void update(long elapsed) {
        
    }
    
    private boolean selectChar(int charNum) {
        WoWgame.self().conn().writePacket(WoWpacket.CMSG_PLAYER_LOGIN,m_chars[charNum].guid());
        WoWgame.self().setCharacter(m_chars[charNum]);
        WoWwindow.self().removeTab(tabName);
        return false;
    }
    
    private WoWchar parseChar(WoWpacket pkt) {
        long guid = pkt.getLong();
        String name = pkt.getString();
        int race = pkt.getByte();
        int cls = pkt.getByte();
        int gender = pkt.getByte();
        pkt.skip(5); // look
        int level = pkt.getByte();
        int zone = pkt.getInt();
        int map = pkt.getInt();
        pkt.skip(16); // xyz, guild
        int flags = pkt.getInt();
        pkt.skip(224); // other flags, pet, slots, bags
        String area = null;
        /* WoWwdbc wma = WoWwdbc.cached("resource:/dbc/WorldMapArea.dbc");
        if (wma != null) {
            int idx = wma.getRecord(2,zone);
            area = wma.getString(3,idx);
        } */
        if (area == null)
            area = "Darnassis";
        System.err.println("Character: "+guid+" '"+name+"' zone "+zone+" map "+map);
        WoWchar c = new WoWchar(name,"Level "+level+" "+WoWchar.textRace(race)+
                                " "+WoWchar.textClass(cls), area);
        /*
        WoWwdbc cc = WoWwdbc.cached("resource:/dbc/ChrClasses.dbc");
        if (cc != null) {
            int idx = cc.getRecord(0,cls);
            System.err.println("Class found at index "+idx+" names="+cc.getString(4,idx)+","+cc.getString(21,idx)+","+cc.getString(38,idx)+" internal="+cc.getString(55,idx));
        }
        WoWwdbc cr = WoWwdbc.cached("resource:/dbc/ChrRaces.dbc");
        if (cr != null) {
            int idx = cr.getRecord(0,race);
            System.err.println("Race found at index "+idx+" names="+cr.getString(14,idx)+","+cr.getString(31,idx)+","+cr.getString(48,idx)+" internal="+cr.getString(11,idx));
        }
        */
        c.m_guid = guid;
        c.m_race = race;
        c.m_class = cls;
        c.m_gender = gender;
        c.m_flags = flags;
        return c;
    }

}
