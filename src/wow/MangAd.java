package wow;

import javax.swing.UIManager;
import wow.ui.WoWlogin;
import wow.ui.WoWwindow;        

public class MangAd {

    private static String s_prefix = "resource:";
    
    public static void main (String[] args) {
        MangAd app = new MangAd();
        
        // Config
        WoWconfig conf = new WoWconfig();
        
        // Look and Feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
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
        
        new WoWlogin(app);
        
    }
    
    public static String resource(String name) {
        return s_prefix + name;
    }

    private void ingame(WoWauth auth) {
        System.err.println("MangAd.ingame()");
        WoWgame.self().setAuth(auth);
        WoWgame.self().pauseEvent(false);
    }

    public boolean login(final String user, final String pass, final String serv) throws Exception {
        boolean ok;
        if (user == null || pass == null || serv == null || user.equals("") || pass.equals("") || serv.equals(""))
            return false;
        
        System.err.println("Logging in "+user);
        System.err.println("Connecting to "+serv);
        
        WoWauth auth = new WoWauth(serv,(byte)3,(byte)3,(byte)5,12340);
        ok = auth.doLogin(user,pass) && auth.doRealms();
        
        if (ok)
            ingame(auth);
        else
            throw new Exception("Login failed: "+auth.getError());
        
        return true;
    }

    public void destroyApp(boolean unconditional) {
        System.err.println("MangAd.destroyApp() "+unconditional);
        System.err.flush();
    }

    public void pauseApp() {
        WoWgame.self().pauseEvent(true);
    }

    public void startGame() {
        System.err.println("MangAd.startGame()");
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
}
