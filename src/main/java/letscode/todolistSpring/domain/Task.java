package letscode.todolistSpring.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import letscode.todolistSpring.domain.List;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean done;
    @ManyToOne()
    @JoinColumn(name = "list_id")
    @JsonIgnore
    private List list;

    public Task() {
    }

    public Task(String name, Boolean done, List list) {
        this.name = name;
        this.done = done;
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
