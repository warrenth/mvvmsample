package pe.warrenth.mymvvmsample.todoedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.warrenth.mymvvmsample.R;
import pe.warrenth.mymvvmsample.databinding.FragmentTodoEditBinding;

public class AddEditTaskFragment extends Fragment {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    private AddEditTaskViewModel mViewModel;
    private FragmentTodoEditBinding mFragmentTodoEditBinding;

    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    public void setViewModel(AddEditTaskViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_todo_edit, container, false);
        if(mFragmentTodoEditBinding == null) {
            mFragmentTodoEditBinding = FragmentTodoEditBinding.bind(root);
        }
        mFragmentTodoEditBinding.setViewmodel(mViewModel);

        return mFragmentTodoEditBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();
    }

    private void setupFab() {
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.saveTask();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() != null) {
            mViewModel.start(getArguments().getString(ARGUMENT_EDIT_TASK_ID));
        } else  {
            mViewModel.start(null);
        }
    }
}
