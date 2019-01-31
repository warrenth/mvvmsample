package pe.warrenth.mymvvmsample.todolist;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;

import pe.warrenth.mymvvmsample.BR;
import pe.warrenth.mymvvmsample.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class TodoViewModel extends BaseObservable {


    //XML 과 바인딩되어있는 객체들이다.
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);  // swipeRefreshLayout의 refreshing와 연결.

    //ListView와 연결된
    public final ObservableList<Task> items = new ObservableArrayList<>();

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final TodoRepository mTodoRepository;

    // MainAcitivity 에서 callback 받기 위한 listener
    private TodoListNavigator mNavigator;

    public TodoViewModel(TodoRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mTodoRepository = repository;
    }

    public void setNavigator(TodoListNavigator navigator) {
        mNavigator = navigator;
    }

    public void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
    }


    public void start() {
        loadData();
    }

    private void loadData() {
        dataLoading.set(true);

        mTodoRepository.getData(new TodoDataSource.LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {

                items.addAll(tasks);
                notifyPropertyChanged(BR.empty);
                dataLoading.set(false);
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


    public void setTask(Task task) {
        mTaskObservable.set(task);
    }

    @Bindable
    public boolean isEmpty() {
        return items.isEmpty();
    }


    public void addTodo() {
        if(mNavigator != null) {
            mNavigator.addTodo();
        }
    }
}
