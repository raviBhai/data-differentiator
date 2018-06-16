package com.data.diff.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.diff.service.DataDiffService;

@Slf4j
@RestController
@RequestMapping(value = DataDiffApiImpl.PATH)
@RequiredArgsConstructor
public class DataDiffApiImpl implements DataDiffApi {

    private final DataDiffService dataDiffService;
    public static final String PATH = "/api/v1/diff";

    @Override
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/left", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity saveLeft(@RequestBody Map<String,String> fields, @PathVariable Integer id) {
        log.info("Incoming request to save left data for object id {}", id);
        return dataDiffService.saveLeft(fields, id);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/right", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity saveRight(@RequestBody Map<String,String> fields, @PathVariable Integer id) {
        log.info("Incoming request to save right data for object id {}", id);
        return dataDiffService.saveRight(fields, id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getDiff(@PathVariable Integer id) {
        log.info("Incoming request to get difference in left and right data for object id {}", id);
        return dataDiffService.getDiff(id);
    }
}
