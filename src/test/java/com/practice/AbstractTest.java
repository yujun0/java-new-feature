package com.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;

/**
 * 抽象測試類別物件. <br>
 * https://junit.org/junit5/docs/current/user-guide/
 */
public class AbstractTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // @BeforeAll - executes once before all test methods in this class
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        // @AfterAll - executed after all test methods
    }


    @BeforeEach
    void setUp() throws Exception {
        // @BeforeEach - executes before each test method in this class
        System.out.println("====================== initialization =====================");
    }

    @AfterEach
    void tearDown() throws Exception {
        // @AfterEach - executed after each test method.
        System.out.println("======================== completed ========================");
    }

    /**
     * 將資料列印出來. <br>
     *
     * @param data
     */
    public void printData(Object data) {
        Assertions.assertNotNull(data);

        String jsonStr = convertToJson(data, false);

        System.out.println(jsonStr);
    }

    /**
     * 將資料列印出來. <br>
     *
     * @param data
     * @param prettyPrint 是否加強排版
     */
    public void printData(Object data, boolean prettyPrint) {
        Assertions.assertNotNull(data);

        String jsonStr = convertToJson(data, prettyPrint);

        System.out.println(jsonStr);
    }

    /**
     * https://mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/
     *
     * @param obj
     * @param prettyPrint 是否加強排版
     * @return String
     */
    public String convertToJson(Object obj, boolean prettyPrint) {
        String jsonStr;

        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

            if (prettyPrint) {
                // pretty print: writerWithDefaultPrettyPrinter() 加強排版
                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } else {
                jsonStr = mapper.writeValueAsString(obj);
            }

            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
