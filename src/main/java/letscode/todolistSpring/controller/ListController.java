package letscode.todolistSpring.controller;

import letscode.todolistSpring.domain.List;
import letscode.todolistSpring.repo.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

@RestController
@RequestMapping("lists")
@CrossOrigin
public class ListController {
    @Autowired
    private final ListRepo listRepo;

    public ListController(ListRepo listRepo) {
        this.listRepo = listRepo;
    }
    @GetMapping
    public java.util.List<List> lists() {
        java.util.List<List> lists = listRepo.findAll();
        lists.sort(Comparator.comparingLong(List::getId));
        return lists;
    }
    @PostMapping
    public List create(@RequestBody List list) {
        return listRepo.save(list);
    }
    @PatchMapping("{id}")
    public List update(@PathVariable("id") List listFromDB, @RequestBody List list) {
        if (list.isPin() != null) {
            listFromDB.setPin(list.isPin());
        }
        if (list.getName() != null) {
            listFromDB.setName(list.getName());
        }
        return listRepo.save(listFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") List list) {
        listRepo.delete(list);
    }
}
