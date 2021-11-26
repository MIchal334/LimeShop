package com.lime.lime.shop.mailSender;

import com.lime.lime.shop.model.entity.AddressEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@EnableAsync
@Component
public class MailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Async(value = "emailSenderExecutor")
    public void sendEmailToProducerAboutNewOrder(OrderEntity order) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(message, true);

        mail.setTo(order.getProducer().getEmail());
        mail.setFrom(new InternetAddress("LimeTransaction@outlook.com", false));

        mail.setSubject("New Order");
        mail.setText("Client "+order.getClient().getName() + " " + order.getClient().getLastName() +
                " has made new order.\n Lime type: "+order.getLime().getType()+"\n"+"amout: "+order.getLime().getAmount()+
                "date of receipt: " + dataFormater(order.getDateOfReceipt())+
                "\nAddress of client: "+ addressFormater(order.getClient().getAddress())+
                "\nYou can find more information in LimeShop system.");
        emailSender.send(message);
    }

    public void sendEmailToClientAboutChangeOrderStatus(OrderEntity order) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(message, true);
        mail.setTo(order.getProducer().getEmail());
        mail.setFrom(new InternetAddress("LimeTransaction@outlook.com", false));

        mail.setSubject("Change Order status");
        mail.setText("Producer: "+ order.getProducer().getName() + " " + order.getProducer().getLastName()+
                " has changed status your order to "+ order.getStatus().getStatusName()+"\n Number of order is "+ order.getId()
                +".You can find more information in LimeShop system.");
        emailSender.send(message);
    }



    private String dataFormater(LocalDateTime dateTime){
        String date = "";
        date = date +dateTime.getDayOfMonth() + "-" + dateTime.getMonthValue() +"-"+ dateTime.getYear()+
                "\n time: "+ dateTime.getHour()+":"+dateTime.getMinute()+":"+dateTime.getSecond();
        return date;
    }

    private String addressFormater(AddressEntity address){
        String addressString ="";
        addressString = addressString + "City: "+ address.getCity()+"\n Street: "+address.getStreet()+
                "\nHouse number:"+ address.getHouseNumber()+ "\nPost code: "+address.getPostCode();
        return  addressString;
    }


}
