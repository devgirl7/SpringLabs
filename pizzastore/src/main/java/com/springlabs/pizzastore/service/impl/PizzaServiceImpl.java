package com.springlabs.pizzastore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springlabs.pizzastore.domain.Pizza;
import com.springlabs.pizzastore.domain.Category;
import com.springlabs.pizzastore.repository.impl.JpaCategoryRepository;
import com.springlabs.pizzastore.repository.impl.JpaPizzaRepository;
import com.springlabs.pizzastore.service.PizzaService;


@Service("pizzaService")
public class PizzaServiceImpl implements PizzaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JpaPizzaRepository pizzaRepository;

    @Autowired
    private JpaCategoryRepository pizzaCategoryRepository;

    public PizzaServiceImpl(JpaPizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @Override
    public List<Pizza> findAllPizzas() {
        logger.debug("Begin operation: findAllPizzas ");
        List<Pizza> pizzaList = pizzaRepository.findAll();
//        List<PizzaResponse> listBases = findAll.stream().map(LayerMapping.getPizzaToPizzaResponseMapperLambdaFunction())
//                .collect(Collectors.toList());
        logger.debug("End operation: findAllPizzas: response:{} ", pizzaList);
        return pizzaList;
    }

    @Override
    public Pizza savePizza(final Pizza pizza) {
        logger.debug("Begin operation: savePizza: request:{} ", pizza);
        Pizza pizzaSaved = pizzaRepository.save(pizza);
        logger.debug("End operation: savePizza: response:{} ", pizza);
        return pizzaSaved;
    }


	@Override
	public Pizza getPizza(long pizzaId) {
		logger.debug("Begin operation: getPizza: request:{} ", pizzaId);
        Pizza pizza = pizzaRepository.getOne(pizzaId);
        List<Category> otherCategories = pizzaCategoryRepository.fetchSecondaryPizzaCategories(pizzaId);
        logger.debug("Fetched non-default categories: "+otherCategories);
        pizza.setSecondaryCategories(otherCategories);
        logger.debug("End operation: getPizza: response:{} ", pizza);
		return pizza;
	}

	@Override
	public List<Category> findAllPizzaCategories() {
		logger.debug("Begin operation: getPizzaCategories");
		List<Category> pizzaCategoryList = pizzaCategoryRepository.findAll();
        logger.debug("End operation: getPizzaCategories: response:{} ", pizzaCategoryList);
		return pizzaCategoryList;
	}
	
	@Override
	public Category getPizzaCategory(final long categoryId) {
		logger.debug("Begin operation: getPizzaCategory: request:{} ", categoryId);
        Category pizzaCategory = pizzaCategoryRepository.getOne(categoryId);
        logger.debug("End operation: getPizzaCategory: response:{} ", pizzaCategory);
		return pizzaCategory;
	}

}