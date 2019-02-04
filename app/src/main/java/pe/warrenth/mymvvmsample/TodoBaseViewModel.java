package pe.warrenth.mymvvmsample;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import java.util.List;

import pe.warrenth.mymvvmsample.BR;
import pe.warrenth.mymvvmsample.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.todolist.TodoListNavigator;

public class TodoBaseViewModel extends BaseObservable {



    private final ObservableField<Task> mTaskObservable = new ObservableField<>();

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
