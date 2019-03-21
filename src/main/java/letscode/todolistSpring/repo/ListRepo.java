package letscode.todolistSpring.repo;

import letscode.todolistSpring.domain.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListRepo extends JpaRepository<List, Long> {
}
