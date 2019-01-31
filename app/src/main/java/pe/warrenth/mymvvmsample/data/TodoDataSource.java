package pe.warrenth.mymvvmsample.data;

import java.util.List;

import pe.warrenth.mymvvmsample.Task;

public interface TodoDataSource {

    interface LoadDataCallback {
        void onDataLoaded(List<Task> tasks);
    }

    void getData(LoadDataCallback callback);
}
