package com.notification.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApiServiceApplication.class, args);
	}

  /*
  1. Packages
  2. implement Abstract generic global Exception handler
  3. Configure Notification Context To propagate common Data using Threat Local
  4. Configure Auth Filter To Filter And Process Context Data
  5. Design Entity & Request / Response
  6. Implement Service & Dao Layer
  7. Implement Create Notification API

   */
}
