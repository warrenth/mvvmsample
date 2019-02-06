package pe.warrenth.mymvvmsample.data.fake;

import android.os.Handler;

import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import pe.warrenth.mymvvmsample.AppExecutors;
import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.local.TasksDao;

public class TodoFakeDataSource implements TodoDataSource {

    private static volatile TodoFakeDataSource INSTANCE;

    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();

    private TasksDao mTasksDao;

    private AppExecutors mAppExecutors;

    public static TodoFakeDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TodoFakeDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getTasks(final GetTasksCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TASKS_SERVICE_DATA.put("0", new Task("0","타이틀01", ""));
                TASKS_SERVICE_DATA.put("1", new Task("1","타이틀03", ""));
                TASKS_SERVICE_DATA.put("2", new Task("2","타이틀03", ""));

                callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        },1500);
    }

    @Override
    public void getTask(String taskId, GetTaskCallback getTaskCallback) {

    }


    @Override
    public void saveTask(final Task newTask) {
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mTasksDao.insertTask(newTask);
            }
        });
    }
}
