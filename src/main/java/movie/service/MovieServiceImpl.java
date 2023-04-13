package movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import movie.mapper.MovieMapper;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieMapper movieMapper;

	@Override
	public List<MovieDto> listMovie() throws Exception {
		return movieMapper.listMovie();
	}

	@Override
	public int insertReservation(ReservationDto reservationDto) throws Exception {
		return movieMapper.insertReservation(reservationDto);
	}

	@Override
	public List<SeatIdxDto> listSeat(SeatTypeDto seatTypeDto) throws Exception {
		return movieMapper.listSeat(seatTypeDto);
	}

	@Override
	public List<CanReservationDateDto> date(int movieIdx) throws Exception {

		return movieMapper.date(movieIdx);
	}

	@Override
	public SeatTypeDto seatType(int dateIdx) throws Exception {

		return movieMapper.seatType(dateIdx);
	}

	@Override
	public List<AnnouncementDto> listAnnouncementDto() throws Exception {

		return movieMapper.listAnnouncementDto();
	}

	@Override
	public AnnouncementDto announcementdetail(int announcementIdx) throws Exception {

		return movieMapper.announcementdetail(announcementIdx);
	}

	// 한줄평 입력 메서드
	@Override
	public int insertComments(CommentsDto commentsDto) throws Exception {
		return movieMapper.insertComments(commentsDto);
	}

	// 한줄평 리스트 조회 메서드
	@Override
	public List<CommentsDto> selectCommentsList(int movieIdx) throws Exception {
		return movieMapper.selectCommentsList(movieIdx);
	}

	// 영화 idx로 영화 정보(타이틀, 줄거리 등) 뽑는 메서드
	@Override
	public MovieDto selectMovieInfo(int movieIdx) throws Exception {
		return movieMapper.selectMovieInfo(movieIdx);
	}

	// 별점 평균 세팅하는 메서드
	@Override
	public void setStarAvg(int movieIdx) throws Exception {
		Double starAvg = movieMapper.getStarAvg(movieIdx);
		if (starAvg == null) {
			starAvg = 0.0;

		}

		MovieDto movieDto = new MovieDto();
		movieDto.setMovieIdx(movieIdx);
		movieDto.setStarAvg(starAvg);
		movieMapper.updateStarAvg(movieDto);

	}

	// 별점 평균 뽑아오는 메서드
	@Override
	public CommentsDto selectStarAvg(int movieIdx) throws Exception {
		return movieMapper.selectStarAvg(movieIdx);
	}

	// 리뷰 작성 insert 메서드
	@Override
	public int insertReview(ReviewDto reviewDto) throws Exception {
		return movieMapper.insertReview(reviewDto);
	}

	// 리뷰 리스트 조회 메서드
	@Override
	public List<ReviewDto> selectReviewList() throws Exception {
		return movieMapper.selectReviewList();
	}

	// 리뷰 상세 조회 + 조회수 증가 메서드
	@Override
	public ReviewDto selectReviewDetail(int reviewIdx) throws Exception {
		movieMapper.updateCount(reviewIdx);
		return movieMapper.selectReviewDetail(reviewIdx);
	}

	// 리뷰 삭제 메서드
	@Override
	public int deleteReview(int reviewIdx) throws Exception {
		return movieMapper.deleteReview(reviewIdx);
	}

	// 리뷰 수정 메서드
	@Override
	public int updateReview(ReviewDto reviewDto) throws Exception {
		return movieMapper.updateReview(reviewDto);
	}

	// 영화 제목 뽑아오는 메서드
	@Override
	public MovieDto selectMovieTitle(int movieIdx) throws Exception {
		return movieMapper.selectMovieTitle(movieIdx);
	}


	@Override
	public ReservationDto reservationDetail(int reservationIdx) throws Exception {

		return movieMapper.reservationDetail(reservationIdx);
	}

	@Override
	public void insertSeat(SeatDto seatDto) throws Exception {
		movieMapper.insertSeat(seatDto);
	}

	@Override
	public 	List<ReservationDto> reservationIdx(String reservationDate) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.reservationIdx(reservationDate);
	}


	@Override
	public List<SeatDto> seatList(int reservationIdx) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.seatList(reservationIdx);
	}

	@Override
	public List<SeatDto> seatListDate(String reservationDate) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.seatListDate(reservationDate);
	}

	@Override
	public int insertMovie(MovieDto movieDto) {

		return movieMapper.insertMovie(movieDto);
	}

	@Override
	public List<ReservationDto> reservationList(String userId) throws Exception {
	
		return movieMapper.reservationList(userId);
	}

	@Override
	public int deleteMoive(MovieDto movieDto) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.deleteMovie(movieDto);
	}

	@Override
	public int inserttime(CanReservationDateDto canReservationDateDto) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.inserttime(canReservationDateDto);
	}

	@Override
	public int insertannouncement(AnnouncementDto announcementDto) throws Exception {
		// TODO Auto-generated method stub
		return movieMapper.insertannouncement(announcementDto);
	}



	

}
