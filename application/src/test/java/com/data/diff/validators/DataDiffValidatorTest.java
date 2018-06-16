package com.data.diff.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.data.diff.entity.DiffData;
import com.data.diff.exceptions.EmptyLeftDataException;
import com.data.diff.exceptions.EmptyRightDataException;
import com.data.diff.exceptions.LeftAndRightDataFieldsNotSameException;
import com.data.diff.exceptions.LeftAndRightDataSizeNotSameException;

@RunWith(MockitoJUnitRunner.class)
public class DataDiffValidatorTest {

    @InjectMocks
    private DataDiffValidator dataDiffValidator;

    @Test(expected = EmptyLeftDataException.class)
    public void leftDataIsEmpty() {
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setRightData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        dataDiffValidator.validate(diffData);
    }

    @Test(expected = EmptyRightDataException.class)
    public void rightDataIsEmpty() {
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setLeftData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        dataDiffValidator.validate(diffData);
    }

    @Test(expected = LeftAndRightDataSizeNotSameException.class)
    public void leftAndRightDataNotOfSameSize() {
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setLeftData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}");
        diffData.setRightData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        dataDiffValidator.validate(diffData);
    }

    @Test(expected = LeftAndRightDataFieldsNotSameException.class)
    public void leftAndRightDataDontHaveSameFields() {
        DiffData diffData = new DiffData();
        diffData.setId(1);
        diffData.setObjectId(1);
        diffData.setLeftData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field3\":\"c3RyaW5nMw==\"}");
        diffData.setRightData("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\",\"field4\":\"c3RyaW5nMw==\"}");
        dataDiffValidator.validate(diffData);
    }
}
