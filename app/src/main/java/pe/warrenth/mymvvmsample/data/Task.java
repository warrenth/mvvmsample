package pe.warrenth.mymvvmsample.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Entity: database의 row와 mapping되는 class, 즉 table의 구조를 나타내는데
 * Database에서 entities함수를 통해 접근할 수 있다.
 *
 * @Ignore를 붙이지 않는한 DB에 지속적으로 유지된다.
 *
 * entity는 empty 생성자나, 부분 field값만 같는 생성자, full field에 대한 생성자 모두를 가질 수 잇다.
 *
 * @Database의 annotation에 속성으로 포함된 entitiy는 실제 @Entity annotation을 붙인
 * class로 만들어야 한다. 이는 각 table로 생성되며, 실제 column으로 만들고 싶지 않은
 * field가 있다면 @Ignore 를 붙인다.
 * Field는 public 형태이어야 하면 private으로 할경우
 *  getter와 setter를 java beans conventions에 따라 만들어야 한다.
 *
 *  https://tourspace.tistory.com/28
 */
@Entity(tableName = "tasks")
public final class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name ="entryid")
    private  String id;

    @NonNull
    @ColumnInfo(name ="title")
    private  String mTitle;

    @NonNull
    @ColumnInfo(name ="description")
    private  String mDescription;

    @Ignore
    public Task(String title, String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    public Task(String id, String mTitle, String mDescription) {
        this.id = id;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }
    @Nullable
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }
    @Nullable
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
                Strings.isNullOrEmpty(mDescription);
    }
}
