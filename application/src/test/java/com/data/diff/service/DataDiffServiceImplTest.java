package com.data.diff.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.data.diff.constants.Constants;
import com.data.diff.entity.DiffData;
import com.data.diff.exceptions.NoRecordFoundException;
import com.data.diff.repository.DataDiffRepository;
import com.data.diff.validators.DataDiffValidator;

@RunWith(MockitoJUnitRunner.class)
public class DataDiffServiceImplTest implements Constants {

    @Mock
    private DataDiffRepository dataDiffRepository;
    @Mock
    private DataDiffValidator dataDiffValidator;

    @InjectMocks
    private DataDiffServiceImpl dataDiffService;

    @Test
    public void createLeftData() {
        Map<String, String> fields = new HashMap<>();
        fields.put("field1", "c3RyaW5nMQ==");
        fields.put("field2", "c3RyaW5nMg==");
        fields.put("field3", "c3RyaW5nMw==");
        fields.put("field4", "c3RyaW5nNA==");

        Integer objectId = 1;
        Optional<DiffData> optDiffData = Optional.ofNullable(null);

        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.saveLeft(fields, objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(LEFT_DATA_CREATED));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
        verify(dataDiffRepository).save(any(DiffData.class));
    }

    @Test
    public void updateLeftData() {
        Map<String, String> fields = new HashMap<>();
        fields.put("field1", "c3RyaW5nMQ==");
        fields.put("field2", "c3RyaW5nMg==");
        fields.put("field3", "c3RyaW5nMw==");
        fields.put("field4", "c3RyaW5nNA==");

        Integer objectId = 1;

        DiffData diffData = new DiffData();
        Optional<DiffData> optDiffData = Optional.ofNullable(diffData);

        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.saveLeft(fields, objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(LEFT_DATA_UPDATED));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        verify(dataDiffRepository).save(any(DiffData.class));
    }

    @Test
    public void createRightData() {
        Map<String, String> fields = new HashMap<>();
        fields.put("field1", "c3RyaW5nMQ==");
        fields.put("field2", "c3RyaW5nMg==");
        fields.put("field3", "c3RyaW5nMw==");
        fields.put("field4", "c3RyaW5nNA==");

        Integer objectId = 1;
        Optional<DiffData> optDiffData = Optional.ofNullable(null);

        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.saveRight(fields, objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(RIGHT_DATA_CREATED));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
        verify(dataDiffRepository).save(any(DiffData.class));
    }

    @Test
    public void updateRightData() {
        Map<String, String> fields = new HashMap<>();
        fields.put("field1", "c3RyaW5nMQ==");
        fields.put("field2", "c3RyaW5nMg==");
        fields.put("field3", "c3RyaW5nMw==");
        fields.put("field4", "c3RyaW5nNA==");

        Integer objectId = 1;

        DiffData diffData = new DiffData();
        Optional<DiffData> optDiffData = Optional.ofNullable(diffData);

        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.saveRight(fields, objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(RIGHT_DATA_UPDATED));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        verify(dataDiffRepository).save(any(DiffData.class));
    }

    @Test(expected = NoRecordFoundException.class)
    public void noDataToGetDiff() {
        Integer objectId = 1;
        Optional<DiffData> optDiffData = Optional.ofNullable(null);
        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);
        dataDiffService.getDiff(objectId);
    }

    @Test
    public void allFieldsSameButDataDoesNotMatch() {
        Integer objectId = 1;
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setLeftData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        diffData.setRightData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nNA==\"}");


        Optional<DiffData> optDiffData = Optional.ofNullable(diffData);
        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.getDiff(objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(LEFT_AND_RIGHT_DATA_DO_NOT_MATCH));
        assertTrue(body.get(FIELDS_WITH_DIFFERENCE).contains("field3"));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void allFieldsSameAndDataAlsoMatches() {
        Integer objectId = 1;
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setLeftData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        diffData.setRightData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");


        Optional<DiffData> optDiffData = Optional.ofNullable(diffData);
        when(dataDiffRepository.findByObjectId(objectId)).thenReturn(optDiffData);

        ResponseEntity responseEntity = dataDiffService.getDiff(objectId);
        Map<String, String> body = (HashMap<String, String>)responseEntity.getBody();
        assertTrue(body.get(STATUS).equals(SUCCESS));
        assertTrue(body.get(RESULT).equals(LEFT_AND_RIGHT_DATA_MATCHES));
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

}
