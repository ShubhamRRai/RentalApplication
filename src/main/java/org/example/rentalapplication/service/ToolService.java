package org.example.rentalapplication.service;

import org.example.rentalapplication.dto.ToolDTO;

import java.util.List;

public interface ToolService {

    ToolDTO getByToolId(long id);
    List<ToolDTO> getAllTool();
    ToolDTO addTool(ToolDTO tool);
    ToolDTO updateTool(ToolDTO tool, long id);
    void deleteTool(long id);
}
