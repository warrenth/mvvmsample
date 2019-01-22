package pe.warrenth.mymvvmsample;

import java.util.List;

public interface MainDataSource {

    interface LoadDataCallback {
        void onDataLoaded(List<Task> tasks);
    }

    void getData(LoadDataCallback callback);
}
