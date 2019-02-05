package pe.warrenth.mymvvmsample.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pe.warrenth.mymvvmsample.data.Task;

/**
 * The Room Database that contains the Task table.
 *
 * Database: database의 holder를 만든다. annotation으로 entities(Table의 구조와 mapping되는 class)를
 * 정의하고, 클래스 내부에 dao를 정의한다.
 *
 * 이 클래스는 RoomDatabase를 상속받은 abstract class가 되며 Room.databaseBuilder() or
 * Room.inMemoryDatabaseBuilder() 를 이용하여 얻을 수 있다.
 *
 *  Dao는 cursor를 자동으로 class로 converting 해준다.
 * Room은 DAO에 정의된 query 확인을 compile time에 즉각 해준다.
 *
 */
@Database(entities = {Task.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase INSTANCE;

    public abstract TasksDao taskDao();

    private static final Object sLock = new Object();

    public static TodoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TodoDatabase.class, "Tasks.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}

