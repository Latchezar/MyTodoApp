package nikolov.com.mytodoapp;

import nikolov.com.mytodoapp.models.Todo;
import nikolov.com.mytodoapp.repositories.FirebaseRepository;
import nikolov.com.mytodoapp.repositories.base.Repository;

public class Application {

    public static Repository<Todo> mTodoRepository;

    public static Repository<Todo> getmTodoRepository() {
        if (mTodoRepository == null) {
            mTodoRepository = new FirebaseRepository<>(Todo.class);
        }
        return mTodoRepository;
    }
}
