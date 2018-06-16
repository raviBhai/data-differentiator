package com.data.diff.api.v1;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.data.diff.constants.Constants;

public interface DataDiffApi {

    /**
     * Accepts an incoming request to save or update the left data {@code fields} for a given object {@code id}.
     *
     * @param fields
     *        Collects the JSON left data in map. Both keys and values are String. Values are Base64 encoded.
     *
     * @param id
     *        The object id of the JSON data
     *
     * @return ResponseEntity with
     *              status set to
     *                  {@linkplain HttpStatus#CREATED} when creating a new left entry for JSON data
     *                  {@linkplain HttpStatus#OK} when updating an existing left entry for JSON data
     *              and body having response as
     *                  STATUS - {@linkplain Constants#SUCCESS}
     *                  RESULT -
     *                      {@linkplain Constants#LEFT_DATA_CREATED} (creating new entry)
     *                      {@linkplain Constants#LEFT_DATA_UPDATED} (updating existing entry)
     */
    ResponseEntity saveLeft(Map<String, String> fields, Integer id);

    /**
     * Accepts an incoming request to save or update the right data {@code fields} for a given object {@code id}.
     *
     * @param fields
     *        Collects the JSON right data in map. Both keys and values are String. Values are Base64 encoded.
     *
     * @param id
     *        The object id of the JSON data
     *
     * @return ResponseEntity with
     *              status set to
     *                  {@linkplain HttpStatus#CREATED} when creating a new right entry for JSON data
     *                  {@linkplain HttpStatus#OK} when updating an existing right entry for JSON data
     *              and body having response as
     *                  STATUS - {@linkplain Constants#SUCCESS}
     *                  RESULT -
     *                      {@linkplain Constants#RIGHT_DATA_CREATED} (creating new entry)
     *                      {@linkplain Constants#RIGHT_DATA_UPDATED} (updating existing entry)
     */
    ResponseEntity saveRight(Map<String, String> fields, Integer id);

    /**
     * Accepts an incoming request to get the difference between left and right data {@code fields} for a given object {@code id}.
     * @param id
     *        The object id of the JSON data for which the difference between left and right data is desired
     *
     * @return ResponseEntity with below use cases depending on the data:
     *
     *              1. No data present for the given object {@code id}
     *                  status - {@linkplain HttpStatus#NOT_FOUND}
     *                  body -
     *                      STATUS - {@linkplain Constants#FAILURE}
     *                      RESULT - Error message mentioning that the record is not found for the given object {@code id}
     *
     *              2. Left data present but right data absent for the given object {@code id}
     *                  status - {@linkplain HttpStatus#UNPROCESSABLE_ENTITY}
     *                  body -
     *                      STATUS - {@linkplain Constants#FAILURE}
     *                      RESULT - Error message mentioning that right data is not present for the given object {@code id}
     *
     *              3. Right data present but left data absent for the given object {@code id}
     *                  status - {@linkplain HttpStatus#UNPROCESSABLE_ENTITY}
     *                  body -
     *                      STATUS - {@linkplain Constants#FAILURE}
     *                      RESULT - Error message mentioning that left data is not present for the given object {@code id}
     *
     *              4. Left and right data don't have the same size for the given object {@code id}
     *                  status - {@linkplain HttpStatus#UNPROCESSABLE_ENTITY}
     *                  body -
     *                      STATUS - {@linkplain Constants#FAILURE}
     *                      RESULT - Error message mentioning that left and right data are of different sizes for the
     *                               given object {@code id}
     *
     *              5. Left and right data have same size but different fields for the given object {@code id}
     *                  status - {@linkplain HttpStatus#UNPROCESSABLE_ENTITY}
     *                  body -
     *                      STATUS - {@linkplain Constants#FAILURE}
     *                      RESULT - Error message mentioning that left and right data have different fields for the
     *                               given object {@code id}
     *
     *              6. Left and right data have same size and same fields for the given object {@code id} but data does not match
     *                  status - {@linkplain HttpStatus#OK}
     *                  body -
     *                      STATUS - {@linkplain Constants#SUCCESS}
     *                      RESULT - Message mentioning that left and right data do not match for the given object {@code id}
     *                      FIELDS_WITH_DIFFERENCE - A list of fields having different values in left and right data
     *
     *              7. Left and right data have same size and same fields for the given object {@code id} and data also matches
     *                  status - {@linkplain HttpStatus#OK}
     *                  body -
     *                      STATUS - {@linkplain Constants#SUCCESS}
     *                      RESULT - Message mentioning that left and right matches for the given object {@code id}
     */
    ResponseEntity getDiff(Integer id);
}
