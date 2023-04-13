package movie.dto;

import lombok.Data;

@Data
public class AnnouncementDto {
	private int announcementIdx;
	private String title;
	private String contents;
	private String announcementDate;
}
