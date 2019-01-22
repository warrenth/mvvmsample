package pe.warrenth.mymvvmsample;

import java.util.List;

public class MainRepository implements MainDataSource {

    private static MainRepository INSTANCE = null;

    private final MainDataSource mMainDataSource;

    private MainRepository(MainDataSource mainDataSource) {
        mMainDataSource = mainDataSource;
    }

    public static MainRepository getInstance(MainDataSource mainDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new MainRepository(mainDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getData(LoadDataCallback callback) {
        mMainDataSource.getData(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {

            }
        });
    }
}
