package message_queue.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Topic {
    private String topicId;
    private String topicName;
    private List<Message> messageList;
    private List<TopicSubscriber> subscriberList;

    public Topic(String topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
        messageList = new ArrayList<>();
        subscriberList = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public void addSubscriber(TopicSubscriber topicSubscriber) {
        subscriberList.add(topicSubscriber);
    }
}
