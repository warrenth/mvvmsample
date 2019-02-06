package pe.warrenth.mymvvmsample.tododetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pe.warrenth.mymvvmsample.ActivityUtils;
import pe.warrenth.mymvvmsample.AppExecutors;
import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.ViewModelHolder;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.data.local.TodoDatabase;
import pe.warrenth.mymvvmsample.data.local.TodoLocalDataSource;

public class TodoDetailActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final String TASKDETAIL_VIEWMODEL_TAG = "TASKDETAIL_VIEWMODEL_TAG";

    public static final String EXTRA_TASK_ID = "TASK_ID";

    private TodoDetailViewModel mTodoDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tododetail);

        TodoDetailFragment todoDetailFragment = findOrCreateViewFragment();

        mTodoDetailViewModel = findOrCreateViewModel();

        todoDetailFragment.setViewModel(mTodoDetailViewModel);
    }

    private TodoDetailViewModel findOrCreateViewModel() {
        ViewModelHolder<TodoDetailViewModel> retainedViewModel =
                (ViewModelHolder<TodoDetailViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(TASKDETAIL_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            TodoDetailViewModel viewModel = new TodoDetailViewModel(
                    getApplicationContext(),
                    TodoRepository.getInstance(new TodoLocalDataSource(new AppExecutors(),
                            TodoDatabase.getInstance(this).taskDao())));

            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    TASKDETAIL_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    private TodoDetailFragment findOrCreateViewFragment() {

        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        TodoDetailFragment todoDetailFragment = (TodoDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if(todoDetailFragment == null) {
            todoDetailFragment = TodoDetailFragment.newInstance(taskId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    todoDetailFragment, R.id.contentFrame);
        }

        return todoDetailFragment;
    }
}
