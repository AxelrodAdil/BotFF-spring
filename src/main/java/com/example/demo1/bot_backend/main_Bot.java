package com.example.demo1.bot_backend;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @create 05.09.2021 18:38
 */

public class main_Bot extends TelegramLongPollingBot{
    /**
     *
     * https://core.telegram.org/bots
     * https://core.telegram.org/bots/api
     * https://qna.habr.com/q/967189
     * https://nationalbank.kz/ru/exchangerates/ezhednevnye-oficialnye-rynochnye-kursy-valyut
     * https://stackoverflow.com/questions/7421612/slf4j-failed-to-load-class-org-slf4j-impl-staticloggerbinder
     *
     */

    static ArrayList<String> getByTable_Date = new ArrayList<>();
    static ArrayList<String> getByTable_USD = new ArrayList<>();
    static ArrayList<String> getByTable_EUR = new ArrayList<>();
    static ArrayList<String> getByTable_RUB = new ArrayList<>();

    public void startBot(ArrayList<String> date, ArrayList<String> usd,
                         ArrayList<String> eur, ArrayList<String> rub) throws TelegramApiException {

        getByTable_Date = date;
        getByTable_USD = usd;
        getByTable_EUR = eur;
        getByTable_RUB = rub;
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(new main_Bot());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            backend_buttons.setButtons_first(sendMessage);
            execute(sendMessage);
        }catch (TelegramApiException | IOException e){
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            System.out.println(message.getText());

            if (message.getText().equals("/start")) {
                sendMessage(message, "Hello. I am Freedom Finance Adil bot." +
                        "\nWith my help, you can view the history of changes in exchange rates (USD, EUR, RUB) in KZT for 10 days."); // * _
            }

            else if (message.getText().equals("About Creator")) {
                sendMessage(message, "Myktybek Adil");
            }

            else {
                String currency = message.getText();
                sendMessage(message, "At your request");

                StringBuilder sb=new StringBuilder();
                double currency_calc_1 = 0, currency_calc_2 = 0;
                switch (currency){
                    case "USD":
                        for (int i=0; i<getByTable_Date.size();i++){
                            sb.append(getByTable_Date.get(i)).append(":  ").append(getByTable_USD.get(i));
                            if(i!=0){
                                currency_calc_1 = Double.parseDouble(getByTable_USD.get(i));
                                currency_calc_2 = Double.parseDouble(getByTable_USD.get(i-1));
                                System.out.println((currency_calc_2*0.1)+currency_calc_2 + " --- " + currency_calc_1);
                                if((currency_calc_2*0.1)+currency_calc_2<currency_calc_1){
                                    sb.append(" - ").append("The exchange rate has changed by more than 10 percent");
                                }
                            }
                            sb.append("\n");
                        }
                        break;
                    case "EUR":
                        for (int i=0; i<getByTable_Date.size();i++){
                            sb.append(getByTable_Date.get(i)).append(":  ").append(getByTable_EUR.get(i));
                            if(i!=0){
                                currency_calc_1 = Double.parseDouble(getByTable_EUR.get(i));
                                currency_calc_2 = Double.parseDouble(getByTable_EUR.get(i-1));
                                System.out.println((currency_calc_2*0.1)+currency_calc_2 + " --- " + currency_calc_1);
                                if((currency_calc_2*0.1)+currency_calc_2<currency_calc_1){
                                    sb.append(" - ").append("The exchange rate has changed by more than 10 percent");
                                }
                            }
                            sb.append("\n");
                        }
                        break;
                    case "RUB":
                        for (int i=0; i<getByTable_Date.size();i++){
                            sb.append(getByTable_Date.get(i)).append(":  ").append(getByTable_RUB.get(i));
                            if(i!=0){
                                currency_calc_1 = Double.parseDouble(getByTable_RUB.get(i));
                                currency_calc_2 = Double.parseDouble(getByTable_RUB.get(i-1));
                                System.out.println((currency_calc_2*0.1)+currency_calc_2 + " --- " + currency_calc_1);
                                if((currency_calc_2*0.1)+currency_calc_2<currency_calc_1){
                                    sb.append(" - ").append("The exchange rate has changed by more than 10 percent");
                                }
                            }
                            sb.append("\n");
                        }
                        break;
                    default:
                        sb.append("Select currency");
                }

                String answer = sb.toString();
                sb.setLength(0);
                sendMessage(message, answer);
            }
        }
    }

    public String getBotUsername() {
        return "Freedom_Finance_Adil_bot";
    }

    public String getBotToken() {
        return "1964504177:AAGzyC65lGx6rsQiIKTFi74dSoxoZTur1RA";
    }
}