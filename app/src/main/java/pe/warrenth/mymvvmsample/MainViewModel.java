package pe.warrenth.mymvvmsample;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import java.util.List;

public class MainViewModel extends BaseObservable {

    private Context mContext; // To avoid leaks, this must be an Application Context.
    private final MainRepository mMainRepository;

    //XML 과 바인딩되어있는 객체들이다.
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);  // swipeRefreshLayout의 refreshing와 연결.

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

            }
        });
    }
}
