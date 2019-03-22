package letscode.todolistSpring.controller;

import letscode.todolistSpring.domain.Task;
import letscode.todolistSpring.domain.ClientTask;
import letscode.todolistSpring.repo.ListRepo;
import letscode.todolistSpring.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks")
@CrossOrigin
public class TaskController {
    @Autowired
    private final TaskRepo taskRepo;
    @Autowired
    private final ListRepo listRepo;

    public TaskController(TaskRepo taskRepo, ListRepo listRepo) {
        this.taskRepo = taskRepo;
        this.listRepo = listRepo;
    }

    @GetMapping()
    public List<ClientTask> getUnDoneLists(
            @RequestParam(name = "isDone", required = false) Boolean isDone,
            @RequestParam(name = "listId", required = false) Long listId
    ) {
        List<Task> tasks;
        if (isDone != null) {
            tasks = taskRepo.findByDone(isDone);
        } else if (listId != null) {
            letscode.todolistSpring.domain.List list = listRepo.findById(listId).orElse(null);
            tasks = taskRepo.findByList(list);
        } else {
            tasks = taskRepo.findAll();
        }
        tasks.sort(Comparator.comparingLong(Task::getId));
        return ClientTask.of(tasks);

    }

    @GetMapping("/preview")
    public Set<letscode.todolistSpring.domain.List> join() {
        Map<letscode.todolistSpring.domain.List, List<Task>> collect = taskRepo.findAllByDoneIsFalse().stream().collect(Collectors.groupingBy(Task::getList));
        collect.forEach((list, tasks) -> {
            list.tasks.clear();
            list.tasks.addAll(tasks);
        });
        return collect.keySet();
    }

    @PostMapping
    public Task create(@RequestBody ClientTask task) {
        letscode.todolistSpring.domain.List list = listRepo.findById(task.listId).orElse(null);
        return taskRepo.save(new Task(task.name, task.done, list));
    }

    @PatchMapping("{id}")
    public Task update(@PathVariable("id") Task taskFromDB, @RequestBody Task newTask) {
        if (newTask.isDone() != null) {
            taskFromDB.setDone(newTask.isDone());
        }
        if (newTask.getName() != null) {
            taskFromDB.setName(newTask.getName());
        }
        return taskRepo.save(taskFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Task task) {
        taskRepo.delete(task);
    }

}
