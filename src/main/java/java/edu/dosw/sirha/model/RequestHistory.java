package java.edu.dosw.sirha.model;

import java.util.*;
import java.edu.dosw.sirha.model.*;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


public class RequestHistory {
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    
    private ArrayList<Request> request;
}