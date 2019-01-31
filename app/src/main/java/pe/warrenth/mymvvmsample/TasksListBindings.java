package pe.warrenth.mymvvmsample;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import java.util.List;

import pe.warrenth.mymvvmsample.todolist.TodoFragment;
import pe.warrenth.mymvvmsample.todolist.TodoViewModel;

public class TasksListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Task> items) {
        TodoFragment.MainListAdapter adapter = (TodoFragment.MainListAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter("android:onRefresh")
    public static void setRefreshLayout(SwipeRefreshLayout view, final TodoViewModel viewModel) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.start();
            }
        });
        //TODO 정의된 뷰모델 외에는 사용 불가!!!
    }
}
