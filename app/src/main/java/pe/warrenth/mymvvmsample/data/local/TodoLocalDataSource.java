package pe.warrenth.mymvvmsample.data.local;

import android.support.annotation.NonNull;

import java.util.List;

import pe.warrenth.mymvvmsample.AppExecutors;
import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;

public class TodoLocalDataSource implements TodoDataSource {

    private static volatile TodoLocalDataSource INSTANCE;

    private AppExecutors mAppExecutors;

    private TasksDao mTasksDao;

    public TodoLocalDataSource(@NonNull AppExecutors appExecutors,
                               @NonNull TasksDao tasksDao) {
        mAppExecutors = appExecutors;
        mTasksDao = tasksDao;
    }

    public static TodoLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                  @NonNull TasksDao tasksDao) {
        if (INSTANCE == null) {
            INSTANCE = new TodoLocalDataSource(appExecutors, tasksDao);
        }
        return INSTANCE;
    }


    @Override
    public void getTasks(final GetTasksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTasksDao.getTasks();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else  {
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(final String taskId, final GetTaskCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Task task = mTasksDao.getTaskById(taskId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task != null) {
                            callback.onTaskLoaded(task);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
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
