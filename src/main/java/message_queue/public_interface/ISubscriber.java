package message_queue.public_interface;

import message_queue.model.Message;

public interface ISubscriber {
    String getId();
    void consume(Message message);
}
