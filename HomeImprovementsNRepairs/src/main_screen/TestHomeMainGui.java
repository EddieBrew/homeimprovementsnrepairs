package main_screen;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestHomeMainGui {

	/*
	 * HomeMainGui homeMainGui;
	 * 
	 * @BeforeEach void setUp() {
	 * 
	 * String name = "rbrewer"; char pword[] = {'a', 'b', 'c', 'd'}; homeMainGui =
	 * new HomeMainGui(name, pword); }
	 */
	
	@Test
	void testConvertDateStringToInt() {;
		assertEquals(20221203, HomeMainGui.convertDateStringToInt("2022-12-03" ,1));
	}
	
	
	@Test
	void testConvertDateStringToIntGetMonth() {
		
		assertEquals(12, HomeMainGui.convertDateStringToInt("2022-12-03" ,2));
	}
	
	@Test
	void testConvertDateStringToIntGetYear() {
		
		assertEquals(2022, HomeMainGui.convertDateStringToInt("2022-12-03" ,3));
	}

	
	@Test 
	void testReformatDateString(){
		assertTrue(HomeMainGui.reformatDateString("12/03/2022").equals("2022-12-03"));
	
	}
	
	@Test
	void testGetDataFromFile(){
		
		List<HomeData> myList = HomeMainGui.getDataFromFile();
		assertTrue(HomeMainGui.getDataFromFile().size() == 359);
	}


}
