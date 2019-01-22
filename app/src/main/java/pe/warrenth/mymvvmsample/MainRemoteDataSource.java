package pe.warrenth.mymvvmsample;

public class MainRemoteDataSource implements MainDataSource {

    private static MainRemoteDataSource INSTANCE;

    public static MainRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getData(LoadDataCallback callback) {

    }
}
