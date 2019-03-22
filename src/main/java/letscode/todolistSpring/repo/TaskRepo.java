package letscode.todolistSpring.repo;

import letscode.todolistSpring.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByList(letscode.todolistSpring.domain.List list);

    List<Task> findByDone(Boolean isDone);

    List<Task> findAllByDoneIsFalse();
}
