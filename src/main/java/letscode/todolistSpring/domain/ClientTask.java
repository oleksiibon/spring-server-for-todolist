package letscode.todolistSpring.domain;

import java.util.stream.Collectors;

public class ClientTask {
    public Long id;
    public String name;
    public Boolean done;
    public Long listId;

    public ClientTask() {
    }

    public ClientTask(Long id, String name, Boolean done, Long listId) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.listId = listId;
    }

    public static java.util.List<ClientTask> of(java.util.List<Task> tasks) {
        return tasks.stream().map(el -> new ClientTask(el.getId(), el.getName(), el.isDone(), el.getList().getId())).collect(Collectors.toList());
    }
}
