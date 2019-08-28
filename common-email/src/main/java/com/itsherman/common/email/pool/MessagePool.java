package com.itsherman.common.email.pool;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.*;

/**
 * @author yumiaoxia
 * created in 2019/8/27
 * auditor: /
 * audited in /
 */
public class MessagePool {
    private static final Set<Flags.Flag> FLAGS = new HashSet<>(7);
    static {
        FLAGS.add(Flags.Flag.ANSWERED);
        FLAGS.add(Flags.Flag.FLAGGED);
        FLAGS.add(Flags.Flag.DELETED);
        FLAGS.add(Flags.Flag.SEEN);
        FLAGS.add(Flags.Flag.DRAFT);
        FLAGS.add(Flags.Flag.RECENT);
        FLAGS.add(Flags.Flag.USER);
    }

    public MessagePool(){
        for (Flags.Flag flag: FLAGS){
            flagMessagesMap.put(flag, new ArrayList<>());
        }
    }
    private Map<String, Message> messageMap = new HashMap<>();
    private Map<Flags.Flag, Collection<Message>> flagMessagesMap = new HashMap<>();


    public void init(Message[] messages) throws MessagingException {
        if(messages != null){
            for (Message message : messages) {
                messageMap.put(message.getSubject(),message);
                for (Flags.Flag flag : FLAGS) {
                    if(message.getFlags().contains(flag)){
                        Collection<Message> messageCollection = flagMessagesMap.get(flag);
                        messageCollection.add(message);
                    }
                }
            }
        }
    }

    public void registerMessage(Message message) throws MessagingException {
        messageMap.put(message.getSubject(),message);
    }

    public void registerFlagMessages(Flags.Flag flag,Collection<Message> messages){
        flagMessagesMap.put(flag,messages);
    }

    public Message getMessageBySubject(String subject){
        return messageMap.get(subject);
    }

    public Collection<Message> loadAllMessage(){
        return messageMap.values();
    }

    public Collection<Message> loadMessageByFlags(Flags.Flag... flags){
        Collection<Message> messages = null;
        if(flags != null){
            for (Flags.Flag flag : flags){
                messages = flagMessagesMap.get(flag);
            }
        }
        return messages;
    }
}
