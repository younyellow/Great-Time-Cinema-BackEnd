package movie.dto;

import lombok.Data;

@Data
public class ReviewDto {
	private int reviewIdx;
	private String title;
	private int count;
	private String contents;
	private String writer;
	private String reviewDate;
	private int movieIdx;
	private String writerId;

}
