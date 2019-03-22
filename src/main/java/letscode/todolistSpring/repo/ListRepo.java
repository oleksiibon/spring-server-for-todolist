package letscode.todolistSpring.repo;

import letscode.todolistSpring.domain.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ListRepo extends JpaRepository<List, Long> {
    @Query(value = "SELECT * FROM list\n" +
            "INNER JOIN task t on list.id = t.list_id\n" +
            "WHERE t.done = FALSE", nativeQuery = true)
    java.util.List<List> getAllAndIsDone(@Param("done") Boolean done);
}

