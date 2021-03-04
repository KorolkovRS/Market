package ru.korolkovrs.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secured.properties")
public class MarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
//		1) Сделать профилировщик
//		2) Cоздать отдельную таблицу адресов с полями id, address, user_id.
//			Таким образом у каждого юзера будет несколько адресов доставки, из которых можно выбрать нужный.
//		Также в таблице orders добавляется колонка address_id.
	}
}
