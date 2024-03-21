package org.example.rentalapplication.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.example.rentalapplication.dto.ToolDTO;
import org.example.rentalapplication.dto.ToolTypeDTO;
import org.example.rentalapplication.service.impl.ToolServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ToolController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToolControllerTest {
  @Autowired
  private ToolController toolController;

  @MockBean
  private ToolServiceImpl toolServiceImpl;

  /**
   * Method under test: {@link ToolController#addTool(ToolDTO)}
   */
  @Test
  void testAddTool() throws Exception {
    
    ToolTypeDTO toolType = new ToolTypeDTO();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    ToolDTO toolDTO = new ToolDTO();
    toolDTO.setBrand("Brand");
    toolDTO.setId(1L);
    toolDTO.setToolCode("Tool Code");
    toolDTO.setToolType(toolType);
    when(toolServiceImpl.addTool(Mockito.<ToolDTO>any())).thenReturn(toolDTO);

    ToolTypeDTO toolType2 = new ToolTypeDTO();
    toolType2.setDailyCharge(10.0d);
    toolType2.setHolidayCharge(true);
    toolType2.setId(1L);
    toolType2.setName("Name");
    toolType2.setWeekDayCharge(true);
    toolType2.setWeekendCharge(true);

    ToolDTO toolDTO2 = new ToolDTO();
    toolDTO2.setBrand("Brand");
    toolDTO2.setId(1L);
    toolDTO2.setToolCode("Tool Code");
    toolDTO2.setToolType(toolType2);
    String content = (new ObjectMapper()).writeValueAsString(toolDTO2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tools/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(toolController).build().perform(requestBuilder);

    
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "{\"id\":1,\"toolCode\":\"Tool Code\",\"brand\":\"Brand\",\"toolType\":{\"id\":1,\"name\":\"Name\",\"dailyCharge\":10.0,"
                                    + "\"weekDayCharge\":true,\"weekendCharge\":true,\"holidayCharge\":true}}"));
  }

  /**
   * Method under test: {@link ToolController#updateTool(ToolDTO, long)}
   */
  @Test
  void testUpdateTool() throws Exception {
    
    ToolTypeDTO toolType = new ToolTypeDTO();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    ToolDTO toolDTO = new ToolDTO();
    toolDTO.setBrand("Brand");
    toolDTO.setId(1L);
    toolDTO.setToolCode("Tool Code");
    toolDTO.setToolType(toolType);
    when(toolServiceImpl.updateTool(Mockito.<ToolDTO>any(), anyLong())).thenReturn(toolDTO);

    ToolTypeDTO toolType2 = new ToolTypeDTO();
    toolType2.setDailyCharge(10.0d);
    toolType2.setHolidayCharge(true);
    toolType2.setId(1L);
    toolType2.setName("Name");
    toolType2.setWeekDayCharge(true);
    toolType2.setWeekendCharge(true);

    ToolDTO toolDTO2 = new ToolDTO();
    toolDTO2.setBrand("Brand");
    toolDTO2.setId(1L);
    toolDTO2.setToolCode("Tool Code");
    toolDTO2.setToolType(toolType2);
    String content = (new ObjectMapper()).writeValueAsString(toolDTO2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tools/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    
    MockMvcBuilders.standaloneSetup(toolController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "{\"id\":1,\"toolCode\":\"Tool Code\",\"brand\":\"Brand\",\"toolType\":{\"id\":1,\"name\":\"Name\",\"dailyCharge\":10.0,"
                                    + "\"weekDayCharge\":true,\"weekendCharge\":true,\"holidayCharge\":true}}"));
  }

  /**
   * Method under test: {@link ToolController#getAllTool()}
   */
  @Test
  void testGetAllTool() throws Exception {
    
    when(toolServiceImpl.getAllTool()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tools/");

    
    MockMvcBuilders.standaloneSetup(toolController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link ToolController#deleteTool(long)}
   */
  @Test
  void testDeleteTool() throws Exception {
    
    doNothing().when(toolServiceImpl).deleteTool(anyLong());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/tools/{id}", 1L);

    
    MockMvcBuilders.standaloneSetup(toolController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link ToolController#deleteTool(long)}
   */
  @Test
  void testDeleteTool2() throws Exception {
    
    doNothing().when(toolServiceImpl).deleteTool(anyLong());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/tools/{id}", 1L);
    requestBuilder.contentType("https://example.org/example");

    
    MockMvcBuilders.standaloneSetup(toolController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link ToolController#getTool(long)}
   */
  @Test
  void testGetTool() throws Exception {
    
    ToolTypeDTO toolType = new ToolTypeDTO();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    ToolDTO toolDTO = new ToolDTO();
    toolDTO.setBrand("Brand");
    toolDTO.setId(1L);
    toolDTO.setToolCode("Tool Code");
    toolDTO.setToolType(toolType);
    when(toolServiceImpl.getByToolId(anyLong())).thenReturn(toolDTO);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tools/{id}", 1L);

    
    MockMvcBuilders.standaloneSetup(toolController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "{\"id\":1,\"toolCode\":\"Tool Code\",\"brand\":\"Brand\",\"toolType\":{\"id\":1,\"name\":\"Name\",\"dailyCharge\":10.0,"
                                    + "\"weekDayCharge\":true,\"weekendCharge\":true,\"holidayCharge\":true}}"));
  }
}
