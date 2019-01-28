package pe.warrenth.mymvvmsample;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import java.util.List;

public class TasksListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Task> items) {
        MainFragment.MainListAdapter adapter = (MainFragment.MainListAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}
