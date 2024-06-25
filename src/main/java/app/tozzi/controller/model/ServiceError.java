package app.tozzi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceError {

    private Date date;
    private int status;
    private String error;
    private String message;
    private String path;

}
