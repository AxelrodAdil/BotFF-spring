package com.example.demo1;

import com.example.demo1.Model.currency;
import com.example.demo1.bot_backend.main_Bot;
import com.example.demo1.repos.currencyRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @create 05.09.2021 16:52
 */

@SpringBootApplication
public class Application {
    static ArrayList<String> arrayList_date=new ArrayList<>();
    static ArrayList<String> arrayList_currency_usd=new ArrayList<>();
    static ArrayList<String> arrayList_currency_eur=new ArrayList<>();
    static ArrayList<String> arrayList_currency_rub=new ArrayList<>();

    public static void main(String[] args) {
        String begin = "28.08.2021";
        String end = "09.09.2021";
        String url_national_bank = "https://nationalbank.kz/ru/exchangerates/ezhednevnye-oficialnye-rynochnye-kursy-valyut/report?rates%5B%5D=5&rates%5B%5D=6&rates%5B%5D=16&beginDate=" + begin + "&endDate=" + end;

        try {
            Document doc = Jsoup.connect(url_national_bank).get();
            Element table = doc.select("tbody").get(0);
            Elements rows = table.select("tr");

            for (Element row : rows) {
                Elements columns = row.select("td");
                arrayList_date.add(columns.get(0).text());
                arrayList_currency_usd.add(columns.get(2).text());
                arrayList_currency_eur.add(columns.get(4).text());
                arrayList_currency_rub.add(columns.get(6).text());
            }
        } catch (IOException exception) { //'org.jsoup.HttpStatusException' is a subclass of 'java.io.IOException'
            exception.printStackTrace();
        }

        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(currencyRepository currencyRepository) {
        return args -> {
            Long i_temp = 1L;
            int i = 0;
            int for_while = arrayList_date.size();
            while (for_while-->0){
                String date_temp=arrayList_date.get(i);
                String currency_temp_usd=arrayList_currency_usd.get(i);
                String currency_temp_eur=arrayList_currency_eur.get(i);
                String currency_temp_rub=arrayList_currency_rub.get(i);

                currency list_data = new currency(
                        i_temp,
                        date_temp,
                        currency_temp_usd,
                        currency_temp_eur,
                        currency_temp_rub
                );
                currencyRepository.saveAll(List.of(list_data));
                i_temp++;
                i++;
            }

            /*currencyRepository
                    .findById(2L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("ID 2 not found"));

            System.out.println("Delete");
            currencyRepository.deleteById(1L);*/

            System.out.println("Select all data");
            List<currency> students = currencyRepository.findAll();
            students.forEach(System.out::println);

            main_Bot main_bot = new main_Bot();
            main_bot.startBot(arrayList_date, arrayList_currency_usd,
                    arrayList_currency_eur, arrayList_currency_rub);
        };
    }
}