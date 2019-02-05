package pe.warrenth.mymvvmsample.data;

import java.util.List;

public interface TodoDataSource {


    interface LoadDataCallback {
        void onDataLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface LoadTasksCallback {
        void onTasksLoaded(Task tasks);

        void onDataNotAvailable();
    }

    void getTasks(LoadDataCallback callback);

    void getTask(String taskId, LoadDataCallback loadDataCallback);

    void saveTask(Task newTask);

}
