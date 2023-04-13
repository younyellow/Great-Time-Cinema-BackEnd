package movie.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import movie.dto.AnnouncementDto;
import movie.dto.CanReservationDateDto;
import movie.dto.CommentsDto;
import movie.dto.MovieDto;
import movie.dto.ReservationDto;
import movie.dto.ReviewDto;
import movie.dto.SeatDto;
import movie.dto.SeatIdxDto;
import movie.dto.SeatTypeDto;


public interface MovieService {

	List<MovieDto> listMovie() throws Exception;

	int insertReservation(ReservationDto reservationDto) throws Exception;

	List<SeatIdxDto> listSeat(SeatTypeDto seatTypeDto)throws Exception;

	List<CanReservationDateDto> date(int movieIdx)throws Exception;

	SeatTypeDto seatType(int dateIdx)throws Exception;

	List<AnnouncementDto> listAnnouncementDto()throws Exception;

	AnnouncementDto announcementdetail(int announcementIdx)throws Exception;

	// 한줄평 insert 메서드
	int insertComments(CommentsDto commentsDto) throws Exception;

	// 한줄평 조회
	public List<CommentsDto> selectCommentsList(int movieIdx) throws Exception;

	// 영화 정보 뽑아오는 메서드
	MovieDto selectMovieInfo(int movieIdx) throws Exception;

	// 평균 평점 반영
	public void setStarAvg(int movieIdx) throws Exception;

	// 별점 평균 뽑아오는 메서드
	CommentsDto selectStarAvg(int movieIdx) throws Exception;
	//리뷰 작성 insert 서비스 메서드
	public int insertReview(ReviewDto reviewDto) throws Exception;
	//리뷰 리스트 조회 메서드
	public List<ReviewDto> selectReviewList() throws Exception;
	//리뷰 상세 조회 메서드
	public ReviewDto selectReviewDetail(int reviewIdx) throws Exception;
	//리뷰 삭제 메서드
	public int deleteReview(int reviewIdx) throws Exception;
	//리뷰 수정 메서드
	public int updateReview(ReviewDto reviewDto) throws Exception;
	// 영화 제목 뽑아오는 메서드
	public MovieDto selectMovieTitle(int movieIdx) throws Exception;
	// 예약 좌석 불러오기
	List<SeatDto> seatList(int reservationIdx)throws Exception;

	ReservationDto reservationDetail(int reservationIdx) throws Exception;
	//좌석 넣는거 
	void insertSeat(SeatDto  seatDto)throws Exception;

	List<ReservationDto> reservationIdx(String reservationDate)throws Exception;
	List<ReservationDto> reservationList(String userId) throws Exception;
	List<SeatDto> seatListDate(String reservationDate)throws Exception;



	int deleteMoive(MovieDto movieDto)throws Exception;

	int inserttime(CanReservationDateDto canReservationDateDto)throws Exception;

	int insertannouncement(AnnouncementDto announcementDto)throws Exception;

	int insertMovie(MovieDto movieDto);





	
}
