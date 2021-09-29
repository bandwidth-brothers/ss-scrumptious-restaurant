package com.ss.scrumptious_restaurant.service.Impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.ss.scrumptious_restaurant.dao.MenuCategoryRepository;
import com.ss.scrumptious_restaurant.dao.MenuItemRepository;
import com.ss.scrumptious_restaurant.dao.TagRepository;
import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.*;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.RestaurantRepository;

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
    public Menuitem createNewMenuItem(@Valid SaveMenuItemDto menuItemDto, Long restaurantId) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        Menuitem menuItem = Menuitem.builder()
                .name(menuItemDto.getName())
                .price(Money.of(menuItemDto.getPrice(), "USD"))
                .isAvailable(menuItemDto.getIsAvailable())
                .restaurant(restaurant.get())
                .build();

        menuItemRepository.save(menuItem);

        return menuItem;
    }

    //"------------------------------------------------------------------------------------------"
    @Transactional
    @Override
    public Long addMenuItem_Owner(SaveMenuItemDto menuItemDto, Long rid) {
        Restaurant r = restaurantService.getRestaurantById_Owner(rid);
        Menuitem menuItem = Menuitem.builder()
                .name(menuItemDto.getName())
                .price(Money.of(menuItemDto.getPrice(), "USD"))
                .isAvailable(menuItemDto.getIsAvailable())
                .restaurant(r)
                .build();
        menuItemRepository.save(menuItem);
        return menuItem.getId();
    }

    @Override
    public Menuitem getMenuItemById_Owner(Long mid) {
        Menuitem menuItem = menuItemRepository.findById(mid).orElseThrow(() -> new NoSuchElementException("MenuItem not exist"));
        return menuItem;
    }

    @Override
    public List<Menuitem> getMenuItemByRestaurantId_Owner(Long rid) {
        Restaurant restaurant = restaurantRepository.findById(rid).orElseThrow(() -> new NoSuchElementException("restaurant not exist"));
        List<Menuitem> list = menuItemRepository.findAllByRestaurant(restaurant);

        return list;
    }

    @Transactional
    @Override
    public List<Tag> addMenuItemTag_Owner(List<String> tagList, Long mid) {
        Menuitem menu = getMenuItemById_Owner(mid);

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
        Menuitem menu = getMenuItemById_Owner(mid);

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

        Menuitem menu = getMenuItemById_Owner(mid);
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
}
