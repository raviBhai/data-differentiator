package com.data.diff.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.data.diff.constants.Constants;
import com.data.diff.entity.DiffData;
import com.data.diff.exceptions.NoRecordFoundException;
import com.data.diff.repository.DataDiffRepository;
import com.data.diff.utils.Base64Util;
import com.data.diff.utils.ParsingUtil;
import com.data.diff.validators.DataDiffValidator;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataDiffServiceImpl implements DataDiffService, Constants {

    private final DataDiffRepository dataDiffRepository;
    private final DataDiffValidator dataDiffValidator;

    @Override
    public ResponseEntity saveLeft(Map<String,String> fields, Integer objectId) {
        Optional<DiffData> optDiffData = dataDiffRepository.findByObjectId(objectId);
        String fieldsObjJson = ParsingUtil.objectToJson(fields);
        if (!optDiffData.isPresent()) {
            log.info("No data present for object id {}. Creating new entry", objectId);
            DiffData diffData = createDiffData(objectId, fieldsObjJson, BLANK_STRING);
            dataDiffRepository.save(diffData);
            return new ResponseEntity(response(SUCCESS, LEFT_DATA_CREATED), HttpStatus.CREATED);
        } else {
            log.info("Data present for object id {}. Updating existing entry", objectId);
            DiffData diffData = optDiffData.get();
            diffData.setLeftData(fieldsObjJson);
            dataDiffRepository.save(diffData);
            return new ResponseEntity(response(SUCCESS, LEFT_DATA_UPDATED), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity saveRight(Map<String,String> fields, Integer objectId) {
        Optional<DiffData> optDiffData = dataDiffRepository.findByObjectId(objectId);
        String fieldsObjJson = ParsingUtil.objectToJson(fields);
        if (!optDiffData.isPresent()) {
            log.info("No data present for object id {}. Creating new entry", objectId);
            DiffData diffData = createDiffData(objectId, BLANK_STRING, fieldsObjJson);
            dataDiffRepository.save(diffData);
            return new ResponseEntity(response(SUCCESS, RIGHT_DATA_CREATED), HttpStatus.CREATED);
        } else {
            log.info("Data present for object id {}. Updating existing entry", objectId);
            DiffData diffData = optDiffData.get();
            diffData.setRightData(fieldsObjJson);
            dataDiffRepository.save(diffData);
            return new ResponseEntity(response(SUCCESS, RIGHT_DATA_UPDATED), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity getDiff(Integer objectId) {
        Optional<DiffData> optDiffData = dataDiffRepository.findByObjectId(objectId);
        if (!optDiffData.isPresent()) {
            log.info("No data present for object id {} to get the difference", objectId);
            throw new NoRecordFoundException("No record found for object id - " + objectId);
        } else {
            DiffData diffData = optDiffData.get();
            dataDiffValidator.validate(diffData);
            log.info("Data for object id {} is valid. Calculating difference", objectId);
            return dataDiffResponse(diffData);
        }
    }

    private ResponseEntity dataDiffResponse(DiffData diffData) {
        Map<String, String> leftDataMap = ParsingUtil.jsonToObject(diffData.getLeftData(), HashMap.class);
        Map<String, String> rightDataMap = ParsingUtil.jsonToObject(diffData.getRightData(), HashMap.class);
        List<String> diffFields = populateFieldsWithDifference(leftDataMap, rightDataMap);

        if (diffFields.size() > 0) {
            Map<String, String> response = response(SUCCESS, LEFT_AND_RIGHT_DATA_DO_NOT_MATCH);
            response.put(FIELDS_WITH_DIFFERENCE, diffFields.toString());
            return new ResponseEntity(response, HttpStatus.OK);
        } else {
            return new ResponseEntity(response(SUCCESS, LEFT_AND_RIGHT_DATA_MATCHES), HttpStatus.OK);
        }
    }

    private List<String> populateFieldsWithDifference(Map<String, String> leftDataMap, Map<String, String> rightDataMap) {
        List<String> diffFields = new ArrayList<>();
        for (Map.Entry<String, String> leftDataEntry : leftDataMap.entrySet()) {
            String key = leftDataEntry.getKey();
            String value = leftDataEntry.getValue();

            if (!Base64Util.decode(value).equals(Base64Util.decode(rightDataMap.get(key)))) {
                diffFields.add(key);
            }
        }
        return diffFields;
    }

    private DiffData createDiffData(Integer objectId, String left, String right) {
        DiffData diffData = new DiffData();
        diffData.setObjectId(objectId);
        diffData.setLeftData(left);
        diffData.setRightData(right);
        return diffData;
    }

    private Map<String, String> response(String status, String message) {
        Map<String, String> map = new HashMap<>();
        map.put(STATUS, status);
        map.put(RESULT, message);
        return map;
    }
}
