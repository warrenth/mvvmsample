package pe.warrenth.mymvvmsample.todolist;

import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import pe.warrenth.mymvvmsample.TodoBaseViewModel;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class TodoListItemViewModel extends TodoBaseViewModel {

    // This navigator is s wrapped in a WeakReference to avoid leaks because it has references to an
    // activity. There's no straightforward way to clear it for each item in a list adapter.
    @Nullable
    private WeakReference<TodoListItemNavigator> mNavigator;

    public TodoListItemViewModel(TodoRepository repository, Context context) {
        super(repository, context);
    }


    public void setNavigator(TodoListItemNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    public void todoItemClicked() {
        String taskId = getTaskId();
        if (taskId == null) {
            // Click happened before task was loaded, no-op.
            return;
        }
        if (mNavigator != null && mNavigator.get() != null) {
            mNavigator.get().openTodoDetail(taskId);
        }
    }


}
