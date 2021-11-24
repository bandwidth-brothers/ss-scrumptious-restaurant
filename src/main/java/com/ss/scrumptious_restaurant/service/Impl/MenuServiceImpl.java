package com.ss.scrumptious_restaurant.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.MenuCategoryRepository;
import com.ss.scrumptious_restaurant.dao.MenuItemRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dao.TagRepository;
import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious.common_entities.entity.MenuCategory;
import com.ss.scrumptious.common_entities.entity.Menuitem;
import com.ss.scrumptious.common_entities.entity.Restaurant;
import com.ss.scrumptious.common_entities.entity.Tag;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;
import com.ss.scrumptious_restaurant.specification.MenuItemSpecificationsBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

	private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;
    private RestaurantService restaurantService;
    private TagRepository tagRepository;
    private MenuCategoryRepository menuCategoryRepository;


	@Transactional
	public Menuitem createMenuItem(@Valid SaveMenuItemDto menuItemDto, Long restaurantId) {
		Restaurant r = restaurantService.getRestaurantById(restaurantId);
        Menuitem menuItem = Menuitem.builder()
                .name(menuItemDto.getName())
                .price(Money.of(menuItemDto.getPrice(), "USD"))
                .isAvailable(menuItemDto.getIsAvailable())
                .restaurant(r)
                .build();
        menuItemRepository.save(menuItem);
        return menuItem;
	}

	@Override
    public Menuitem getMenuItemById(Long menuId) {
        Menuitem menuItem = menuItemRepository.findById(menuId).orElseThrow(() -> new NoSuchElementException("MenuItem not exist"));
        return menuItem;
    }



    @Transactional
    @Override
    public List<Tag> updateMenuItemTag(List<String> tagList, Long menuId) {
        Menuitem menu = getMenuItemById(menuId);

        List<Tag> list = tagList.stream()
                .filter(s -> !tagRepository.existsTagByType(s))
                .map(s -> Tag.builder().type(s).build())
                .collect(Collectors.toList());
        tagRepository.saveAll(list);

        Set<Tag> all = tagRepository.findAllByTypeIn(tagList);
        menu.setTags(all);
        menuItemRepository.save(menu);
        return all.stream().collect(Collectors.toList());
    }

    @Override
    public List<MenuCategory> updateMenuItemCategory(List<String> categoryList, Long menuId) {
        Menuitem menu = getMenuItemById(menuId);

        List<MenuCategory> list = categoryList.stream()
                .filter(s -> !menuCategoryRepository.existsMenuCategoryByType(s))
                .map(s -> MenuCategory.builder().type(s).build())
                .collect(Collectors.toList());
        menuCategoryRepository.saveAll(list);

        Set<MenuCategory> all = menuCategoryRepository.findAllByTypeIn(categoryList);
        menu.setCategories(all);
        menuItemRepository.save(menu);
        return all.stream().collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateMenuItemById(SaveMenuItemDto menuItemDto, Long menuId) {

        Menuitem menu = getMenuItemById(menuId);
        menu.setDescription(menuItemDto.getDescription());
        menu.setDiscount(menuItemDto.getDiscount());
        menu.setIsAvailable(menuItemDto.getIsAvailable());
        menu.setName(menuItemDto.getName());
        menu.setPrice(Money.of(menuItemDto.getPrice(),"USD"));
        menu.setSize(menuItemDto.getSize());
        menuItemRepository.save(menu);
        updateMenuItemCategory(menuItemDto.getCategories(), menuId);
        updateMenuItemTag(menuItemDto.getTags(), menuId);

    }

    @Transactional
    @Override
    public void deleteMenuItemByIds(List<Long> ids) {
        menuItemRepository.deleteMenuItemsByIdIn(ids);
    }

    @Override
	public List<Menuitem> getAllMenuItems() {
		return menuItemRepository.findAll();
	}

    @Override
	public List<Menuitem> getAllMenuItemsFromRestaurant(Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		return menuItemRepository.findAllByRestaurant(restaurant);
	}

    @Override
	public Menuitem getMenuItemFromRestaurant(Long restaurantId, Long menuId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		Menuitem menuItem = menuItemRepository.findByIdAndRestaurant(menuId, restaurant);
		return menuItem;
	}

    @Override
	public List<Menuitem> searchMenuItems(String search) {
		 MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
	        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	        Specification<Menuitem> spec = builder
	        		.search()
	        		.build();
	        return menuItemRepository.findAll(spec);
	}


    @Override
	public List<Menuitem> searchMenuItemsFromRestaurant(String search, Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(null);

		System.out.println(search);

		MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Menuitem> spec = builder
        		.search()
        		.isFromRestaurant(restaurant)
        		.build();

        return menuItemRepository.findAll(spec);
	}

    @Override
	public Set<Restaurant> getRestaurantsFromMenuItemSearch(String search) {
		MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Menuitem> spec = builder
        		.search()
        		.build();

        Set<Restaurant> restaurants = menuItemRepository.findAll(spec).stream()
        		.map(item -> item.getRestaurant())
        		.collect(Collectors.toSet());

        return restaurants;
	}







}
