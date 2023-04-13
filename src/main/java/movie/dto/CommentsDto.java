package movie.dto;

import lombok.Data;

@Data
public class CommentsDto {
	private int commentsIdx;
	private String writer;
	private String contents;
	private int star;
	private int movieIdx;
}
