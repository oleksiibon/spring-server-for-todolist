package letscode.todolistSpring.controller;

import letscode.todolistSpring.domain.Task;
import letscode.todolistSpring.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortById implements Comparator<Task>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Task a, Task b)
    {
        if (+a.getId() > +b.getId()){
            return 1;
        }
        return  -1;
    }
}
@RestController
@RequestMapping("tasks")
@CrossOrigin
public class TaskController {
    @Autowired
    private final TaskRepo taskRepo;

    public TaskController(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @GetMapping()
    public List<Task> getUnDoneLists(
            @RequestParam(name = "isDone", required = false) Boolean isDone,
            @RequestParam(name = "listId", required = false) Long listId
    ) {
        List<Task> tasks;
        if (isDone != null) {
            tasks = taskRepo.findByDone(isDone);
        } else if (listId != null) {
            tasks = taskRepo.findByListId(listId);
        } else {
            tasks = taskRepo.findAll();
        }
        tasks.sort(Comparator.comparingLong(Task::getId));
        return tasks;

    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskRepo.save(task);
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
