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
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.Tag;
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
	public MenuItem createNewMenuItem(@Valid SaveMenuItemDto createMenuItemDto, Long restaurantId) {
		
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		MenuItem menuItem = MenuItem.builder()
				.name(createMenuItemDto.getName())
				.price(Money.of(createMenuItemDto.getPrice(), "USD"))
				.isAvailable(createMenuItemDto.getIsAvailable())
				.restaurant(restaurant)
				.build();
		
		menuItemRepository.save(menuItem);
		
		return menuItem;
	}
	
	//"------------------------------------------------------------------------------------------"
    @Transactional
    @Override
    public Long addMenuItem_Owner(SaveMenuItemDto menuItemDto, Long rid) {
        Restaurant r = restaurantService.getRestaurantById_Owner(rid);
        MenuItem menuItem = MenuItem.builder()
                .name(menuItemDto.getName())
                .price(Money.of(menuItemDto.getPrice(), "USD"))
                .isAvailable(menuItemDto.getIsAvailable())
                .restaurant(r)
                .build();
        menuItemRepository.save(menuItem);
        return menuItem.getId();
    }

    @Override
    public MenuItem getMenuItemById_Owner(Long mid) {
        MenuItem menuItem = menuItemRepository.findById(mid).orElseThrow(() -> new NoSuchElementException("MenuItem not exist"));
        return menuItem;
    }

    @Override
    public List<MenuItem> getMenuItemByRestaurantId_Owner(Long rid) {
        Restaurant restaurant = restaurantRepository.findById(rid).orElseThrow(() -> new NoSuchElementException("restaurant not exist"));
        List<MenuItem> list = menuItemRepository.findAllByRestaurant(restaurant);

        return list;
    }

    @Transactional
    @Override
    public List<Tag> addMenuItemTag_Owner(List<String> tagList, Long mid) {
        MenuItem menu = getMenuItemById_Owner(mid);

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
    public List<MenuCategory> addMenuItemCategory_Owner(List<String> categoryList, Long mid) {
        MenuItem menu = getMenuItemById_Owner(mid);

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
    public void updateMenuItemById_Owner(SaveMenuItemDto dto, Long mid) {

        MenuItem menu = getMenuItemById_Owner(mid);
        menu.setDescription(dto.getDescription());
        menu.setDiscount(dto.getDiscount());
        menu.setIsAvailable(dto.getIsAvailable());
        menu.setName(dto.getName());
        menu.setPrice(Money.of(dto.getPrice(),"USD"));
        menu.setSize(dto.getSize());
        menuItemRepository.save(menu);
        addMenuItemCategory_Owner(dto.getCategories(), mid);
        addMenuItemTag_Owner(dto.getTags(), mid);

    }

    @Transactional
    @Override
    public void deleteMenuItemByIds_Owner(List<Long> ids) {
        menuItemRepository.deleteMenuItemsByIdIn(ids);
    }
	
    @Override
	public List<MenuItem> getAllMenuItems() {
		
		return menuItemRepository.findAll();
	}

	@Override
	public List<MenuItem> getAllMenuItemsFromRestaurant(Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		return menuItemRepository.findAllByRestaurant(restaurant);
	}

	

	@Override
	public MenuItem getMenuItemFromRestaurant(Long restId, Long itemId) {
		Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow();

		MenuItem menuItem = menuItemRepository.findByIdAndRestaurant(itemId, restaurant);
		
		return menuItem;
	}

	@Override
	public List<MenuItem> searchMenuItems(String search) {
		 MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
	        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	        
	        Specification<MenuItem> spec = builder
	        		.search()
	        		.build();
	        return menuItemRepository.findAll(spec);
	}


	@Override
	public List<MenuItem> searchMenuItemsFromRestaurant(String search, Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		System.out.println(search);
		
		MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        
        Specification<MenuItem> spec = builder
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
        
        Specification<MenuItem> spec = builder
        		.search()
        		.build();
        
        Set<Restaurant> restaurants = menuItemRepository.findAll(spec).stream()
        		.map(item -> item.getRestaurant())
        		.collect(Collectors.toSet());
        	
        return restaurants;
	}

	

	

	

}
