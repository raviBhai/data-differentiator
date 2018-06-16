package com.data.diff.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface Constants {
    ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    String BLANK_STRING = "";
    String SUCCESS = "SUCCESS";
    String FAILURE = "FAILURE";
    String STATUS = "STATUS";
    String RESULT = "RESULT";
    String LEFT_DATA_CREATED = "Left data is created";
    String LEFT_DATA_UPDATED = "Left data is updated";
    String RIGHT_DATA_CREATED = "Right data is created";
    String RIGHT_DATA_UPDATED = "Right data is updated";
    String LEFT_AND_RIGHT_DATA_MATCHES = "Left and right data matches with each other";
    String LEFT_AND_RIGHT_DATA_DO_NOT_MATCH = "Left and right data don't match with each other";
    String FIELDS_WITH_DIFFERENCE = "FIELDS_WITH_DIFFERENCE";
}
