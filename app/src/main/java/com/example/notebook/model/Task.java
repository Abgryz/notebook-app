package com.example.notebook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Task {
    private int id;
    private String task;
    private boolean isCompleted;
}
