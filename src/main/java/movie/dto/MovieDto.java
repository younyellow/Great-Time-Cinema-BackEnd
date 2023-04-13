package movie.dto;

import lombok.Data;

@Data
public class MovieDto {
	private int movieIdx ;
	private String title; 
    private String story;
    private String opening; 
    private String poster;
    private boolean deleted;
    private Double starAvg;
}
