package message_queue.handler;

import message_queue.model.Topic;
import message_queue.model.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private Topic topic;
    private Map<String, SubscriberWorker> subscriberWorkerMap = null;

    TopicHandler(Topic topic){
        this.topic = topic;
        subscriberWorkerMap = new HashMap<>();
    }

    public void publish(){
        for(TopicSubscriber topicSubscriber : topic.getSubscriberList()){
            startSubscriberWorker(topicSubscriber);
        }
    }

    public void startSubscriberWorker(TopicSubscriber topicSubscriber){
        String subscriptionId = topicSubscriber.getSubscriber().getId();
        if(!subscriberWorkerMap.containsKey(subscriptionId)){
            subscriberWorkerMap.put(subscriptionId, new SubscriberWorker(topic, topicSubscriber));
            new Thread(() -> subscriberWorkerMap.get(subscriptionId)).start();
        }
        subscriberWorkerMap.get(subscriptionId).wakeUpiFRequired();
    }
}
