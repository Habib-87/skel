package com.cepheid.cloud.skel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.DescriptionModel;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.ItemModel;
import com.cepheid.cloud.skel.repository.ItemRepository;

/**
 * Test class for Service Unit Test. Always you can improve this class and add
 * more test scenarios.
 * 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	ItemService itemService;

	@MockBean
	ItemRepository itemRepoMock;

	@Test
	public void validateAddItem() {

		Item testItem = testData();
		Mockito.when(itemRepoMock.save(testItem)).thenReturn(testItem);
		Mockito.when(itemRepoMock.findById(Long.valueOf("1"))).thenReturn(Optional.of(testItem));

		ResponseEntity<String> returnModel = itemService.addItem(testModelData());

		assertEquals("Successfully Created Item and Description", returnModel.getBody());

	}

	@Test
	public void validateGetItem() {

		Item testItem = testData();
		Mockito.when(itemRepoMock.findById(Long.valueOf("1"))).thenReturn(Optional.of(testItem));

		ResponseEntity<ItemModel> returnModel = itemService.getItem(Long.valueOf(1));

		assertEquals(1, returnModel.getBody().getId());

	}

	@Test
	public void validateGetItems() {

		List<Item> items = new ArrayList<Item>();
		items.add(testData());
		items.add(testDataAnother());

		Mockito.when(itemRepoMock.findAll()).thenReturn(items);

		ResponseEntity<List<ItemModel>> returnedItems = itemService.getAllItems();
		assertEquals(2, returnedItems.getBody().size());

	}

	@Test
	public void validateDeleteItem() {

		Item testItem = testData();
		Mockito.when(itemRepoMock.findById(Long.valueOf("1"))).thenReturn(Optional.of(testItem));

		Mockito.doNothing().when(itemRepoMock).deleteById(testItem.getId());

		ResponseEntity<String> deleteResult = itemService.deleteItem(testItem.getId());

		assertEquals(200, deleteResult.getStatusCode().value());

	}

	private Item testData() {
		Item testItem = new Item();
		List<Description> descList = new ArrayList<Description>();
		Description descModel1 = new Description();
		descModel1.setId(Long.valueOf("1"));
		descModel1.setDescription("Unit Testing Description");
		Description descModel2 = new Description();
		descModel2.setId(Long.valueOf("2"));
		descModel2.setDescription("Another Unit Testing Description");
		descList.add(descModel1);
		descList.add(descModel2);
		testItem.setDescriptions(descList);
		testItem.setName("UnitTestItem");
		testItem.setState("Valid");
		testItem.setId(Long.valueOf("1"));
		return testItem;
	}

	private Item testDataAnother() {
		Item testItem = new Item();
		List<Description> descList = new ArrayList<Description>();
		Description descModel1 = new Description();
		descModel1.setId(Long.valueOf("1"));
		descModel1.setDescription("Unit Testing Description 2");
		Description descModel2 = new Description();
		descModel2.setId(Long.valueOf("2"));
		descModel2.setDescription("Another Unit Testing Description");
		descList.add(descModel1);
		descList.add(descModel2);
		testItem.setDescriptions(descList);
		testItem.setName("UnitTestItemSecond");
		testItem.setState("invalid");
		testItem.setId(Long.valueOf("2"));
		return testItem;
	}

	private ItemModel testModelData() {
		ItemModel testItem = new ItemModel();
		List<DescriptionModel> descList = new ArrayList<DescriptionModel>();
		DescriptionModel descModel1 = new DescriptionModel();
		descModel1.setId(Long.valueOf("1"));
		descModel1.setDescription("Unit Testing Description");
		DescriptionModel descModel2 = new DescriptionModel();
		descModel2.setId(Long.valueOf("2"));
		descModel2.setDescription("Another Unit Testing Description");
		descList.add(descModel1);
		descList.add(descModel2);
		testItem.setDescriptions(descList);
		testItem.setName("UnitTestItem");
		testItem.setState("Valid");
		testItem.setId(Long.valueOf("1"));
		return testItem;
	}

}
