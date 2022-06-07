package controller;

import notificator.Notificator;
import response.ListOfferResponse;
import response.OfferResponse;
import service.UserService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AsyncController {

    private final UserService userService = new UserService();
    private final Map<String, OfferResponse> offers = new LinkedHashMap<>();
    private final Set<OfferResponse> notifications = new LinkedHashSet<>();
    private final Notificator notificator = new Notificator();
    private final Random random = new Random();
    private final SimpleDateFormat timePattern = new SimpleDateFormat("HH:mm:ss");


    public void run() throws IOException {
        while(true){
            ListOfferResponse activeOffers = userService.getActiveOffers();

            if(!isEmpty(activeOffers)) {
                prepareNotifications(activeOffers);

                if (Boolean.parseBoolean(System.getProperty("console_print")))
                    consolePrint(activeOffers);

                if (Boolean.parseBoolean(System.getProperty("windows_notifications")))
                    asyncNotify();
            }
            runWatcherInterval();
        }
    }

    private boolean isEmpty(ListOfferResponse activeOffers){
        if(activeOffers.getItems().isEmpty() && Boolean.parseBoolean(System.getProperty("console_print"))){
            System.out.println("==========" + getCurrentTime() + "==========");
            System.out.println("No offers available");
            offers.clear();
        }
        return activeOffers.getItems().isEmpty();
    }

    private String getCurrentTime(){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "[" + time + "]";
    }

    private void prepareNotifications(ListOfferResponse activeOffers){
        activeOffers.getItems().stream()
                .filter(offer -> !offers.containsKey(offer.getItem().getItemId()))
                .forEach(offer -> {
                    offers.put(offer.getItem().getItemId(), offer);
                    notifications.add(offer);
                });
    }

    private void consolePrint(ListOfferResponse activeOffers){
        System.out.println("==========" + getCurrentTime() + "==========");
        activeOffers.getItems()
                .forEach(offer -> {
                    System.out.println("\t" + offer.getStore().getStoreName());
                    System.out.println("\t" + offer.getPickupLocation().getAddress().getAddressLine());
                    System.out.println("\tValue: " + offer.getItem().getValueIncludingTaxes());
                    System.out.println("\tPrice: " + offer.getItem().getPriceIncludingTaxes());
                    System.out.println("\tItems available: " + offer.getItemsAvailable());
                    System.out.println("------------------------------");
                });
    }

    private void asyncNotify(){
        Set<OfferResponse> notificationsCopy = new HashSet<>(notifications);
        Thread newThread = new Thread(() -> {
            notificator.notify(notificationsCopy);
        });
        newThread.start();
        notifications.clear();
    }

    private void runWatcherInterval(){
        try {
            long interval = getWatcherInterval();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, (int) interval);
            System.out.println("[INFO] Next check at: " + timePattern.format(calendar.getTime()));
            TimeUnit.SECONDS.sleep(interval);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private long getWatcherInterval(){
        long minWatcherInterval = Long.parseLong(System.getProperty("min_watcher_interval"));
        long maxWatcherInterval = Long.parseLong(System.getProperty("max_watcher_interval"));

        if(minWatcherInterval < 1) {
            minWatcherInterval = 1;
            System.err.println("[WARNING] Invalid minimal watcher interval. Run with default: 1min.");
        }
        if(maxWatcherInterval < 2) {
            maxWatcherInterval = 5;
            System.err.println("[WARNING] Invalid maximum watcher interval. Run with default: 5min.");
        }

        maxWatcherInterval *= 60;
        minWatcherInterval *= 60;

        return random.nextInt((int) (maxWatcherInterval - minWatcherInterval)) + minWatcherInterval;
    }
}
