package pe.warrenth.mymvvmsample.tododetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import pe.warrenth.mymvvmsample.TodoBaseViewModel;
import pe.warrenth.mymvvmsample.data.TodoRepository;

public class TodoDetailViewModel extends TodoBaseViewModel {


    public TodoDetailViewModel(Context context, TodoRepository repository) {
        super(repository, context);
    }

}
