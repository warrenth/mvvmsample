package pe.warrenth.mymvvmsample.data.remote;

import pe.warrenth.mymvvmsample.data.TodoDataSource;

public class TodoRemoteDataSource implements TodoDataSource {

    private static TodoRemoteDataSource INSTANCE;

    public static TodoRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TodoRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getData(LoadDataCallback callback) {

    }
}
