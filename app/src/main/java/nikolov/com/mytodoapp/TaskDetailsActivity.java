package nikolov.com.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class TaskDetailsActivity extends Activity {
    private FirebaseFirestore mDb;
    private TaskDetailsFragment mTaskDetailsFragment;
    private String taskName;
    private String endDate;
    private String priority;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        taskName = intent.getStringExtra("TASK_NAME");
        mDb = FirebaseFirestore.getInstance();
        mTaskDetailsFragment = TaskDetailsFragment.getInstance(taskName);
        mTaskDetailsFragment.setTaskName(taskName);
       // mTaskDetailsFragment.createView();
        DocumentReference docRef = mDb.collection("todos").document(taskName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        endDate = document.getString("endDate");
                        priority = document.getString("priority");
                    }
                }
            }
        });

        getFragmentManager().
                beginTransaction()
                .replace(R.id.details_content, mTaskDetailsFragment)
                .commit();
    }


    public void markAsDone(View button){
        status = "completed";
        Map<String, Object> current = new HashMap<>();
        current.put("name", taskName);
        current.put("endDate", endDate);
        current.put("priority", priority);
        current.put("status", status);

        mDb.collection("todos").document(taskName)
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
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
