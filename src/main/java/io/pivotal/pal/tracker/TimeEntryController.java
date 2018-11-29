package io.pivotal.pal.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }


    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry te = timeEntryRepository.create(timeEntryToCreate);
       return new ResponseEntity(te, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry=timeEntryRepository.find(timeEntryId);
        if (timeEntry!=null)
        return new ResponseEntity(timeEntry,HttpStatus.OK);
        else
            return new ResponseEntity(timeEntry,HttpStatus.NOT_FOUND);
    }


    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity(timeEntryRepository.list(),HttpStatus.OK);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry entry) {

        TimeEntry timeEntry=timeEntryRepository.update(timeEntryId, entry);
        if (timeEntry!=null)
            return new ResponseEntity(timeEntry,HttpStatus.OK);
        else
            return new ResponseEntity(timeEntry,HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        TimeEntry timeEntry=timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(timeEntry, HttpStatus.NO_CONTENT);

       // return new ResponseEntity(timeEntryRepository.delete(timeEntryId),HttpStatus.OK);

    }
}



