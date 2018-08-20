package nikolov.com.mytodoapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import nikolov.com.mytodoapp.models.Todo;
import nikolov.com.mytodoapp.repositories.base.Repository;

public class AllTasks extends Fragment implements AdapterView.OnItemClickListener{
    private ListView mAllCurrentList;
    private ListView mAllCompletedList;
    private ArrayAdapter<String> mAllCurrentAdapter;
    private ArrayAdapter<String> mAllCompletedAdapter;
    private Repository<Todo> mTodoRepository;

    public AllTasks(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_tasks, container, false);

        mAllCurrentList = rootView.findViewById(R.id.all_current_list);
        mAllCurrentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        mAllCompletedList = rootView.findViewById(R.id.all__completed_list);
        mAllCompletedAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        mAllCurrentList.setAdapter(mAllCurrentAdapter);
        mAllCompletedList.setAdapter(mAllCompletedAdapter);
        mAllCurrentList.setOnItemClickListener(this);
        mAllCompletedList.setOnItemClickListener(this);


        mTodoRepository = Application.getmTodoRepository();

        mTodoRepository.getAll(todos -> {
            for (Todo todo: todos) {
                if (todo.status.equals("current")) {
                    mAllCurrentAdapter.add(todo.name);
                } else if (todo.status.equals("completed")){
                    mAllCompletedAdapter.add(todo.name);
                }
            }
        });

        return rootView;
    }

    public static AllTasks getInstance(){
        return new AllTasks();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
