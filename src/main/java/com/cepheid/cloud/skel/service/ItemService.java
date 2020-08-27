package com.cepheid.cloud.skel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.DescriptionModel;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.ItemModel;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.repository.ItemRepository;

@Service
public class ItemService {

	private ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public ResponseEntity<List<ItemModel>> getAllItems() {
		List<ItemModel> items = itemRepository.findAll().stream().map(item -> {
			ItemModel model = toItemModel(item);
			return model;
		}).collect(Collectors.toList());
		return ResponseEntity.of(Optional.of(items));
	}

	public ResponseEntity<ItemModel> getItem(Long id) {

		Optional<Item> itemOptional = itemRepository.findById(id);
		if (itemOptional.isPresent())
			return ResponseEntity.of(Optional.of(toItemModel(itemOptional.get())));

		else
			return ResponseEntity.of(Optional.empty());

	}

	private ItemModel toItemModel(Item item) {
		ItemModel itemModel = new ItemModel();

		itemModel.setId(item.getId());
		itemModel.setName(item.getName());
		itemModel.setState(item.getType().name());
		List<DescriptionModel> descModelList = item.getDescriptions().stream().map(desc -> {
			DescriptionModel descModel = new DescriptionModel();
			descModel.setId(desc.getId());
			descModel.setDescription(desc.getDescription());
			return descModel;
		}).collect(Collectors.toList());
		itemModel.setDescriptions(descModelList);

		return itemModel;
	}

	public ResponseEntity<String> addItem(ItemModel itemModel) {

		try {
			Item newItem = toItem(itemModel, itemModel.getId());

			Item savedItem = itemRepository.save(newItem);
			if (itemRepository.findById(savedItem.getId()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created Item and Description");
			} else
				return ResponseEntity.unprocessableEntity().body("Failed to Create specified Item");
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"State of Item should be Undefined,Valid or Invalid");
		}
	}

	public ResponseEntity<String> deleteItem(Long id) {
		if (itemRepository.findById(id).isPresent()) {
			itemRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Item Deleted");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");

	}

	public ResponseEntity<ItemModel> updateItem(ItemModel itemModel, Long iD) {
		try {
			Item item = toItem(itemModel, iD);
			if (itemRepository.findById(iD).isPresent()) {
				Item savedItem = itemRepository.save(item);
				return ResponseEntity.ok(toItemModel(savedItem));
			} else {
				return ResponseEntity.of(Optional.empty());
			}
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"State of Item should be Undefined,Valid or Invalid");
		}

	}

	private Item toItem(ItemModel itemModel, Long iD) {
		Item item = new Item();
		item.setId(iD);
		item.setName(itemModel.getName());
		item.setState(itemModel.getState());
		List<Description> descList = itemModel.getDescriptions().stream().map(desc -> {
			Description description = new Description();
			description.setId(desc.getId());
			description.setDescription(desc.getDescription());
			return description;
		}).collect(Collectors.toList());
		item.setDescriptions(descList);
		item.setType(State.of(itemModel.getState().toUpperCase()));
		return item;
	}

}
