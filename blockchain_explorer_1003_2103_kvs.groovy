// 代码生成时间: 2025-10-03 21:03:34
import grails.transaction.Transactional
import org.springframework.web.bind.annotation.*
import groovy.json.JsonBuilder

@RestController
@RequestMapping('/api/blockchain')
class BlockchainExplorerController {

    // Service to handle blockchain operations
    BlockchainService blockchainService
    
    // Endpoint to retrieve blockchain details
    @GetMapping('/blocks')
    @Transactional(readOnly = true)
    @ResponseBody
    List<Map> listBlocks() {
        // Fetch and return the blocks from blockchain service
        return blockchainService.listBlocks()
    }
    
    // Endpoint to retrieve a specific block's details
    @GetMapping('/blocks/{index}')
    @Transactional(readOnly = true)
    @ResponseBody
    Map getBlock(@PathVariable int index) {
        // Fetch a specific block by its index
        def block = blockchainService.getBlock(index)
        if (block == null) {
            // Handle the case where the block is not found
            throw new ResourceNotFoundException("Block with index $index not found.")
        }
        return block
    }
    
    // Endpoint to retrieve the latest block in the blockchain
    @GetMapping('/latest-block')
    @Transactional(readOnly = true)
    @ResponseBody
    Map getLatestBlock() {
        // Fetch and return the latest block from blockchain service
        return blockchainService.getLatestBlock()
    }
}

/*
 * BlockchainService.groovy
 *
 * Service class responsible for blockchain operations.
 */

@Service
class BlockchainService {

    // Simulated blockchain data
    List<Map> blockchain = []
    
    // Method to list all blocks in the blockchain
    List<Map> listBlocks() {
        return blockchain
    }
    
    // Method to retrieve a specific block by its index
    Map getBlock(int index) {
        return blockchain.get(index)
    }
    
    // Method to add a new block to the blockchain
    void addBlock(Map data) {
        blockchain.add(data)
    }
    
    // Method to retrieve the latest block in the blockchain
    Map getLatestBlock() {
        return blockchain.isEmpty() ? null : blockchain.last()
    }
}

/*
 * ResourceNotFoundException.java
 *
 * Custom exception to handle resource not found cases.
 */

class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message)
    }
}
