package org.example.spring_booking_bot;

import org.example.spring_booking_bot.commands.LoginCommand;
import org.example.spring_booking_bot.commands.WorkerCommand;
import org.example.spring_booking_bot.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class Bot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "max_spring_medicine_bot";
    }

    @Override
    public String getBotToken(){
        return "7611560847:AAFcDJviBq8XnLjsSa3jxyiOIwFIxThMiKU";
    }

    @Override
    public void onUpdateReceived(Update update){
        KeyboardRow k=new KeyboardRow();
            k.add(new KeyboardButton("Log In"));
        k.add(new KeyboardButton("Записаться к врачу"));
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите действие");

        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<WorkerCommand> list=new ArrayList<>();
        list.add(new LoginCommand());

        for(WorkerCommand w:list){
            if(w.start(update)!=null)
                sendMessage=w.start(update);
            break;
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
