package pe.warrenth.mymvvmsample.data;

import java.util.List;

import pe.warrenth.mymvvmsample.Task;


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
public class TodoRepository implements TodoDataSource {

    private static TodoRepository INSTANCE = null;

    private final TodoDataSource mTodoDataSource;

    private TodoRepository(TodoDataSource todoDataSource) {
        mTodoDataSource = todoDataSource;
    }

    public static TodoRepository getInstance(TodoDataSource todoDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new TodoRepository(todoDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getData(final LoadDataCallback callback) {
        mTodoDataSource.getData(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {
                //refreshCache(tasks);
                callback.onDataLoaded(tasks);
            }
        });
    }
}
