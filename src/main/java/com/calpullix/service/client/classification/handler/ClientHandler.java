package com.calpullix.service.client.classification.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.client.classification.model.ClientPromotionsRequestDTO;
import com.calpullix.service.client.classification.model.ClientRequestDTO;
import com.calpullix.service.client.classification.model.DetailRecomendationsRequestDTO;
import com.calpullix.service.client.classification.service.ClientClassificationService;
import com.calpullix.service.client.classification.util.AbstractWrapper;
import com.calpullix.service.client.classification.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ClientHandler {

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private ClientClassificationService clientClassificationService;
	
	@Autowired
	private ValidationHandler validationHandler;


	@Timed(value = "calpullix.service.client.metrics", description = "Retriving clients")
	public Mono<ServerResponse> getClients(ServerRequest serverRequest) {
		log.info(":: Get Clients handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input
						.flatMap(request -> AbstractWrapper
								.async(() -> clientClassificationService.getProfileInformation(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, ClientRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.client.detail.metrics", description = "Retriving client detail ")
	public Mono<ServerResponse> getClientDetail(ServerRequest serverRequest) {
		log.info(":: Get Detail Client Promotions {} ", serverRequest);
		return validationHandler.handle(
				input -> input
						.flatMap(request -> AbstractWrapper
								.async(() -> clientClassificationService.getDetailProfileInformation(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, ClientPromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.detail.product.recomendations.metrics", 
			description = "Retriving detail product recomendations")
	public Mono<ServerResponse> getDetailProductRecomendations(ServerRequest serverRequest) {
		log.info(":: Get product recomendations {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> {
					return clientClassificationService.getDetailProductRecomendations(request);
				}))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, DetailRecomendationsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.detail.promotion.recomendations.metrics", description = "Retriving detail promotion recomendations")
	public Mono<ServerResponse> getDetailPromotionRecomendations(ServerRequest serverRequest) {
		log.info(":: Get Promotion Recomendations {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> {
					return clientClassificationService.getDetailPromotionRecomendations(request);
				}))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, DetailRecomendationsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.client.classify.metrics", description = "Updating clients to classify")
	public Mono<ServerResponse> updateClientsToClassify(ServerRequest serverRequest) {
		log.info(":: Update Clients to Classify handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> {
					return clientClassificationService.updateClientsToClassify(request);
				}))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, ClientRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	
	@Timed(value = "calpullix.service.users.regresssion.metrics", description = "Retrieving customers regression information")
	public Mono<ServerResponse> getUsersRegression(ServerRequest serverRequest) {
		log.info(":: Get Users Regression handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> {
					return clientClassificationService.getUsersRegression(request);
				}))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, DetailRecomendationsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	
	
}
