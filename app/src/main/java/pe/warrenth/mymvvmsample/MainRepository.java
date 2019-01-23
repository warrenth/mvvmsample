package pe.warrenth.mymvvmsample;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 *  Clean Architecture 와 일반 Repository Pattern 차이
 *
 *  일반 :
 *         1. respository 에서 Datasource를 주입.
 *         2. DataSource는 Remote, Local의 데이터를 가져오는 로직으로 구현.
 *         3. cache 처리.
 *
 *  Clean Architecture
 *           1. UseCase 사용. Usecase에서 repository 를 주입.
 *           2. UseCase 는 도메인 레이어로 비지니스 로직을 담당.
 *
 */
public class MainRepository implements MainDataSource {

    private static MainRepository INSTANCE = null;

    private final MainDataSource mMainDataSource;

    private MainRepository(MainDataSource mainDataSource) {
        mMainDataSource = mainDataSource;
    }

    public static MainRepository getInstance(MainDataSource mainDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new MainRepository(mainDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getData(final LoadDataCallback callback) {
        mMainDataSource.getData(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {
                //refreshCache(tasks);
                callback.onDataLoaded(tasks);
            }
        });
    }
}
