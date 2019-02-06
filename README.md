#MVVM DataBinding, ROOM 테스트
============

ViewModel은 UI를 업데이트하기 위해 XML 에 직접 액세스 할 수 없으므로, 
ViewModel이 View를 업데이트 할 수 없다.

데이터바인딩을 이용해, Layout 파일을 사용해서 ViewModel 에서 관찰 가능 필드를 
View, ViewGroup 에 바인딩 하면, View와 ViewModel이 양방향으로 동기화 시킨다.

Observable 객체를 사용하는 방법
=====

> ##### ObservableList<Task> 를 이용해서 ListView 에 데이터를 바로 set 하는방법
<pre><code>
public final ObservableList<Task> items = new ObservableArrayList<>();
items.addAll(tasks);

/ListView/
android:id="@+id/tasks_list"
app:items="@{viewmodel.items}"
android:layout_width="match_parent"
android:layout_height="wrap_content"
/ListView/


 @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Task> items) {
        TodoListFragment.MainListAdapter adapter = (TodoListFragment.MainListAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
 
</code></pre>
xml에 viewmodel의 items 를 Observable로 Binding 시키고, BindingAdaper 를 이용해 
ListView, items의 매개변수를 받아 Adapter 에 set 시킨다.



 ##### ObservableField<Task> 와 addOnPropertyChangedCallback 를 이용해서 title, description을 업데이트 하는 방법 
void addOnPropertyChangedCallback (Observable.OnPropertyChangedCallback callback)
<pre><code>
private final ObservableField<Task> mTaskObservable = new ObservableField<>();

mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Task task = mTaskObservable.get();
                if (task != null) {
                    title.set(task.getTitle());
                    description.set(task.getDescription());
                }
            }
        });
</code></pre>
Observable에 대한 변경 사항을 수신대기하는 콜백을 등록.

<pre><code>
void notifyChange ()

mTaskObservable.set(task);
notifyChange();
</pre></code>
이 인스턴스의 모든속성이 변경된 것을 리스너에게 알린다.



