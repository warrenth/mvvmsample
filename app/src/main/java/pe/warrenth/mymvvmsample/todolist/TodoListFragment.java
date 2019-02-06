package pe.warrenth.mymvvmsample.todolist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.data.Task;
import pe.warrenth.mymvvmsample.data.TodoRepository;
import pe.warrenth.mymvvmsample.data.remote.TodoRemoteDataSource;
import pe.warrenth.mymvvmsample.databinding.FragmentTodoListBinding;
import pe.warrenth.mymvvmsample.databinding.TodoListItemBinding;


public class TodoListFragment extends Fragment {

    private TodoListViewModel mViewModel;

    private FragmentTodoListBinding mFragmentTodoBinding;

    private MainListAdapter mListAdapter;

    public static TodoListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(TodoListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //자동으로 Binding 클래스 생성.  (xml 이름 + Binding)

        mFragmentTodoBinding = FragmentTodoListBinding.inflate(inflater, container, false);

        //xml에 정의된 view, viewmodel 에 주입. 자동생성된 함수.
        mFragmentTodoBinding.setView(this);
        mFragmentTodoBinding.setViewmodel(mViewModel);

        return mFragmentTodoBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();
        setupListAdapter();
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.addTodo();
            }
        });
    }

    private void setupListAdapter() {
        ListView listView =  mFragmentTodoBinding.tasksList;

        mListAdapter = new MainListAdapter(
                new ArrayList<Task>(0),
                TodoRepository.getInstance(TodoRemoteDataSource.getInstance()),
                mViewModel,
                (TodoListItemNavigator) getActivity());
        listView.setAdapter(mListAdapter);
    }

    @Override
    public void onDestroy() {
        mListAdapter.onDestory();
        super.onDestroy();
    }

    public static class MainListAdapter extends BaseAdapter {

        private final TodoListViewModel mMainViewModel;
        private List<Task> mTasks;
        private TodoRepository mTasksRepository;
        private TodoListItemNavigator mTodoListItemNavigator;

        public MainListAdapter(ArrayList<Task> tasks, TodoRepository repository,
                               TodoListViewModel viewModel, TodoListItemNavigator todoListItemNavigator) {
            mTasks = tasks;
            mTasksRepository = repository;
            mMainViewModel = viewModel;
            mTodoListItemNavigator = todoListItemNavigator;
        }

        @Override
        public int getCount() {
            return mTasks != null  ? mTasks.size() : 0;
        }

        @Override
        public Task getItem(int i) {
            return mTasks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Task task = getItem(i);
            TodoListItemBinding binding;
            if( view == null) {
                binding = TodoListItemBinding.inflate(
                        LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            } else  {
                binding = DataBindingUtil.getBinding(view);
            }

            final TodoListItemViewModel viewModel = new TodoListItemViewModel(
                    mTasksRepository,
                    viewGroup.getContext().getApplicationContext()
            );

            viewModel.setNavigator(mTodoListItemNavigator);

            binding.setViewmodel(viewModel);

            viewModel.setTask(task);

            return binding.getRoot();
        }

        public void onDestory() {
            //interface를 null 한다.
            mTodoListItemNavigator = null;
        }

        public void replaceData(List<Task> items) {
            mTasks = items;
            notifyDataSetChanged();
        }
    }
}
