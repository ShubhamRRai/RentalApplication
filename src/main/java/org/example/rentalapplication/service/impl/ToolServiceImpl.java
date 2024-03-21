package org.example.rentalapplication.service.impl;

import org.example.rentalapplication.dto.ToolDTO;
import org.example.rentalapplication.dto.ToolTypeDTO;
import org.example.rentalapplication.entity.Tool;
import org.example.rentalapplication.entity.ToolType;
import org.example.rentalapplication.repository.ToolRepository;
import org.example.rentalapplication.service.ToolService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolServiceImpl implements ToolService {

    final ToolRepository toolRepository;

    public ToolServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Override
    public ToolDTO getByToolId(long id) {
        Optional<Tool> toolOptional = toolRepository.findById(id);
        Tool tool = toolOptional.orElse(null);
        if (tool != null) {
            return convertToDTO(tool);
        } else {
            return null;
        }
    }

    @Override
    public List<ToolDTO> getAllTool() {
        List<Tool> toolList = toolRepository.findAll();
        return toolList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ToolDTO addTool(ToolDTO toolDTO) {
        Tool tool = toolRepository.save(convertToEntity(toolDTO));
        toolDTO.setId(tool.getId());
        return toolDTO;
    }

    @Override
    public ToolDTO updateTool(ToolDTO toolDTO, long id) {
        toolDTO.setId(id);
        toolRepository.save(convertToEntity(toolDTO));
        return toolDTO;
    }

    @Override
    public void deleteTool(long id) {
        toolRepository.deleteById(id);
    }

    private ToolDTO convertToDTO(Tool tool) {
        ToolDTO toolDTO = new ToolDTO();
        BeanUtils.copyProperties(tool, toolDTO);
        if (tool.getToolType() != null) {
            ToolTypeDTO toolTypeDTO = new ToolTypeDTO();
            BeanUtils.copyProperties(tool.getToolType(), toolTypeDTO);
            toolDTO.setToolType(toolTypeDTO);
        }
        return toolDTO;
    }

    private Tool convertToEntity(ToolDTO toolDTO) {
        Tool tool = new Tool();
        BeanUtils.copyProperties(toolDTO, tool);
        if (toolDTO.getToolType() != null) {
            ToolType toolType = new ToolType();
            BeanUtils.copyProperties(toolDTO.getToolType(), toolType);
            toolType.setTools(new HashSet<Tool>());
            tool.setToolType(toolType);
        }

        return tool;
    }
}
