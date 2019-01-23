package pe.warrenth.mymvvmsample;

import android.os.Handler;

import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainLocalDataSource implements MainDataSource {

    private static MainLocalDataSource INSTANCE;

    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();

    public static MainLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getData(final LoadDataCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDataLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        },3000);
    }
}
