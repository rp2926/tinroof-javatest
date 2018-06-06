package com.tinroof.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class TinroofApplication {
	private static final Logger logger = LoggerFactory.getLogger(TinroofApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TinroofApplication.class, args);
	}
}
