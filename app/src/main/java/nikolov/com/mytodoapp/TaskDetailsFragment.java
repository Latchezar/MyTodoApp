package nikolov.com.mytodoapp;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailsFragment extends Fragment {
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private String mTaskName;
    private String mTaskEndDate;
    private String mTaskPriority;
    private String mStatus;
    private TextView mTaskNameView;
    private TextView mTaskEndDateView;
    private TextView mTaskPriorityView;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_details, container, false);
        mTaskNameView = view.findViewById(R.id.task_name);
        mTaskEndDateView = view.findViewById(R.id.task_end_date);
        mTaskPriorityView = view.findViewById(R.id.task_priority);
        createView();
        return view;
    }

    public void createView() {
        View view = getView();

        //read from firestore
        DocumentReference docRef = mDb.collection("todos").document(mTaskName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mTaskEndDate = document.getString("endDate");
                        mTaskPriority = document.getString("priority");
                        mStatus = document.getString("status");
                    }
                }
            }
        });
        //end read from firestore

        mTaskNameView.setText(mTaskName);
        mTaskEndDateView.setText(mTaskEndDate);
        mTaskPriorityView.setText(mTaskPriority);
    }

    public void setTaskName(String taskName){
        mTaskName = taskName;
    }

    public static TaskDetailsFragment getInstance(String taskName){
        TaskDetailsFragment current = new TaskDetailsFragment();

        Bundle args = new Bundle();
        args.putString("taskName", taskName);
        current.setArguments(args);

        return current;
    }
}
