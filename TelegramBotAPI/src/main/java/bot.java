import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class bot extends TelegramLongPollingBot{

        private String old_balance = "0";
        private String balance;
        final public String help_message = "Hello i am a bot";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new bot());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message , String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            sendMessage(sendMessage);
        }catch(TelegramApiException e ){
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()){
            switch(message.getText()){
                case "/help":
                    sendMsg(message , help_message);
                    break;

                case "/stop":
                    sendMsg(message , "0");
                    break;

                case "/balance":
                    sendMsg(message , "set your balance");
                    break;
            }
        }
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/stop"));
        keyboardFirstRow.add(new KeyboardButton("/balance"));


        keyboardRows.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public void testMessage(Message message){




    }


    public void setBalance(Message message){

       old_balance = balance;
       balance = message.getText().toString();

    }


    public String getBotUsername() {
        return "WalletTracker";
    }

    public String getBotToken() {
        return "740589818:AAEMS-fq6w9rbTDOBqHWCKUwuJStjzXOqLk";
    }
}
