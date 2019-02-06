package pe.warrenth.mymvvmsample.data;

import java.util.List;

public interface TodoDataSource {


    interface GetTasksCallback {
        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {
        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(GetTasksCallback callback);

    void getTask(String taskId, GetTaskCallback getTaskCallback);

    void saveTask(Task newTask);

}
