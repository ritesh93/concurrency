package message_queue.model;

import lombok.Getter;
import lombok.Setter;
import message_queue.public_interface.ISubscriber;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class TopicSubscriber {
    AtomicInteger offset = new AtomicInteger(0);
    ISubscriber subscriber;
}
