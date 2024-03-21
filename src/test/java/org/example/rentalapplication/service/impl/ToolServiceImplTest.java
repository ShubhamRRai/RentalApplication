package org.example.rentalapplication.service.impl;

import org.example.rentalapplication.dto.ToolDTO;
import org.example.rentalapplication.dto.ToolTypeDTO;
import org.example.rentalapplication.entity.Tool;
import org.example.rentalapplication.entity.ToolType;
import org.example.rentalapplication.repository.ToolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToolServiceImplTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolServiceImpl toolService;

    @Test
    public void testGetByToolId() {
        
        long id = 1;
        ToolDTO expectedToolDTO = createToolDTO(id);
        Tool expectedTool = createTool(id);
        when(toolRepository.findById(id)).thenReturn(Optional.of(expectedTool));
        ToolDTO actualToolDTO = toolService.getByToolId(id);
        assertEquals(expectedToolDTO, actualToolDTO);
    }

    @Test
    public void testGetTools() {
        
        List<Tool> expectedToolList = new ArrayList<>();
        expectedToolList.add(createTool(1));
        expectedToolList.add(createTool(2));
        when(toolRepository.findAll()).thenReturn(expectedToolList);
        List<ToolDTO> actualToolDTOList = toolService.getAllTool();
        assertEquals(expectedToolList.size(), actualToolDTOList.size());
    }
    @Test
    public void testAddTool() {
        
        ToolDTO inputToolDTO = createToolDTO();
        Tool expectedTool = createTool(inputToolDTO);
        when(toolRepository.save(any(Tool.class))).thenReturn(expectedTool);

        // Act
        ToolDTO actualToolDTO = toolService.addTool(inputToolDTO);


        assertEquals(expectedTool.getId(), actualToolDTO.getId());
    }

    @Test
    public void testUpdateTool() {
        
        long id = 1;
        ToolDTO inputToolDTO = createToolDTO();
        Tool expectedTool = createTool(inputToolDTO);
        expectedTool.setId(id);
        when(toolRepository.save(any(Tool.class))).thenReturn(expectedTool);

        // Act
        ToolDTO actualToolDTO = toolService.updateTool(inputToolDTO, id);


        assertEquals(id, actualToolDTO.getId());
    }

    @Test
    public void testDeleteToolById() {
        
        long id = 1;
        // Act
        toolService.deleteTool(id);

        verify(toolRepository, times(1)).deleteById(id);
    }

    @Test
    void testCreateTool() {
        
        ToolType toolType = new ToolType();
        toolType.setDailyCharge(10.0d);
        toolType.setHolidayCharge(true);
        toolType.setId(1L);
        toolType.setName("Name");
        toolType.setTools(new HashSet<>());
        toolType.setWeekDayCharge(true);
        toolType.setWeekendCharge(true);

        Tool tool = new Tool();
        tool.setBrand("Brand");
        tool.setId(1L);
        tool.setToolCode("Tool Code");
        tool.setToolType(toolType);
        when(toolRepository.save(Mockito.<Tool>any())).thenReturn(tool);

        ToolTypeDTO toolType2 = new ToolTypeDTO();
        toolType2.setDailyCharge(10.0d);
        toolType2.setHolidayCharge(true);
        toolType2.setId(1L);
        toolType2.setName("Name");
        toolType2.setWeekDayCharge(true);
        toolType2.setWeekendCharge(true);

        ToolDTO toolDTO = new ToolDTO();
        toolDTO.setBrand("Brand");
        toolDTO.setId(1L);
        toolDTO.setToolCode("Tool Code");
        toolDTO.setToolType(toolType2);

        // Act
        ToolDTO actualAddToolResult = toolService.addTool(toolDTO);


        verify(toolRepository).save(Mockito.<Tool>any());
        assertEquals(1L, actualAddToolResult.getId());
        assertSame(toolDTO, actualAddToolResult);
    }

      /**
   * Method under test: {@link ToolServiceImpl#getByToolId(long)}
   */
  @Test
  void testGetByToolId2() {
    
    ToolType toolType = new ToolType();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setTools(new HashSet<>());
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    Tool tool = new Tool();
    tool.setBrand("Brand");
    tool.setId(1L);
    tool.setToolCode("Tool Code");
    tool.setToolType(toolType);
    Optional<Tool> ofResult = Optional.of(tool);
    when(toolRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    ToolDTO actualByToolId = toolService.getByToolId(1L);


    verify(toolRepository).findById(Mockito.<Long>any());
    assertEquals("Brand", actualByToolId.getBrand());
    ToolTypeDTO toolType2 = actualByToolId.getToolType();
    assertEquals("Name", toolType2.getName());
    assertEquals("Tool Code", actualByToolId.getToolCode());
    assertEquals(10.0d, toolType2.getDailyCharge());
    assertEquals(1L, actualByToolId.getId());
    assertEquals(1L, toolType2.getId());
    assertTrue(toolType2.isHolidayCharge());
    assertTrue(toolType2.isWeekDayCharge());
    assertTrue(toolType2.isWeekendCharge());
  }

  /**
   * Method under test: {@link ToolServiceImpl#getByToolId(long)}
   */
  @Test
  void testGetByToolId3() {
    
    Optional<Tool> emptyResult = Optional.empty();
    when(toolRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    // Act
    ToolDTO actualByToolId = toolService.getByToolId(1L);


    verify(toolRepository).findById(Mockito.<Long>any());
    assertNull(actualByToolId);
  }

  /**
   * Method under test: {@link ToolServiceImpl#getAllTool()}
   */
  @Test
  void testGetAllTool() {
    
    when(toolRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<ToolDTO> actualAllTool = toolService.getAllTool();


    verify(toolRepository).findAll();
    assertTrue(actualAllTool.isEmpty());
  }

  /**
   * Method under test: {@link ToolServiceImpl#getAllTool()}
   */
  @Test
  void testGetAllTool2() {
    
    ToolType toolType = new ToolType();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setTools(new HashSet<>());
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    Tool tool = new Tool();
    tool.setBrand("Brand");
    tool.setId(1L);
    tool.setToolCode("Tool Code");
    tool.setToolType(toolType);

    ArrayList<Tool> toolList = new ArrayList<>();
    toolList.add(tool);
    when(toolRepository.findAll()).thenReturn(toolList);

    // Act
    List<ToolDTO> actualAllTool = toolService.getAllTool();


    verify(toolRepository).findAll();
    assertEquals(1, actualAllTool.size());
    ToolDTO getResult = actualAllTool.get(0);
    assertEquals("Brand", getResult.getBrand());
    ToolTypeDTO toolType2 = getResult.getToolType();
    assertEquals("Name", toolType2.getName());
    assertEquals("Tool Code", getResult.getToolCode());
    assertEquals(10.0d, toolType2.getDailyCharge());
    assertEquals(1L, getResult.getId());
    assertEquals(1L, toolType2.getId());
    assertTrue(toolType2.isHolidayCharge());
    assertTrue(toolType2.isWeekDayCharge());
    assertTrue(toolType2.isWeekendCharge());
  }

  /**
   * Method under test: {@link ToolServiceImpl#getAllTool()}
   */
  @Test
  void testGetAllTool3() {
    
    ToolType toolType = new ToolType();
    toolType.setDailyCharge(10.0d);
    toolType.setHolidayCharge(true);
    toolType.setId(1L);
    toolType.setName("Name");
    toolType.setTools(new HashSet<>());
    toolType.setWeekDayCharge(true);
    toolType.setWeekendCharge(true);

    Tool tool = new Tool();
    tool.setBrand("Brand");
    tool.setId(1L);
    tool.setToolCode("Tool Code");
    tool.setToolType(toolType);

    ToolType toolType2 = new ToolType();
    toolType2.setDailyCharge(0.5d);
    toolType2.setHolidayCharge(false);
    toolType2.setId(2L);
    toolType2.setName("42");
    toolType2.setTools(new HashSet<>());
    toolType2.setWeekDayCharge(false);
    toolType2.setWeekendCharge(false);

    Tool tool2 = new Tool();
    tool2.setBrand("42");
    tool2.setId(2L);
    tool2.setToolCode("42");
    tool2.setToolType(toolType2);

    ArrayList<Tool> toolList = new ArrayList<>();
    toolList.add(tool2);
    toolList.add(tool);
    when(toolRepository.findAll()).thenReturn(toolList);

    // Act
    List<ToolDTO> actualAllTool = toolService.getAllTool();


    verify(toolRepository).findAll();
    assertEquals(2, actualAllTool.size());
    ToolDTO getResult = actualAllTool.get(0);
    assertEquals("42", getResult.getBrand());
    assertEquals("42", getResult.getToolCode());
    ToolTypeDTO toolType3 = getResult.getToolType();
    assertEquals("42", toolType3.getName());
    ToolDTO getResult2 = actualAllTool.get(1);
    assertEquals("Brand", getResult2.getBrand());
    ToolTypeDTO toolType4 = getResult2.getToolType();
    assertEquals("Name", toolType4.getName());
    assertEquals("Tool Code", getResult2.getToolCode());
    assertEquals(0.5d, toolType3.getDailyCharge());
    assertEquals(10.0d, toolType4.getDailyCharge());
    assertEquals(1L, getResult2.getId());
    assertEquals(1L, toolType4.getId());
    assertEquals(2L, getResult.getId());
    assertEquals(2L, toolType3.getId());
    assertFalse(toolType3.isHolidayCharge());
    assertFalse(toolType3.isWeekDayCharge());
    assertFalse(toolType3.isWeekendCharge());
    assertTrue(toolType4.isHolidayCharge());
    assertTrue(toolType4.isWeekDayCharge());
    assertTrue(toolType4.isWeekendCharge());
  }

    /**
     * Method under test: {@link ToolServiceImpl#updateTool(ToolDTO, long)}
     */
    @Test
    void testUpdateTool2() {
        
        ToolType toolType = new ToolType();
        toolType.setDailyCharge(10.0d);
        toolType.setHolidayCharge(true);
        toolType.setId(1L);
        toolType.setName("Name");
        toolType.setTools(new HashSet<>());
        toolType.setWeekDayCharge(true);
        toolType.setWeekendCharge(true);

        Tool tool = new Tool();
        tool.setBrand("Brand");
        tool.setId(1L);
        tool.setToolCode("Tool Code");
        tool.setToolType(toolType);
        when(toolRepository.save(Mockito.<Tool>any())).thenReturn(tool);

        ToolTypeDTO toolType2 = new ToolTypeDTO();
        toolType2.setDailyCharge(10.0d);
        toolType2.setHolidayCharge(true);
        toolType2.setId(1L);
        toolType2.setName("Name");
        toolType2.setWeekDayCharge(true);
        toolType2.setWeekendCharge(true);

        ToolDTO toolDTO = new ToolDTO();
        toolDTO.setBrand("Brand");
        toolDTO.setId(1L);
        toolDTO.setToolCode("Tool Code");
        toolDTO.setToolType(toolType2);

        // Act
        ToolDTO actualUpdateToolResult = toolService.updateTool(toolDTO, 1L);


        verify(toolRepository).save(Mockito.<Tool>any());
        assertEquals(1L, actualUpdateToolResult.getId());
        assertSame(toolDTO, actualUpdateToolResult);
    }

    /**
     * Method under test: {@link ToolServiceImpl#deleteTool(long)}
     */
    @Test
    void testDeleteTool() {
        
        doNothing().when(toolRepository).deleteById(Mockito.<Long>any());

        // Act
        toolService.deleteTool(1L);

        verify(toolRepository).deleteById(Mockito.<Long>any());
    }
    private ToolDTO createToolDTO(long id) {
        ToolDTO toolDTO = new ToolDTO();
        toolDTO.setId(id);
        return toolDTO;
    }

    private Tool createTool(long id) {
        Tool tool = new Tool();
        tool.setId(id);
        return tool;
    }

    private ToolDTO createToolDTO() {
        ToolDTO toolDTO = new ToolDTO();
        // Set properties as needed
        return toolDTO;
    }

    private Tool createTool(ToolDTO toolDTO) {
        Tool tool = new Tool();
        BeanUtils.copyProperties(toolDTO, tool);
        // Set properties as needed
        return tool;
    }
}
