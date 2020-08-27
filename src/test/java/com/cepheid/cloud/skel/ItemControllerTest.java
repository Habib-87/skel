package com.cepheid.cloud.skel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cepheid.cloud.skel.model.DescriptionModel;
import com.cepheid.cloud.skel.model.ItemModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Unit test for controller class and end to end test using MockMvc
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ItemControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	public void testAddItem() throws Exception {
		ItemModel testModel = testData();
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/app/api/1.0/items/item").content(asJsonString(testModel))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(201, status);

	}

	@Test
	@Order(2)
	public void testGetItem() throws Exception {

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/app/api/1.0/items/item/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

		String content = result.getResponse().getContentAsString();
		ItemModel returnModel = mapFromJson(content, ItemModel.class);
		assertEquals("UnitTestItem", returnModel.getName());
		assertEquals(1, returnModel.getId());

	}

	@Test
	@Order(3)
	public void testUpdateItem() throws Exception {

		/** Get the item and update the item and persist it */
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/app/api/1.0/items/item/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		ItemModel updateModel = mapFromJson(result.getResponse().getContentAsString(), ItemModel.class);
		DescriptionModel newDesc = new DescriptionModel();
		newDesc.setDescription("Unit Test to check update operation");
		updateModel.getDescriptions().add(newDesc);

		MvcResult updateResult = mvc
				.perform(
						MockMvcRequestBuilders.put("/app/api/1.0/items/item/{id}", 1).content(asJsonString(updateModel))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = updateResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = updateResult.getResponse().getContentAsString();
		ItemModel returnModel = mapFromJson(content, ItemModel.class);
		assertEquals(3, returnModel.getDescriptions().size());

	}

	@Test
	@Order(4)
	public void testDeleteItem() throws Exception {

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/app/api/1.0/items/item/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = result.getResponse().getStatus();
		String body = result.getResponse().getContentAsString();
		assertEquals(200, status);
		assertEquals("Item Deleted", body);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	private ItemModel testData() {
		ItemModel testModel = new ItemModel();
		List<DescriptionModel> descList = new ArrayList<DescriptionModel>();
		DescriptionModel descModel1 = new DescriptionModel();
		descModel1.setDescription("Unit Testing Description");
		DescriptionModel descModel2 = new DescriptionModel();
		descModel2.setDescription("Another Unit Testing Description");
		descList.add(descModel1);
		descList.add(descModel2);
		testModel.setDescriptions(descList);
		testModel.setName("UnitTestItem");
		testModel.setState("Valid");
		return testModel;
	}
}
