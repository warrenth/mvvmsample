package pe.warrenth.mymvvmsample.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pe.warrenth.mymvvmsample.ActivityUtils;
import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.ViewModelHolder;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.data.local.TodoLocalDataSource;
import pe.warrenth.mymvvmsample.tododetail.TodoDetailActivity;

public class MainActivity extends AppCompatActivity implements TodoListNavigator {

    public static final String MAIN_VIEWMODEL_TAG = "MAIN_VIEWMODEL_TAG";
    private TodoViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //잔디테스트!!!

        // Fragment와 ViewModel 을 각각 생성하여 FragmentManager에 각각 넣는다.
        TodoFragment todoFragment = findOrCreateViewFragment();

        //Activity에서 ViewModel을 생성하고, 생성된걸 fragment에 넘김. 그리고 MainActivity와 listner(Navigator) 로 연결
        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigator(this);

        // Fragment에 ViewModel 인스턴스를 넘긴다.
        todoFragment.setViewModel(mViewModel);
    }

    private TodoViewModel findOrCreateViewModel() {
        ViewModelHolder<TodoViewModel> retainedViewModel =
                (ViewModelHolder<TodoViewModel>) getSupportFragmentManager().findFragmentByTag(MAIN_VIEWMODEL_TAG);

        if(retainedViewModel != null && retainedViewModel.getView() != null) {
            return retainedViewModel.getViewmodel();
        } else {
            TodoViewModel viewModel = new TodoViewModel(
                    TodoRepository.getInstance(TodoLocalDataSource.getInstance()),
                    getApplicationContext());

            // Activity의 라이프사이클을 사용하기 위해 FragmentManager를 사용.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MAIN_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    private TodoFragment findOrCreateViewFragment() {
        TodoFragment todoFragment = (TodoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(todoFragment == null) {
            todoFragment = TodoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), todoFragment, R.id.contentFrame);
        }

        return todoFragment;
    }

    @Override
    protected void onDestroy() {
        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    @Override
    public void addTodo() {
        startActivityForResult(new Intent(this, TodoDetailActivity.class), TodoDetailActivity.REQUEST_CODE);
    }
}
