package com.vitaliy.podgorny.triolanInfo;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static final String LOGIN = "your_logon";
    private static final String PASSWORD = "your_password";
    private static final String LOGIN_URL = "https://triolan.name/LP.aspx";
    private static final String NEED_URL = "https://triolan.name/Registration.aspx";

    public static void main(String[] args) throws Exception {
        logger.info("Start process\ncreate client");
        LoginToWebSite client = new LoginToWebSite(LOGIN, PASSWORD);
        logger.info("login -->");
        client.login(LOGIN_URL);
        logger.info("scrape web page");
        String html = client.get(NEED_URL);
        parceAndPrintResult(html);
    }

    private static void parceAndPrintResult(String html){
        logger.info("Договор "+Jsoup.parse(html).getElementsByTag("ul").get(0).getElementsByTag("li").get(0).ownText());
        logger.info("Баланс "+Jsoup.parse(html).getElementsByTag("ul").get(0).getElementsByTag("li").get(1).ownText());
        logger.info("Оплачено до "+Jsoup.parse(html).getElementsByTag("ul").get(0).getElementsByTag("li").get(2).ownText());
    }

}