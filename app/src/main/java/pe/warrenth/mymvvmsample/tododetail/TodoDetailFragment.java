package pe.warrenth.mymvvmsample.tododetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.databinding.FragmentTodoDetailBinding;

public class TodoDetailFragment extends Fragment {

    public static final String ARGUMENT_TASK_ID = "TASK_ID";

    public static final int REQUEST_EDIT_TASK = 1;

    private TodoDetailViewModel mViewModel;

    public static TodoDetailFragment newInstance(String taskId) {

        Bundle args = new Bundle();
        args.putString(ARGUMENT_TASK_ID, taskId);
        TodoDetailFragment fragment = new TodoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(TodoDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        FragmentTodoDetailBinding todoDetailBinding = FragmentTodoDetailBinding.bind(view);
        todoDetailBinding.setViewmodel(mViewModel);

        return todoDetailBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start(getArguments().getString(ARGUMENT_TASK_ID));
    }
}
