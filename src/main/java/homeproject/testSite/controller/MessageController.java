package homeproject.testSite.controller;
import homeproject.testSite.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add (new HashMap<String, String>() {{put("id", "1"); put("text", "first Message");}});
        add (new HashMap<String, String>() {{put("id", "2"); put("text", "second Message");}});
        add (new HashMap<String, String>() {{put("id", "3"); put("text", "third Message");}});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getMessage(@PathVariable String id) {
        return getMessageForId(id);
    }



    @PostMapping
    public Map<String, String> createMassage(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> updateMassage(@PathVariable String id, @RequestBody Map<String, String> message){
        Map<String, String> messageFromDb = getMessageForId(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable String id){
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }

    private Map<String, String> getMessageForId(@PathVariable String id) {
        return messages.stream().filter(message -> message.get("id").equals(id))
                .findFirst().orElseThrow(NotFoundException::new);
    }
}
