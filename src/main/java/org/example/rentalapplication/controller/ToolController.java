package org.example.rentalapplication.controller;

import org.example.rentalapplication.dto.ToolDTO;
import org.example.rentalapplication.service.impl.ToolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {
    final ToolServiceImpl toolService;

    public ToolController(ToolServiceImpl toolService) {
        this.toolService = toolService;
    }

    @PostMapping("/")
    public ResponseEntity<ToolDTO> addTool(@RequestBody ToolDTO toolDTO) {
        ToolDTO tool = toolService.addTool(toolDTO);
        return new ResponseEntity<>(tool, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolDTO> updateTool(@RequestBody ToolDTO toolDTO, @PathVariable long id) {
        ToolDTO updateTool = toolService.updateTool(toolDTO, id);
        return new ResponseEntity<>(updateTool, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> getTool(@PathVariable long id) {
        ToolDTO toolDTO = toolService.getByToolId(id);
        return new ResponseEntity<>(toolDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ToolDTO>> getAllTool() {
        List<ToolDTO> toolDTOList = toolService.getAllTool();
        return new ResponseEntity<>(toolDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
