package wow;

public class MangAd {

    private static String s_prefix = "resource:";
    
    private Thread m_async;
    
    public static void main (String[] args) {
        MangAd app = new MangAd();
        
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
        app.login(conf.GetS("user"), conf.GetS("pass"), conf.GetS("realm"));
        app.startApp();
    }
    
    public static String resource(String name) {
        return s_prefix + name;
    }

    private void ingame(WoWauth auth) {
        System.err.println("WoWmobile.ingame()");
        WoWgame.self().setAuth(auth);
        WoWgame.self().pauseEvent(false);
    }

    private void logfail(final String error) {
        System.err.println("WoWmobile.logfail() "+error);
    }

    private boolean login(final String user, final String pass, final String serv) {
        System.err.println("WoWmobile.login() "+user);
        if (user == null || pass == null || serv == null || user.equals("") || pass.equals("") || serv.equals(""))
            return false;
        System.err.println("Logging in "+user);
        System.err.println("Connecting to "+serv);
        Thread login = new Thread() {
            public void run() {
                m_async = this;
                WoWauth auth = new WoWauth(serv,(byte)3,(byte)3,(byte)5,12340);
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
        WoWgame.self().pauseEvent(true);
    }

    public void startApp() {
        System.err.println("WoWmobile.startApp()");
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
