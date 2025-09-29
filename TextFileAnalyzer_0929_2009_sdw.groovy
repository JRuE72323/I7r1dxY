// 代码生成时间: 2025-09-29 20:09:51
import grails.transaction.Transactional
import groovy.io.FileType
import groovy.io.FileUtil
import org.apache.commons.io.FileUtils
import org.apache.commons.io.input.ReversedLinesFileReader

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

/**
 * TextFileAnalyzer service class for analyzing text file content.
 * It provides methods to count words, lines, and characters
 * and to perform other text analysis tasks as needed.
 */
class TextFileAnalyzer {

    /**
     * Analyzes the content of a text file and returns a summary.
     *
     * @param filePath Path to the text file to analyze.
     * @return A map containing the results of the analysis.
     * @throws IOException If an error occurs while reading the file.
     */
    @Transactional(readOnly = true)
    Map analyzeTextFile(String filePath) throws IOException {
        // Check if the file exists
        File file = new File(filePath)
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException('File not found: ' + filePath)
        }

        // Initialize counters
        int wordCount = 0
        int lineCount = 0
        int charCount = 0

        // Read the file and analyze its content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line
            while ((line = reader.readLine()) != null) {
                lineCount++
                charCount += line.length()
                wordCount += line.split(' ').length
            }
        }

        // Return the analysis results
        return [
            wordCount: wordCount,
            lineCount: lineCount,
            charCount: charCount
        ]
    }

    /**
     * Reverses the content of a text file.
     *
     * @param filePath Path to the text file to reverse.
     * @throws IOException If an error occurs while reading or writing the file.
     */
    void reverseTextFileContent(String filePath) throws IOException {
        // Check if the file exists
        File file = new File(filePath)
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException('File not found: ' + filePath)
        }

        // Create a temporary file to store the reversed content
        File tempFile = File.createTempFile(file.name, null)
        tempFile.deleteOnExit()

        // Read the file in reverse order and write it to the temporary file
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, Charset.defaultCharset())) {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), Charset.defaultCharset())
            char[] buffer = new char[1024]
            int read
            while ((read = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, read)
            }
            writer.close()
        }

        // Replace the original file with the temporary file
        Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
}
