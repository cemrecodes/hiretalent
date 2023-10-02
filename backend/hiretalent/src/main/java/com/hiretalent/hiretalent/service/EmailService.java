package com.hiretalent.hiretalent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.JobApplicationStatus;

@Service
public class EmailService {
	
    @Value("${spring.mail.username}")
    private String from;
      
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String title, String nameSurname, String email, JobApplicationStatus status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject(title + " Pozisyonu - İş Başvurunuz Hakkında");
        
        if(status == JobApplicationStatus.REJECTED) {
        	message.setText("Sayın "
        			+ nameSurname
        			+ ",\r\n"
        			+ "\r\n"
        			+ "Başvurunuz için teşekkür ederiz. Maalesef, bu sefer için başvurunuz olumsuz sonuçlandı. Deneyiminizi ve ilginizi takdir ediyoruz.\r\n"
        			+ "\r\n"
        			+ "Sizi ilerideki fırsatlarımız için göz önünde bulunduracağız. İlginiz için tekrar teşekkür eder, kariyerinizde başarılar dileriz.\r\n"
        			+ "\r\n"
        			+ "Saygılarımızla,\r\n"
        			+ "\r\n"
        			+ "OBSS Technology");
        }
        else if( status == JobApplicationStatus.ACCEPTED) {
        	message.setText("Sayın "
        			+ nameSurname
        			+ ",\r\n"
        			+ "\r\n"
        			+ "Öncelikle şirketimize gösterdiğiniz ilgi için teşekkür ederiz. Başvurunuz, değerli yeteneklerinizi ve deneyiminizi göz önünde bulundurarak olumlu bir şekilde değerlendirilmiştir.\r\n"
        			+ "\r\n"
        			+ "Sizi şirketimizde çalışmak için potansiyel bir aday olarak görmekten heyecan duyuyoruz. Lütfen, mülakat süreci ve diğer adımlar hakkında bilgi almak için hazırlıklı olun..\r\n"
        			+ "\r\n"
        			+ "Tekrar teşekkür eder, en kısa sürede sizinle iletişime geçeceğimizi bilmenizi isterim.\r\n"
        			+ "\r\n"
        			+ "Saygılarımızla,\r\n"
        			+ "\r\n"
        			+ "OBSS Technology");
        	
        }else {
        	message.setText("Sayın "
        			+ nameSurname
        			+ ",\r\n"
        			+ "\r\n"
        			+ "Başvurunuz, şirketimiz tarafından başarıyla alınmıştır. Gösterdiğiniz ilgi ve güven için teşekkür ederiz.\r\n"
        			+ "\r\n"
        			+ "Başvurunuz, adayları değerlendiren ekibimiz tarafından incelenmektedir. Şu anda süreç devam etmektedir ve başvurunuzla ilgili olarak ilerleyen aşamalarda sizinle iletişime geçeceğiz.\r\n"
        			+ "\r\n"
        			+ "Sizi değerlendirme sürecine dahil etmekten mutluluk duyuyoruz ve başvurunuzla ilgili gelişmeleri paylaşmak için sabırsızlanıyoruz.\r\n"
        			+ "\r\n"
        			+ "İlginiz için tekrar teşekkür ederiz."
        			+ "\r\n"
        			+ "Saygılarımızla,\r\n"
        			+ "\r\n"
        			+ "OBSS Technology");
        }
        javaMailSender.send(message);
    }
	
}
