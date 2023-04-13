package movie.dto;

import lombok.Data;

@Data
public class CanReservationDateDto {
	private String canReservationDate;
	private int movieIdx;
	private int dateIdx;
}
