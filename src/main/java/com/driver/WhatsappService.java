package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) {
        User user = new User(name, mobile);
        return whatsappRepository.saveUser(user);
    }

    public Group createGroup(List<User> users) {
        return whatsappRepository.saveGroup(users);
    }

    public int createMessage(String content) {
        return whatsappRepository.saveMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) {
        return whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) {
        return whatsappRepository.changeAdmin(approver,user,group);
    }

    public int removeUser(User user) {
        return whatsappRepository.removeUser(user);
    }

}
