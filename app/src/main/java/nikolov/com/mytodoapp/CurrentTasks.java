package nikolov.com.mytodoapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;

import nikolov.com.mytodoapp.models.Todo;
import nikolov.com.mytodoapp.repositories.base.Repository;

public class CurrentTasks extends Fragment implements AdapterView.OnItemClickListener {
    private FirebaseFirestore mDb;
    private ListView mCurrentList;
    private ArrayAdapter<String> mCurrentAdapter;
    private Repository<Todo> mTodoRepository;

    public CurrentTasks(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.current_tasks, container, false);

        mCurrentList = rootView.findViewById(R.id.current_list);
        mCurrentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        mCurrentList.setAdapter(mCurrentAdapter);
        mCurrentList.setOnItemClickListener(this);

        mTodoRepository = Application.getmTodoRepository();

        mTodoRepository.getAll(todos -> {
            for (Todo todo: todos){
                if (todo.status.equals("current")) {
                    mCurrentAdapter.add(todo.name);
                }
            }
        });

        return rootView;
    }

    public static CurrentTasks getInstance(){
        return new CurrentTasks();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
