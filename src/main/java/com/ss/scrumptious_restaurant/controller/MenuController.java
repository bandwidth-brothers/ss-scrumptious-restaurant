package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Tag;
import com.ss.scrumptious_restaurant.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/restaurants/{restaurantId}/menu-items")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<List<MenuItem>> getAllMenuItemsFromRestaurant(@PathVariable Long restaurantId,
			@RequestParam(value = "search", required=false) String search) {
		List<MenuItem> menuItems; 
		if (search == null) {
			menuItems = menuService.getAllMenuItemsFromRestaurant(restaurantId);
		} else {
			menuItems = menuService.searchMenuItemsFromRestaurant(search, restaurantId);
		}

		if (menuItems.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(menuItems);
		}
	}

	@GetMapping("/menu-items")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<List<MenuItem>> getAllMenuItems(@RequestParam(value = "search", required=false) String search) {
		List<MenuItem> menuItems;
		if (search == null) {
			menuItems = menuService.getAllMenuItems();
		} else {
			menuItems = menuService.searchMenuItems(search);
		}

		if (menuItems.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(menuItems);
		}
	}

	@GetMapping("/restaurants/{restaurantId}/menu-items/{menuId}")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<MenuItem> getMenuItemFromRestaurant(@PathVariable Long restaurantId, @PathVariable Long menuId) {
		MenuItem menuItem = menuService.getMenuItemFromRestaurant(restaurantId, menuId);

		return ResponseEntity.ok(menuItem);

	}

	@PostMapping("/restaurants/{restaurantId}/menu-items")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #restaurantId)")
	public ResponseEntity<Void> createNewMenuItem(@Valid @RequestBody SaveMenuItemDto menuItemDto,
			@PathVariable Long restaurantId) {
		MenuItem menuItem = menuService.createMenuItem(menuItemDto, restaurantId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{restaurantId}")
	            .buildAndExpand(menuItem.getId()).toUri();
		return ResponseEntity.created(location).build();

	}

    @PutMapping("/restaurants/{restaurantId}/menu-items/{menuId}")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #restaurantId)")
    public ResponseEntity<Void> updateMenuItem(@RequestBody SaveMenuItemDto menuItemDto,
                                                    @PathVariable Long restaurantId, @PathVariable Long menuId) {
        menuService.updateMenuItemById(menuItemDto, menuId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurants/menu-items/{menuId}")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable Long menuId) {
        MenuItem m = menuService.getMenuItemById(menuId);
        return ResponseEntity.ok(m);
    }

    @PostMapping("/restaurants/{restaurantId}/menu-items/{menuId}/tags")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #restaurantId)")
    public ResponseEntity<List<Tag>> createMenuItemTags(@RequestBody List<String> tagList,
                                            @PathVariable Long menuId) {
        List<Tag> tags = menuService.updateMenuItemTag(tagList, menuId);
        if (tags.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(tags);
		}
    }

    @PostMapping("/restaurants/{restaurantId}/menu-items/{menuId}/categories")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #restaurantId)")
    public ResponseEntity<List<MenuCategory>> createMenuItemCategories(@RequestBody List<String> categoryList,
                                                    @PathVariable Long menuId) {
        List<MenuCategory> categories = menuService.updateMenuItemCategory(categoryList, menuId);
        if (categories.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(categories);
		}
    }

    @DeleteMapping("/restaurants/menu-items")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMenuItemCategories(@RequestBody List<Long> ids) {
        menuService.deleteMenuItemByIds(ids);
        return ResponseEntity.noContent().build();
    }
}
