package pe.warrenth.mymvvmsample;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

public class TasksViewModel extends BaseObservable {

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();


    public void setTask(Task task) {
        mTaskObservable.set(task);
    }

    @Nullable
    protected String getTaskId() {
        return mTaskObservable.get().getId();
    }
}
