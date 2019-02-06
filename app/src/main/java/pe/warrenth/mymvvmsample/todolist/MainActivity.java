package pe.warrenth.mymvvmsample.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pe.warrenth.mymvvmsample.ActivityUtils;
import pe.warrenth.mymvvmsample.AppExecutors;
import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.ViewModelHolder;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.data.local.TodoDatabase;
import pe.warrenth.mymvvmsample.data.local.TodoLocalDataSource;
import pe.warrenth.mymvvmsample.tododetail.TodoDetailActivity;
import pe.warrenth.mymvvmsample.todoedit.AddEditTaskActivity;

public class MainActivity extends AppCompatActivity implements TodoListNavigator, TodoListItemNavigator {

    public static final String MAIN_VIEWMODEL_TAG = "MAIN_VIEWMODEL_TAG";
    private TodoListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //잔디테스트!!!

        // Fragment와 ViewModel 을 각각 생성하여 FragmentManager에 각각 넣는다.
        TodoListFragment todoListFragment = findOrCreateViewFragment();

        //Activity에서 ViewModel을 생성하고, 생성된걸 fragment에 넘김. 그리고 MainActivity와 listner(Navigator) 로 연결
        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigator(this);

        // Fragment에 ViewModel 인스턴스를 넘긴다.
        todoListFragment.setViewModel(mViewModel);
    }

    private TodoListViewModel findOrCreateViewModel() {
        ViewModelHolder<TodoListViewModel> retainedViewModel =
                (ViewModelHolder<TodoListViewModel>) getSupportFragmentManager().findFragmentByTag(MAIN_VIEWMODEL_TAG);

        if(retainedViewModel != null && retainedViewModel.getView() != null) {
            return retainedViewModel.getViewmodel();
        } else {
            TodoListViewModel viewModel = new TodoListViewModel(
                    TodoRepository.getInstance(TodoLocalDataSource.getInstance(
                            new AppExecutors(), TodoDatabase.getInstance(this).taskDao())),
                    getApplicationContext());

            // Activity의 라이프사이클을 사용하기 위해 FragmentManager를 사용.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MAIN_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    private TodoListFragment findOrCreateViewFragment() {
        TodoListFragment todoListFragment = (TodoListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(todoListFragment == null) {
            todoListFragment = TodoListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), todoListFragment, R.id.contentFrame);
        }

        return todoListFragment;
    }

    @Override
    protected void onDestroy() {
        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    @Override
    public void addNewTodo() {
        startActivityForResult(new Intent(this, AddEditTaskActivity.class), AddEditTaskActivity.REQUEST_CODE);
    }

    @Override
    public void openTodoDetail(String taskId) {
        Intent intent = new Intent(this, TodoDetailActivity.class);
        intent.putExtra(TodoDetailActivity.EXTRA_TASK_ID, taskId);
        startActivity(intent);
    }
}
