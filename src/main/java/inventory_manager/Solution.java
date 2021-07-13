package inventory_manager;

import lombok.ToString;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String args[] ) throws Exception {
        Queue<BlockedInventory> queue =  new PriorityQueue<>((a,b) -> Long.compare(a.getTimeTillBlocked().longValue(),b.getTimeTillBlocked().longValue()));
        InventoryManager inventoryManager = new InventoryManager(queue);
        TaskScheduler taskScheduler = new TaskScheduler(inventoryManager, queue);
        inventoryManager.createProduct("p1", "product1", 10);
        System.out.println("current inventory"+ inventoryManager.getInventory("p1"));
        inventoryManager.blockInventory("p1", 1, "o1");
        Thread t1 = new Thread(() -> taskScheduler.consumeTask());
//        Thread.sleep(100);
        t1.start();
        inventoryManager.confirmOrder("o1");
    }

}

class InventoryManager {
    private static final Long TIME_TO_BE_BLOCKED = 500L;
    Map<String, ProductInventory> inventoryMap = new HashMap<>();
    Map<String, BlockedInventory> blockedInventoryMap = new HashMap<>();
    Queue<BlockedInventory> queue = null;
//    TaskScheduler taskScheduler = new TaskScheduler(this);

    InventoryManager(Queue<BlockedInventory> queue){
        this.queue =  queue;
    }
    public void createProduct(String productId ,String name, Integer count){
        // if(!inventoryMap.containsKey(productId)){
        inventoryMap.put(productId, new ProductInventory(productId, count, name));
        // }

    }

    public Integer getInventory(String productId){
        if(inventoryMap.containsKey(productId)){
            return inventoryMap.get(productId).getCount();
        }
        return 0;
    }

    public void blockInventory(String productId, Integer count, String orderId){
        if(isStockAvailable(productId, count)){
            ProductInventory productInventory = inventoryMap.get(productId);
            int inventoryAvailable = productInventory.getCount();
            String productName = productInventory.getProductName();
            Long timeTillToBeBlocked = System.currentTimeMillis()+TIME_TO_BE_BLOCKED;
            inventoryMap.put(productId, new ProductInventory(productId, inventoryAvailable-count, productName));
            final BlockedInventory blockedInventory = new BlockedInventory(productId, count, orderId, timeTillToBeBlocked, InventoryStatus.IN_PROGRESS);
            blockedInventoryMap.put(orderId, blockedInventory);
            System.out.println("Inventory blocked for productId"+ productId);
            new Thread(() -> createTask(blockedInventory)).start();
        }
        else{
            System.out.println("Order failed as the inventory count is less than order placed");
        }
    }

    public void createTask(BlockedInventory blockedInventory){
        try{
            synchronized(queue){
                while(queue.size() == 10){
                    System.out.println("Queue is full");
                    queue.wait();
                }
                queue.add(blockedInventory);
                System.out.println("queue size"+ queue.peek());
                System.out.println("task added into queue");
                queue.notify();
            }
        }catch(InterruptedException exception){
            System.out.println("Exception ");
        }

    }

    public void confirmOrder(String orderId){
        BlockedInventory blockedInventory = blockedInventoryMap.get(orderId);
        queue.remove(blockedInventory);
        blockedInventoryMap.remove(orderId);
        System.out.println("Order placed for orderId"+ orderId);
    }

    public void updateInventory(String productId, Integer count, String orderId){
        ProductInventory productInventory = inventoryMap.get(productId);
        inventoryMap.put(productId, new ProductInventory(productId, productInventory.getCount() + count, productInventory.getProductName()));
        blockedInventoryMap.remove(orderId);
    }

    private boolean isStockAvailable(String productId, Integer count){
        int inventoryAvailable  = inventoryMap.get(productId).getCount();
        return inventoryAvailable >= count;
    }
}

class ProductInventory {
    private String productId;
    private Integer count;
    private String productName;

    public ProductInventory(String productId, Integer count, String productName){
        this.productId = productId;
        this.count = count;
        this.productName = productName;
    }

    public String getProductId(){
        return productId;
    }

    public Integer getCount(){
        return count;
    }

    public String getProductName(){
        return productName;
    }


}

@ToString
class BlockedInventory {
    private String productId;
    private Integer count;
    private String orderId;
    private Long timeTillBlocked;
    private InventoryStatus status;
    private String name;

    public BlockedInventory(String productId, Integer count, String orderId,Long timeTillBlocked, InventoryStatus status){
        this.productId = productId;
        this.count = count;
        this.orderId = orderId;
        this.timeTillBlocked = timeTillBlocked;
        this.status = status;
        this.name = name;
    }

    public String getProductId(){
        return productId;
    }

    public Integer getCount(){
        return count;
    }

    public String getOrderId(){
        return orderId;
    }

    public Long getTimeTillBlocked(){
        return timeTillBlocked;
    }

    public InventoryStatus getStatuc(){
        return status;
    }
    public String getName(){
        return name;
    }
    boolean equals(BlockedInventory inventory){
        if(this == inventory)
            return true;
        return this.orderId.equals(inventory.orderId);
    }
}

enum InventoryStatus {
    IN_PROGRESS,
    COMPLETE;
}

class TaskScheduler{
    // Long.compare(a.getTimeTillBlocked(),b.getTimeTillBlocked()
    Queue<BlockedInventory> queue = null;
    InventoryManager inventoryManager = null;
    private static final int MAX_LIMIT = 10;
    TaskScheduler(InventoryManager inventoryManager, Queue<BlockedInventory> queue){
        this.inventoryManager = inventoryManager;
        this.queue = queue;
    }

    public void consumeTask(){
        try{
            while(true){
                synchronized(queue){
                    System.out.println("queue"+ queue.peek());
                    while(queue.isEmpty()){
                        System.out.println("Queue is empty");
                        Thread.sleep(100);
                    }
                    BlockedInventory task = queue.peek();
                    Long currentTime = System.currentTimeMillis();
                    if(currentTime > task.getTimeTillBlocked()){
                        queue.poll();
                        inventoryManager.updateInventory(task.getProductId(), task.getCount(), task.getOrderId());
                        System.out.println("inventory unblocked for orderID"+ task.getOrderId());
                        System.out.println(inventoryManager.getInventory("p1"));
                    }
                    else{
                        System.out.println("No elligible task to poll");
                    }
                }
            }
        }catch(InterruptedException exception){
            System.out.println("Exception thrown");
        }
    }

    public void removeEntry(BlockedInventory blockedInventory){
        queue.remove(blockedInventory);
    }
}



