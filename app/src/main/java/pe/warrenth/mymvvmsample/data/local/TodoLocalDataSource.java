package pe.warrenth.mymvvmsample.data.local;

import android.os.Handler;

import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import pe.warrenth.mymvvmsample.Task;
import pe.warrenth.mymvvmsample.data.TodoDataSource;

public class TodoLocalDataSource implements TodoDataSource {

    private static TodoLocalDataSource INSTANCE;

    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();

    public static TodoLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TodoLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getData(final LoadDataCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TASKS_SERVICE_DATA.put("0", new Task("0","타이틀01", ""));
                TASKS_SERVICE_DATA.put("1", new Task("1","타이틀03", ""));
                TASKS_SERVICE_DATA.put("2", new Task("2","타이틀03", ""));

                callback.onDataLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        },1500);
    }
}
