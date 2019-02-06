package pe.warrenth.mymvvmsample.todolist;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class TodoListViewModel extends BaseObservable {

    //XML 과 바인딩되어있는 객체들이다.
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);  // swipeRefreshLayout의 refreshing와 연결.

    //ListView와 연결된
    public final ObservableList<Task> items = new ObservableArrayList<>();

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final TodoRepository mTodoRepository;

    // MainAcitivity 에서 callback 받기 위한 listener.
    private TodoListNavigator mNavigator;

    public TodoListViewModel(TodoRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mTodoRepository = repository;
    }

    void setNavigator(TodoListNavigator navigator) {
        mNavigator = navigator;
    }

    public void start() {
        loadData();
    }


    private void loadData() {
        dataLoading.set(true);

        mTodoRepository.getTasks(new TodoDataSource.GetTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                items.clear();
                items.addAll(tasks);
                notifyPropertyChanged(BR.empty);
                dataLoading.set(false);
            }

            @Override
            public void onDataNotAvailable() {
                dataLoading.set(false);
            }
        });
    }


    public void addTodo() {
        if(mNavigator != null) {
            mNavigator.addNewTodo();
        }
    }

    public void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        mNavigator = null;
    }


    @Bindable
    public boolean isEmpty() {
        return items.isEmpty();
    }



}
