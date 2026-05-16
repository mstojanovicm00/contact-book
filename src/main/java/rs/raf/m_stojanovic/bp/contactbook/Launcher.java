package rs.raf.m_stojanovic.bp.contactbook;

public class Launcher {

    private static Launcher launcher;

    public static Launcher getLauncher() {
        if (launcher == null) {
            synchronized (Launcher.class) {
                if (launcher == null)
                    launcher = new Launcher();
            }
        }
        return launcher;
    }

    private Launcher() {

    }

    void launch(String... args) {
        this.setUp(args);
        this.work(args);
        this.clean(args);
    }

    private void setUp(String... args) {
        Config.loadProperties(args[0]);
        Config.loadProperties(args[1]);

        String host = Config.getPropertyValue("relational.host", "");
        String port = Config.getPropertyValue("relational.port", "");
        String db = Config.getPropertyValue("relational.db", "");
        String user = Config.getPropertyValue("relational.user", "");
        String password = Config.getPropertyValue("relational.password", "");
        Config.connectToRelationalDatabase(host, port, db, user, password);

        host = Config.getPropertyValue("mongo.host", "");
        port = Config.getPropertyValue("mongo.port", "");
        db = Config.getPropertyValue("mongo.db", "");
        Config.connectToMongoDatabase(host, port, db);
    }

    private void work(String... args) {
        App.launch(App.class, args);
    }

    private void clean(String... args) {
        Config.disconnectFromRelationalDatabase();
        Config.disconnectFromMongoDatabase();
    }

}
