package com.calpullix.service.client.classification.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.client.classification.handler.ClientHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-get-clients}")
	private String pathGetClients;
	
	@Value("${app.path-update-clients-classify}")
	private String pathUpdateClientsToClassify;
	
	@Value("${app.path-get-client-detail}")
	private String pathGetClientDetail;

	@Value("${app.path-update-client-promotions}")
	private String pathUpdateClientPromotions;
	
	@Value("${app.path-retrieve-client-product-recomendations}")
	private String pathRetrieveClientProductRecomendations;
	
	@Value("${app.path-retrieve-client-promotions-recomendations}")
	private String pathRetrieveClientPromotionsRecomendations;
	
	@Value("${app.path-retrieve-users-regression}")
	private String pathRetrieveUsersRegression;
	
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(ClientHandler loginHandler) {
		return route(POST(pathGetClients), loginHandler::getClients)
				.and(route(POST(pathUpdateClientsToClassify), loginHandler::updateClientsToClassify))
				.and(route(POST(pathGetClientDetail), loginHandler::getClientDetail))
				.and(route(POST(pathRetrieveClientProductRecomendations), loginHandler::getDetailProductRecomendations))
				.and(route(POST(pathRetrieveClientPromotionsRecomendations), loginHandler::getDetailPromotionRecomendations)
				.and(route(POST(pathRetrieveUsersRegression), loginHandler::getUsersRegression)));
	}
	
}
