package pe.warrenth.mymvvmsample;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;

public class MainViewModel extends BaseObservable {


    //XML 과 바인딩되어있는 객체들이다.
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);  // swipeRefreshLayout의 refreshing와 연결.

    //ListView와 연결된
    public final ObservableList<Task> items = new ObservableArrayList<>();

    private final ObservableField<Task> mTaskObservable = new ObservableField<>();



    private Context mContext; // To avoid leaks, this must be an Application Context.
    private final MainRepository mMainRepository;


    public MainViewModel(MainRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mMainRepository = repository;
    }

    public void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
    }


    public void start() {
        loadData();
    }

    private void loadData() {
        dataLoading.set(true);

        mMainRepository.getData(new MainDataSource.LoadDataCallback() {
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
}
