package com.data.diff.api.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.data.diff.constants.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DataDiffApiImplTest implements Constants {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        String cleanUpDatabase = "delete from diff_data";
        jdbcTemplate.update(cleanUpDatabase);
    }

    @Test
    public void createLeftData() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"STATUS\":\"SUCCESS\",\"RESULT\":\"Left data is created\"}"));
    }

    @Test
    public void updateLeftData() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //update left data created above
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"STATUS\":\"SUCCESS\",\"RESULT\":\"Left data is updated\"}"));
    }

    @Test
    public void createRightData() throws Exception {
        Integer objectId = 1;

        //create right data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"STATUS\":\"SUCCESS\",\"RESULT\":\"Right data is created\"}"));
    }

    @Test
    public void updateRightData() throws Exception {
        Integer objectId = 1;

        //create right data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //create right data created above
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"STATUS\":\"SUCCESS\",\"RESULT\":\"Right data is updated\"}"));
    }

    @Test
    public void noDataToGetDiff() throws Exception {
        Integer objectId = 1;
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isNotFound())
                .andExpect(
                        content().string("{\"STATUS\":\"FAILURE\",\"RESULT\":\"No record found for object id - 1\"}")
                );
    }

    @Test
    public void allFieldsSameButDataDoesNotMatch() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //update right data for object id created in above step
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMw==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isOk())
                .andExpect(
                        content().string("{\"STATUS\":\"SUCCESS\",\"FIELDS_WITH_DIFFERENCE\":\"[field2]\",\"RESULT\":\"Left and right data don't match with each other\"}")
                );
    }

    @Test
    public void allFieldsSameAndDataAlsoMatches() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //update right data for object id created in above step
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isOk())
                .andExpect(
                        content().string("{\"STATUS\":\"SUCCESS\",\"RESULT\":\"Left and right data matches with each other\"}")
                );
    }

    @Test
    public void leftDataIsEmpty() throws Exception {
        Integer objectId = 1;

        //create right data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        content().string("{\"STATUS\":\"FAILURE\",\"RESULT\":\"Left data not present for object id - 1\"}")
                );
    }

    @Test
    public void rightDataIsEmpty() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        content().string("{\"STATUS\":\"FAILURE\",\"RESULT\":\"Right data not present for object id - 1\"}")
                );
    }

    @Test
    public void leftAndRightDataNotOfSameSize() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //update right data for object id created in above step
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\", \"field3\":\"c3RyaW5nMw==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        content().string("{\"STATUS\":\"FAILURE\",\"RESULT\":\"Left and right data are of different sizes for object id - 1\"}")
                );
    }

    @Test
    public void leftAndRightDataDontHaveSameFields() throws Exception {
        Integer objectId = 1;

        //create left data
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field2\":\"c3RyaW5nMg==\"}"));

        //update right data for object id created in above step
        mockMvc.perform(put(DataDiffApiImpl.PATH + "/" + objectId + "/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"field1\":\"c3RyaW5nMQ==\",\"field3\":\"c3RyaW5nMw==\"}"));

        //get difference for left and right data for above object id
        mockMvc.perform(get(DataDiffApiImpl.PATH + "/" + objectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        content().string("{\"STATUS\":\"FAILURE\",\"RESULT\":\"Left and right data have different fields for object id - 1\"}")
                );
    }

}
