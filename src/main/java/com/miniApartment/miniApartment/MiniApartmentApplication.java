package com.miniApartment.miniApartment;

import com.miniApartment.miniApartment.Controller.CassoAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class MiniApartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniApartmentApplication.class, args);
//		String apiKey = "AK_CS.3d8aad6058b811ef9477cf31e322e4f8.VYTRwhzv48gwUqtI8VzdzmvSmNJOmyg5FtcDGlPfOtR650p3wKRSyH5gt3kc2jgN1Lar67jz";
//		CassoAPI cassoAPI = new CassoAPI(apiKey);
//
//		try {
//			// Lấy danh sách giao dịch
//			String transactions = cassoAPI.getTransactions("2024-01-01", "2024-07-31");
//			System.out.println("Transactions: " + transactions);
//
//			// Tạo webhook
//			String webhookResponse = cassoAPI.createWebhook("https://spotty-comics-tan.loca.lt");
//			System.out.println("Webhook Response: " + webhookResponse);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:5173");
//			}
//		};
//	}
}
