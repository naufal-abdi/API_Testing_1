package response_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("department")
    private String department;
}