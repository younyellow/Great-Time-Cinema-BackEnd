package movie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import movie.dto.AnnouncementDto;
import movie.dto.CanReservationDateDto;
import movie.dto.CommentsDto;
import movie.dto.MovieDto;
import movie.dto.ReservationDto;
import movie.dto.ReviewDto;
import movie.dto.SeatDto;
import movie.dto.SeatIdxDto;
import movie.dto.SeatTypeDto;
@Mapper
public interface MovieMapper {
	public int insertReview(ReviewDto reviewDto) throws Exception;
	List<MovieDto> listMovie() throws Exception;
	public int insertReservation(ReservationDto reservationDto) throws Exception;
	public List<SeatIdxDto> listSeat(SeatTypeDto seatTypeDto)throws Exception;
	public List<CanReservationDateDto> date(int movieIdx)throws Exception;
	public SeatTypeDto seatType(int dateIdx)throws Exception;
	public List<AnnouncementDto> listAnnouncementDto()throws Exception;
	public AnnouncementDto announcementdetail(int announcementIdx)throws Exception;
	
	// 한줄 평 조회
	List<CommentsDto> selectCommentsList(int movieIdx) throws Exception;

	// 영화 정보 뽑아오는 메서드
	MovieDto selectMovieInfo(int movieIdx) throws Exception;
	
	//별점 평균 
	public Double getStarAvg(int starAvg) throws Exception;
	

	// 별점 평균 뽑아오는 메서드
	CommentsDto selectStarAvg(int movieIdx) throws Exception;
	

	// 리뷰 리스트 조회 메서드
	public List<ReviewDto> selectReviewList() throws Exception;

	// 조회수 증가 메서드
	public void updateCount(int reviewIdx);

	// 리뷰 상세 조회 메서드
	public ReviewDto selectReviewDetail(int reviewIdx);

	// 리뷰 삭제 메서드
	public int deleteReview(int reviewIdx) throws Exception;

	// 리뷰 수정 메서드
	public int updateReview(ReviewDto reviewDto) throws Exception;

	// 영화 제목 뽑아오는 메서드
	public MovieDto selectMovieTitle(int movieIdx) throws Exception;
	// 한줄평 등록
	public int insertComments(CommentsDto commentsDto)throws Exception;
	public void updateStarAvg(MovieDto movieDto)throws Exception;

	public ReservationDto reservationDetail(int reservationIdx)throws Exception;
	void insertSeat(SeatDto seatDto )throws Exception;
	public List<ReservationDto>reservationIdx(String reservationDate)throws Exception;
	public List<SeatDto> seatList(int reservationIdx)throws Exception;
	public List<SeatDto> seatListDate(String reservationDate)throws Exception;
	public int insertMovie(MovieDto movieDto);
	public List<ReservationDto> reservationList(String userId) throws Exception;
	public int deleteMovie(MovieDto movieDto)throws Exception;
	public int inserttime(CanReservationDateDto canReservationDateDto)throws Exception;
	public int insertannouncement(AnnouncementDto announcementDto)throws Exception;
	

}
