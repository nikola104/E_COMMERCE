CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint  NOT NULL,
  `user_id` bigint  NOT NULL,
  `rating` int NOT NULL,
  `comment` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reviews_products_idx` (`product_id`),
  KEY `fk_reviews_users_idx` (`user_id`),
  CONSTRAINT `fk_reviews_products` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_reviews_users` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;