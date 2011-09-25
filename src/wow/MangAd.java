// World of Warcraft Mobile
//
// MIDlet implementation

package wow;

import java.io.*;

public class MangAd {

    private static String s_prefix = "resource:";
    
    private Thread m_async;
    private String m_user, m_pass, m_serv;

    
    public static void main (String[] args) {
        // Config
        WoWconfig conf = new WoWconfig();
        
        // UI: logging
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    new WoWwindow().setVisible(true);
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // Game
        MangAd app = new MangAd();
        
        app.login(conf.GetS("user"), conf.GetS("pass"), conf.GetS("realm"));
        final Thread game = new Thread() {
            public void run() {
                while (true) {
                    try {
                    this.sleep(WoWgame.self().idle());
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        game.start();        
    }
    
    public static String resource(String name) {
        return s_prefix + name;
    }

    private void ingame(WoWauth auth) {
        System.err.println("WoWmobile.ingame()");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(bos);
        try {
            System.out.println(m_user);
            System.out.println(m_pass);
            System.out.println(m_serv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            os.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WoWgame.self().setExtra(this);
        WoWgame.self().setAuth(auth);
    }

    private void logfail(final String error) {
        System.err.println("WoWmobile.logfail() "+error);
    }

    private boolean login(final String user, final String pass, final String serv) {
        System.err.println("WoWmobile.login() "+user);
        if (user == null || pass == null || serv == null || user.equals("") || pass.equals("") || serv.equals(""))
            return false;
        String servAddr = serv;
        int servPort = 3724;
        if (serv.indexOf(':') > 0) {
            servAddr = serv.substring(0, serv.lastIndexOf(':'));
            servPort = Integer.parseInt( serv.substring(serv.lastIndexOf(':')+1) );
        }
        final String addr = servAddr;
        final int port = servPort;
        System.err.println("Logging in "+user);
        System.err.println("Connecting to "+servAddr+":"+servPort);
        Thread login = new Thread() {
            public void run() {
                m_async = this;
                WoWauth auth = new WoWauth(addr, port,(byte)3,(byte)3,(byte)5,12340);
                boolean ok = auth.doLogin(user,pass);
                System.err.println("Conn: "+auth.isConnected()+", Auth: "+ok+", error: "+auth.getError());
                if (ok)
                    ok = auth.doRealms();
                if (m_async == this)
                    m_async = null;
                if (ok) {
                    auth.disconnect();
                    ingame(auth);
                } else {
                    logfail(auth.getError());
                    auth.cleanup();
                }
            }
        };
        login.start();
        return true;
    }

    public void destroyApp(boolean unconditional) {
        System.err.println("WoWmobile.destroyApp() "+unconditional);
        System.err.flush();
    }

    public void pauseApp() {
        //WoWgame.self().pauseEvent(true);
    }

    public void startApp() {
        System.err.println("WoWmobile.startApp()");
        //WoWgame.self().pauseEvent(false);
    }
}
