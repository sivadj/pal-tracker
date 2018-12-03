package io.pivotal.pal.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.Counter;

import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;
    private final CounterService counterService;
    private final GaugeService gaugeService;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counterService, GaugeService gaugeService) {
        this.timeEntryRepository = timeEntryRepository;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
   }


    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry te = timeEntryRepository.create(timeEntryToCreate);
        counterService.increment("timeEntry.created");
        gaugeService.submit("timeEntries.count",timeEntryRepository.list().size());
       return new ResponseEntity(te, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry=timeEntryRepository.find(timeEntryId);
        counterService.increment("timeEntry.read");
        if (timeEntry!=null)
        return new ResponseEntity(timeEntry,HttpStatus.OK);
        else
            return new ResponseEntity(timeEntry,HttpStatus.NOT_FOUND);
    }


    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        counterService.increment("timeEntry.list");
        return new ResponseEntity(timeEntryRepository.list(),HttpStatus.OK);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry entry) {

        TimeEntry timeEntry=timeEntryRepository.update(timeEntryId, entry);
        counterService.increment("timeEntry.update");
        if (timeEntry!=null)
            return new ResponseEntity(timeEntry,HttpStatus.OK);
        else
            return new ResponseEntity(timeEntry,HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        counterService.increment("timeEntry.delete");
        gaugeService.submit("timeEntries.count",timeEntryRepository.list().size());
        return new ResponseEntity( HttpStatus.NO_CONTENT);

    }
}



