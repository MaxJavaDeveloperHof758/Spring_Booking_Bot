package org.example.spring_booking_bot.commands;

import org.example.spring_booking_bot.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

@Component
public class LoginCommand implements WorkerCommand {

    @Override
    public SendMessage start(Update update) {
        if(!update.getMessage().getText().equals("Log In")
            &&!update.getMessage().getText().equals("Оставить свое имя")
            &&!update.getMessage().getText().equals("Остаться анонимом")){
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите действие");
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        if (update.getMessage().getText().equals("Log In")) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton("Оставить свое имя"));
            keyboardRow.add(new KeyboardButton("Остаться анонимом"));

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        UserModel userModel=new UserModel();
        userModel.setUsername(update.getMessage().getFrom().getUserName());
        userModel.setTgId(update.getMessage().getFrom().getId().toString());
        if(update.getMessage().getText().equals("Остаться анонимом")){

        }
        if(update.getMessage().getText().equals("Оставить свое имя")){
            userModel.setName(update.getMessage().getFrom().getFirstName());
        }
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите действие");
        return sendMessage;
    }
}
