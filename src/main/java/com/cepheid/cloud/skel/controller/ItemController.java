package com.cepheid.cloud.skel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cepheid.cloud.skel.model.ItemModel;
import com.cepheid.cloud.skel.service.ItemService;

import io.swagger.annotations.ApiOperation;


// curl http:/localhost:9443/app/api/1.0/items

@Component
@RequestMapping("/app/api/1.0/items")
public class ItemController {

  
  private ItemService itemService;

  public ItemController(ItemService itemService) {
	  this.itemService = itemService;
  }
  
  @ApiOperation(value = "Get All Item details")
  @GetMapping
  public ResponseEntity<List<ItemModel>> getItems() {
	  return itemService.getAllItems();
  }
  
  @ApiOperation(value = "Add Item")
  @RequestMapping(value = "/item", method = RequestMethod.POST)
  public ResponseEntity<String> addItem(@RequestBody ItemModel item) {
	  return itemService.addItem(item);
  }
  
  @ApiOperation(value = "Get Item by item ID")
  @RequestMapping(value = "/item/{ID}", method = RequestMethod.GET)
  public ResponseEntity<ItemModel> getItem(@PathVariable Long ID) {
	  return itemService.getItem(ID);
  }
 
  @ApiOperation(value = "Delete item by item ID")
  @RequestMapping(value = "/item/{ID}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteItem(@PathVariable Long ID) {
	   return itemService.deleteItem(ID);
  }
 
  @ApiOperation(value = "Update Item")
  @RequestMapping(value = "/item/{ID}", method = RequestMethod.PUT)
  public ResponseEntity<ItemModel> updateItem(@PathVariable Long ID,
		  				 @RequestBody ItemModel item) {
	   return itemService.updateItem(item, ID);
  }
  
}
