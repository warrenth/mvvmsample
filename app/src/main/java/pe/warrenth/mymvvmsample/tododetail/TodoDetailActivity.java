package pe.warrenth.mymvvmsample.tododetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pe.warrenth.mymvvmsample.R;

public class TodoDetailActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final String EXTRA_TASK_ID = "TASK_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tododetail);


    }
}
