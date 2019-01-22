package pe.warrenth.mymvvmsample;

public class MainLocalDataSource implements MainDataSource {

    private static MainLocalDataSource INSTANCE;

    public static MainLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getData(LoadDataCallback callback) {

    }
}
