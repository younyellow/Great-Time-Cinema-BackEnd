package movie.dto;

import lombok.Data;

@Data
public class SeatDto {
	private String reservedSeat;
	private int reservationIdx;
	private String [] registSeat;
}
