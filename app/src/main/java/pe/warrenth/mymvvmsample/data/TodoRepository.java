package pe.warrenth.mymvvmsample.data;

import java.util.List;


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
    public void saveTask(Task newTask) {
        mTodoDataSource.saveTask(newTask);
    }

    @Override
    public void getTasks(final GetTasksCallback callback) {
        mTodoDataSource.getTasks(new GetTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                //refreshCache(tasks);
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }


    @Override
    public void getTask(String taskId, final GetTaskCallback callback) {
        mTodoDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
