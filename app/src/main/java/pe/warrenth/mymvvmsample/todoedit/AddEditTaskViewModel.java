package pe.warrenth.mymvvmsample.todoedit;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class AddEditTaskViewModel implements TodoDataSource.GetTaskCallback {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    private AddEditTaskNavigator mNavigator;
    private final Context mContext;  // To avoid leaks, this must be an Application Context.
    private final TodoRepository mTodoRepository;
    @Nullable
    private String mTaskId;
    private boolean mIsNewTask;

    public AddEditTaskViewModel(Context context, TodoRepository todoRepository) {
        mContext = context.getApplicationContext();
        mTodoRepository = todoRepository;
    }

    public void onActivityCreated(AddEditTaskNavigator navigator) {
        mNavigator = navigator;
    }

    public void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        mNavigator = null;
    }

    public void start(String taskId) {

        mTaskId = taskId;

        if(taskId == null) {
            mIsNewTask = true;
            return;
        }
        mIsNewTask = false;
        dataLoading.set(true);
        //mTodoRepository.getTask(taskId, this);
    }


    @Override
    public void onTaskLoaded(Task tasks) {

    }

    @Override
    public void onDataNotAvailable() {

    }

    public void saveTask() {
        if(mIsNewTask) {
            createTask(title.get(), description.get());
        } else  {
            //update task
        }
    }

    private void createTask(String title, String description) {
        Task newTask = new Task(title, description);
        if(newTask.isEmpty()) {
            //empty task message
        } else  {
            mTodoRepository.saveTask(newTask);

            if(mNavigator != null) {
                mNavigator.onTaskSaved();
            }
        }
    }
}
