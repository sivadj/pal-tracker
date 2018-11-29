package io.pivotal.pal.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {


    Map<Long, TimeEntry> repository= new HashMap<Long, TimeEntry>();
    long id = 1L;
    @Autowired
    ObjectMapper jsonObjectMapper;
    @Override
    public TimeEntry create(TimeEntry any) {
        any.setId(id);
        repository.put(id,any);
        id++;
        return repository.get(any.getId());
    }

    @Override
    public TimeEntry find(long timeEntryId) {

         return repository.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {

            Collection<TimeEntry> allentries = repository.values();
        ArrayList<TimeEntry> al = new ArrayList();
        for(TimeEntry re:allentries) {
            al.add(re);
        }
        return al;
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {

        any.setId(eq);
        repository.put(eq,any);
       return any;
    }

    @Override
    public TimeEntry delete(long timeEntryId) {
        TimeEntry re= repository.remove(timeEntryId);
        return re;
    }
}


