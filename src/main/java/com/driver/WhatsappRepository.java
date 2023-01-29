package com.driver;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class WhatsappRepository {
    private HashMap<String, User> userDB = new HashMap<>();;
    private List<Message> messageDB = new ArrayList<>();;
    private List<Group> groupDB = new ArrayList<>();;


    public String saveUser(User user) {
        if(userDB.containsKey(user.getMobile())){
            throw new RuntimeException("User already exists");
        }
        else{
            userDB.put(user.getMobile(),user);
            return "SUCCESS";
        }
    }

    public Group saveGroup(List<User> users) {
        int size = users.size();
        Group group = new Group();
        if(size == 2){
            group.setName(users.get(1).getName());
            group.setAdmin(users.get(0).getName());
        } else if (size > 2) {
            if(groupDB.size() == 0){
                group.setName("Group "+"1");
                group.setNumberOfParticipants(size);
            }
            else{
                int groupNo = groupDB.get(groupDB.size()-1).getName().charAt(6)-'0';
                group.setName("Group "+"++groupNo");
                group.setNumberOfParticipants(size);
                group.setAdmin(users.get(0).getName());
            }
        }

        group.setUsers(users);

        groupDB.add(group);

        return group;
    }

    public int saveMessage(String content) {
        Message message = new Message();
        message.setContent(content);
        if(messageDB.size() == 0){
            message.setId(1);
        }
        else{
            int id = messageDB.get(messageDB.size()-1).getId();
            message.setId(++id);
        }

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        message.setTimestamp(ts);

        messageDB.add(message);

        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group) {
        Group group2 = null;
        for(Group group1 : groupDB){
            if(group.getName().equals(group1.getName())){
                group2 = group1;
            }
        }

        if(group2 == null){
            throw new RuntimeException("Group does not exist");
        }
        else{
            User user = null;
            for(User user1 : group2.getUsers()){
                if(sender.getName().equals(user1.getName())){
                    user = user1;
                }
            }

            if(user == null){
                throw new RuntimeException("You are not allowed to send message");
            }
            else{
                int numberOfMessage = group2.getNumberOfMessages();
                numberOfMessage++;
                user.getMessageList().add(message);
                return numberOfMessage;
            }
        }

    }

    public String changeAdmin(User approver, User user, Group group) {
        Group group2 = null;
        for(Group group1 : groupDB){
            if(group.getName().equals(group1.getName())){
                group2 = group1;
            }
        }

        if(group2 == null){
            throw new RuntimeException("Group does not exist");
        }
        else{
            if(!group2.getAdmin().equals(approver)){
                throw new RuntimeException("Approver does not have rights");
            }
            else{
                User user1 = null;
                for(User user2 : group2.getUsers()){
                    if(user.getName().equals(user2.getName())){
                        user1 = user2;
                    }
                }

                if(user1 == null){
                    throw new RuntimeException("User is not a participant");
                }
                else{
                    group2.setAdmin(user1.getName());
                    return "SUCCESS";
                }
            }
        }
    }

    public int removeUser(User user) {
        Group group = null;
        for(Group group1 : groupDB){
            for(User user1 : group1.getUsers()){
                if(user1.getName().equals(user.getName())){
                    group = group1;
                }
            }
        }

        if(group == null){
            throw new RuntimeException("User not found");
        }
        else{
            if(group.getAdmin().equals(user.getName())){
                throw new RuntimeException("Cannot remove admin");
            }
            else{
                for (Message message : user.getMessageList()){
                    for(Message message1 : messageDB){
                        if(message1.getId() == message.getId()){
                            messageDB.remove(message);
                        }
                    }
                }
                for(User user1 : group.getUsers()){
                    if(user1.getName().equals(user.getName())){
                        group.getUsers().remove(user);
                    }
                }
                userDB.remove(user);
                int totalMessages = 0;
                totalMessages += group.getUsers().size();

                for(User user1 : group.getUsers()){
                    totalMessages += user1.getMessageList().size();
                }

                totalMessages += user.getMessageList().size();

                return totalMessages;
            }
        }
    }
}
