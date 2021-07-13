package message_queue.handler;

import lombok.SneakyThrows;
import message_queue.model.Topic;
import message_queue.model.TopicSubscriber;

public class SubscriberWorker implements Runnable {
    private Topic topic;
    private TopicSubscriber topicSubscriber;

    SubscriberWorker(Topic topic, TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            while (topic.getMessageList().size() <= topicSubscriber.getOffset().get()) {
                System.out.println("subscriber is updated");
                topicSubscriber.wait();
            }
        }
    }

    public void wakeUpiFRequired() {

    }
}
