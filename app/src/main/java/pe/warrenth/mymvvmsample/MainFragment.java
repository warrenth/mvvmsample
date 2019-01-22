package pe.warrenth.mymvvmsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pe.warrenth.mymvvmsample.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    private FragmentMainBinding mFragmentMainBinding;

    private MainListAdapter mListAdapter;

    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(MainViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //자동으로 Binding 클래스 생성.  (xml 이름 + Binding)
        mFragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false);

        //xml에 정의된 view, viewmodel 에 주입.
        mFragmentMainBinding.setView(this);
        mFragmentMainBinding.setViewmodel(mViewModel);

        return mFragmentMainBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  mFragmentMainBinding.tasksList;

        mListAdapter = new MainListAdapter(
                new ArrayList<Task>(0),
                MainRepository.getInstance(MainRemoteDataSource.getInstance()),
                mViewModel);
        listView.setAdapter(mListAdapter);
    }

    @Override
    public void onDestroy() {
        mListAdapter.onDestory();
        super.onDestroy();
    }

    public static class MainListAdapter extends BaseAdapter {

        private final MainViewModel mMainViewModel;
        private List<Task> mTasks;
        private MainRepository mTasksRepository;


        public MainListAdapter(ArrayList<Task> tasks, MainRepository repository, MainViewModel viewModel) {
            mTasks = tasks;
            mTasksRepository = repository;
            mMainViewModel = viewModel;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        public void onDestory() {
            //interface를 null 한다.
        }
    }
}
