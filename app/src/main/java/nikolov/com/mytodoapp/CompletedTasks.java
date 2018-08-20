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


public class CompletedTasks extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mCompletedList;
    private ArrayAdapter<String> mCompletedAdapter;
    private Repository<Todo> mTodoRepository;

    public CompletedTasks(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.completed_tasks, container, false);

        mCompletedList = rootView.findViewById(R.id.completed_list);
        mCompletedAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        mCompletedList.setAdapter(mCompletedAdapter);
        mCompletedList.setOnItemClickListener(this);

        mTodoRepository = Application.getmTodoRepository();

        mTodoRepository.getAll(todos -> {
            for (Todo todo: todos) {
                if (todo.status.equals("completed")){
                    mCompletedAdapter.add(todo.name);
                }
            }
        });

        return rootView;
    }

    public static CompletedTasks getInstance(){
        return new CompletedTasks();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
