package nikolov.com.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import nikolov.com.mytodoapp.models.Todo;

import static android.support.constraint.Constraints.TAG;

public class AddTaskActivity extends Activity {
    private AddTaskFragment mAddTaskFragment;
    private EditText nameInput;
    private EditText endDateInput;
    private Spinner priorityInput;
    private String name;
    private String endDate;
    private String priority;
    private final String STATUS = "current";
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mAddTaskFragment = AddTaskFragment.getInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.new_content, mAddTaskFragment)
                .commit();
    }

    public void createTask(View button){
        mDb = FirebaseFirestore.getInstance();
        nameInput = (EditText) findViewById(R.id.new_task_name);
        endDateInput = (EditText) findViewById(R.id.new_task_end_date);
        priorityInput = (Spinner) findViewById(R.id.new_task_priority);
        name = nameInput.getText().toString();
        endDate = endDateInput.getText().toString();
        priority = priorityInput.getSelectedItem().toString();

        Map<String, Object> current = new HashMap<>();
        current.put("name", name);
        current.put("endDate", endDate);
        current.put("priority", priority);
        current.put("status", STATUS);
        mDb.collection("todos").document(name)
                .set(current)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot succesfully writen");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writting document", e);
                    }
                });
        Intent intent = new Intent(button.getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
