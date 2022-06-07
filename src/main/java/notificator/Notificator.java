package notificator;

import response.OfferResponse;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Notificator {

    private SystemTray tray;

    public Notificator(){
        if(SystemTray.isSupported())
            tray = SystemTray.getSystemTray();
    }

    public void notify(Set<OfferResponse> offersToNotify){
        if(SystemTray.isSupported())
            offersToNotify.forEach(this::displayNotification);
        else
            System.err.println("[WARNING] Notifications are not supported by your system");
    }

    private void displayNotification(OfferResponse offer) {
        Image image = Toolkit.getDefaultToolkit().createImage("");
        TrayIcon trayIcon = new TrayIcon(image, "TGTG - notification");

        try {
            tray.add(trayIcon);
            trayIcon.displayMessage(offer.getDisplayName(), getNotificationMessage(offer), TrayIcon.MessageType.INFO);
            sleep();
            tray.remove(trayIcon);
        } catch (AWTException ex){
            ex.printStackTrace();;
        }
    }

    private String getNotificationMessage(OfferResponse offer){
        return offer.getPickupLocation().getAddress().getAddressLine() +
                "\nPrice: " + offer.getItem().getPriceIncludingTaxes() +
                "\nItems available: " + offer.getItemsAvailable();
    }

    private void sleep(){
        try{
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

}
