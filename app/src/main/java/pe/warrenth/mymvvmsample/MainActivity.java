package pe.warrenth.mymvvmsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_VIEWMODEL_TAG = "MAIN_VIEWMODEL_TAG";
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment와 ViewModel 을 각각 생성하여 FragmentManager에 각각 넣는다.
        MainFragment mainFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();

        // Fragment에 ViewModel 인스턴스를 넘긴다.
        mainFragment.setViewModel(mViewModel);
    }

    private MainViewModel findOrCreateViewModel() {
        ViewModelHolder<MainViewModel> retainedViewModel =
                (ViewModelHolder<MainViewModel>) getSupportFragmentManager().findFragmentByTag(MAIN_VIEWMODEL_TAG);

        if(retainedViewModel != null && retainedViewModel.getView() != null) {
            return retainedViewModel.getViewmodel();
        } else {
            MainViewModel viewModel = new MainViewModel(
                    MainRepository.getInstance(MainLocalDataSource.getInstance()),
                    getApplicationContext());

            // Activity의 라이프사이클을 사용하기 위해 FragmentManager를 사용.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MAIN_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    private MainFragment findOrCreateViewFragment() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }

        return mainFragment;
    }

    @Override
    protected void onDestroy() {
        mViewModel.onActivityDestroyed();
        super.onDestroy();
    }
}
