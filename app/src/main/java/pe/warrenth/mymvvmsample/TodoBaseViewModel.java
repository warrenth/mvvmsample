package pe.warrenth.mymvvmsample;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class TodoBaseViewModel extends BaseObservable implements TodoDataSource.GetTaskCallback {

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final TodoRepository mTodoRepository;

    public TodoBaseViewModel(TodoRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mTodoRepository = repository;

        // Exposed observables depend on the mTaskObservable observable:
        mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Task task = mTaskObservable.get();
                if (task != null) {
                    title.set(task.getTitle());
                    description.set(task.getDescription());
                }
            }
        });
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

    public void start(String taskId) {
        if(taskId != null) {
            mTodoRepository.getTask(taskId, this);
        }
    }

    @Override
    public void onTaskLoaded(Task task) {
        mTaskObservable.set(task);
        notifyChange(); // For the @Bindable properties
    }

    @Override
    public void onDataNotAvailable() {

    }
}
