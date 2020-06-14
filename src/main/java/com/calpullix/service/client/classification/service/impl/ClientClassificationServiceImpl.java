package com.calpullix.service.client.classification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.calpullix.db.process.catalog.model.ProductStatus;
import com.calpullix.db.process.catalog.model.State;
import com.calpullix.db.process.customer.model.CustomerProfile;
import com.calpullix.db.process.customer.model.Customers;
import com.calpullix.db.process.customer.repository.CustomerProfileRepository;
import com.calpullix.db.process.customer.repository.CustomerRepository;
import com.calpullix.db.process.product.model.Product;
import com.calpullix.db.process.product.model.ProductHistory;
import com.calpullix.db.process.product.repository.ProductBranchRepository;
import com.calpullix.db.process.product.repository.ProductHistoryRepository;
import com.calpullix.db.process.profile.model.ProductRecomendationProfile;
import com.calpullix.db.process.profile.model.ProfileKmeans;
import com.calpullix.db.process.profile.model.ProfileKnearest;
import com.calpullix.db.process.profile.model.ProfilePromotions;
import com.calpullix.db.process.profile.model.ProfileRegression;
import com.calpullix.db.process.profile.repository.ProductRecomendationProfileRepository;
import com.calpullix.db.process.profile.repository.ProfileKMeansRepository;
import com.calpullix.db.process.profile.repository.ProfileKNearestRepository;
import com.calpullix.db.process.profile.repository.ProfilePromotionsRepository;
import com.calpullix.db.process.profile.repository.ProfileRegressionRepository;
import com.calpullix.service.client.classification.dao.ClientClassificationDAO;
import com.calpullix.service.client.classification.model.BranchDTO;
import com.calpullix.service.client.classification.model.ClientDetailResponseDTO;
import com.calpullix.service.client.classification.model.ClientPromotionsRequestDTO;
import com.calpullix.service.client.classification.model.ClientRequestDTO;
import com.calpullix.service.client.classification.model.ClientResponseDTO;
import com.calpullix.service.client.classification.model.DetailClientClassificationDTO;
import com.calpullix.service.client.classification.model.DetailRecomendationsDTO;
import com.calpullix.service.client.classification.model.DetailRecomendationsRequestDTO;
import com.calpullix.service.client.classification.model.MunicipalityDTO;
import com.calpullix.service.client.classification.model.ProductDTO;
import com.calpullix.service.client.classification.model.ProfileDTO;
import com.calpullix.service.client.classification.model.StateDTO;
import com.calpullix.service.client.classification.model.UsersRegressionDTO;
import com.calpullix.service.client.classification.repository.ProcedureInvoker;
import com.calpullix.service.client.classification.service.ClientClassificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientClassificationServiceImpl implements ClientClassificationService {

	private static final String COMMA = ", ";

	private static final String MONEY_SIGN = "$ ";

	private static final int LEFT_CLASSIFICATION = 0;
	
	private static final int LOYAL_CLASSIFICATION = 1;

	
	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private ClientClassificationDAO clientClassificationDAO;

	@Autowired
	private ProcedureInvoker procedureInvoker;

	@Autowired
	private ProfileKMeansRepository profileKMeansRepository;

	@Autowired
	private ProfileKNearestRepository profileKNearestRepository;

	@Autowired
	private ProfileRegressionRepository profileRegressionRepository;

	@Autowired
	private ProductRecomendationProfileRepository productRecomendationProfileRepository;

	@Autowired
	private ProductHistoryRepository productHistoryRepository;

	@Autowired
	private ProfilePromotionsRepository profilePromotionsRepository;

	@Autowired
	private ProductBranchRepository productBranchRepository;

	@Autowired
	private CustomerRepository customerRepository; 

	@Value("${app.profile-client-information-procedure}")
	private String profileClientInformationProcedure;

	
	
	@Value("${app.left-current-label-regression}")
	private String leftCurrentLabelRegression;

	@Value("${app.loyal-current-label-regression}")
	private String loyalCurrentLabelRegression;
	
	@Value("${app.left-last-label-regression}")
	private String leftLastLabelRegression;

	@Value("${app.loyal-last-label-regression}")
	private String loyalLastLabelRegression;
	
	
	
	@Value("${app.pagination-size}")
	private Integer paginationSize;

	@Value("${app.pagination-product-promotion-size}")
	private Integer paginationProductPromotionSize;

	@Override
	public ClientResponseDTO getProfileInformation(ClientRequestDTO request) {
		log.info(":: Service getProfileInformation {} ", request);
		final ClientResponseDTO result = new ClientResponseDTO();
		final Pageable pagination = PageRequest.of(request.getPage() - 1, paginationSize);
		final List<CustomerProfile> customerProfile = customerProfileRepository.findAllByActive(Boolean.TRUE,
				pagination);
		if (BooleanUtils.negate(CollectionUtils.isEmpty(customerProfile))) {
			final List<ClientDetailResponseDTO> list = new ArrayList<>();
			List<BranchDTO> branches;
			List<ProductDTO> products;
			List<StateDTO> states;
			List<MunicipalityDTO> municipalities;
			ClientDetailResponseDTO item;
			int itemCount = customerProfileRepository.getCountCustomerProfileByActive(Boolean.TRUE);
			int index;
			result.setItemCount(itemCount);
			result.setLastClassificationDate(customerProfile.get(0).getCreationdate());
			result.setIsUpdated(Boolean.TRUE);
			for (final CustomerProfile profile : customerProfile) {
				item = new ClientDetailResponseDTO();
				item.setId(profile.getId());
				item.setHeader(profile.getName());

				branches = clientClassificationDAO.getBranches(profile.getId());
				products = clientClassificationDAO.getProducts(profile.getId());
				states = clientClassificationDAO.getStates(profile.getId());
				municipalities = clientClassificationDAO.getMunicipalities(profile.getId());

				final StringBuilder branch = new StringBuilder();
				final StringBuilder product = new StringBuilder();
				final StringBuilder state = new StringBuilder();
				final StringBuilder municipality = new StringBuilder();
				for (index = 0; index < branches.size(); index++) {
					if (index + 1 < branches.size()) {
						branch.append(branches.get(index).getName() + COMMA);
						product.append(products.get(index).getName() + COMMA);
						state.append(State.of(states.get(index).getState()).getDescription() + COMMA);
						municipality.append(municipalities.get(index).getName() + COMMA);
					} else {
						branch.append(branches.get(index).getName());
						product.append(products.get(index).getName());
						state.append(State.of(states.get(index).getState()).getDescription());
						municipality.append(municipalities.get(index).getName());
					}
				}
				item.setBranch(branch.toString());
				item.setBestProducts(product.toString());
				item.setState(state.toString());
				item.setMunicipality(municipality.toString());
				procedureInvoker.executeProcedureBranchInformation(item, profile.getId(),
						profileClientInformationProcedure);
				list.add(item);
			}
			result.setClients(list);
		}
		return result;
	}
			
	@Override
	public DetailClientClassificationDTO getDetailProfileInformation(ClientPromotionsRequestDTO request) {
		log.info(":: Service getDetailProfileInformation {} ", request);
		final DetailClientClassificationDTO result = new DetailClientClassificationDTO();
		result.setId(request.getId());
		final CustomerProfile profile = new CustomerProfile();
		profile.setId(request.getId());
		final ProfileKmeans profileKmeans = profileKMeansRepository.findOneByIdprofileAndIsactive(profile,
				Boolean.TRUE);
		result.setKMeansGraphic(profileKmeans.getImage());
		result.setLabel(profileKmeans.getIdprofile().getName());
		result.setGraphicColor(profileKmeans.getColor());
		result.setKMeansDate(profileKmeans.getDate());
		final List<ProfileKnearest> profileKnearest = profileKNearestRepository.findAllByIsactive(Boolean.TRUE);
		profileKnearest.stream().filter(i -> i.getIdprofile().getId().equals(request.getId())).forEach(item -> {
			result.setKNearestGraphic(item.getImage());
			result.setKNearestDate(item.getDate());
			result.setKNeighbor(item.getKaccuracy() + ", k = " + item.getKneighborg());
		});
		final List<List<String>> rowsKNearest = new ArrayList<>();
		profileKnearest.stream().filter(i -> BooleanUtils.negate(i.getIdprofile().getId().equals(request.getId())))
				.forEach(item -> {
					final List<String> rowKNearest = new ArrayList<>();
					rowKNearest.add(item.getIdprofile().getName());
					rowKNearest.add(item.getNumbercustomers().toString());
					rowsKNearest.add(rowKNearest);
				});
		result.setLastKNearest(rowsKNearest);
		final List<List<String>> rowsCurrentKNearest = new ArrayList<>();
		profileKnearest.stream().filter(i -> i.getIdprofile().getId().equals(request.getId())).forEach(item -> {
			final List<String> rowKNearest = new ArrayList<>();
			rowKNearest.add(item.getIdprofile().getName());
			rowKNearest.add(item.getNumbercustomers().toString());
			rowsCurrentKNearest.add(rowKNearest);
		});
		result.setCurrentKNearest(rowsCurrentKNearest);
		
		
		final ProfileRegression profileRegression = profileRegressionRepository.findOneByIdprofileAndIsactive(profile,
				Boolean.TRUE);
		result.setConfusionRegression(profileRegression.getImageconfusion());
		result.setRegressionDate(profileRegression.getDate());
		

		result.setQuantityLeftCurrent(profileRegression.getNumbercustomersleft() + " " + leftCurrentLabelRegression);
		result.setQuantityLoyalCurrent(profileRegression.getNumbercustomersloyal() + " " + loyalCurrentLabelRegression);
		
		result.setQuantityLeftLast(customerRepository.
				getNumberCustomersByIdprofileAndClassificationAndIdLessThan(
						profile, LEFT_CLASSIFICATION, profileRegression.getInitialcustomer().getId()) + " " + leftLastLabelRegression);
		result.setQuantityLoyalLast(customerRepository.
				getNumberCustomersByIdprofileAndClassificationAndIdLessThan(
						profile, LOYAL_CLASSIFICATION, profileRegression.getInitialcustomer().getId()) + " " + loyalLastLabelRegression);


		result.setItemCountLast(customerRepository.getNumberCustomersByIdprofileAndIdLessThan(
				profile, profileRegression.getInitialcustomer().getId()));
		result.setItemCountCurrent(customerRepository.getNumberCustomersByIdprofileAndIdGreaterThan(
				profile, profileRegression.getInitialcustomer().getId()));
				
		
		Pageable pagination = PageRequest.of(0, paginationSize);
		result.setRowsLast(getCustomerRows(customerRepository.
				findAllByIdprofileAndIdLessThan(profile, profileRegression.getInitialcustomer().getId(), pagination)));
		result.setRowsCurrent(getCustomerRows(customerRepository.
				findAllByIdprofileAndIdGreaterThan(profile, profileRegression.getInitialcustomer().getId(), pagination)));
		
		
				

		final List<ProfileDTO> profiles = new ArrayList<>();
		profileKnearest.stream().forEach(i -> {
			final ProfileDTO item = new ProfileDTO();
			item.setId(i.getId());
			item.setName(i.getIdprofile().getName());
			profiles.add(item);
		});
		result.setProfiles(profiles);

		pagination = PageRequest.of(0, paginationProductPromotionSize);

		final Optional<List<ProductRecomendationProfile>> productRecomendation = productRecomendationProfileRepository
				.findAllByIdprofileAndIsactive(profile, Boolean.TRUE, pagination);
		int itemCount = 0;
		if (productRecomendation.isPresent()) {
			itemCount = productRecomendationProfileRepository.getCountByIdprofileAndIsactive(profile, Boolean.TRUE);
			final List<List<String>> tableProductRows = new ArrayList<>();
			final List<Product> product = new ArrayList<>();
			productRecomendation.get().forEach(item -> {
				final List<String> row = new ArrayList<>();
				row.add(item.getIdproduct().getId().toString());
				row.add(item.getIdproduct().getName());
				product.add(item.getIdproduct());
				tableProductRows.add(row);
			});
			final List<ProductHistory> productsHistory = productHistoryRepository
					.findPurchasePriceByIdproductAndStatusIn(product, ProductStatus.ACTIVE.getId());
			int index = 0;
			for (final List<String> row : tableProductRows) {
				row.add(MONEY_SIGN + productsHistory.get(index).getSaleprice());
				row.add(MONEY_SIGN + productsHistory.get(index).getPurchaseprice());
				index++;
			}
			result.setProductsProfile(tableProductRows);
			result.setProductsDate(productRecomendation.get().get(0).getDate());
		}
		result.setItemCountProducts(itemCount);

		
		final Page<ProfilePromotions> profilePromotions = profilePromotionsRepository
				.findAllByIdprofileAndActive(profile, Boolean.TRUE, pagination);
		log.info(":: PROFILE {} {} ",
				profilePromotions.getContent().get(0).getIdprofile().getName(),
				profilePromotions.getContent().get(0).getIdprofile().getId());
		itemCount = 0;

		if (profilePromotions.hasContent()) {
			itemCount = profilePromotionsRepository.getCountProfilePromotionsByIdProfileAndActive(profile,
					Boolean.TRUE);
			final List<List<String>> tablePromotionsRows = new ArrayList<>();
			profilePromotions.forEach(item -> {
				final List<String> promotions = new ArrayList<>();
				promotions.add(item.getIdpromotion().getName());
				promotions.add(item.getIdpromotion().getCreationdate());
				promotions.add(item.getIdpromotion().getEnddate());
				promotions.add(productBranchRepository.getNumberCostumersByIdpromotion(item.getIdpromotion()) + "");
				tablePromotionsRows.add(promotions);
			});
			result.setPromotionsProfile(tablePromotionsRows);
			result.setPromotionsDate(profilePromotions.get().findFirst().get().getCreationdate());

			log.info(":: Rows Promotions {} :::: {} ", itemCount, tablePromotionsRows);
		}
		

		result.setItemCountPromotions(itemCount);
		return result;
	}

	@Override
	public DetailRecomendationsDTO getDetailProductRecomendations(DetailRecomendationsRequestDTO request) {
		log.info(":: Service getDetailProductRecomendations {} ", request);
		final Pageable pagination = PageRequest.of(request.getPage() - 1, paginationProductPromotionSize);
		final CustomerProfile idprofile = new CustomerProfile();
		idprofile.setId(request.getIdProfile());
		final Optional<List<ProductRecomendationProfile>> productRecomendation = productRecomendationProfileRepository
				.findAllByIdprofileAndIsactive(idprofile, Boolean.TRUE, pagination);
		final DetailRecomendationsDTO result = new DetailRecomendationsDTO();
		if (productRecomendation.isPresent()) {
			final List<List<String>> tableProductRows = new ArrayList<>();
			final List<Product> product = new ArrayList<>();
			productRecomendation.get().forEach(item -> {
				final List<String> row = new ArrayList<>();
				row.add(item.getIdproduct().getId().toString());
				row.add(item.getIdproduct().getName());
				product.add(item.getIdproduct());
				tableProductRows.add(row);
			});
			final List<ProductHistory> productsHistory = productHistoryRepository
					.findPurchasePriceByIdproductAndStatusIn(product, ProductStatus.ACTIVE.getId());
			int index = 0;
			for (final List<String> row : tableProductRows) {
				row.add(MONEY_SIGN + productsHistory.get(index).getSaleprice());
				row.add(MONEY_SIGN + productsHistory.get(index).getPurchaseprice());
				index++;
			}
			result.setRecomendations(tableProductRows);
		}
		return result;
	}

	@Override
	public DetailRecomendationsDTO getDetailPromotionRecomendations(DetailRecomendationsRequestDTO request) {
		log.info(":: Service getDetailPromotionRecomendations {} ", request);
		final DetailRecomendationsDTO result = new DetailRecomendationsDTO();
		final Pageable pagination = PageRequest.of(request.getPage() - 1, paginationProductPromotionSize);
		final CustomerProfile profile = new CustomerProfile();
		profile.setId(request.getIdProfile());
		final Page<ProfilePromotions> profilePromotions = profilePromotionsRepository
				.findAllByIdprofileAndActive(profile, Boolean.TRUE, pagination);
		if (profilePromotions.hasContent()) {
			final List<List<String>> tablePromotionsRows = new ArrayList<>();
			profilePromotions.forEach(item -> {
				final List<String> promotions = new ArrayList<>();
				promotions.add(item.getIdpromotion().getName());
				promotions.add(item.getIdpromotion().getCreationdate());
				promotions.add(item.getIdpromotion().getEnddate());
				promotions.add(productBranchRepository.getNumberCostumersByIdpromotion(item.getIdpromotion()) + "");
				tablePromotionsRows.add(promotions);
			});
			result.setRecomendations(tablePromotionsRows);
		}
		return result;	
	}

	@Override
	public ClientResponseDTO updateClientsToClassify(ClientRequestDTO request) {
		log.info(":: Service updateClientsToClassify {} ", request);
		final ClientResponseDTO result = new ClientResponseDTO();
		result.setIsUpdated(Boolean.TRUE);
		return result;
	}
	
	@Override
	public UsersRegressionDTO getUsersRegression(DetailRecomendationsRequestDTO request) {
		log.info("*********************** getUsersRegression {} ", request);
		final UsersRegressionDTO result = new UsersRegressionDTO();
		final CustomerProfile idprofile = new CustomerProfile();
		idprofile.setId(request.getIdProfile());
		ProfileRegression profileRegression = profileRegressionRepository.
							findOneByIdprofileAndIsactive(idprofile, Boolean.TRUE);
		final Pageable pagination = PageRequest.of(request.getPage() - 1, paginationSize);
		final List<Customers> customers;
		if (BooleanUtils.negate(request.getIsLast() == null) && request.getIsLast()) {
			customers = customerRepository.findAllByIdprofileAndIdGreaterThan(
					idprofile, profileRegression.getInitialcustomer().getId(), pagination);
		} else {
			customers = customerRepository.findAllByIdprofileAndIdLessThan(
					idprofile, profileRegression.getInitialcustomer().getId(), pagination);
		}
		
		result.setUsersRegression(getCustomerRows(customers));
		
		return result;
	}

	private List<List<String>> getCustomerRows(List<Customers> customers) {
		final List<List<String>> result = new ArrayList<>();
		List<String> row;
		for (final Customers customer : customers) {
			row = new ArrayList<>();
			row.add(customer.getId().toString());
			row.add(customer.getClassification().toString());
			result.add(row);
		}
		return result;
	}
	
}
