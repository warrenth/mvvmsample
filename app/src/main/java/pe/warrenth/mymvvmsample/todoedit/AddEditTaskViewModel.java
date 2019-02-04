package pe.warrenth.mymvvmsample.todoedit;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import pe.warrenth.mymvvmsample.data.TodoRepository;

public class AddEditTaskViewModel {

    private AddEditTaskNavigator mNavigator;

    private final Context mContext;  // To avoid leaks, this must be an Application Context.

    private final TodoRepository mTodoRepository;


    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);


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

    public void start(String string) {

    }
}
