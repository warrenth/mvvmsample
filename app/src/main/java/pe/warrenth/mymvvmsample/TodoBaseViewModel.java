package pe.warrenth.mymvvmsample;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.todolist.TodoListNavigator;

public class TodoBaseViewModel extends BaseObservable {

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final TodoRepository mTodoRepository;

    // MainAcitivity 에서 callback 받기 위한 listener
    private TodoListNavigator mNavigator;

    public TodoBaseViewModel(TodoRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mTodoRepository = repository;
    }

    public void setNavigator(TodoListNavigator navigator) {
        mNavigator = navigator;
    }

    // This could be an observable, but we save a call to Task.getTitleForList() if not needed.
    @Bindable
    public String getTitleForList() {
        if (mTaskObservable.get() == null) {
            return "No data";
        }
        return mTaskObservable.get().getTitleForList();
    }

    @Nullable
    protected String getTaskId() {
        return mTaskObservable.get().getId();
    }

    public void setTask(Task task) {
        mTaskObservable.set(task);
    }

}
