package movie.dto;

import lombok.Data;

@Data
public class ReservationDto {
	private int reservationIdx;
	private String reservationName;
	private String reservationDate;
	private int movieIdx;
	private String reservationNumber;
	private String reservationId;
}
