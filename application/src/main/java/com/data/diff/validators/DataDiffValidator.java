package com.data.diff.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.data.diff.entity.DiffData;
import com.data.diff.exceptions.EmptyLeftDataException;
import com.data.diff.exceptions.EmptyRightDataException;
import com.data.diff.exceptions.LeftAndRightDataFieldsNotSameException;
import com.data.diff.exceptions.LeftAndRightDataSizeNotSameException;
import com.data.diff.utils.ParsingUtil;
import com.data.diff.utils.ValidationUtil;

@Component
public class DataDiffValidator implements Validator<DiffData, Optional<Void>> {

    @Override
    public Optional<Void> validate(DiffData diffData) {
        validateIfLeftAndRightDataIsNotEmpty(diffData);
        validateIfLeftAndRightDataHaveSameSize(diffData);
        validateIfLeftAndRightDataHaveSameFields(diffData);
        return Optional.empty();
    }

    private void validateIfLeftAndRightDataIsNotEmpty(DiffData diffData) {
        if (ValidationUtil.nullOrEmpty(diffData.getLeftData())) {
            throw new EmptyLeftDataException("Left data not present for object id - " + diffData.getObjectId());
        }
        if (ValidationUtil.nullOrEmpty(diffData.getRightData())) {
            throw new EmptyRightDataException("Right data not present for object id - " + diffData.getObjectId());
        }
    }

    private void validateIfLeftAndRightDataHaveSameSize(DiffData diffData) {
        Map<String, String> leftDataMap = ParsingUtil.jsonToObject(diffData.getLeftData(), HashMap.class);
        Map<String, String> rightDataMap = ParsingUtil.jsonToObject(diffData.getRightData(), HashMap.class);
        if (leftDataMap.size() != rightDataMap.size()) {
            throw new LeftAndRightDataSizeNotSameException("Left and right data are of different sizes for object id - "
                    + diffData.getObjectId());
        }
    }

    private void validateIfLeftAndRightDataHaveSameFields(DiffData diffData) {
        Map<String, String> leftDataMap = ParsingUtil.jsonToObject(diffData.getLeftData(), HashMap.class);
        Map<String, String> rightDataMap = ParsingUtil.jsonToObject(diffData.getRightData(), HashMap.class);
        for (Map.Entry<String, String> leftDataEntry : leftDataMap.entrySet()) {
            if (ValidationUtil.isNull(rightDataMap.get(leftDataEntry.getKey()))) {
                throw new LeftAndRightDataFieldsNotSameException("Left and right data have different fields for object id - "
                        + diffData.getObjectId());
            }
        }
    }
}
