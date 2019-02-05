package pe.warrenth.mymvvmsample.todoedit;

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

public class AddEditTaskActivity extends AppCompatActivity implements AddEditTaskNavigator{

    public static final String ADD_EDIT_VIEWMODEL_TAG = "ADD_EDIT_VIEWMODEL_TAG";
    public static final int ADD_EDIT_RESULT_OK = RESULT_FIRST_USER + 1;

    public static final int REQUEST_CODE = 0;

    private AddEditTaskViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        AddEditTaskFragment addEditTaskFragment = findOrCreateViewFragment();

        mViewModel = findOrCreateViewModel();

        addEditTaskFragment.setViewModel(mViewModel);

        mViewModel.onActivityCreated(this);
    }

    @Override
    protected void onDestroy() {
        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    private AddEditTaskViewModel findOrCreateViewModel() {

        ViewModelHolder<AddEditTaskViewModel> retainedViewModel =
                (ViewModelHolder<AddEditTaskViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(ADD_EDIT_VIEWMODEL_TAG);

        if(retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            return retainedViewModel.getViewmodel();
        } else  {
            AddEditTaskViewModel viewModel = new AddEditTaskViewModel(
                    getApplicationContext(),
                    TodoRepository.getInstance(new TodoLocalDataSource(new AppExecutors(),
                            TodoDatabase.getInstance(this).taskDao())));

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    ADD_EDIT_VIEWMODEL_TAG);

            return viewModel;
        }
    }

    private AddEditTaskFragment findOrCreateViewFragment() {

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if(addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID,
                    getIntent().getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID));
            addEditTaskFragment.setArguments(bundle);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTaskFragment, R.id.contentFrame);
        }
        return addEditTaskFragment;
    }

    @Override
    public void onTaskSaved() {
        setResult(ADD_EDIT_RESULT_OK);
        finish();
    }
}
