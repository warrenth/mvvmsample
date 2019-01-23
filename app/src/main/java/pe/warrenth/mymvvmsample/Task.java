package pe.warrenth.mymvvmsample;

import android.support.annotation.Nullable;

import com.google.common.base.Strings;

public class Task {

    private String id;

    private String mTitle;

    private String mDescription;

    public Task(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }
}
