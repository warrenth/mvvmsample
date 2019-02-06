package pe.warrenth.mymvvmsample.data.remote;

import pe.warrenth.mymvvmsample.data.Task;
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
    public void getTasks(GetTasksCallback callback) {

    }

    @Override
    public void getTask(String taskId, GetTaskCallback getTaskCallback) {

    }


    @Override
    public void saveTask(Task newTask) {

    }
}
