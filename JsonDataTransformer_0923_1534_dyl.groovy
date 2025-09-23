// 代码生成时间: 2025-09-23 15:34:56
package com.example

import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

/**
 * A Java application using the Grails framework to implement
 * a JSON data format converter.
 *
 * @author Your Name
 * @since 1.0
 */
class JsonDataTransformer {

    private final JsonSlurper jsonSlurper = new JsonSlurper()
    private final JsonBuilder jsonBuilder = new JsonBuilder()

    /**
     * Converts a JSON string from one format to another.
     *
     * @param jsonString The JSON string to be converted.
     * @return The converted JSON string.
     * @throws IllegalArgumentException If the input is not a valid JSON string.
     */
    String convertJsonString(String jsonString) {
        // Parse the JSON string to a Map
        Map jsonMap = jsonSlurper.parseText(jsonString)

        // Check if the parsed object is a Map (to ensure JSON is valid)
        if (!(jsonMap instanceof Map)) {
            throw new IllegalArgumentException('Invalid JSON string provided.')
        }

        // Convert the Map back to a JSON string
        return jsonBuilder.toJson(jsonMap)
    }

    /**
     * Main method for testing the JSON converter.
     *
     * @param args Command line arguments.
     */
    static void main(String[] args) {
        JsonDataTransformer converter = new JsonDataTransformer()

        // Example JSON string for testing
        String originalJson = '''{
            "name": "John Doe",
            "age": 30
        }'''

        try {
            String convertedJson = converter.convertJsonString(originalJson)
            println "Converted JSON: $convertedJson"
        } catch (IllegalArgumentException e) {
            println e.message
        }
    }
}
